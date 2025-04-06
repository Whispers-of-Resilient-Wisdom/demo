package org.qiyu.live.gift.provider.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiyu.live.common.interfaces.ConvertBeanUtils;

import com.qiyu.live.common.interfaces.Enum.CommonStatusEnum;
import com.qiyu.live.common.interfaces.topic.GiftProviderTopicNames;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import org.qiyu.live.framework.redis.starter.key.GiftProviderCacheKeyBuilder;
import org.qiyu.live.gift.dto.GiftConfigDTO;
import org.qiyu.live.gift.provider.dao.mapper.GiftConfigMapper;
import org.qiyu.live.gift.provider.dao.po.GiftConfigPO;
import org.qiyu.live.gift.provider.service.IGiftConfigService;
import org.qiyu.live.gift.provider.service.bo.GiftCacheRemoveBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
/**
 * 礼物配置服务实现类，提供了礼物配置的增、删、查、改等操作。
 * 该类通过访问数据库、缓存和消息队列来优化性能，并提供对礼物配置的管理。
 *
 */
@Service
public class GiftConfigServiceImpl implements IGiftConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiftConfigServiceImpl.class);

    @Resource
    private GiftConfigMapper giftConfigMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private GiftProviderCacheKeyBuilder cacheKeyBuilder;

    @Resource
    private MQProducer mqProducer;

    /**
     * 根据礼物ID查询礼物配置。
     * <p>
     * 本方法首先从缓存中获取礼物配置信息，如果缓存中不存在或已过期，则从数据库中查询并将结果存入缓存。
     * </p>
     *
     * @param giftId 礼物的唯一标识ID
     * @return 如果找到该礼物配置，则返回 {@link GiftConfigDTO}；否则返回 null
     */
    @Override
    public GiftConfigDTO getByGiftId(Integer giftId) {
        String cacheKey = cacheKeyBuilder.buildGiftConfigCacheKey(giftId);
        // 使用缓存去减少对数据库的访问
        GiftConfigDTO giftConfigDTO = (GiftConfigDTO) redisTemplate.opsForValue().get(cacheKey);
        if (giftConfigDTO != null) {
            if (giftConfigDTO.getGiftId() != null) {
                redisTemplate.expire(cacheKey, 60, TimeUnit.MINUTES);
                return giftConfigDTO;
            }
            // 空值缓存
            return null;
        }
        // 如果缓存中没有，查询数据库
        LambdaQueryWrapper<GiftConfigPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GiftConfigPO::getGiftId, giftId);
        queryWrapper.eq(GiftConfigPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
        queryWrapper.last("limit 1");
        GiftConfigPO giftConfigPO = giftConfigMapper.selectOne(queryWrapper);
        // 将查询结果存入缓存
        if (giftConfigPO != null) {
            GiftConfigDTO configDTO = ConvertBeanUtils.convert(giftConfigPO, GiftConfigDTO.class);
            redisTemplate.opsForValue().set(cacheKey, configDTO, 60, TimeUnit.MINUTES);
            return configDTO;
        }
        // 防止对数据库的二次请求
        redisTemplate.opsForValue().set(cacheKey, new GiftConfigDTO(), 5, TimeUnit.MINUTES);
        return null;
    }

    /**
     * 查询所有有效的礼物配置。
     * <p>
     * 本方法首先从缓存中获取礼物配置列表，如果缓存中不存在，则从数据库中查询并存入缓存。
     * </p>
     *
     * @return 返回所有有效的礼物配置信息列表
     */
    @Override
    public List<GiftConfigDTO> queryGiftList() {
        String cacheKey = cacheKeyBuilder.buildGiftListCacheKey();
        List<GiftConfigDTO> cacheList = Objects.requireNonNull(redisTemplate.opsForList().range(cacheKey, 0, 100)).stream()
                .map(x -> (GiftConfigDTO) x).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(cacheList)) {
            if (cacheList.get(0).getGiftId() != null) {
                redisTemplate.expire(cacheKey, 60, TimeUnit.MINUTES);
                return cacheList;
            }
            return Collections.emptyList();
        }
        // 如果缓存为空，则查询数据库
        LambdaQueryWrapper<GiftConfigPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GiftConfigPO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
        List<GiftConfigPO> giftConfigPOList = giftConfigMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(giftConfigPOList)) {
            List<GiftConfigDTO> resultList = ConvertBeanUtils.convertList(giftConfigPOList, GiftConfigDTO.class);
            boolean trySetToRedis = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(cacheKeyBuilder.buildGiftListLockCacheKey(), 1, 3, TimeUnit.SECONDS));
            if(trySetToRedis) {
                redisTemplate.opsForList().leftPushAll(cacheKey, resultList.toArray());
                redisTemplate.expire(cacheKey, 60, TimeUnit.MINUTES);
            }
            return resultList;
        }
        // 如果没有查询到数据，设置空列表缓存
        redisTemplate.opsForList().leftPush(cacheKey, new GiftConfigDTO());
        redisTemplate.expire(cacheKey, 5, TimeUnit.MINUTES);
        return Collections.emptyList();
    }

    /**
     * 插入单个礼物配置信息。
     * <p>
     * 本方法将礼物配置信息插入到数据库，并删除相关缓存，确保缓存数据的一致性。
     * </p>
     *
     * @param giftConfigDTO 礼物配置信息的数据传输对象
     */
    @Override
    public void insertOne(GiftConfigDTO giftConfigDTO) {
        GiftConfigPO giftConfigPO = ConvertBeanUtils.convert(giftConfigDTO, GiftConfigPO.class);
        giftConfigPO.setStatus(CommonStatusEnum.VALID_STATUS.getCode());
        giftConfigMapper.insert(giftConfigPO);
        redisTemplate.delete(cacheKeyBuilder.buildGiftListCacheKey());
        GiftCacheRemoveBO giftCacheRemoveBO = new GiftCacheRemoveBO();
        giftCacheRemoveBO.setRemoveListCache(true);
        Message message = new Message();
        message.setTopic(GiftProviderTopicNames.REMOVE_GIFT_CACHE);
        message.setBody(JSON.toJSONBytes(giftCacheRemoveBO));
        message.setDelayTimeLevel(1);
        try {
            SendResult sendResult = mqProducer.send(message);
            LOGGER.info("[insertOne] sendResult is {}", sendResult);
        } catch (Exception e) {
            LOGGER.info("[insertOne] mq send error: {}", e);
        }
    }

    /**
     * 更新单个礼物配置信息。
     * <p>
     * 本方法将更新数据库中的礼物配置，并删除相关缓存，确保缓存数据的一致性。
     * </p>
     *
     * @param giftConfigDTO 礼物配置信息的数据传输对象，包含更新后的配置信息
     */
    @Override
    public void updateOne(GiftConfigDTO giftConfigDTO) {
        GiftConfigPO giftConfigPO = ConvertBeanUtils.convert(giftConfigDTO, GiftConfigPO.class);
        giftConfigMapper.updateById(giftConfigPO);
        redisTemplate.delete(cacheKeyBuilder.buildGiftListCacheKey());
        redisTemplate.delete(cacheKeyBuilder.buildGiftConfigCacheKey(giftConfigDTO.getGiftId()));
        GiftCacheRemoveBO giftCacheRemoveBO = new GiftCacheRemoveBO();
        giftCacheRemoveBO.setRemoveListCache(true);
        giftCacheRemoveBO.setGiftId(giftConfigDTO.getGiftId());
        Message message = new Message();
        message.setTopic(GiftProviderTopicNames.REMOVE_GIFT_CACHE);
        message.setBody(JSON.toJSONBytes(giftCacheRemoveBO));
        message.setDelayTimeLevel(1);
        try {
            SendResult sendResult = mqProducer.send(message);
            LOGGER.info("[updateOne] sendResult is {}", sendResult);
        } catch (Exception e) {
            LOGGER.info("[updateOne] mq send error: {}", e);
        }
    }
}
