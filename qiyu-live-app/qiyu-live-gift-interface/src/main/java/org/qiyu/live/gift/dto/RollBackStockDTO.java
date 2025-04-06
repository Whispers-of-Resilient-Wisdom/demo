package org.qiyu.live.gift.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class RollBackStockDTO implements Serializable {
    private Long orderId;
    private Long userId;

}
