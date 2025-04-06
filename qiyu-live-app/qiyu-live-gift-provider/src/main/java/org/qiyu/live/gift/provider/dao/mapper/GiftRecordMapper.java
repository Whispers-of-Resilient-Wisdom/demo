package org.qiyu.live.gift.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.live.gift.provider.dao.po.GiftRecordPO;

/**
 * 礼物记录映射接口。
 * 继承自 BaseMapper，用于操作礼物记录表。
 */
@Mapper
public interface GiftRecordMapper extends BaseMapper<GiftRecordPO> {
}

