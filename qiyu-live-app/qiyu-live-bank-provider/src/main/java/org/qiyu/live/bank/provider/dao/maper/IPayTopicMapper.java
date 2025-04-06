package org.qiyu.live.bank.provider.dao.maper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.live.bank.provider.dao.po.PayTopicPO;

/**
 * 支付主题数据访问层接口。

 */
@Mapper
public interface IPayTopicMapper extends BaseMapper<PayTopicPO> {

}

