package org.qiyu.user.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.qiyu.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.qiyu.live.user.dto.UserDTO;
import org.qiyu.user.provider.dao.mapper.IUserMapper;
import org.qiyu.user.provider.dao.po.UserPo;
import org.qiyu.user.provider.service.IUserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class IUserServiceImpl implements IUserService {
    @Resource
    private IUserMapper userMapper;
    @Resource
    private RedisTemplate<String,UserDTO> redisTemplate;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Resource
    private MQProducer mqProducer;
    @Override
    public UserDTO getUserId(Long UserId)
    {
        if(UserId==null) {
            return null;
        }
        String key= userProviderCacheKeyBuilder.buildUserInfoKey(UserId);
        UserDTO userDTO = redisTemplate.opsForValue().get(key);
        if(userDTO!=null){
        return userDTO;

                         }

        userDTO=ConvertBeanUtils.convert(userMapper.selectById(UserId), UserDTO.class);
        if(userDTO!=null){redisTemplate.opsForValue().set(key,userDTO,30, TimeUnit.MINUTES);}
        return userDTO;


    }

    public void updateUserInfo(UserDTO userDTO) {
        // 1. 将 UserDTO 对象转换为 UserPo 对象，通常用于数据库操作。
        UserPo convert = ConvertBeanUtils.convert(userDTO, UserPo.class);

        // 2. 调用 userMapper 的 updateById 方法，更新数据库中的用户信息。
        userMapper.updateById(convert);

        // 3. 构建缓存中的用户信息键（key），该键通过 userId 获取用户信息。
        String key = userProviderCacheKeyBuilder.buildUserInfoKey(userDTO.getUserId());

        // 4. 立即删除缓存中的该用户信息。
        redisTemplate.delete(key);

        try {
            // 5. 构建一个消息对象用于发送到消息队列。消息内容是用户信息（以字节形式存储）。
            Message message = new Message();
            message.setBody(JSON.toJSONString(userDTO).getBytes());  // 将 UserDTO 对象转换为 JSON 字符串，并转换为字节数组
            message.setTopic("user-update-cache");  // 消息的主题，用于标识消息类型
            message.setDelayTimeLevel(1);  // 设置延迟级别，1 代表延迟一秒发送消息

            // 6. 将消息发送到消息队列，目的是触发二次缓存删除操作。
            mqProducer.send(message);

        } catch (Exception e) {
            // 7. 如果发送消息过程中出现异常，抛出运行时异常
            throw new RuntimeException(e);
        }
    }




    @Override
    public boolean insertOne(UserDTO userDTO) {
            if(userDTO==null||userDTO.getUserId()==null) {return false;}
            userMapper.insert(ConvertBeanUtils.convert(userDTO, UserPo.class));
            return true;
    }



    @Override
    public Map<Long, UserDTO> batchQueryUser(List<Long> userIdList) {
        // 如果用户 ID 列表为空，直接返回空的 Map
        if (CollectionUtils.isEmpty(userIdList)) {
            return Maps.newHashMap();
        }

        // 过滤掉 null 或小于等于 100 的用户 ID
        userIdList = userIdList.stream().filter(id -> id != null && id > 100).collect(Collectors.toList());
        // 如果过滤后的用户 ID 列表为空，返回空的 Map
        if (CollectionUtils.isEmpty(userIdList)) {
            return Maps.newHashMap();
        }

        // 根据用户 ID 列表构造出 Redis 缓存中的键列表
        List<String> userInfoKeyList = userIdList.stream()
                .map(userId -> userProviderCacheKeyBuilder.buildUserInfoKey(userId)) // 使用 cacheKeyBuilder 构建缓存键
                .collect(Collectors.toList());

        // 从 Redis 中批量获取用户信息
        List<UserDTO> userDTOList = redisTemplate.opsForValue().multiGet(userInfoKeyList).stream().filter(x -> x != null).collect(Collectors.toList());

        // 如果缓存中返回的数据数量和请求的用户 ID 数量一致，说明缓存数据已完整，直接返回
        if (userDTOList.size() ==  userIdList.size()) {
            return userDTOList.stream().collect(Collectors.toMap(UserDTO::getUserId, x -> x));
        }

        // 获取缓存中存在的用户 ID 列表
        List<Long> userIdInCache = userDTOList.stream().map(userDTO -> userDTO.getUserId()).toList();

        // 获取那些不在缓存中的用户 ID 列表
        List<Long> userIdNotInCache = userIdList.stream()
                .filter(userId -> !userIdInCache.contains(userId))
                .toList();

        // 对于不在缓存中的用户 ID，去数据库查询
        Map<Long, List<Long>>useridMap=userIdNotInCache.stream().collect(Collectors.groupingBy(x -> x % 100));
        List<UserDTO> dbQueryList=new CopyOnWriteArrayList<>();

        useridMap.values().parallelStream().forEach(queryUserIdList->{
    dbQueryList.addAll(ConvertBeanUtils.convertList(userMapper.selectBatchIds(queryUserIdList), UserDTO.class));
                                           }
    );//不在缓存的数据保存到到redis
        if(!dbQueryList.isEmpty()){
            Map<String, UserDTO>saveCacheMap=dbQueryList.stream().collect(Collectors.toMap(userDTO -> userProviderCacheKeyBuilder.buildUserInfoKey(userDTO.getUserId()), x -> x));
            redisTemplate.opsForValue().multiSet(saveCacheMap);//保存到
            redisTemplate.executePipelined(new SessionCallback<>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    for (String key : saveCacheMap.keySet()) {
                        operations.expire((K) key, 30, TimeUnit.MINUTES);

                    }
                    return null;
                }
            });
            userDTOList.addAll(dbQueryList);
        }
       return userDTOList.stream().collect(Collectors.toMap(UserDTO::getUserId, x -> x));
   }
    }