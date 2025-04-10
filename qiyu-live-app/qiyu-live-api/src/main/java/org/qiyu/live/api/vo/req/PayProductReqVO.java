package org.qiyu.live.api.vo.req;


import lombok.Getter;
import lombok.Setter;

/**

 */
@Setter
@Getter
public class PayProductReqVO {

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 支付来源 (直播间，个人中心，聊天页面，第三方宣传页面，广告弹窗引导)
     * //@see org.qiyu.live.bank.constants.PaySourceEnum
     */
    private Integer paySource;

    /**
     * 支付渠道
     * //@see org.qiyu.live.bank.constants.//PayChannelEnum
     */
    private Integer payChannel;

    @Override
    public String toString() {
        return "PayProductReqVO{" +
                "productId=" + productId +
                ", payChannel=" + payChannel +
                ", paySource=" + paySource +
                '}';
    }
}
