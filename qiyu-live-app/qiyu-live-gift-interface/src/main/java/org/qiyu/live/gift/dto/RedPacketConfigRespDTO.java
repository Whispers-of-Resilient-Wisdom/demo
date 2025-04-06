package org.qiyu.live.gift.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
public class RedPacketConfigRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5117539613836783248L;
    private Long anchorId;
    private Integer totalPrice;
    private Integer totalCount;
    private String configCode;
    private String remark;
}
