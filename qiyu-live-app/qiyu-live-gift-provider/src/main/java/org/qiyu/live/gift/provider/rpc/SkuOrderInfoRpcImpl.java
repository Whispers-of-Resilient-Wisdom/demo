package org.qiyu.live.gift.provider.rpc;

import com.alibaba.fastjson.JSON;
import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import com.qiyu.live.common.interfaces.topic.GiftProviderTopicNames;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.qiyu.live.bank.interfaces.IQiyuCurrencyAccountRpc;
import org.qiyu.live.gift.BO.RollBackStockBO;
import org.qiyu.live.gift.constants.SkuOrderInfoEnum;
import org.qiyu.live.gift.dto.*;
import org.qiyu.live.gift.interfaces.ISkuInfoRpc;
import org.qiyu.live.gift.interfaces.ISkuOrderInfoRpc;
import org.qiyu.live.gift.provider.dao.po.SkuInfoPO;
import org.qiyu.live.gift.provider.dao.po.SkuOrderInfoPO;
import org.qiyu.live.gift.provider.service.IShopCarService;
import org.qiyu.live.gift.provider.service.ISkuInfoService;
import org.qiyu.live.gift.provider.service.ISkuOrderInfoService;
import org.qiyu.live.gift.provider.service.ISkuStockInfoService;
import org.qiyu.live.gift.provider.service.impl.RedPacketConfigServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@DubboService
public class SkuOrderInfoRpcImpl implements ISkuOrderInfoRpc {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkuInfoRpcImpl.class);
    @Resource
    private ISkuOrderInfoService skuOrderInfoService;
    @Resource
    private IShopCarService shopCarService;
    @Resource
    private ISkuStockInfoService skuStockInfoService;
    @Resource
    private ISkuInfoService skuInfoService;
    @Resource
    private IQiyuCurrencyAccountRpc qiyuCurrencyAccountRpc;
    @Resource
    private MQProducer mqProducer;


    @Override
    public SkuOrderInfoRespDTO queryByUserIdAndRoomId(Long userId, Integer roomId) {
        return skuOrderInfoService.queryByUserIdAndRoomId(userId, roomId);
    }

    @Override
    public boolean insertOne(SkuOrderInfoReqDTO skuOrderInfoReqDTO) {
        return skuOrderInfoService.insertOne(skuOrderInfoReqDTO) != null;
    }

    @Override
    public boolean updateOrderStatus(SkuOrderInfoReqDTO skuOrderInfoReqDTO) {
        return updateOrderStatus(skuOrderInfoReqDTO);
    }

    @Override
    public SkuPrepareOrderInfoDTO prepareOrder(PrepareOrderReqDTO reqDTO) {
        ShopCarReqDTO shopCarReqDTO = ConvertBeanUtils.convert(reqDTO, ShopCarReqDTO.class);
        ShopCarRespDTO carInfo = shopCarService.getCarInfo(shopCarReqDTO);
        List<ShopCarItemRespDTO> carItemList = carInfo.getSkuCarItemRespDTODTOS();
        if (CollectionUtils.isEmpty(carItemList)) {
            return new SkuPrepareOrderInfoDTO();
        }

        List<Long> skuIdList = carItemList.stream().map(item -> item.getSkuInfoDTO().getSkuId()).collect(Collectors.toList());
        Iterator<Long> iterator = skuIdList.iterator();
        // 进行商品库存的扣减
        while (iterator.hasNext()) {
            Long skuId = iterator.next();
            boolean isSuccess = skuStockInfoService.decrStockNumBySkuIdByLua(skuId, 1);
            LOGGER.info("isSuccess:{}", isSuccess);
            if (!isSuccess) iterator.remove();
        }
        SkuOrderInfoPO skuOrderInfoPO = skuOrderInfoService.insertOne(new SkuOrderInfoReqDTO(
                null, reqDTO.getUserId(), reqDTO.getRoomId(), SkuOrderInfoEnum.PREPARE_PAY.getCode(), skuIdList));
        // 清空购物车
        shopCarService.clearShopCar(shopCarReqDTO);
        // 发送延时MQ：若订单未支付，进行库存回滚
        RollBackStockBO rollBackStockBO=new RollBackStockBO();
        rollBackStockBO.setOrderId(skuOrderInfoPO.getId());
        rollBackStockBO.setUserId(skuOrderInfoPO.getUserId());
        Message message = new Message();
        message.setBody(JSON.toJSONBytes(rollBackStockBO));
        message.setTopic(GiftProviderTopicNames.ROLL_BACK_STOCK);
        //30min时间延时
        message.setDelayTimeLevel(16);
        try {
            SendResult sendResult = mqProducer.send(message);
            LOGGER.info("消息发送成功: {}", sendResult);
        } catch (Exception e) {
            LOGGER.error("消息发送失败: {}",  rollBackStockBO);
            throw new RuntimeException(e);

        }
//
        LOGGER.info("成功");
        // 封装返回对象
        SkuPrepareOrderInfoDTO respDTO = new SkuPrepareOrderInfoDTO();
        List<ShopCarItemRespDTO> itemList = carItemList.stream().filter(item -> skuIdList.contains(item.getSkuInfoDTO().getSkuId())).collect(Collectors.toList());
        respDTO.setSkuPrepareOrderItemInfoDTOS(itemList);
        respDTO.setTotalPrice(itemList.stream().map(item -> item.getSkuInfoDTO().getSkuPrice()).reduce(Integer::sum).orElse(0));
        return respDTO;
    }

    @Override
    public boolean payNow(Long userId, Integer roomId) {
        SkuOrderInfoRespDTO skuOrderInfo = skuOrderInfoService.queryByUserIdAndRoomId(userId, roomId);
        // 判断是否是未支付状态
        if (!skuOrderInfo.getStatus().equals(SkuOrderInfoEnum.PREPARE_PAY.getCode())) {
            return false;
        }
        // 获取到订单中的skuIdList
        List<Long> skuIdList = Arrays.stream(skuOrderInfo.getSkuIdList().split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<SkuInfoPO> skuInfoPOS = skuInfoService.queryBySkuIds(skuIdList);
        // 计算出商品的总价
        Integer totalPrice = skuInfoPOS.stream().map(SkuInfoPO::getSkuPrice).reduce(Integer::sum).orElse(0);
        // 获取余额并判断余额是否充足
        Integer balance = qiyuCurrencyAccountRpc.getBalance(userId);
        if (balance < totalPrice) {
            return false;
        }
        // 余额扣减
        qiyuCurrencyAccountRpc.decr(userId, totalPrice);
        // 更改订单状态未已支付
        SkuOrderInfoReqDTO reqDTO = ConvertBeanUtils.convert(skuOrderInfo, SkuOrderInfoReqDTO.class);
        reqDTO.setStatus(SkuOrderInfoEnum.HAS_PAY.getCode());
        skuOrderInfoService.updateOrderStatus(reqDTO);
        return true;
    }
}
