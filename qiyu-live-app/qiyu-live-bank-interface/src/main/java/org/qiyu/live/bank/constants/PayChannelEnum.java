package org.qiyu.live.bank.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付渠道 0支付宝 1微信 2银联 3收银台
 *

 */

@Getter
public enum PayChannelEnum {

    ZHI_FU_BAO(0,"支付宝"),
    WEI_XIN(1,"微信"),
    YIN_LIAN(2,"银联"),
    SHOU_YIN_TAI(3,"收银台");

    PayChannelEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;


}
