package org.qiyu.live.living.provider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import com.qiyu.live.common.interfaces.Enum.CommonStatusEnum;
import com.qiyu.live.common.interfaces.dto.PageWrapper;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.qiyu.live.framework.redis.starter.key.LivingProviderCacheKeyBuilder;
import org.qiyu.live.im.DTO.ImMsgBody;
import org.qiyu.live.im.core.server.interfaces.dto.ImOfflineDTO;
import org.qiyu.live.im.core.server.interfaces.dto.ImOnlineDTO;
import org.qiyu.live.im.enmu.AppIdEnum;
import org.qiyu.live.im.router.interfaces.constants.ImMsgBizCodeEnum;
import org.qiyu.live.im.router.interfaces.rpc.ImRouterRpc;
import org.qiyu.live.living.interfaces.dto.LivingPkRespDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomReqDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomRespDTO;
import org.qiyu.live.living.provider.dao.mapper.LivingRoomMapper;
import org.qiyu.live.living.provider.dao.po.LivingRoomPO;
import org.qiyu.live.living.provider.service.ILivingRoomService;
import org.qiyu.live.living.provider.service.ILivingRoomTxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
/**
 * LivingRoomServiceImpl 是直播间的核心业务实现类，提供直播间的各种操作。
 * 包括用户上下线管理、直播间状态管理、PK管理等。
 */
