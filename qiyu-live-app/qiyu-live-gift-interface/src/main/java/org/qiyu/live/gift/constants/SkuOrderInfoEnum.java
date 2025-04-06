package org.qiyu.live.gift.constants;

import lombok.Getter;

@Getter
public enum SkuOrderInfoEnum {
    PREPARE_PAY(0, "待支付状态"),
    HAS_PAY(1, "已支付状态"),
    CANCEL(2, "取消订单状态");

    int code;
    String desc;

    SkuOrderInfoEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
