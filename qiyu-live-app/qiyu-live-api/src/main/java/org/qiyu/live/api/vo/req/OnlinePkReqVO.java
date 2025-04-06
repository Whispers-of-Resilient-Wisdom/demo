package org.qiyu.live.api.vo.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 在线PK请求VO类，用于封装在线PK的请求信息
 */
@Setter
@Getter
public class OnlinePkReqVO {

    /**
     * 房间ID，标识发起或参与PK的房间
     */
    private Integer roomId;


    @Override
    public String toString() {
        return "OnlinePkReqVO{" +
                "roomId=" + roomId +
                '}';
    }
}