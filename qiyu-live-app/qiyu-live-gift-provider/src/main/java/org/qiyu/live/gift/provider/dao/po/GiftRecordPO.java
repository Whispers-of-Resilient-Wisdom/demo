package org.qiyu.live.gift.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
/**
 * 礼物记录持久化对象。
 * 表示系统中的礼物发送记录。
 */
@TableName("t_gift_record")
public class GiftRecordPO {

    /** 记录ID（自增） */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 对象ID（接收方） */
    private Long objectId;

    /** 礼物来源 */
    private Integer source;

    /** 礼物价格 */
    private Integer price;

    /** 价格单位 */
    private Integer priceUnit;

    /** 礼物ID */
    private Integer giftId;

    /** 发送时间 */
    private Date sendTime;

    /**
     * 获取记录ID。
     *
     * @return 记录ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置记录ID。
     *
     * @param id 记录ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户ID。
     *
     * @return 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID。
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取对象ID（接收方）。
     *
     * @return 对象ID
     */
    public Long getObjectId() {
        return objectId;
    }

    /**
     * 设置对象ID（接收方）。
     *
     * @param objectId 对象ID
     */
    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    /**
     * 获取礼物来源。
     *
     * @return 礼物来源
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置礼物来源。
     *
     * @param source 礼物来源
     */
    public void setSource(Integer source) {
        this.source = source;
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
     * 获取价格单位。
     *
     * @return 价格单位
     */
    public Integer getPriceUnit() {
        return priceUnit;
    }

    /**
     * 设置价格单位。
     *
     * @param priceUnit 价格单位
     */
    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
    }

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
     * 获取发送时间。
     *
     * @return 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间。
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 返回 GiftRecordPO 对象的字符串表示形式。
     *
     * @return GiftRecordPO 对象的字符串表示形式
     */
    @Override
    public String toString() {
        return "GiftRecordPO{" +
                "id=" + id +
                ", userId=" + userId +
                ", objectId=" + objectId +
                ", source=" + source +
                ", price=" + price +
                ", priceUnit=" + priceUnit +
                ", giftId=" + giftId +
                ", sendTime=" + sendTime +
                '}';
    }
}
