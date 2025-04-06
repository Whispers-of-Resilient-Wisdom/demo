package org.qiyu.live.bank.provider.service;

/**
 * 旗鱼平台虚拟币交易服务接口
 * <p>
 * 该接口定义了虚拟币交易相关的业务逻辑方法，特别是插入虚拟币交易流水记录。
 * </p>
 */
public interface IQiyuCurrencyTradeService {

    /**
     * 插入一条流水记录
     * <p>
     * 用于记录虚拟币账户的交易，记录用户的交易流水（如充值、消费等）。
     * </p>
     * @param userId 用户ID
     * @param num 交易的虚拟币数量（正值表示充值，负值表示消费）
     * @param type 交易类型，具体定义取决于业务需求（如充值、扣款等）
     * @return 返回操作是否成功
     */
    boolean insertOne(long userId, int num, int type);
}

