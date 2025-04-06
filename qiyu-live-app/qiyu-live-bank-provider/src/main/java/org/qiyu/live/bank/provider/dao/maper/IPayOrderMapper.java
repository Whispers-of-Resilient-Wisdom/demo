package org.qiyu.live.bank.provider.dao.maper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.live.bank.provider.dao.po.PayOrderPO;

/**
 * 支付订单数据访问层接口。

 */
@Mapper
public interface IPayOrderMapper extends BaseMapper<PayOrderPO> {
}

