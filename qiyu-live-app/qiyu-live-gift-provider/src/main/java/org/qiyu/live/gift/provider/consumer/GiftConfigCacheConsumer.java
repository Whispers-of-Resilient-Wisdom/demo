package org.qiyu.live.gift.provider.consumer;

import com.alibaba.fastjson.JSON;
import com.qiyu.live.common.interfaces.topic.GiftProviderTopicNames;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.idea.qiyu.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import org.qiyu.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import org.qiyu.live.gift.provider.service.bo.GiftCacheRemoveBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 在容器初始化时，消费关于礼物配置缓存的主题消息。
 * <p>
 * 本类负责从 RocketMQ 消费与礼物缓存相关的消息，并根据消息内容删除 Redis 中的缓存数据。
 * </p>
 * <p>
 * 消费者会订阅更新礼物缓存的消息主题，并在收到消息后执行缓存删除操作。
 * </p>
 *
 * @since 1.0
 */
@Configuration
public class GiftConfigCacheConsumer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiftConfigCacheConsumer.class);

    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private GiftProviderCacheKeyBuilder cacheKeyBuilder;

    /**
     * 初始化方法，配置并启动 RocketMQ 消费者。
     * <p>
     * 在应用启动时，创建并启动 RocketMQ 消费者，订阅消息主题，并处理消息。
     * </p>
     *
     * @throws Exception 如果初始化过程出现错误
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        mqPushConsumer.setVipChannelEnabled(false);
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + GiftConfigCacheConsumer.class.getSimpleName());
        mqPushConsumer.setConsumeMessageBatchMaxSize(10);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 订阅礼物缓存数据更新的消息主题
        mqPushConsumer.subscribe(GiftProviderTopicNames.REMOVE_GIFT_CACHE, "");
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                GiftCacheRemoveBO giftCacheRemoveBO = JSON.parseObject(new String(msg.getBody()), GiftCacheRemoveBO.class);
                // 根据消息内容删除缓存
                if (giftCacheRemoveBO.isRemoveListCache()) {
                    redisTemplate.delete(cacheKeyBuilder.buildGiftListCacheKey());
                }
                if (giftCacheRemoveBO.getGiftId() > 0) {
                    redisTemplate.delete(cacheKeyBuilder.buildGiftConfigCacheKey(giftCacheRemoveBO.getGiftId()));
                }
                LOGGER.info("[GiftConfigCacheConsumer] 移除礼物缓存");
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        mqPushConsumer.start();
        LOGGER.info("mq消费者启动成功, namesrv is {}", rocketMQConsumerProperties.getNameSrv());
    }
}

