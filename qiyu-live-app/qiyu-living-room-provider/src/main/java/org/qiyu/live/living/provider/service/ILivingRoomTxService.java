package org.qiyu.live.living.provider.service;

import org.qiyu.live.living.interfaces.dto.LivingRoomReqDTO;
/**
 * 直播间事务服务接口，定义涉及直播间的事务性操作。
 */
public interface ILivingRoomTxService {

    /**
     * 关闭直播间。
     * 执行该操作后，直播间将被关闭，相关资源和状态会被清理。
     *
     * @param livingRoomReqDTO 请求参数，包含直播间 ID 和其他必要信息
     * @return boolean 关闭结果，成功返回 true，失败返回 false
     */
    boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO);

}
