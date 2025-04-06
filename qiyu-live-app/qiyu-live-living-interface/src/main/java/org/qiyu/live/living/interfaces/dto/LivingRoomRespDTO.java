package org.qiyu.live.living.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 直播间相关请求DTO
 *

 */
@Setter
@Getter
public class LivingRoomRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4370402310595190339L;
    private Integer id;
    private Long anchorId;
    private String roomName;
    private String covertImg;
    private Integer type;
    private Integer watchNum;
    private Integer goodNum;
    private Long pkObjId;

    @Override
    public String toString() {
        return "LivingRoomReqDTO{" +
                "id=" + id +
                ", anchorId=" + anchorId +
                ", roomName='" + roomName + '\'' +
                ", covertImg='" + covertImg + '\'' +
                ", type=" + type +
                ", watchNum=" + watchNum +
                ", goodNum=" + goodNum +
                '}';
    }
}
