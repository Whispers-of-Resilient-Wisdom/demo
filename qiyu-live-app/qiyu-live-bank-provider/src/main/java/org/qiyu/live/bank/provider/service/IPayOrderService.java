package org.qiyu.live.bank.provider.service;

import org.qiyu.live.bank.dto.PayOrderDTO;
import org.qiyu.live.bank.provider.dao.po.PayOrderPO;
/**
 * 支付订单服务接口
 * <p>
 * 提供了关于支付订单的管理功能，包括根据订单ID查询、插入新订单、更新订单状态以及处理支付回调。
 * </p>
 */
public interface IPayOrderService {

    /**
     * 根据订单ID查询订单
     * <p>
     * 根据提供的订单ID查询对应的订单信息。
     * </p>
     *
     * @param orderId 订单ID
     * @return 返回订单信息，类型为PayOrderPO
     */
    PayOrderPO queryByOrderId(String orderId);

    /**
     * 插入一条新的支付订单
     * <p>
     * 将支付订单信息插入数据库，并返回订单ID或其他信息。
     * </p>
     *
     * @param payOrderPO 支付订单对象，包含订单的详细信息
     * @return 返回插入订单后的标识（可能是订单ID或其他相关信息）
     */
    String insertOne(PayOrderPO payOrderPO);

    /**
     * 根据订单主键ID更新订单状态
     * <p>
     * 根据订单ID更新订单状态，例如支付成功、支付失败等。
     * </p>
     *
     * @param id     订单的主键ID
     * @param status 新的订单状态
     * @return 返回是否更新成功
     */
    boolean updateOrderStatus(Long id, Integer status);

    /**
     * 根据订单ID更新订单状态
     * <p>
     * 根据订单ID更新订单状态，通常用于支付回调时改变订单状态。
     * </p>
     *
     * @param orderId 订单ID
     * @param status  订单的新状态
     * @return 返回是否更新成功
     */
    boolean updateOrderStatus(String orderId, Integer status);

    /**
     * 处理支付回调
     * <p>
     * 用于处理支付系统回调，更新订单状态并进行相关处理。
     * </p>
     *
     * @param payOrderDTO 支付订单信息，通常包含支付成功与否的状态等
     * @return 返回是否处理成功
     */
    boolean payNotify(PayOrderDTO payOrderDTO);
}
