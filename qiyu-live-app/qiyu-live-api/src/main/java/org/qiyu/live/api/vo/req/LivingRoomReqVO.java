package org.qiyu.live.api.vo.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 直播房间请求参数对象（VO）
 */
@Setter
@Getter
public class LivingRoomReqVO {

    /** 房间类型（如：1-娱乐、2-教育、3-游戏等） */
    private Integer type;

    /** 当前页码（从1开始） */
    private int page;

    /** 每页条数 */
    private int pageSize;
     private int roomId;
    /** 红包唯一标识 **/
    private String  redPacketConfigCode;

    @Override
    public String toString() {
        return "LivingRoomReqVO{" +
                "type=" + type +
                ", page=" + page +
                ", redPacketConfigCode=" + redPacketConfigCode +
                ", roomId=" + roomId +
                ", pageSize=" + pageSize +
                '}';
    }
}
