package org.qiyu.live.bank.constants;


import lombok.Getter;
import lombok.Setter;

/**
 * 支付渠道类型
 *

 */

@Getter
public enum PaySourceEnum {

    QIYU_LIVING_ROOM(1,"旗鱼直播间内支付"),
    QIYU_USER_CENTER(2,"用户中心");

    PaySourceEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PaySourceEnum find(int code) {
        for (PaySourceEnum value : PaySourceEnum.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    private int code;
    private String desc;


}
