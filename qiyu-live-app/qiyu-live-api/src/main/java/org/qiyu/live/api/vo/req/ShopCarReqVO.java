package org.qiyu.live.api.vo.req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShopCarReqVO {
    
    private Long skuId;
    private Integer roomId;

    @Override
    public String toString() {
        return "ShopCarReqVO{" +
                "roomId=" + roomId +
                ", skuId=" + skuId +
                '}';
    }


}
