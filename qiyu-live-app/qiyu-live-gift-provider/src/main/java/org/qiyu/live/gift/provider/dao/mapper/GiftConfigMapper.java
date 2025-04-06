package org.qiyu.live.gift.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.live.gift.provider.dao.po.GiftConfigPO;

/**
 * 礼物配置映射接口。
 * 继承自 BaseMapper，用于操作礼物配置表。
 */
@Mapper
public interface GiftConfigMapper extends BaseMapper<GiftConfigPO> {
}

