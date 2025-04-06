package org.qiyu.live.im.core.server.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**

 */
@Setter
@Getter
public class ImOfflineDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7435114723872010599L;
    private Long userId;
    private Integer appId;
    private Integer roomId;
    private Long loginTime;


    @Override
    public String toString() {
        return "ImOfflineDTO{" +
                "userId=" + userId +
                ", appId=" + appId +
                ", roomId=" + roomId +
                ", loginTime=" + loginTime +
                '}';
    }
}
