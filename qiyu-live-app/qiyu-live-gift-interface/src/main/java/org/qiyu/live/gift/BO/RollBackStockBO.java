package org.qiyu.live.gift.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollBackStockBO {
    
    private Long userId;
    private Long orderId;
}
