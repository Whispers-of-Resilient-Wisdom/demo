package org.qiyu.live.gift.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
public class PrepareOrderReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1742445784431200306L;
    
    private Long userId;
    private Integer roomId;
}
