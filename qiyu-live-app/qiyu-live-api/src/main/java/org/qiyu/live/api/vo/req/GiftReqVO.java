package org.qiyu.live.api.vo.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 礼物请求VO类，用于封装发送礼物的请求信息
 */
@Setter
@Getter
public class GiftReqVO {

    /**
     * 礼物ID，标识礼物的唯一编号
     */
    private int giftId;

    /**
     * 房间ID，标识礼物发送的房间
     */
    private Integer roomId;

    /**
     * 发送礼物的用户ID
     */
    private Long senderUserId;

    /**
     * 接收礼物的用户ID
     */
    private Long receiverId;

    /**
     * 礼物类型，标识礼物的种类或分类
     */
    private int type;


    @Override
    public String toString() {
        return "GiftReqVO{" +
                "giftId=" + giftId +
                ", roomId=" + roomId +
                ", type=" + type +
                ", senderUserId=" + senderUserId +
                ", receiverId=" + receiverId +
                '}';
    }
}