package org.qiyu.live.bank.constants;

import lombok.Getter;

/**
 * 订单状态（0待支付,1支付中,2已支付,3撤销,4无效）

 */

@Getter
public enum OrderStatusEnum {

    WAITING_PAY(0,"待支付"),
    PAYING(1,"支付中"),
    PAYED(2,"已支付"),
    PAY_BACK(3,"撤销"),
    IN_VALID(4,"无效");

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;


}
