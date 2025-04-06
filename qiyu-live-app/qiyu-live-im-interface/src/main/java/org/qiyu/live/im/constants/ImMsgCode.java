package org.qiyu.live.im.constants;

import lombok.Getter;

@Getter
public enum ImMsgCode {

    IM_LOGIN(0,"登录消息","imLoginHandler"),
    IM_LOGOUT(1,"注销消息","imLogoutHandler");

    int code;
    String desc;
    String handlerName;




    ImMsgCode(int code, String desc, String handlerName) {
        this.code = code;
        this.desc = desc;
        this.handlerName = handlerName;
    }
}