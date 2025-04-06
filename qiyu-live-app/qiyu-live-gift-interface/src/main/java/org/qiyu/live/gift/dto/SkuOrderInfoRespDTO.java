package org.qiyu.live.gift.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SkuOrderInfoRespDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2916280620499166681L;

    private Long Id;
    private String skuIdList;
    private Long userId;
    private Integer roomId;
    private Integer status;
    private String extra;
}
