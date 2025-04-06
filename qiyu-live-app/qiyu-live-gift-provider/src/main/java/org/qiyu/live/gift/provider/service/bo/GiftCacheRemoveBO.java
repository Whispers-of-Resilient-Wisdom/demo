package org.qiyu.live.gift.provider.service.bo;


/**
 * 礼物缓存移除业务对象。
 * <p>
 * 本类用于表示在礼物缓存移除操作中需要携带的参数，
 * 包含是否移除缓存列表的标志以及礼物的唯一ID。
 * </p>
 */
public class GiftCacheRemoveBO {

    /**
     * 是否移除缓存列表的标志。
     * <p>
     * 如果该值为 true，则表示需要移除缓存中的礼物列表；
     * 如果为 false，则表示不需要移除缓存列表。
     * </p>
     */
    private boolean removeListCache;

    /**
     * 礼物的唯一标识ID。
     * <p>
     * 本字段用于标识具体的礼物项，通常在缓存操作或数据库更新中使用。
     * </p>
     */
    private int giftId;

    /**
     * 获取是否移除缓存列表的标志。
     *
     * @return 返回 true 表示移除缓存列表，false 表示不移除缓存列表
     */
    public boolean isRemoveListCache() {
        return removeListCache;
    }

    /**
     * 设置是否移除缓存列表的标志。
     *
     * @param removeListCache true 表示移除缓存列表，false 表示不移除
     */
    public void setRemoveListCache(boolean removeListCache) {
        this.removeListCache = removeListCache;
    }

    /**
     * 获取礼物的唯一标识ID。
     *
     * @return 礼物的ID
     */
    public int getGiftId() {
        return giftId;
    }

    /**
     * 设置礼物的唯一标识ID。
     *
     * @param giftId 礼物的ID
     */
    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    /**
     * 返回 GiftCacheRemoveBO 对象的字符串表示形式。
     *
     * @return 返回 GiftCacheRemoveBO 对象的字符串表示
     */
    @Override
    public String toString() {
        return "GiftCacheRemoveBO{" +
                "removeListCache=" + removeListCache +
                ", giftId=" + giftId +
                '}';
    }
}
