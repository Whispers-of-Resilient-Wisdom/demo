package org.qiyu.live.bank.api.vo;

import lombok.Getter;
import lombok.Setter;

/**

 */
@Setter
@Getter
public class WxPayNotifyVO {

    private String orderId;
    private Long userId;
    private Integer bizCode;


    @Override
    public String toString() {
        return "WxPayNotifyVO{" +
                "orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", bizCode=" + bizCode +
                '}';
    }
}
