package org.qiyu.live.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 首页展示信息对象（VO）
 */
@Setter
@Getter
public class HomePageVO {

    /** 用户登录状态：true-已登录，false-未登录 */
    private boolean loginStatus;

    /** 用户ID */
    private long userId;

    /** 用户昵称 */
    private String nickName;

    /** 用户头像URL */
    private String avatar;

    /** 是否展示开播按钮（只有主播身份时才展示） */
    private boolean showStartLivingBtn;

    @Override
    public String toString() {
        return "HomePageVO{" +
                "avatar='" + avatar + '\'' +
                ", loginStatus=" + loginStatus +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", showStartLivingBtn=" + showStartLivingBtn +
                '}';
    }
}
