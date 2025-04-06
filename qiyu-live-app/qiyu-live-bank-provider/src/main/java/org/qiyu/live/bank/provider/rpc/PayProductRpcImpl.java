package org.qiyu.live.bank.provider.rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.bank.dto.PayProductDTO;
import org.qiyu.live.bank.interfaces.IPayProductRpc;
import org.qiyu.live.bank.provider.service.IPayProductService;

import java.util.List;

/**
 * 支付产品RPC实现类
 * <p>
 * 该类实现了 `IPayProductRpc` 接口，
 * 提供了与支付产品相关的远程调用服务。
 * 通过调用 `IPayProductService`，
 * 该服务可以获取指定类型的支付产品列表或通过产品ID获取特定支付产品的信息。
 * </p>
 */
@DubboService
public class PayProductRpcImpl implements IPayProductRpc {

    @Resource// 注入支付产品服务
    private IPayProductService payProductService;
    /**
     * 获取指定类型的支付产品列表
     * @param type 支付产品的类型
     * @return 返回符合类型的支付产品DTO列表
     */
    @Override
    public List<PayProductDTO> products(Integer type) {
        return payProductService.products(type);
    }
    /**
     * 根据产品ID获取支付产品信息
     * @param productId 产品ID
     * @return 返回与给定产品ID对应的支付产品DTO
     */
    @Override
    public PayProductDTO getByProductId(Integer productId) {
        return payProductService.getByProductId(productId);
    }
}
