package org.qiyu.live.gift.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SkuOrderInfoReqDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -9220028624463964600L;
    
    private Long id;
    private Long userId;
    private Integer roomId;
    private Integer status;
    private List<Long> skuIdList;
}
