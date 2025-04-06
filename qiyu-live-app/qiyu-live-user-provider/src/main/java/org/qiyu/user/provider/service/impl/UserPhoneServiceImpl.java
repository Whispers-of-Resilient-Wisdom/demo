package org.qiyu.user.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import com.qiyu.live.common.interfaces.Enum.CommonStatusEnum;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.qiyu.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.qiyu.live.id.generate.interfaces.Enum.IdTypeEnum;
import org.qiyu.live.id.generate.interfaces.IdBuilderRpc;
import org.qiyu.live.user.dto.UserDTO;
import org.qiyu.live.user.dto.UserLoginDTO;
import org.qiyu.live.user.dto.UserPhoneDTO;
import org.qiyu.user.provider.Rpc.UserPhoneRpcImpl;
import org.qiyu.user.provider.dao.mapper.IUserPhoneMapper;
import org.qiyu.user.provider.dao.po.UserPhonePO;
import org.qiyu.user.provider.service.IUserPhoneService;
import org.qiyu.user.provider.service.IUserService;
import org.qiyu.user.provider.service.bo.UserRegisterBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserPhoneServiceImpl implements IUserPhoneService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPhoneRpcImpl.class);
    @Resource
    private IUserPhoneMapper userPhoneMapper;

    @Resource
    private IUserService userService;
    @DubboReference(protocol = "dubbo")
    private IdBuilderRpc idBuilderRpc;
    @Resource
    private RedisTemplate<String, UserPhoneDTO> redisTemplate;
    @Resource
    private RedisTemplate<String, Object>
            stringObjectRedisTemplate;
    @Resource
    private UserProviderCacheKeyBuilder cacheKeyBuilder;
    @Override
    public UserLoginDTO login(String phone) {

        if (phone == null) {
            return UserLoginDTO.loginError("手机号不能为空");
        }
        UserPhoneDTO userPhoneDTO = this.queryByPhone(phone);
        String token;
        if (userPhoneDTO != null) {
            LOGGER.error("phone is {} 已经被注册,可以直接登录",
                    phone);
            Long userId = userPhoneDTO.getUserId();
            token=this.createAndSaveToken(userId);
            return
                    UserLoginDTO.loginSuccess(userId,token);
        }
        //进行注册操作
        UserRegisterBO userRegisterBO = registerUser(phone);
        Long userId = userRegisterBO.getUserId();
        // todo 应该将这转交给account service,现在可删除它
        token=this.createAndSaveToken(userRegisterBO.getUserId());
        LOGGER.error("userPhoneDTO is {} 注册成功，进行登录", (Object) null);

        return UserLoginDTO.loginSuccess(userId,token);
    }

    /**
     * function:登录，返回token,
     * @param userId 用户Id
     * @return token token
     */
    private String createAndSaveToken(Long userId) {

        String token = UUID.randomUUID().toString();
        //todo
        String tokenKey = cacheKeyBuilder.buildUserLoginTokenKey(token);
        //key is tokenKey,value is userId ,EndTime is after 30 days
        LOGGER.error("userId is {}", userId);
        stringObjectRedisTemplate.opsForValue().set(tokenKey,
                userId, 30, TimeUnit.DAYS);
        return token;
    }

    /**
     * function:使用phone注册用户账号
     *
     * @param phone 手机号码
     * @return userRegisterBO 注册好的账户信息
     */
    private UserRegisterBO registerUser(String phone) {
        Long newUserId = idBuilderRpc.increaseUnSeqId(IdTypeEnum.USER_ID.getCode());

        if (newUserId == null) {
            LOGGER.error("Failed to generate new user ID for phone {}", phone);
            throw new RuntimeException("User ID generation failed");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(newUserId);

        //设置默认昵称是旗鱼-+"userId"
        userDTO.setNickName("旗鱼用户-" + newUserId);
        userService.insertOne(userDTO);
        //将userId与phone绑定
        this.insert(phone, newUserId);
        UserRegisterBO userRegisterBO = new UserRegisterBO();
        userRegisterBO.setPhone(phone);
        userRegisterBO.setUserId(newUserId);
        redisTemplate.delete(cacheKeyBuilder.buildUserPhoneObjKey(phone));

        LOGGER.info("New user registered successfully: userId={}, phone={}", newUserId, phone);
        //返回注册成功的对象
        return userRegisterBO;
    }

    @Override
    public List<UserPhoneDTO> queryByUserId(Long userId) {
        String redisKey =
                cacheKeyBuilder.buildUserPhoneListKey(userId);
        List<UserPhoneDTO> userPhoneCacheList = redisTemplate.opsForList().range(redisKey, 0, -1);
        if (!CollectionUtils.isEmpty(userPhoneCacheList)) {
            //可能是缓存的空值
            if (userPhoneCacheList.get(0).getUserId() == null) {
                return Collections.emptyList();
            }
            //命中
            return userPhoneCacheList;
        }
        userPhoneCacheList = this.queryByUserIdFromDB(userId);
        int expireTime = 30;
        if (CollectionUtils.isEmpty(userPhoneCacheList)) {
            //防止缓存击穿
            userPhoneCacheList = List.of(new UserPhoneDTO());
            expireTime = 5;
        }
        redisTemplate.opsForList().leftPushAll(redisKey, userPhoneCacheList);
        redisTemplate.expire(redisKey, expireTime, TimeUnit.MINUTES);
        return userPhoneCacheList;
    }

    /**
     * function:通过phone查询用户信息
     * @param phone 电话号码
     * @return UserPhoneDTO
     */
    public UserPhoneDTO queryByPhone(String phone)  {
        String redisKey = cacheKeyBuilder.buildUserPhoneObjKey(phone);
        UserPhoneDTO userPhoneDTO =
                redisTemplate.opsForValue().get(redisKey);
        if (userPhoneDTO != null && userPhoneDTO.getUserId() != null) {
            return userPhoneDTO;
        }
        if (userPhoneDTO != null && userPhoneDTO.getUserId() == null) {
            //缓存是空缓存
            return null;
        }
        //通过mysql查询用户信息
        userPhoneDTO = this.queryByPhoneFromDb(phone);
        if (userPhoneDTO != null) {
            redisTemplate.opsForValue().set(redisKey,
                    userPhoneDTO, 30, TimeUnit.MINUTES);
            return userPhoneDTO;
        }
        //缓存一个空对象，防止缓存击穿
        redisTemplate.opsForValue().set(redisKey, new
                UserPhoneDTO(), 1, TimeUnit.MINUTES);
        return null;
    }
    @Override
    /*
      function:注册好的用户插入数据库中
     */
    public UserPhoneDTO insert(String phone, Long userId) {
        UserPhonePO userPhonePO = new UserPhonePO();
        userPhonePO.setUserId(userId);
        userPhonePO.setPhone(phone);

        userPhonePO.setStatus(CommonStatusEnum.VALID_STATUS.getCode());
        userPhoneMapper.insert(userPhonePO);
        return ConvertBeanUtils.convert(userPhonePO,
                UserPhoneDTO.class);
    }

    /**
     * function:从mysql查询数据
     * @param userId 用户的userId
     * @return LIst<UserPhoneDTO></>
     */
    public List<UserPhoneDTO> queryByUserIdFromDB(Long userId) {
        //底层会走用户手机号的主键索引
        LambdaQueryWrapper<UserPhonePO> queryWrapper = new
                LambdaQueryWrapper<>();
        queryWrapper.eq(UserPhonePO::getUserId, userId);

        queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
        return
                ConvertBeanUtils.convertList(userPhoneMapper.selectList(queryWrapper), UserPhoneDTO.class);
    }

    /**
     *function:通过手机号通过mysql查询用户的userId
     * @param phone 用户的手机号码
     *
     */
    public UserPhoneDTO queryByPhoneFromDb(String phone) {
        //底层会走用户 id 的辅助索引
        LambdaQueryWrapper<UserPhonePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPhonePO::getPhone, phone);
        queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
        queryWrapper.orderByDesc(UserPhonePO::getCreateTime);
        queryWrapper.last("limit 1");
        return ConvertBeanUtils.convert(userPhoneMapper.selectOne(queryWrapper),UserPhoneDTO.class);
    }
}