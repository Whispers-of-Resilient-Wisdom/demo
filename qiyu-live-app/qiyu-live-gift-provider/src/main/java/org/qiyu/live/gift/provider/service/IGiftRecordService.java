package org.qiyu.live.gift.provider.service;

import org.qiyu.live.gift.dto.GiftRecordDTO;

/**
 * 礼品记录服务接口，定义了礼品记录相关的操作方法。
 * 提供了插入单个礼品记录的方法。
 * <p>
 * 本接口可以被实现类用于操作礼品记录，例如插入记录、更新记录等。
 * </p>
 *

 */
public interface IGiftRecordService {

    /**
     * 插入单个礼品记录信息。
     * <p>
     * 本方法将接收一个 {@link GiftRecordDTO} 数据传输对象，并将其信息插入到数据库中。
     * </p>
     *
     * @param giftRecordDTO 礼品记录的数据传输对象，包含要插入的礼品记录信息
     */
    void insertOne(GiftRecordDTO giftRecordDTO);

}

