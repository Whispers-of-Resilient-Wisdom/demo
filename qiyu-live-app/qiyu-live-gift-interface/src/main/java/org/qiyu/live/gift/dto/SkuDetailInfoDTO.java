package org.qiyu.live.gift.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
public class SkuDetailInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1279033925266285109L;

    private Long skuId;
    private Integer skuPrice;
    private String skuCode;
    private String name;
    private String iconUrl;
    private String originalIconUrl;
    private String remark;
    //还有其它复杂数据
}
