package org.qiyu.live.bank.provider.service;

import org.qiyu.live.bank.provider.dao.po.PayTopicPO;

/**
 * 支付主题服务接口
 * <p>
 * 提供了通过主题code查询支付主题的功能。
 * </p>
 */
public interface IPayTopicService {

    /**
     * 根据code查询支付主题
     * <p>
     * 使用提供的code值查询与之对应的支付主题记录。
     * </p>
     * @param code 主题code
     * @return 如果找到对应的支付主题，返回PayTopicPO对象，否则返回null
     */
    PayTopicPO getByCode(Integer code);
}

