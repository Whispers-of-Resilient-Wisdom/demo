package org.qiyu.live.gift.provider.rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.gift.dto.GiftRecordDTO;
import org.qiyu.live.gift.interfaces.IGiftRecordRpc;
import org.qiyu.live.gift.provider.service.IGiftRecordService;

/**
 * 礼品记录服务实现类，用于处理礼品记录的 RPC 请求。
 * 实现了 {@link IGiftRecordRpc} 接口，提供了插入礼品记录的方法。
 *

 */
@DubboService
public class GiftRecordRpcImpl implements IGiftRecordRpc {

    /**
     * 礼品记录服务接口，用于操作礼品记录的数据库。
     * 通过此接口可以实现插入礼品记录等操作。
     */
    @Resource
    private IGiftRecordService giftRecordService;

    /**
     * 插入一条新的礼品记录。
     * <p>
     * 本方法将接受一个 {@link GiftRecordDTO} 对象作为参数，并将其数据插入到数据库中。
     * </p>
     *
     * @param giftRecordDTO 包含礼品记录信息的数据传输对象
     *
     */
    @Override
    public void insertOne(GiftRecordDTO giftRecordDTO) {
        giftRecordService.insertOne(giftRecordDTO);
    }
}
