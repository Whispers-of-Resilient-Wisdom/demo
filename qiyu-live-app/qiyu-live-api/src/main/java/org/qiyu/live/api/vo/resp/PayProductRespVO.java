package org.qiyu.live.api.vo.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付产品响应VO类，用于封装支付产品的响应信息
 */
@Setter
@Getter
public class PayProductRespVO {

    /**
     * 订单ID，唯一标识一个支付订单
     */
    private String orderId;


    @Override
    public String toString() {
        return "PayProductRespVO{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}