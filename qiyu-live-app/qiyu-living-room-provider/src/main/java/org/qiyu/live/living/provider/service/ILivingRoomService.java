package org.qiyu.live.living.provider.service;

import com.qiyu.live.common.interfaces.dto.PageWrapper;
import org.qiyu.live.im.core.server.interfaces.dto.ImOfflineDTO;
import org.qiyu.live.im.core.server.interfaces.dto.ImOnlineDTO;
import org.qiyu.live.living.interfaces.dto.LivingPkRespDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomReqDTO;
import org.qiyu.live.living.interfaces.dto.LivingRoomRespDTO;

import java.util.List;
/**
 * 直播间服务接口，定义与直播间相关的核心业务逻辑。
 */
public interface ILivingRoomService {

    /**
     * 根据 roomId 查询直播间内所有用户的 ID 列表。
     * 该方法返回一个包含用户 ID 的列表（List），用户数量可能非常多（如 3000+），时间复杂度为 O(n)。
     *
     * @param livingRoomReqDTO 请求参数，包含房间 ID
     * @return List<Long> 用户 ID 列表
     */
    List<Long> queryUserIdByRoomId(LivingRoomReqDTO livingRoomReqDTO);

    /**
     * 处理用户下线事件，例如清理缓存、更新状态等。
     *
     * @param imOfflineDTO 用户下线数据传输对象
     */
    void userOfflineHandler(ImOfflineDTO imOfflineDTO);

    /**
     * 处理用户上线事件，例如初始化连接、发送通知等。
     *
     * @param imOnlineDTO 用户上线数据传输对象
     */
    void userOnlineHandler(ImOnlineDTO imOnlineDTO);

    /**
     * 查询所有的直播间类型。
     *
     * @param type 直播间类型（可选参数）
     * @return List<LivingRoomRespDTO> 返回所有匹配类型的直播间信息列表
     */
    List<LivingRoomRespDTO> listAllLivingRoomFromDB(Integer type);

    /**
     * 分页查询直播间列表。
     *
     * @param livingRoomReqDTO 请求参数，包含分页信息和筛选条件
     * @return PageWrapper<LivingRoomRespDTO> 返回分页包装的直播间信息列表
     */
    PageWrapper<LivingRoomRespDTO> list(LivingRoomReqDTO livingRoomReqDTO);

    /**
     * 根据 roomId 查询直播间信息。
     *
     * @param roomId 直播间 ID
     * @return LivingRoomRespDTO 返回直播间的详细信息
     */
    LivingRoomRespDTO queryByRoomId(Integer roomId);

    /**
     * 开启直播间。
     *
     * @param livingRoomReqDTO 请求参数，包含主播信息、直播间配置等内容
     * @return Integer 返回直播间 ID
     */
    Integer startLivingRoom(LivingRoomReqDTO livingRoomReqDTO);

    /**
     * 查询指定房间当前参与 PK 的用户 ID。
     *
     * @param roomId 直播间 ID
     * @return Long 当前参与 PK 的用户 ID，如果没有人参与 PK 则返回 null
     */
    Long queryOnlinePkUserId(Integer roomId);

    /**
     * 用户在 PK 直播间中发起连线请求。
     * 成功时，系统通知所有房间用户 PK 开始，失败时提示当前已有用户在线 PK。
     *
     * @param livingRoomReqDTO 请求参数，包含房间 ID 和 PK 对象 ID
     * @return LivingPkRespDTO 返回连线结果，包含连线状态和提示信息
     */
    LivingPkRespDTO onlinePk(LivingRoomReqDTO livingRoomReqDTO);

    /**
     * 用户在 PK 直播间中下线。
     * 成功时，清理 Redis 缓存中的 PK 用户信息，实现 PK 下线。
     *
     * @param livingRoomReqDTO 请求参数，包含房间 ID
     * @return boolean 下线结果，成功返回 true，失败返回 false
     */
    boolean offlinePk(LivingRoomReqDTO livingRoomReqDTO);

    LivingRoomRespDTO queryByAnchorId(Long anchorId);
}
