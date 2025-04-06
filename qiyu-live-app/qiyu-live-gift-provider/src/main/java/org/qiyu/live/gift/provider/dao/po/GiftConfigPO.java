package org.qiyu.live.gift.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
/**
 * 礼物配置持久化对象。
 * 表示系统中的礼物配置信息。
 */
@TableName("t_gift_config")
public class GiftConfigPO {

    /** 礼物ID（自增） */
    @TableId(type = IdType.AUTO)
    private Integer giftId;

    /** 礼物价格 */
    private Integer price;

    /** 礼物名称 */
    private String giftName;

    /** 礼物状态（例如：启用/禁用） */
    private Integer status;

    /** 封面图片URL */
    private String coverImgUrl;

    /** SVGA 动画 URL */
    private String svgaUrl;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /**
     * 获取礼物ID。
     *
     * @return 礼物ID
     */
    public Integer getGiftId() {
        return giftId;
    }

    /**
     * 设置礼物ID。
     *
     * @param giftId 礼物ID
     */
    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    /**
     * 获取礼物价格。
     *
     * @return 礼物价格
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置礼物价格。
     *
     * @param price 礼物价格
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取礼物名称。
     *
     * @return 礼物名称
     */
    public String getGiftName() {
        return giftName;
    }

    /**
     * 设置礼物名称。
     *
     * @param giftName 礼物名称
     */
    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    /**
     * 获取礼物状态。
     *
     * @return 礼物状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置礼物状态。
     *
     * @param status 礼物状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取封面图片 URL。
     *
     * @return 封面图片 URL
     */
    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    /**
     * 设置封面图片 URL。
     *
     * @param coverImgUrl 封面图片 URL
     */
    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    /**
     * 获取 SVGA 动画 URL。
     *
     * @return SVGA 动画 URL
     */
    public String getSvgaUrl() {
        return svgaUrl;
    }

    /**
     * 设置 SVGA 动画 URL。
     *
     * @param svgaUrl SVGA 动画 URL
     */
    public void setSvgaUrl(String svgaUrl) {
        this.svgaUrl = svgaUrl;
    }

    /**
     * 获取创建时间。
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间。
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间。
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间。
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 返回 GiftConfigPO 对象的字符串表示形式。
     *
     * @return GiftConfigPO 对象的字符串表示形式
     */
    @Override
    public String toString() {
        return "GiftConfigPO{" +
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
