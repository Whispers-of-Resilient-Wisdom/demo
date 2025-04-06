package org.qiyu.live.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiyu.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import jakarta.annotation.Resource;

import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;


import org.qiyu.live.core.server.service.IMsgAckCheckService;
import org.qiyu.live.framework.redis.starter.key.ImCoreServerProviderCacheKeyBuilder;
import org.qiyu.live.im.DTO.ImMsgBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
确认消息是否收到，未收到则向mq发送消息，随后发送给他
 */
@Service
public class MsgAckCheckServiceImpl implements IMsgAckCheckService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgAckCheckServiceImpl.class);

    @Resource
    private MQProducer mqProducer;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private ImCoreServerProviderCacheKeyBuilder cacheKeyBuilder;
    /**
     * 删除消息确认记录（当收到 ACK 时调用）
     * @param imMsgBody 消息体
     */
    @Override
    public void doMsgAck(ImMsgBody imMsgBody) {
        String key = cacheKeyBuilder.buildImAckMapKey(imMsgBody.getUserId(), imMsgBody.getAppId());
        redisTemplate.opsForHash().delete(key, imMsgBody.getMsgId());
        redisTemplate.expire(key,30, TimeUnit.MINUTES);
    }
    /**
     * 记录消息发送状态（记录重发次数）
     * @param imMsgBody 消息体
     * @param times 重试次数
     */
    @Override
    public void recordMsgAck(ImMsgBody imMsgBody, int times) {
        String key = cacheKeyBuilder.buildImAckMapKey(imMsgBody.getUserId(), imMsgBody.getAppId());
        redisTemplate.opsForHash().put(key, imMsgBody.getMsgId(), times);
        redisTemplate.expire(key,30, TimeUnit.MINUTES);
    }
    /**
     * 发送延迟消息（用于检测未确认的消息）
     * @param imMsgBody 消息体
     */
    @Override
    public void sendDelayMsg(ImMsgBody imMsgBody) {
        String json = JSON.toJSONString(imMsgBody);
        LOGGER.info("json:{}",json);
        Message message = new Message();
        message.setBody(json.getBytes());
        message.setTopic(ImCoreServerProviderTopicNames.QIYU_LIVE_IM_ACK_MSG_TOPIC);
        //等级1 -> 1s，等级2 -> 5s mqq的延长等级，还会影响mq发送消息的
        message.setDelayTimeLevel(2);
        try {
            SendResult sendResult = mqProducer.send(message);
            LOGGER.info("[MsgAckCheckServiceImpl] msg is {},sendResult is {}", json, sendResult);
        } catch (Exception e) {
            LOGGER.error("[MsgAckCheckServiceImpl] error is ", e);
        }
    }
    /**
     * 获取消息的重试次数（用于判断消息是否需要重发）
     * @param msgId 消息 ID
     * @param userId 用户 ID
     * @param appId 应用 ID
     * @return 返回消息的重试次数，若未找到则返回 -1
     */
    @Override
    public int getMsgAckTimes(String msgId, long userId, int appId) {
        Object value = redisTemplate.opsForHash().get(cacheKeyBuilder.buildImAckMapKey(userId, appId), msgId);
        if (value == null) {
            return -1;
        }
        return (int) value;
    }
}
