package org.qiyu.live.im.core.server.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**

 */
@Setter
@Getter
public class ImOnlineDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8966707365668168554L;
    private Long userId;
    private Integer appId;
    private Integer roomId;
    private Long loginTime;


    @Override
    public String toString() {
        return "ImOnlineDTO{" +
                "userId=" + userId +
                ", appId=" + appId +
                ", roomId=" + roomId +
                ", loginTime=" + loginTime +
                '}';
    }
}
