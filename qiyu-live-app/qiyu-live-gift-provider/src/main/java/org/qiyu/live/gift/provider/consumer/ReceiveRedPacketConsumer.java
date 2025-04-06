package org.qiyu.live.gift.provider.consumer;

import com.alibaba.fastjson.JSONObject;
import com.qiyu.live.common.interfaces.topic.GiftProviderTopicNames;
import jakarta.annotation.Resource;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.idea.qiyu.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import org.qiyu.live.gift.BO.SendRedPacketBO;
import org.qiyu.live.gift.provider.service.IRedPacketConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 处理抢红包mq消息的消费者
 */
@Component
public class ReceiveRedPacketConsumer implements InitializingBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveRedPacketConsumer.class);
    @Resource
    private IRedPacketConfigService redPacketConfigService;

    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;


    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        mqPushConsumer.setVipChannelEnabled(false);
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + ReceiveRedPacketConsumer.class.getSimpleName());
        mqPushConsumer.setConsumeMessageBatchMaxSize(10);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 异步增加
        mqPushConsumer.subscribe(GiftProviderTopicNames.RECEIVE_RED_PACKET, "");
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                SendRedPacketBO  reqBO = JSONObject.parseObject(msg.getBody(), SendRedPacketBO.class);
                redPacketConfigService.receiveRedPacketHandler( reqBO.getReqDTO(),reqBO.getPrice());
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        mqPushConsumer.start();
        LOGGER.info("mq消费者启动成功, namesrv is {}", rocketMQConsumerProperties.getNameSrv());
    }


}
