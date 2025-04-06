package org.qiyu.live.gift.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class RedPacketReceiveDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5916608127876611063L;
    
    private Integer price;
    private String notifyMsg;
}
