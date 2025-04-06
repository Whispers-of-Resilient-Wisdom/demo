package org.qiyu.live.bank.provider.service;

import org.qiyu.live.bank.dto.AccountTradeReqDTO;
import org.qiyu.live.bank.dto.AccountTradeRespDTO;
import org.qiyu.live.bank.dto.QiyuCurrencyAccountDTO;
/**
 * 旗鱼平台虚拟币账户服务接口
 * <p>
 * 该接口定义了虚拟币账户管理的核心操作，包含账户创建、余额增减以及专门的扣减逻辑。
 * </p>
 */
public interface IQiyuCurrencyAccountService {

    /**
     * 新增账户
     * <p>
     * 为用户创建一个虚拟币账户。
     * </p>
     *
     * @param userId 用户ID
     * @return 如果账户创建成功，返回true；否则返回false。
     */
    boolean insertOne(long userId);

    /**
     * 增加虚拟币
     * <p>
     * 向用户的虚拟币账户中增加指定数量的虚拟币。
     * </p>
     *
     * @param userId 用户ID
     * @param num 增加的虚拟币数量（正数）
     */
    void incr(long userId, int num);

    /**
     * 扣减虚拟币
     * <p>
     * 从用户的虚拟币账户中扣减指定数量的虚拟币。
     * </p>
     *
     * @param userId 用户ID
     * @param num 扣减的虚拟币数量（负数）
     */
    void decr(long userId, int num);

    /**
     * 查询余额
     * <p>
     * 获取用户虚拟币账户的当前余额。
     * </p>
     *
     * @param userId 用户ID
     * @return 当前虚拟币账户的余额
     */
    Integer getBalance(long userId);

    /**
     * 专门给送礼业务调用的扣减余额逻辑
     * <p>
     * 为送礼业务提供专用的虚拟币扣减方法，处理虚拟币的消费操作。
     * </p>
     *
     * @param accountTradeReqDTO 账户交易请求DTO，包含了用户ID、扣减数量等信息
     * @return 执行后的响应，可能包含扣减结果等信息
     */
    AccountTradeRespDTO consumeForSendGift(AccountTradeReqDTO accountTradeReqDTO);

    /**
     * 底层需要判断用户余额是否充足，充足则扣减，不足则拦截
     * <p>
     * 判断用户账户的余额是否足够，如果足够则进行扣减，否则拦截交易。
     * </p>
     *
     * @param accountTradeReqDTO 账户交易请求DTO
     * @return 执行后的响应，包含扣减结果或拦截信息
     */
    AccountTradeRespDTO consume(AccountTradeReqDTO accountTradeReqDTO);
}
