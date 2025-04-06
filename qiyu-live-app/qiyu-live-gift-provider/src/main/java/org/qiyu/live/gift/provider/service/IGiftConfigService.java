package org.qiyu.live.gift.provider.service;

import org.qiyu.live.gift.dto.GiftConfigDTO;

import java.util.List;
/**
 * 礼物配置服务接口，定义了与礼物配置相关的操作方法。
 * 提供了根据礼物ID查询、查询所有礼物、插入和更新礼物配置的方法。
 * <p>
 * 本接口用于操作礼物配置数据，供服务层实现和调用。
 * </p>

 */
public interface IGiftConfigService {

    /**
     * 根据礼物ID查询礼物配置。

     *
     * @param giftId 礼物的唯一标识ID
     * @return 返回对应礼物ID的礼物配置数据
     */
    GiftConfigDTO getByGiftId(Integer giftId);

    /**
     * 查询所有礼物配置信息。
     * <p>
     * 本方法将返回系统中所有礼物的配置信息列表。
     * </p>
     *
     * @return 返回所有礼物的配置信息列表
     */
    List<GiftConfigDTO> queryGiftList();

    /**
     * 插入单个礼物配置信息。

     *
     * @param giftConfigDTO 礼物配置信息的数据传输对象
     */
    void insertOne(GiftConfigDTO giftConfigDTO);

    /**
     * 更新单个礼物配置信息。

     *
     * @param giftConfigDTO 礼物配置信息的数据传输对象，包含更新后的礼物配置数据
     */
    void updateOne(GiftConfigDTO giftConfigDTO);
}
