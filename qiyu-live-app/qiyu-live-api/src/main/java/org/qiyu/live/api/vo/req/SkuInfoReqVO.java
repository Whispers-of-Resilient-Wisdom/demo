package org.qiyu.live.api.vo.req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SkuInfoReqVO {
    
    private Long skuId;
    private Long anchorId;

    @Override
    public String toString() {
        return "SkuInfoReqVO{" +
                "anchorId=" + anchorId +
                ", skuId=" + skuId +
                '}';
    }

}
