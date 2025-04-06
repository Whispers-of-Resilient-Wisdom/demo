package org.qiyu.live.api.vo.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 礼物配置VO类，用于封装礼物的配置信息
 */
@Setter
@Getter
public class GiftConfigVO {

    /**
     * 礼物ID，唯一标识一个礼物
     */
    private Integer giftId;

    /**
     * 礼物价格，表示礼物的价值
     */
    private Integer price;

    /**
     * 礼物名称，表示礼物的显示名称
     */
    private String giftName;

    /**
     * 礼物状态，标识礼物是否可用（如：0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 礼物封面图片URL，用于展示礼物的封面
     */
    private String coverImgUrl;

    /**
     * 礼物动画文件URL（SVGA格式），用于播放礼物的动画效果
     */
    private String svgaUrl;

    /**
     * 创建时间，记录礼物的创建时间
     */
    private Date createTime;

    /**
     * 更新时间，记录礼物的最后更新时间
     */
    private Date updateTime;

    @Override
    public String toString() {
        return "GiftConfigVO{" +
                "giftId=" + giftId +
                ", price=" + price +
                ", giftName='" + giftName + '\'' +
                ", status=" + status +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", svgaUrl='" + svgaUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}