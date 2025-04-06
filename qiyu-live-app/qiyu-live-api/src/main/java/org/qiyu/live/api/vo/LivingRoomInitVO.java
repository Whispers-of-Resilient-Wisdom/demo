package org.qiyu.live.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 直播房间初始化信息对象（VO）
 */
@Setter
@Getter
public class LivingRoomInitVO {

    /** 主播ID */
    private Long anchorId;

    /** 用户ID */
    private Long userId;

    /** 主播头像图片URL */
    private String anchorImg;

    /** 房间名称 */
    private String roomName;

    /** 是否为主播身份：true-是，false-否 */
    private boolean isAnchor;

    /** 用户头像URL */
    private String avatar;

    /** 房间ID */
    private Integer roomId;

    /** 观众昵称 */
    private String watcherNickName;

    /** 主播昵称 */
    private String anchorNickName;

    /** 观众头像URL */
    private String watcherAvatar;

    /** 默认背景图片URL（为了方便讲解使用） */
    private String defaultBgImg;

    /** PK对象ID */
    private Long pkObjId;
    /**
     *红包唯一标识
     */
    private String redPacketConfigCode;


    @Override
    public String toString() {
        return "LivingRoomInitVO{" +
                "anchorId=" + anchorId +
                ", userId=" + userId +
                ", anchorImg='" + anchorImg + '\'' +
                ", roomName='" + roomName + '\'' +
                ", isAnchor=" + isAnchor +
                ", avatar='" + avatar + '\'' +
                ", roomId=" + roomId +
                ", redPacketConfigCode=" + redPacketConfigCode +
                ", watcherNickName='" + watcherNickName + '\'' +
                ", anchorNickName='" + anchorNickName + '\'' +
                ", watcherAvatar='" + watcherAvatar + '\'' +
                ", defaultBgImg='" + defaultBgImg + '\'' +
                ", pkObjId=" + pkObjId +
                '}';
    }
}
