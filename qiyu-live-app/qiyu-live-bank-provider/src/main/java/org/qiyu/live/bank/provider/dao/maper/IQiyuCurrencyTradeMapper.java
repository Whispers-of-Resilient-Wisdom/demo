package org.qiyu.live.bank.provider.dao.maper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.live.bank.provider.dao.po.QiyuCurrencyTradePO;

/**
 * 虚拟币交易数据访问层接口。

 */
@Mapper
public interface IQiyuCurrencyTradeMapper extends BaseMapper<QiyuCurrencyTradePO> {
}

