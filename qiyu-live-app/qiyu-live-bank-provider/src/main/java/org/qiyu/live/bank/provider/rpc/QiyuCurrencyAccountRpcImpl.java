package org.qiyu.live.bank.provider.rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.bank.dto.AccountTradeReqDTO;
import org.qiyu.live.bank.dto.AccountTradeRespDTO;
import org.qiyu.live.bank.interfaces.IQiyuCurrencyAccountRpc;
import org.qiyu.live.bank.provider.service.IQiyuCurrencyAccountService;
/**
 * 旗鱼平台虚拟币账户RPC实现类
 * <p>
 * 该类实现了 `IQiyuCurrencyAccountRpc` 接口，提供了虚拟币账户的远程调用服务。通过调用 `IQiyuCurrencyAccountService`，该服务可以实现虚拟币账户的充值、扣款、查询余额以及消费记录等操作。
 * </p>
 */
@DubboService
public class QiyuCurrencyAccountRpcImpl implements IQiyuCurrencyAccountRpc {

    @Resource
    private IQiyuCurrencyAccountService qiyuCurrencyAccountService;
    /**
     * 增加虚拟币账户余额
     * @param userId 用户ID
     * @param num 增加的虚拟币数量
     */
    @Override
    public void incr(long userId, int num) {
        qiyuCurrencyAccountService.incr(userId, num);
    }
    /**
     * 减少虚拟币账户余额
     * @param userId 用户ID
     * @param num 减少的虚拟币数量
     */
    @Override
    public void decr(long userId, int num) {
        qiyuCurrencyAccountService.decr(userId, num);
    }
    /**
     * 获取虚拟币账户余额
     * @param userId 用户ID
     * @return 用户的当前余额
     */
    @Override
    public Integer getBalance(long userId) {
        return qiyuCurrencyAccountService.getBalance(userId);
    }
    /**
     * 用于消费，扣减余额（如送礼物等场景）
     * @param accountTradeReqDTO 请求参数，包括消费相关信息
     * @return 返回账户交易响应数据
     */
    @Override
    public AccountTradeRespDTO consumeForSendGift(AccountTradeReqDTO accountTradeReqDTO) {
        return qiyuCurrencyAccountService.consumeForSendGift(accountTradeReqDTO);
    }

}
