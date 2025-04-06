package org.qiyu.live.gift.constants;

import lombok.Getter;

@Getter
public enum RedPacketStatusEnum {
    
    WAIT(1,"待准备"),
    IS_PREPARED(2, "已准备"),
    IS_SEND(3, "已发送");

    int code;
    String desc;

    RedPacketStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
