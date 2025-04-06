package org.qiyu.live.bank.provider.service;

import org.qiyu.live.bank.dto.PayProductDTO;

import java.util.List;

/**
 * 支付商品服务接口
 * <p>
 * 提供了获取支付商品信息的功能，可以根据业务类型获取商品列表，或者根据产品ID查询特定商品。
 * </p>
 */
public interface IPayProductService {

    /**
     * 返回根据业务类型筛选的商品信息
     * <p>
     * 根据提供的type参数，返回符合该业务类型的商品列表。
     * </p>
     *
     * @param type 业务场景类型，例如虚拟商品、实物商品等
     * @return 商品列表
     */
    List<PayProductDTO> products(Integer type);

    /**
     * 根据产品ID查询具体的商品信息
     * <p>
     * 根据提供的产品ID查询单个商品的详细信息。
     * </p>
     *
     * @param productId 产品ID
     * @return 对应的PayProductDTO对象，包含商品详细信息
     */
    PayProductDTO getByProductId(Integer productId);
}
