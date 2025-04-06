package org.qiyu.live.living.provider.rpc;

import com.qiyu.live.common.interfaces.dto.PageWrapper;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import org.qiyu.live.living.interfaces.dto.LivingPkRespDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomReqDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomRespDTO;
import org.qiyu.live.living.interfaces.rpc.ILivingRoomRpc;
import org.qiyu.live.living.provider.service.ILivingRoomService;
import org.qiyu.live.living.provider.service.ILivingRoomTxService;

import java.util.List;
/**
 * 直播间远程过程调用（RPC）实现类。
 * 提供对直播间的远程访问能力，代理 ILivingRoomService 和 ILivingRoomTxService 的方法。
 */

/**
 *
 *todo 主播可以开多个直播，有点bug,主播
 * 
 */
@DubboService
public class LivingRoomRpcImpl implements ILivingRoomRpc {

    @Resource
    private ILivingRoomService livingRoomService;

    @Resource
    private ILivingRoomTxService livingRoomTxService;

    /**
     * 根据房间 ID 查询所有用户 ID。
     *
     * @param livingRoomReqDTO 请求参数，包含房间 ID 和其他必要信息
     * @return List<Long> 用户 ID 列表
     */
    @Override
    public List<Long> queryUserIdByRoomId(LivingRoomReqDTO livingRoomReqDTO) {
        return livingRoomService.queryUserIdByRoomId(livingRoomReqDTO);
    }

    /**
     * 分页查询直播间列表。
     *
     * @param livingRoomReqDTO 请求参数，包含分页信息和筛选条件
     * @return PageWrapper<LivingRoomRespDTO> 分页结果封装对象
     */
    @Override
    public PageWrapper<LivingRoomRespDTO> list(LivingRoomReqDTO livingRoomReqDTO) {
        return livingRoomService.list(livingRoomReqDTO);
    }

    /**
     * 根据房间 ID 查询直播间信息。
     *
     * @param roomId 房间 ID
     * @return LivingRoomRespDTO 直播间信息
     */
    @Override
    public LivingRoomRespDTO queryByRoomId(Integer roomId) {
        return livingRoomService.queryByRoomId(roomId);
    }

    /**
     * 开启直播间。
     *
     * @param livingRoomReqDTO 请求参数，包含直播间信息
     * @return Integer 直播间 ID
     */
    @Override
    public Integer startLivingRoom(LivingRoomReqDTO livingRoomReqDTO) {
        return livingRoomService.startLivingRoom(livingRoomReqDTO);
    }

    /**
     * 关闭直播间。
     *
     * @param livingRoomReqDTO 请求参数，包含直播间 ID 和其他必要信息
     * @return boolean 成功返回 true，失败返回 false
     */
    @Override
    public boolean closeLiving(LivingRoomReqDTO livingRoomReqDTO) {
        return livingRoomTxService.closeLiving(livingRoomReqDTO);
    }

    /**
     * 用户在 PK 直播间请求连线。
     *
     * @param livingRoomReqDTO 请求参数，包含房间 ID 和 PK 对象 ID
     * @return LivingPkRespDTO 连线结果
     */
    @Override
    public LivingPkRespDTO onlinePk(LivingRoomReqDTO livingRoomReqDTO) {
        return livingRoomService.onlinePk(livingRoomReqDTO);
    }

    /**
     * 根据房间 ID 查询当前 PK 用户 ID。
     *
     * @param roomId 房间 ID
     * @return Long 当前 PK 用户 ID，若无用户在线则返回 null
     */
    @Override
    public Long queryOnlinePkUserId(Integer roomId) {
        return livingRoomService.queryOnlinePkUserId(roomId);
    }

    /**
     * 用户在 PK 直播间请求下线。
     *
     * @param livingRoomReqDTO 请求参数，包含房间 ID 和 PK 对象 ID
     * @return boolean 下线成功返回 true，失败返回 false
     */
    @Override
    public boolean offlinePk(LivingRoomReqDTO livingRoomReqDTO) {
        return livingRoomService.offlinePk(livingRoomReqDTO);
    }

    @Override
    public LivingRoomRespDTO queryByAnchorId(Long anchorId) {
        return livingRoomService.queryByAnchorId(anchorId);
    }


}
