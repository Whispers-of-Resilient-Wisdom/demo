package org.qiyu.live.gift.provider.consumer;



import com.alibaba.fastjson.JSON;
import com.qiyu.live.common.interfaces.topic.GiftProviderTopicNames;
import jakarta.annotation.Resource;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.idea.qiyu.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import org.qiyu.live.gift.BO.RollBackStockBO;
import org.qiyu.live.gift.dto.RollBackStockDTO;
import org.qiyu.live.gift.provider.service.ISkuStockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class StockRollBackConsumer implements InitializingBean {


    private static final Logger LOGGER = LoggerFactory.getLogger(StockRollBackConsumer.class);
    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;
    @Resource
    private ISkuStockInfoService skuStockInfoService;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
         //基本配置
        mqPushConsumer.setVipChannelEnabled(false);
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" +StockRollBackConsumer.class.getSimpleName());
        mqPushConsumer.setConsumeMessageBatchMaxSize(1);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        mqPushConsumer.subscribe(GiftProviderTopicNames.ROLL_BACK_STOCK, "");
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {

          try {
              String mqStr = String.valueOf(msgs.get(0));
              RollBackStockBO rollBackStockBO = JSON.parseObject(mqStr, RollBackStockBO.class);
              skuStockInfoService.stockRollBackHandler(rollBackStockBO);
          }
          catch (Exception e) {
              LOGGER.error(e.getMessage(), e);
              LOGGER.error("回滚出错");
              return ConsumeConcurrentlyStatus.RECONSUME_LATER;

          }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        mqPushConsumer.start();
        LOGGER.info("mq消费者启动成功,namesrv is {}", rocketMQConsumerProperties.getNameSrv());
    }


}
