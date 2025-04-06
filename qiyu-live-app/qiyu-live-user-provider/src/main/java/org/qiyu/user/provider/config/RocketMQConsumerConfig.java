package org.qiyu.user.provider.config;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.qiyu.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.qiyu.live.user.constants.CacheAsyncDeleteCode;
import org.qiyu.live.user.dto.UserCacheAsyncDeleteDTO;
import org.qiyu.live.user.topic.UserProviderTopicNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.List;

@Configuration
public class RocketMQConsumerConfig implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQConsumerConfig.class);
    @Resource
    private RocketMQConsumerProperties consumerProperties;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Resource
    private UserProviderCacheKeyBuilder cacheKeyBuilder;
    @Override
    public void afterPropertiesSet() throws Exception {
        initConsumer();
    }
    public void initConsumer() {
        try {
            // 1. 初始化 RocketMQ 消费者实例（DefaultMQPushConsumer），用于接收消息
            DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();

            // 2. 设置消费者的 NameServer 地址，用于连接 RocketMQ 服务
            defaultMQPushConsumer.setNamesrvAddr(consumerProperties.getNameSrv());

            // 3. 设置消费者所属的消费组名称，用于标识不同消费者的分组
            defaultMQPushConsumer.setConsumerGroup(consumerProperties.getGroupName());

            // 4. 设置每次拉取的消息最大数量，这里是一次只拉取一条消息
            defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);

            // 5. 设置消费者从哪里开始消费消息，这里是从消息队列的最早位置开始消费
            defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            // 6. 订阅一个主题 "user-update-cache"，* 表示订阅所有的标签（即所有消息类型）
            defaultMQPushConsumer.subscribe(UserProviderTopicNames.CACHE_ASYNC_DELETE_TOPIC, "*");

            // 7. 设置消息监听器，定义消费消息的具体处理逻辑
            defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    String json = new String(msgs.get(0).getBody());
                    UserCacheAsyncDeleteDTO userCacheAsyncDeleteDTO = JSON.parseObject(json, UserCacheAsyncDeleteDTO.class);
                    if (CacheAsyncDeleteCode.USER_INFO_DELETE.getCode() == userCacheAsyncDeleteDTO.getCode()) {
                        Long userId = JSON.parseObject(userCacheAsyncDeleteDTO.getJson()).getLong("userId");
                        redisTemplate.delete(cacheKeyBuilder.buildUserInfoKey(userId));
                        LOGGER.info("延迟删除用户信息缓存，userId is {}",userId);
                    } else if (CacheAsyncDeleteCode.USER_TAG_DELETE.getCode() == userCacheAsyncDeleteDTO.getCode()) {
                        Long userId = JSON.parseObject(userCacheAsyncDeleteDTO.getJson()).getLong("userId");
                        redisTemplate.delete(cacheKeyBuilder.buildTagKey(userId));
                        LOGGER.info("延迟删除用户标签缓存，userId is {}",userId);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            // 14. 启动消费者开始消费消息
            defaultMQPushConsumer.start();

            // 15. 打印日志，表示消费者启动成功，并输出 NameServer 地址
            LOGGER.info("mq 消费者启动成功,nameSrv is {}", consumerProperties.getNameSrv());
        } catch (MQClientException e) {
            // 16. 如果初始化消费者时发生异常，捕获并抛出运行时异常
            throw new RuntimeException(e);
        }
    }

}
