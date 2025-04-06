package org.qiyu.live.api.vo.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**

 */
@Setter
@Getter
public class PayProductVO {

    /**
     * 当前余额
     */
    private Integer currentBalance;

    /**
     * 一系列的付费产品
     */
    private List<PayProductItemVO>  payProductItemVOList;

}
