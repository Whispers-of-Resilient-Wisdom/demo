package org.qiyu.live.living.provider.config;

import jakarta.annotation.Resource;

import org.qiyu.live.framework.redis.starter.key.LivingProviderCacheKeyBuilder;
import org.qiyu.live.living.interfaces.constants.LivingRoomTypeEnum;
import org.qiyu.live.living.interfaces.dto.LivingRoomRespDTO;
import org.qiyu.live.living.provider.service.ILivingRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动服务后，定时同步直播间信息到 Redis。
 * 使用定时任务每隔一段时间从数据库中加载直播间信息并更新到 Redis 缓存中。
 */
@Configuration
public class RefreshLivingRoomListJob implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshLivingRoomListJob.class);

    @Resource
    private ILivingRoomService livingRoomService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private LivingProviderCacheKeyBuilder cacheKeyBuilder;

    private final ScheduledThreadPoolExecutor schedulePool = new ScheduledThreadPoolExecutor(1);

    /**
     * 在服务启动后，初始化定时任务，定期刷新直播间信息到 Redis。
     * 该任务每隔 1 秒刷新一次数据。
     *
     */
    @Override
    public void afterPropertiesSet() {
        // 每隔 1 秒刷新一次直播间数据
        schedulePool.scheduleWithFixedDelay(new RefreshCacheListJob(), 3000, 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 刷新缓存的具体任务类。
     * 该任务会从数据库中加载直播间信息并更新到 Redis 中。
     */
    class RefreshCacheListJob implements Runnable {

        @Override
        public void run() {
            String cacheKey = cacheKeyBuilder.buildRefreshLivingRoomListLock();
            // 获取分布式锁，防止多个线程并发刷新数据
            boolean lockStatus = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(cacheKey, 1, 1, TimeUnit.SECONDS));
            if (lockStatus) {
                LOGGER.debug("[RefreshLivingRoomListJob] starting 加载db中记录的直播间进redis里");
                // 刷新两种类型的直播间数据
                refreshDBToRedis(LivingRoomTypeEnum.DEFAULT_LIVING_ROOM.getCode());
                refreshDBToRedis(LivingRoomTypeEnum.PK_LIVING_ROOM.getCode());
                LOGGER.debug("[RefreshLivingRoomListJob] end 加载db中记录的直播间进redis里");
            }
        }
    }

    /**
     * 将数据库中的直播间信息刷新到 Redis 中。
     * 如果查询到的直播间为空，则删除相应的缓存。
     *
     * @param type 直播间类型
     */
    private void refreshDBToRedis(Integer type) {
        String cacheKey = cacheKeyBuilder.buildLivingRoomList(type);
        List<LivingRoomRespDTO> resultList = livingRoomService.listAllLivingRoomFromDB(type);

        if (CollectionUtils.isEmpty(resultList)) {
            // 如果查询结果为空，删除缓存
            redisTemplate.delete(cacheKey);
            return;
        }

        String tempListName = cacheKey + "_temp";

        // 按照查询出来的顺序，将每个直播间信息添加到临时列表
        for (LivingRoomRespDTO livingRoomRespDTO : resultList) {
            redisTemplate.opsForList().rightPush(tempListName, livingRoomRespDTO);
        }

        // 将临时列表重命名为正式的缓存列表
        redisTemplate.rename(tempListName, cacheKey);
        redisTemplate.delete(tempListName);  // 删除临时列表
    }
}
