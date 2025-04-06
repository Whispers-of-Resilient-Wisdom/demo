
package org.qiyu.live.api.service.impl;

import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import com.qiyu.live.common.interfaces.dto.PageWrapper;
import com.qiyu.live.common.interfaces.utils.IsEmptyUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.qiyu.live.api.error.ApiErrorEnum;
import org.qiyu.live.api.service.ILivingRoomService;
import org.qiyu.live.api.vo.LivingRoomInitVO;
import org.qiyu.live.api.vo.req.LivingRoomReqVO;
import org.qiyu.live.api.vo.req.OnlinePkReqVO;
import org.qiyu.live.api.vo.resp.LivingRoomPageRespVO;
import org.qiyu.live.api.vo.resp.LivingRoomRespVO;
import org.qiyu.live.api.vo.resp.RedPacketReceiveVO;
import org.qiyu.live.gift.dto.RedPacketConfigReqDTO;
import org.qiyu.live.gift.dto.RedPacketConfigRespDTO;
import org.qiyu.live.gift.dto.RedPacketReceiveDTO;
import org.qiyu.live.gift.interfaces.IRedPacketConfigRpc;
import org.qiyu.live.im.enmu.AppIdEnum;
import org.qiyu.live.living.interfaces.dto.LivingPkRespDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomReqDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomRespDTO;
import org.qiyu.live.living.interfaces.rpc.ILivingRoomRpc;
import org.qiyu.live.user.dto.UserDTO;
import org.qiyu.live.user.interfaces.IUserRpc;
import org.qiyu.live.web.starter.context.QiyuRequestContext;
import org.qiyu.live.web.starter.error.BizBaseErrorEnum;
import org.qiyu.live.web.starter.error.ErrorAssert;
import org.qiyu.live.web.starter.error.QiyuErrorException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * LivingRoomServiceImpl 负责处理与直播间相关的业务逻辑
 * 实现了 ILivingRoomService 接口
 * 提供了获取直播间列表、开启直播间、在线PK、关闭直播间、配置主播信息等功能
 */
@Service
public class LivingRoomServiceImpl implements ILivingRoomService {

    // 引用用户服务接口
    @DubboReference
    private IUserRpc userRpc;

    // 引用直播间服务接口
    @DubboReference
    private ILivingRoomRpc livingRoomRpc;

    @DubboReference
    private IRedPacketConfigRpc redPacketConfigRpc;
    /**
     * 获取直播间列表
     *
     * @param livingRoomReqVO 请求参数，包含查询条件
     * @return 返回分页后的直播间信息
     */
    @Override
    public LivingRoomPageRespVO list(LivingRoomReqVO livingRoomReqVO) {
        // 调用服务获取分页后的直播间数据
        PageWrapper<LivingRoomRespDTO> resultPage = livingRoomRpc.list(ConvertBeanUtils.convert(livingRoomReqVO,LivingRoomReqDTO.class));

        // 转换数据并封装成响应对象
        LivingRoomPageRespVO livingRoomPageRespVO = new LivingRoomPageRespVO();
        livingRoomPageRespVO.setList(ConvertBeanUtils.convertList(resultPage.getList(), LivingRoomRespVO.class));
        livingRoomPageRespVO.setHasNext(resultPage.isHasNext());  // 是否还有下一页

        return livingRoomPageRespVO;
    }

    /**
     * 开始一个新的直播
     *
     * @param type 直播间类型
     * @return 返回直播间创建成功的状态
     */
    @Override
    public Integer startingLiving(Integer type) {
        // 获取当前用户ID
        Long userId = QiyuRequestContext.getUserId();

        // 获取用户信息
        UserDTO userDTO = userRpc.getUserById(userId);

        // 构建直播间请求参数
        LivingRoomReqDTO livingRoomReqDTO = new LivingRoomReqDTO();
        livingRoomReqDTO.setAnchorId(userId);  // 设置主播ID
        livingRoomReqDTO.setRoomName("主播-" + QiyuRequestContext.getUserId() + "的直播间");  // 设置直播间名称
        livingRoomReqDTO.setCovertImg(userDTO.getAvatar());  // 设置主播头像
        livingRoomReqDTO.setType(type);  // 设置直播间类型

        // 调用服务开始直播
        return livingRoomRpc.startLivingRoom(livingRoomReqDTO);
    }

    /**
     * 开启在线PK
     *
     * @param onlinePkReqVO 请求参数，包含在线PK所需的各种信息
     * @return 返回PK成功状态
     */
    @Override
    public boolean onlinePk(OnlinePkReqVO onlinePkReqVO) {
        // 转换请求参数
        LivingRoomReqDTO reqDTO = ConvertBeanUtils.convert(onlinePkReqVO,LivingRoomReqDTO.class);

        // 设置AppId和PK对象ID（当前用户）
        reqDTO.setAppId(AppIdEnum.QIYU_LIVE_BIZ.getCode());
        reqDTO.setPkObjId(QiyuRequestContext.getUserId());

        // 调用服务检查在线PK状态
        LivingPkRespDTO tryOnlineStatus = livingRoomRpc.onlinePk(reqDTO);

        // 验证在线PK状态
        ErrorAssert.isTure(tryOnlineStatus.isOnlineStatus(), new QiyuErrorException(-1,tryOnlineStatus.getMsg()));

        return true;
    }

