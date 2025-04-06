package org.qiyu.live.gift.provider.service.impl;

import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import jakarta.annotation.Resource;
import org.qiyu.live.gift.dto.GiftRecordDTO;
import org.qiyu.live.gift.provider.dao.mapper.GiftRecordMapper;
import org.qiyu.live.gift.provider.dao.po.GiftRecordPO;
import org.qiyu.live.gift.provider.service.IGiftRecordService;
import org.springframework.stereotype.Service;

/**
 * 礼品记录服务实现类，提供了礼品记录的增操作。
 * 本类实现了 {@link IGiftRecordService} 接口，负责将礼品记录插入到数据库中。
 *

 */
@Service
public class GiftRecordServiceImpl implements IGiftRecordService {

    @Resource
    private GiftRecordMapper giftRecordMapper;

    /**
     * 插入一条新的礼品记录。
     * <p>
     * 本方法将接收一个 {@link GiftRecordDTO} 数据传输对象，并将其转换为 {@link GiftRecordPO} 实体对象，
     * 然后插入到数据库中。
     * </p>
     *
     * @param giftRecordDTO 礼品记录的数据传输对象，包含要插入的礼品记录信息
     */
    @Override
    public void insertOne(GiftRecordDTO giftRecordDTO) {
        GiftRecordPO giftRecordPO = ConvertBeanUtils.convert(giftRecordDTO, GiftRecordPO.class);
        giftRecordMapper.insert(giftRecordPO);
    }
}

