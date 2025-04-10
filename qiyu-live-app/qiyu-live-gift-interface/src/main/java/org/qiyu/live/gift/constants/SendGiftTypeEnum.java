package org.qiyu.live.gift.constants;

import lombok.Getter;

/**

 */
@Getter
public enum SendGiftTypeEnum {

    DEFAULT_SEND_GIFT(0,"直播间默认送礼物"),
    PK_SEND_GIFT(1,"直播间PK送礼物");

    SendGiftTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;


}
