package org.qiyu.live.living.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.qiyu.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import org.idea.qiyu.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import org.qiyu.live.im.core.server.interfaces.dto.ImOfflineDTO;

import org.qiyu.live.living.provider.service.ILivingRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 直播间用户下线消费者。
 * 该类通过 RocketMQ 消费用户下线消息，并调用 `ILivingRoomService` 进行用户下线处理。
 * 消费者从消息队列中订阅 IM 下线事件，处理用户下线逻辑。
 */
@Component
public class LivingRoomOfflineConsumer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LivingRoomOfflineConsumer.class);

    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties; // RocketMQ 配置
    @Resource
    private ILivingRoomService livingRoomService; // 直播间服务

    /**
     * 初始化时启动 RocketMQ 消费者。
     * 设置消费者相关配置，订阅消息主题，并设置消息监听器来处理下线消息。
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();

        // 关闭 VIP Channel，避免兼容老版本时引起问题
        mqPushConsumer.setVipChannelEnabled(false);
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv()); // 设置 NameServer 地址
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + LivingRoomOfflineConsumer.class.getSimpleName()); // 设置消费者组
        mqPushConsumer.setConsumeMessageBatchMaxSize(10); // 每次拉取10条消息
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET); // 从最后偏移量开始消费
        mqPushConsumer.subscribe(ImCoreServerProviderTopicNames.IM_OFFLINE_TOPIC, ""); // 订阅下线消息主题

        // 设置消息监听器
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                // 处理用户下线消息
                livingRoomService.userOfflineHandler(JSON.parseObject(new String(msg.getBody()), ImOfflineDTO.class));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 确认消息消费成功
        });

        // 启动消费者
        mqPushConsumer.start();
        LOGGER.info("mq消费者启动成功, namesrv is {}", rocketMQConsumerProperties.getNameSrv());
    }
}

