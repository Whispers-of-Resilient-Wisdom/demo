package org.qiyu.live.bank.provider.rpc;

import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.bank.dto.PayOrderDTO;
import org.qiyu.live.bank.interfaces.IPayOrderRpc;
import org.qiyu.live.bank.provider.dao.po.PayOrderPO;
import org.qiyu.live.bank.provider.service.IPayOrderService;

/**
 * 支付订单RPC实现类
 * <p>
 * 该类实现了 `IPayOrderRpc` 接口，
 * 提供了与支付订单相关的远程调用服务。
 * 该服务通过调用 `IPayOrderService` 执行实际的业务逻辑，
 * 如插入订单、更新订单状态和处理支付通知。
 * </p>
 */
@DubboService
public class PayOrderRpcImpl implements IPayOrderRpc {

    @Resource
    private IPayOrderService payOrderService;  // 注入支付订单服务

    /**
     * 插入一条新的支付订单记录
     * @param payOrderDTO 支付订单的数据传输对象
     * @return 返回订单的 ID 或其他标识符
     */
    @Override
    public String insertOne(PayOrderDTO payOrderDTO) {
        // 将 DTO 转换为 PO 并调用服务插入订单
        return payOrderService.insertOne(ConvertBeanUtils.convert(payOrderDTO, PayOrderPO.class));
    }

    /**
     * 更新订单的状态
     * @param id 订单的唯一标识符
     * @param status 订单的目标状态
     * @return 返回是否成功
     */
    @Override
    public boolean updateOrderStatus(Long id, Integer status) {
        // 调用支付订单服务更新订单状态
        return payOrderService.updateOrderStatus(id, status);
    }

    /**
     * 更新订单的状态，使用订单号进行更新
     * @param orderId 订单号
     * @param status 订单的目标状态
     * @return 返回是否成功
     */
    @Override
    public boolean updateOrderStatus(String orderId, Integer status) {
        // 调用支付订单服务更新订单状态
        return payOrderService.updateOrderStatus(orderId, status);
    }

    /**
     * 支付通知处理
     * @param payOrderDTO 支付订单数据传输对象
     * @return 返回支付通知是否成功处理
     */
    @Override
    public boolean payNotify(PayOrderDTO payOrderDTO) {
        // 调用支付订单服务处理支付通知
        return payOrderService.payNotify(payOrderDTO);
    }
}
