package org.qiyu.live.api.vo.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付产品项对象，封装支付产品的基本信息
 */
@Setter
@Getter
public class PayProductItemVO {

    /** 产品ID */
    private Long id;

    /** 产品名称 */
    private String name;

    /** 所需金币数量 */
    private Integer coinNum;


    @Override
    public String toString() {
        return "PayProductItemVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coinNum=" + coinNum +
                '}';
    }
}
