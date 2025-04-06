package org.qiyu.live.api.vo.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 直播房间响应对象，封装直播房间的基本信息
 */
@Setter
@Getter
public class LivingRoomRespVO {

    /** 房间ID */
    private Integer id;

    /** 房间名称 */
    private String roomName;

    /** 主播ID */
    private Long anchorId;

    /** 观看人数 */
    private Integer watchNum;

    /** 点赞数 */
    private Integer goodNum;

    /** 房间类型 */
    private Integer type;

    /** 封面图片地址 */
    private String covertImg;

    @Override
    public String toString() {
        return "LivingRoomRespVO{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", anchorId=" + anchorId +
                ", type=" + type +
                ", watchNum=" + watchNum +
                ", goodNum=" + goodNum +
                ", covertImg='" + covertImg + '\'' +
                '}';
    }
}
