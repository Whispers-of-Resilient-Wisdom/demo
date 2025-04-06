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
public class ShopCarRespDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7147830236451419334L;
    
    private Long userId;
    private Integer roomId;
    private List<ShopCarItemRespDTO> skuCarItemRespDTODTOS;
}