@Service
public class LivingRoomServiceImpl implements ILivingRoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LivingRoomServiceImpl.class);

    // 注入数据库操作对象，用于操作直播间数据
    @Resource
    private LivingRoomMapper livingRoomMapper;

    // 注入数据库操作对象，用于操作直播间历史记录
    //    @Resource
    //    private LivingRoomRecordMapper livingRoomRecordMapper;

    // 注入 Redis 模板，用于操作缓存
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 注入缓存键构建器，用于生成 Redis 缓存键
    @Resource
    private LivingProviderCacheKeyBuilder cacheKeyBuilder;

    // 注入事务管理的直播间服务，处理需要事务的操作
    @Resource
    private ILivingRoomTxService livingRoomTxService;

    // 注入 Dubbo 的远程调用接口，用于发送 IM 消息
    @DubboReference
    private ImRouterRpc imRouterRpc;

    /**
     * 根据房间 ID 查询用户 ID 列表
     */
    @Override
    public List<Long> queryUserIdByRoomId(LivingRoomReqDTO livingRoomReqDTO) {
        Integer roomId = livingRoomReqDTO.getRoomId();
        Integer appId = livingRoomReqDTO.getAppId();
        String cacheKey = cacheKeyBuilder.buildLivingRoomUserSet(roomId, appId);
        // 使用 Redis Scan 逐步查询直播间的用户 ID 列表
        Cursor<Object> cursor = redisTemplate.opsForSet().scan(cacheKey, ScanOptions.scanOptions().match("*").count(100).build());
        List<Long> userIdList = new ArrayList<>();
        while (cursor.hasNext()) {
            Integer userId = (Integer) cursor.next();
            userIdList.add(Long.valueOf(userId));
        }
        return userIdList;
    }

    /**
     * 用户下线事件处理
     */
    @Override
    public void userOfflineHandler(ImOfflineDTO imOfflineDTO) {
        LOGGER.info("offline handler, imOfflineDTO is {}", imOfflineDTO);
        Long userId = imOfflineDTO.getUserId();
        Integer roomId = imOfflineDTO.getRoomId();
        Integer appId = imOfflineDTO.getAppId();
        String cacheKey = cacheKeyBuilder.buildLivingRoomUserSet(roomId, appId);
        // 将用户从直播间的在线用户集合中移除
        redisTemplate.opsForSet().remove(cacheKey, userId);

        // 监听 PK 主播下线事件，处理 PK 逻辑
        LivingRoomReqDTO roomReqDTO = new LivingRoomReqDTO();
        roomReqDTO.setRoomId(imOfflineDTO.getRoomId());
        roomReqDTO.setPkObjId(imOfflineDTO.getUserId());
        roomReqDTO.setAnchorId(imOfflineDTO.getUserId());
        this.offlinePk(roomReqDTO);

        // 当主播断开 IM 服务器时，自动关闭直播间
        livingRoomTxService.closeLiving(roomReqDTO);
    }

    /**
     * 用户上线事件处理
     */
    @Override
    public void userOnlineHandler(ImOnlineDTO imOnlineDTO) {
        LOGGER.info("online handler, imOnlineDTO is {}", imOnlineDTO);
        Long userId = imOnlineDTO.getUserId();
        Integer roomId = imOnlineDTO.getRoomId();
        Integer appId = imOnlineDTO.getAppId();
        String cacheKey = cacheKeyBuilder.buildLivingRoomUserSet(roomId, appId);
        // 将用户加入在线集合，并设置缓存过期时间
        redisTemplate.opsForSet().add(cacheKey, userId);
        redisTemplate.expire(cacheKey, 12, TimeUnit.HOURS);
    }

    /**
     * 查询所有直播间（从数据库中查）
     */
    @Override
    public List<LivingRoomRespDTO> listAllLivingRoomFromDB(Integer type) {
        LambdaQueryWrapper<LivingRoomPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LivingRoomPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
        queryWrapper.eq(LivingRoomPO::getType, type);
        queryWrapper.orderByDesc(LivingRoomPO::getId);
        queryWrapper.last("limit 1000");
        return ConvertBeanUtils.convertList(livingRoomMapper.selectList(queryWrapper), LivingRoomRespDTO.class);
    }

    /**
     * 分页查询直播间列表（使用缓存）
     */
    @Override
    public PageWrapper<LivingRoomRespDTO> list(LivingRoomReqDTO livingRoomReqDTO) {
        String cacheKey = cacheKeyBuilder.buildLivingRoomList(livingRoomReqDTO.getType());
        int page = livingRoomReqDTO.getPage();
        int pageSize = livingRoomReqDTO.getPageSize();
        long total = redisTemplate.opsForList().size(cacheKey);
        List<Object> resultList = redisTemplate.opsForList().range(cacheKey, (long) (page - 1) * pageSize, ((long) page * pageSize));
        PageWrapper<LivingRoomRespDTO> pageWrapper = new PageWrapper<>();
        if (CollectionUtils.isEmpty(resultList)) {
            pageWrapper.setList(Collections.emptyList());
            pageWrapper.setHasNext(false);
        } else {
            List<LivingRoomRespDTO> livingRoomRespDTOS = ConvertBeanUtils.convertList(resultList, LivingRoomRespDTO.class);
            pageWrapper.setList(livingRoomRespDTOS);
            pageWrapper.setHasNext((long) page * pageSize < total);
        }
        return pageWrapper;
    }

    /**
     * 根据房间 ID 查询直播间信息（优先从缓存中查询）
     */
    @Override
    public LivingRoomRespDTO queryByRoomId(Integer roomId) {
        String cacheKey = cacheKeyBuilder.buildLivingRoomObj(roomId);
        LivingRoomRespDTO queryResult = (LivingRoomRespDTO) redisTemplate.opsForValue().get(cacheKey);
        if (queryResult != null) {
            if (queryResult.getId() == null) {
                return null;
            }
            return queryResult;
        }
        // 缓存未命中，从数据库查询
        LambdaQueryWrapper<LivingRoomPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LivingRoomPO::getId, roomId);
        queryWrapper.eq(LivingRoomPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
        queryWrapper.last("limit 1");
        queryResult = ConvertBeanUtils.convert(livingRoomMapper.selectOne(queryWrapper), LivingRoomRespDTO.class);

        // 防止缓存穿透，设置空值缓存
        if (queryResult == null) {
            redisTemplate.opsForValue().set(cacheKey, new LivingRoomRespDTO(), 1, TimeUnit.MINUTES);
            return null;
        }
        redisTemplate.opsForValue().set(cacheKey, queryResult, 30, TimeUnit.MINUTES);
        return queryResult;
    }

    /**
     * 开启新的直播间
     */
    @Override
    public Integer startLivingRoom(LivingRoomReqDTO livingRoomReqDTO) {
        LivingRoomPO livingRoomPO = ConvertBeanUtils.convert(livingRoomReqDTO, LivingRoomPO.class);
        livingRoomPO.setStatus(CommonStatusEnum.VALID_STATUS.getCode());
        livingRoomPO.setStartTime(new Date());
        livingRoomMapper.insert(livingRoomPO);
        // 清除可能存在的空值缓存
        String cacheKey = cacheKeyBuilder.buildLivingRoomObj(livingRoomPO.getId());
        redisTemplate.delete(cacheKey);
        return livingRoomPO.getId();
    }

    /**
     * 查询在线 PK 用户 ID
     */
    @Override
    public Long queryOnlinePkUserId(Integer roomId) {
        String cacheKey = cacheKeyBuilder.buildLivingOnlinePk(roomId);
        Object userId = redisTemplate.opsForValue().get(cacheKey);
        return userId != null ? (long) (int) userId : null;
    }

    /**
     * 处理PK连线请求
     *
     * @param livingRoomReqDTO 包含房间ID和PK对象ID的请求数据传输对象
     * @return LivingPkRespDTO 返回PK连线结果，包括连线状态和提示信息
     */
    @Override
    public LivingPkRespDTO onlinePk(LivingRoomReqDTO livingRoomReqDTO) {
        // 查询当前房间信息
        LivingRoomRespDTO currentLivingRoom = this.queryByRoomId(livingRoomReqDTO.getRoomId());
        LivingPkRespDTO respDTO = new LivingPkRespDTO();
        respDTO.setOnlineStatus(false);
        LOGGER.info("userId:{},ObjectID:{}",currentLivingRoom.getAnchorId(),livingRoomReqDTO.getPkObjId());
        // 校验：主播不能和自己进行PK
        if (currentLivingRoom.getAnchorId().equals(livingRoomReqDTO.getPkObjId())) {
            respDTO.setMsg("主播不可以连线参与pk");
            return respDTO;
        }

        // 构建PK用户上线的缓存键
        String cacheKey = cacheKeyBuilder.buildLivingOnlinePk(livingRoomReqDTO.getRoomId());

        // 尝试将PK对象ID设置到Redis缓存中，防止并发冲突
        boolean tryOnline = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(cacheKey, livingRoomReqDTO.getPkObjId(), 30, TimeUnit.HOURS));

        if (tryOnline) {
            // 成功上线后，获取房间内所有用户ID列表
            List<Long> userIdList = this.queryUserIdByRoomId(livingRoomReqDTO);

            // 构建通知消息体，通知房间内用户PK开始
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pkObjId", livingRoomReqDTO.getPkObjId());
            jsonObject.put("pkObjAvatar", "https://img.touxiangkong.com/uploads/allimg/2023012023/3ygukui4snl.jpg");

            // 批量发送PK上线通知给房间内所有用户
            batchSendImMsg(userIdList, ImMsgBizCodeEnum.LIVING_ROOM_PK_ONLINE.getCode(), jsonObject);

            respDTO.setMsg("连线成功");
            respDTO.setOnlineStatus(true);
        } else {
            // 如果已经有人在PK，提示用户稍后再试
            respDTO.setMsg("目前有人在线，请稍后再试");
        }
        return respDTO;
    }

    /**
     * 处理PK下线请求
     *
     * @param livingRoomReqDTO 包含房间ID的请求数据传输对象
     * @return boolean 返回下线结果，成功返回true，失败返回false
     */
    @Override
    public boolean offlinePk(LivingRoomReqDTO livingRoomReqDTO) {
        // 根据房间ID删除Redis缓存中的PK用户信息，实现PK下线
        String cacheKey = cacheKeyBuilder.buildLivingOnlinePk(livingRoomReqDTO.getRoomId());
        return Boolean.TRUE.equals(redisTemplate.delete(cacheKey));
    }

    @Override
    public LivingRoomRespDTO queryByAnchorId(Long anchorId) {
        LambdaQueryWrapper<LivingRoomPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LivingRoomPO::getAnchorId, anchorId);
        queryWrapper.eq(LivingRoomPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
        queryWrapper.last("limit 1");
        return ConvertBeanUtils.convert(livingRoomMapper.selectOne(queryWrapper), LivingRoomRespDTO.class);
    }

    /**
     * 批量发送IM消息，通知房间内所有用户
     *
     * @param userIdList 用户ID列表
     * @param bizCode    业务编码（用于区分不同的IM消息类型）
     * @param jsonObject 消息内容（包含PK对象ID和头像等信息）
     */
    private void batchSendImMsg(List<Long> userIdList, int bizCode, JSONObject jsonObject) {
        // 构建IM消息体列表
        List<ImMsgBody> imMsgBodies = userIdList.stream().map(userId -> {
            ImMsgBody imMsgBody = new ImMsgBody();
            imMsgBody.setAppId(AppIdEnum.QIYU_LIVE_BIZ.getCode());
            imMsgBody.setBizCode(bizCode);
            imMsgBody.setUserId(userId);
            imMsgBody.setData(jsonObject.toJSONString());
            return imMsgBody;
        }).collect(Collectors.toList());

        // 通过IM路由服务批量发送消息
        imRouterRpc.batchSendMsg(imMsgBodies);
    }
}
