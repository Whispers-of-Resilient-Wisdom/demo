package org.qiyu.live.gift.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
public class ShopCarItemRespDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7247175817439564893L;
    
    private Integer count;
    private SkuInfoDTO skuInfoDTO;
}
