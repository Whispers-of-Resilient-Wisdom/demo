package org.qiyu.live.api.service;


import org.qiyu.live.api.vo.LivingRoomInitVO;
import org.qiyu.live.api.vo.req.LivingRoomReqVO;
import org.qiyu.live.api.vo.req.OnlinePkReqVO;
import org.qiyu.live.api.vo.resp.LivingRoomPageRespVO;
import org.qiyu.live.api.vo.resp.RedPacketReceiveVO;

/**
 * ILivingRoomService 定义了与直播间相关的服务接口
 * 包含展示直播间列表、开启直播间、处理连线请求、关闭直播间等方法
 */
public interface ILivingRoomService {

    /**
     * 展示直播间列表
     *
     * @param livingRoomReqVO 包含查询直播间所需参数的请求对象
     * @return 返回包含直播间列表及分页信息的 LivingRoomPageRespVO 对象
     */
    LivingRoomPageRespVO list(LivingRoomReqVO livingRoomReqVO);

    /**
     * 开启一个新的直播间
     *
     * @param type 直播间类型，用于指定直播间的性质（如公开或私密）
     * @return 返回开启直播间后的状态标识（如直播间ID）
     */
    Integer startingLiving(Integer type);

    /**
     * 用户在PK直播间中发起连线请求
     *
     * @param onlinePkReqVO 包含连线请求相关信息的对象
     * @return 返回是否成功发起连线请求，若成功则返回 true，否则返回 false
     */
    boolean onlinePk(OnlinePkReqVO onlinePkReqVO);

    /**
     * 关闭指定的直播间
     *
     * @param roomId 直播间ID，用于标识要关闭的直播间
     * @return 返回关闭直播间的操作结果，成功返回 true，失败返回 false
     */
    boolean closeLiving(Integer roomId);

    /**
     * 根据用户ID返回当前直播间的相关信息
     *
     * @param userId 用户ID，用于获取该用户的直播间信息
     * @param roomId 直播间ID，用于获取指定直播间的详细信息
     * @return 返回包含直播间信息的 LivingRoomInitVO 对象
     */
    LivingRoomInitVO anchorConfig(Long userId, Integer roomId);

    /**
     * 主播点击开始准备红包雨金额
     */
    Boolean prepareRedPacket(Long userId, Integer roomId);

    /**
     * 主播开始红包雨
     */
    Boolean startRedPacket(Long userId, String code);

    /**
     * 根据红包雨code领取红包
     */
    RedPacketReceiveVO getRedPacket(Long userId, String redPacketConfigCode);
}
