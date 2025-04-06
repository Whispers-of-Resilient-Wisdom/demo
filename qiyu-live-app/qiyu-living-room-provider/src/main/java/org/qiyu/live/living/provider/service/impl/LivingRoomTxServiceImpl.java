package org.qiyu.live.living.provider.service.impl;

import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import com.qiyu.live.common.interfaces.Enum.CommonStatusEnum;
import jakarta.annotation.Resource;

import org.qiyu.live.framework.redis.starter.key.LivingProviderCacheKeyBuilder;
import org.qiyu.live.living.interfaces.dto.LivingRoomReqDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomRespDTO;
import org.qiyu.live.living.provider.dao.mapper.LivingRoomMapper;
import org.qiyu.live.living.provider.dao.mapper.LivingRoomRecordMapper;
import org.qiyu.live.living.provider.dao.po.LivingRoomRecordPO;
import org.qiyu.live.living.provider.service.ILivingRoomService;
import org.qiyu.live.living.provider.service.ILivingRoomTxService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
/**
 * LivingRoomTxServiceImpl 是直播间业务逻辑的实现类，提供关闭直播间的功能。
 * 使用 @Service 注解声明为 Spring 的服务组件。
 */
@Service
public class LivingRoomTxServiceImpl implements ILivingRoomTxService {

    // 注入直播间服务，提供查询直播间信息的能力
    @Resource
    private ILivingRoomService livingRoomService;

    // 注入 Redis 模板，用于操作缓存
    @Resource
    private RedisTemplate <String,Object>redisTemplate;

    // 注入直播记录数据访问层，用于持久化历史记录
    @Resource
    private LivingRoomRecordMapper livingRoomRecordMapper;

    // 注入直播间数据访问层，用于操作直播间数据
    @Resource
    private LivingRoomMapper livingRoomMapper;

    // 注入缓存键构建器，用于生成 Redis 缓存键
    @Resource
    private LivingProviderCacheKeyBuilder cacheKeyBuilder;

    /**
     * 关闭直播间的方法，使用事务管理，确保所有数据库操作要么全部成功，要么全部回滚。
     *
     * @param livingRoomReqDTO 关闭直播间的请求参数对象
     * @return 关闭成功返回 true，失败返回 false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)  // 遇到任何 Exception 都会触发事务回滚
    public boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO) {
        // 根据房间 ID 查询直播间信息
        LivingRoomRespDTO livingRoomRespDTO = livingRoomService.queryByRoomId(livingRoomReqDTO.getRoomId());

        // 如果直播间不存在，返回 false
        if (livingRoomRespDTO == null) {
            return false;
        }

        // 校验主播 ID 是否匹配，防止非本人操作
        if (!(livingRoomRespDTO.getAnchorId().equals(livingRoomReqDTO.getAnchorId()))) {
            return false;
        }

        // 转换直播间信息为历史记录对象，并设置结束时间和状态
        LivingRoomRecordPO livingRoomRecordPO = ConvertBeanUtils.convert(livingRoomRespDTO, LivingRoomRecordPO.class);
        livingRoomRecordPO.setEndTime(new Date());
        livingRoomRecordPO.setStatus(CommonStatusEnum.INVALID_STATUS.getCode());

        // 插入一条直播记录，用于存档
        livingRoomRecordMapper.insert(livingRoomRecordPO);

        // 删除直播间记录，实现逻辑关闭
        livingRoomMapper.deleteById(livingRoomRecordPO.getId());

        // 从缓存中移除直播间数据，避免脏数据存在
        String cacheKey = cacheKeyBuilder.buildLivingRoomObj(livingRoomReqDTO.getRoomId());
        redisTemplate.delete(cacheKey);

        return true;  // 关闭成功
    }
}
