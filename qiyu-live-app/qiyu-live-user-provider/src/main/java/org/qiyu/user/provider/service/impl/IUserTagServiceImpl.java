package org.qiyu.user.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiyu.live.common.interfaces.ConvertBeanUtils;

import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.qiyu.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.qiyu.live.user.constants.CacheAsyncDeleteCode;
import org.qiyu.live.user.constants.UserTagsEnum;
import org.qiyu.live.user.dto.UserCacheAsyncDeleteDTO;
import org.qiyu.live.user.dto.UserTagDTO;
import org.qiyu.live.user.topic.UserProviderTopicNames;
import org.qiyu.user.provider.dao.mapper.IUserTagMapper;
import org.qiyu.user.provider.dao.po.UserTagPO;
import org.qiyu.user.provider.service.IUserTagService;
import org.qiyu.user.provider.utils.TagInfoUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class IUserTagServiceImpl implements IUserTagService {
    static final String TAG_INFO_01="tag_info_01";
    static final String TAG_INFO_02="tag_info_02";
    static final String TAG_INFO_03="tag_info_03";
    @Resource
    private IUserTagMapper userTagMapper;

    @Resource
    private RedisTemplate<String, UserTagDTO>redisTemplate;
    @Resource
    private UserProviderCacheKeyBuilder cacheKeyBuilder;
    @Resource
    private MQProducer mqProducer;
    @Override
    public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
        // 1. 尝试通过数据库更新用户标签。如果更新成功，返回 true。
        boolean updateStatus = userTagMapper.setTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()) > 0;
        if (updateStatus) {
            deleteUserTagDTOFromRedis(userId);
            return true;
        }
        String setNxKey = cacheKeyBuilder.buildTagLockKey(userId);
        String setNxResult = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                return (String) connection.execute("set", keySerializer.serialize(setNxKey),
                        valueSerializer.serialize("-1"),
                        "NX".getBytes(StandardCharsets.UTF_8),
                        "EX".getBytes(StandardCharsets.UTF_8),
                        "3".getBytes(StandardCharsets.UTF_8));
            }
        });
        if (!"OK".equals(setNxResult)) {
            return false;
        }

        // 4. 如果获取到锁，查询数据库是否已有该用户的标签信息。
        UserTagPO userTagPO = userTagMapper.selectById(userId);
        if (userTagPO != null) {
            // 如果用户已有标签记录，则不需要重复插入，直接返回 false。
            return false;
        }

        // 5. 如果用户标签记录不存在，创建一个新的标签记录。
        userTagPO = new UserTagPO();
        userTagPO.setUserId(userId); // 设置用户ID。

        // 根据传入的枚举值设置标签的具体字段。
        long tag = userTagsEnum.getTag();
        String fieldName = userTagsEnum.getFieldName();
        if (TAG_INFO_01.equals(fieldName)) {
            userTagPO.setTagInfo01(tag); // 设置标签信息 1。
        } else if (TAG_INFO_02.equals(fieldName)) {
            userTagPO.setTagInfo02(tag); // 设置标签信息 2。
        } else if (TAG_INFO_03.equals(fieldName)) {
            userTagPO.setTagInfo03(tag); // 设置标签信息 3。
        }

        // 6. 插入新的标签记录到数据库。
        userTagMapper.insert(userTagPO);

        // 7. 操作完成后，删除 Redis 锁。
        redisTemplate.delete(setNxKey);

        // 8. 返回 true，表示标签设置成功。
        return true;
    }


    @Override
    public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
        boolean cancelStatus = userTagMapper.cancelTag(userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()) > 0;
        if (!cancelStatus) {
            return false;
        }
        deleteUserTagDTOFromRedis(userId);
        return true;
    }

    @Override
    public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
        UserTagDTO userTagDTO = this.queryByUserIdFromRedis(userId);
        if(userTagDTO==null){
            return false;

        }
        String fieldName = userTagsEnum.getFieldName();
        Long match=userTagsEnum.getTag();
        Long tagInfo;
        if(TAG_INFO_01.equals(fieldName)){
             tagInfo= userTagDTO.getTagInfo01();//实际的
        }
        else if(TAG_INFO_02.equals(fieldName)){
            tagInfo= userTagDTO.getTagInfo02();

        }else {
            tagInfo= userTagDTO.getTagInfo03();

        }
        return TagInfoUtils.containTag(tagInfo,match);
    }

    /**
     * 删除redis中的标签信息
     */
    private void deleteUserTagDTOFromRedis(Long userId) {
        String redisKey = cacheKeyBuilder.buildTagKey(userId);
        redisTemplate.delete(redisKey);

        UserCacheAsyncDeleteDTO userCacheAsyncDeleteDTO = new UserCacheAsyncDeleteDTO();
        userCacheAsyncDeleteDTO.setCode(CacheAsyncDeleteCode.USER_TAG_DELETE.getCode());
        Map<String,Object> jsonParam = new HashMap<>();
        jsonParam.put("userId",userId);
        userCacheAsyncDeleteDTO.setJson(JSON.toJSONString(jsonParam));

        Message message = new Message();
        message.setTopic(UserProviderTopicNames.CACHE_ASYNC_DELETE_TOPIC);
        message.setBody(JSON.toJSONString(userCacheAsyncDeleteDTO).getBytes());
        //延迟一秒进行缓存的二次删除
        message.setDelayTimeLevel(1);
        try {
            mqProducer.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 从redis中查询用户标签对象
     *
     */
    private UserTagDTO queryByUserIdFromRedis(Long userId) {
        String redisKey = cacheKeyBuilder.buildTagKey(userId);
        UserTagDTO userTagDTO = redisTemplate.opsForValue().get(redisKey);
        if (userTagDTO != null) {
            return userTagDTO;
        }
        UserTagPO userTagPO = userTagMapper.selectById(userId);
        if (userTagPO == null) {
            return null;
        }
        userTagDTO = ConvertBeanUtils.convert(userTagPO, UserTagDTO.class);
        redisTemplate.opsForValue().set(redisKey, userTagDTO);
        redisTemplate.expire(redisKey,30, TimeUnit.MINUTES);
        return userTagDTO;
    }
}
