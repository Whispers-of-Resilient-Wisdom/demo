package org.qiyu.live.living.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 直播房间请求数据传输对象（DTO）
 */
@Setter
@Getter
public class LivingRoomReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4370401310595190339L;

    /** 房间唯一标识ID */
    private Integer id;

    /** 主播ID */
    private Long anchorId;

    /** PK对象ID（对手主播ID） */
    private Long pkObjId;

    /** 房间名称 */
    private String roomName;

    /** 房间ID（可能与id不同，用于业务逻辑上的区分） */
    private Integer roomId;

    /** 封面图片URL */
    private String covertImg;

    /** 房间类型（如：1-娱乐、2-教育、3-游戏等） */
    private Integer type;

    /** 应用ID（区分不同的应用场景或平台） */
    private Integer appId;

    /** 分页：当前页码（默认从1开始） */
    private int page;

    /** 分页：每页条数（最大不超过100） */
    private int pageSize;


    @Override
    public String toString() {
        return "LivingRoomReqDTO{" +
                "id=" + id +
                ", anchorId=" + anchorId +
                ", pkObjId=" + pkObjId +
                ", roomName='" + roomName + '\'' +
                ", roomId=" + roomId +
                ", covertImg='" + covertImg + '\'' +
                ", type=" + type +
                ", appId=" + appId +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
