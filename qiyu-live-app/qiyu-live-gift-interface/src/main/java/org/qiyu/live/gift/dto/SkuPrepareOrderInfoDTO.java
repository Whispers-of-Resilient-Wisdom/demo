package org.qiyu.live.gift.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class SkuPrepareOrderInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8683020132073931910L;
    
    private Integer totalPrice;
    private List<ShopCarItemRespDTO> skuPrepareOrderItemInfoDTOS;
    
}