    /**
     * 关闭直播间
     *
     * @param roomId 房间号
     * @return 返回直播间关闭是否成功
     */
    @Override
    public boolean closeLiving(Integer roomId) {
        // 构建直播间请求参数
        LivingRoomReqDTO livingRoomReqDTO = new LivingRoomReqDTO();
        livingRoomReqDTO.setRoomId(roomId);  // 设置房间号
        livingRoomReqDTO.setAnchorId(QiyuRequestContext.getUserId());  // 设置主播ID

        // 调用服务关闭直播间
        return livingRoomRpc.closeLiving(livingRoomReqDTO);
    }

    /**
     * 配置主播信息
     *
     * @param userId 主播ID
     * @param roomId 房间ID
     * @return 返回直播间初始化信息
     */
    @Override
    public LivingRoomInitVO anchorConfig(Long userId, Integer roomId) {
        // 根据房间ID查询直播间信息
        LivingRoomRespDTO respDTO = livingRoomRpc.queryByRoomId(roomId);

        // 如果直播间不存在，抛出错误
        ErrorAssert.isNotNull(respDTO, ApiErrorEnum.LIVING_ROOM_END);

        // 批量查询用户信息
        Map<Long, UserDTO> userDTOMap = userRpc.batchQuery(Stream.of(respDTO.getAnchorId(), userId).distinct().collect(Collectors.toList()));

        // 获取主播和观看者信息
        UserDTO anchor = userDTOMap.get(respDTO.getAnchorId());
        UserDTO watcher = userDTOMap.get(userId);

        // 构建直播间初始化响应对象
        LivingRoomInitVO respVO = new LivingRoomInitVO();
        respVO.setAnchorNickName(anchor.getNickName());  // 设置主播昵称
        respVO.setWatcherNickName(watcher.getNickName());  // 设置观看者昵称
        respVO.setUserId(userId);  // 设置用户ID
        respVO.setAvatar(IsEmptyUtils.isEmpty(anchor.getAvatar()) ? "https://s1.ax1x.com/2022/12/18/zb6q6f.png" : anchor.getAvatar());  // 设置头像，若为空则使用默认头像
        respVO.setWatcherAvatar(watcher.getAvatar());  // 设置观看者头像

        // 如果直播间不存在，则设置无效房间ID
        if (respDTO.getAnchorId() == null || userId == null) {
            respVO.setRoomId(-1);
            return respVO;
        }
        boolean isAnchor=respDTO.getAnchorId().equals(userId);
        respVO.setRoomId(respDTO.getId());  // 设置房间ID
        respVO.setAnchorId(respDTO.getAnchorId());  // 设置主播ID
        respVO.setAnchor(respDTO.getAnchorId().equals(userId));  // 判断当前用户是否为主播
        // 如果是主播，查看有无红包雨权限
        if (respVO.isAnchor()) {
            RedPacketConfigRespDTO redPacketConfigRespDTO = redPacketConfigRpc.queryByAnchorId(userId);
            if (redPacketConfigRespDTO != null) {
                respVO.setRedPacketConfigCode(redPacketConfigRespDTO.getConfigCode());
            }
        }
        // 设置默认背景图片
        respVO.setDefaultBgImg("https://img.touxiangkong.com/uploads/allimg/2023012023/3ygukui4snl.jpg");

        return respVO;
    }

    @Override
    public Boolean prepareRedPacket(Long userId, Integer roomId) {
        LivingRoomRespDTO livingRoomRespDTO = livingRoomRpc.queryByRoomId(roomId);
        ErrorAssert.isNotNull(livingRoomRespDTO, BizBaseErrorEnum.PARAM_ERROR);
        ErrorAssert.isTure(userId.equals(livingRoomRespDTO.getAnchorId()), BizBaseErrorEnum.PARAM_ERROR);
        return redPacketConfigRpc.prepareRedPacket(userId);
    }

    @Override
    public Boolean startRedPacket(Long userId, String code) {
        RedPacketConfigReqDTO reqDTO = new RedPacketConfigReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setRedPacketConfigCode(code);
        LivingRoomRespDTO livingRoomRespDTO = livingRoomRpc.queryByAnchorId(userId);
        ErrorAssert.isNotNull(livingRoomRespDTO, BizBaseErrorEnum.PARAM_ERROR);
        reqDTO.setRoomId(livingRoomRespDTO.getId());
        return redPacketConfigRpc.startRedPacket(reqDTO);
    }

    @Override
    public RedPacketReceiveVO getRedPacket(Long userId, String code) {
        RedPacketConfigReqDTO reqDTO = new RedPacketConfigReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setRedPacketConfigCode(code);
        RedPacketReceiveDTO receiveDTO = redPacketConfigRpc.receiveRedPacket(reqDTO);
        RedPacketReceiveVO respVO = new RedPacketReceiveVO();
        if (receiveDTO == null) {
            respVO.setMsg("红包已派发完毕");
        } else {
            respVO.setPrice(receiveDTO.getPrice());
            respVO.setMsg(receiveDTO.getNotifyMsg());
        }
        return respVO;
    }
}