package org.qiyu.live.gift.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiyu.live.common.interfaces.topic.GiftProviderTopicNames;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.idea.qiyu.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import org.qiyu.live.gift.BO.RollBackStockBO;
import org.qiyu.live.gift.BO.SendRedPacketBO;
import org.qiyu.live.gift.interfaces.ISkuStockInfoRpc;
import org.qiyu.live.gift.provider.service.ISkuStockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class StartLivingRoomConsumer implements InitializingBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StartLivingRoomConsumer.class);
    @Resource
    private ISkuStockInfoRpc skuStockInfoRpc;
    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;


    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        //基本配置
        mqPushConsumer.setVipChannelEnabled(false);
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + StartLivingRoomConsumer.class.getSimpleName());
        mqPushConsumer.setConsumeMessageBatchMaxSize(10);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        mqPushConsumer.subscribe(GiftProviderTopicNames.START_LIVING_ROOM, "");
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                Long anchorId = JSON.parseObject(msg.getBody(), Long.class);
                boolean isSuccess = skuStockInfoRpc.prepareStockInfo(anchorId);
                if (isSuccess) {
                    LOGGER.info("[StartLivingRoomConsumer] 同步库存到Redis成功，主播id：{}", anchorId);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        mqPushConsumer.start();
        LOGGER.info("mq消费者启动成功,namesrv is {}", rocketMQConsumerProperties.getNameSrv());
    }
}
