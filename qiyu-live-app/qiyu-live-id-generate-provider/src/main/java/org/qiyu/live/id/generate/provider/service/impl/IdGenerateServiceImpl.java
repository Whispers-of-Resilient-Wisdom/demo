package org.qiyu.live.id.generate.provider.service.impl;

import jakarta.annotation.Resource;

import org.qiyu.live.id.generate.provider.dao.mapper.IdBuilderMapper;
import org.qiyu.live.id.generate.provider.dao.po.IdBuilderPO;
import org.qiyu.live.id.generate.provider.service.BO.LocalSeqIdBO;
import org.qiyu.live.id.generate.provider.service.IdBuilderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.qiyu.live.id.generate.provider.service.BO.LocalUnSeqIdBO;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class IdGenerateServiceImpl implements IdBuilderService, InitializingBean {

    private static final float UPDATE_RATE =0.75f ;
    @Resource
    private IdBuilderMapper idBuilderMapper; // 数据库操作的 mapper

    private static Map<Integer, LocalSeqIdBO> localSeqIdMap = new ConcurrentHashMap<>(); // 存储有序 ID
    private static Map<Integer, LocalUnSeqIdBO> localUnSeqIdMap = new ConcurrentHashMap<>();
    private static Map<Integer, Semaphore> semaPhoreMap=new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerateServiceImpl.class); // 日志记录器
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000),
            new ThreadFactory(){
           @Override
           public Thread newThread(Runnable r) {
             Thread thread = new Thread(r);
             thread.setName("id-generate-thread"+ ThreadLocalRandom.current().nextInt(1000));
             return thread;
                              }
            }
    );

    // 获取有序 ID
    @Override
    public Long getSeqId(Integer code) {
        if(code==null) return   null;
        LocalSeqIdBO localSeqIdBO = localSeqIdMap.get(code); // 获取当前 code 对应的 ID 配置
        if (localSeqIdBO == null) {
            LOGGER.error("[getSeqId] code is error,{}", code); // 如果未找到，则输出错误日志
            return null;
        }
        this.refreshLocalSeqId(localSeqIdBO);
        if(localSeqIdBO.getCurrentValue().get()> localSeqIdBO.getNextThreshold()){
            LOGGER.error("[getSeqId] code is limit,{}", code); // 如果未找到，则输出错误日志
            return null;

        }
        return  localSeqIdBO.getCurrentValue().getAndIncrement(); // 获取当前 ID，并自增



    }

    // 获取无序 ID
    @Override
    public Long getUnSeqId(Integer code) {
        if(code==null) return   null;
        LocalUnSeqIdBO localUnSeqIdBO = localUnSeqIdMap.get(code); // 获取当前 code 对应的 ID 配置
        if (localUnSeqIdBO == null) {
            LOGGER.error("[getUnSeqId] id is error,{}", code); // 如果未找到，则输出错误日志
            return null;
        }
        Long id = localUnSeqIdBO.getIdQueue().poll();
        if(id==null){
            LOGGER.error("[getUnSeqId] id is null,{}", code); // 如果未找到，则输出错误日志
        }
        this.refreshUnLocalSeqId(localUnSeqIdBO);
        return id ;
    }

    private void refreshUnLocalSeqId(LocalUnSeqIdBO localUnSeqIdBO) {
        Integer unSeqId = localUnSeqIdBO.getId();
        if (localUnSeqIdBO.getIdQueue().size() < localUnSeqIdBO.getStep() * 0.25){
            Semaphore semaphore = semaPhoreMap.get(localUnSeqIdBO.getId());
            // 如果没有找到对应的 Semaphore，打印错误日志并返回，表示无法进行同步操作
            if (semaphore == null) {
                LOGGER.error("semaphore  is null ,unSeqId is {}", unSeqId);
                return;
            }
            // 尝试获取信号量，如果能成功获取，就意味着当前线程可以进行同步操作
            boolean acquireStatus = semaphore.tryAcquire();
            // 如果成功获取到信号量，表示可以进行同步操作
            if (acquireStatus) {
                LOGGER.info("无序id尝试同步开始");
                // 使用线程池执行同步操作
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            IdBuilderPO idBuilderPO = idBuilderMapper.selectById(unSeqId);
                            // 执行数据库更新操作
                            tryUpdateMysqlRecord(idBuilderPO);


                           }catch (Exception e){
                            LOGGER.error("有序id尝试同步失败，Exception: ",e);
                           }finally {
                            semaPhoreMap.get(unSeqId).release();
                            LOGGER.info("无序id尝试同步完成");
                           }
                        // 从数据库中根据 id 获取对应的 IdBuilderPO 实体
                                            }
                });
            }
        }

    }


    private void refreshLocalSeqId(LocalSeqIdBO localSeqIdBO){
        // 计算出 step（步长），表示当前阈值与起始值之间的差距。
        long step = localSeqIdBO.getNextThreshold() - localSeqIdBO.getCurrentStart();
        Integer SeqId = localSeqIdBO.getId();
        // 判断当前值与起始值之间的差距是否超过了一个比例（UPDATE_RATE）
        if (localSeqIdBO.getCurrentValue().get() - localSeqIdBO.getCurrentStart() > step * UPDATE_RATE) {
            // 从 semaphoreMap 中获取对应的 Semaphore 对象，Semaphore 是一种信号量，用于控制对某个资源的访问
            Semaphore semaphore = semaPhoreMap.get(SeqId);
            // 如果没有找到对应的 Semaphore，打印错误日志并返回，表示无法进行同步操作
            if (semaphore == null) {
                LOGGER.error("semaphore  is null ,id is {}", localSeqIdBO.getId());
                return;
            }
            // 尝试获取信号量，如果能成功获取，就意味着当前线程可以进行同步操作
            boolean acquireStatus = semaphore.tryAcquire();

            // 如果成功获取到信号量，表示可以进行同步操作
            if (acquireStatus) {
                LOGGER.info("尝试同步开始");
                // 使用线程池执行同步操作
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            IdBuilderPO idBuilderPO = idBuilderMapper.selectById(SeqId);
                            // 执行数据库更新操作
                            tryUpdateMysqlRecord(idBuilderPO);

                        }catch (Exception e){
                            LOGGER.error("有序id尝试同步失败，Exception: ",e);
                        }finally {
                            semaPhoreMap.get(SeqId).release();
                            LOGGER.info("有序id尝试同步完成");
                        }
                    }
                });
            }
        }
    }

    void tryUpdateMysqlRecord(IdBuilderPO idBuilderPO){

    int updateResult=idBuilderMapper.updateIdAndVersionCount(idBuilderPO.getId(),idBuilderPO.getVersion());
    if(updateResult>0){
        localIdBOHandle(idBuilderPO);
    }
    for (int i = 0; i < 3; i++) {
        idBuilderPO=idBuilderMapper.selectById(idBuilderPO.getId());
        updateResult=idBuilderMapper.updateIdAndVersionCount(idBuilderPO.getId(),idBuilderPO.getVersion());
        if(updateResult>0){
          localIdBOHandle(idBuilderPO);

        }
    }


}

    /**
     * 对 tryUpdateMysqlRecord（） 方法进行封装，将本地id进入LocalSeqIdMap和LocalUnSeqIdMap
     *
     */
   void  localIdBOHandle(IdBuilderPO idBuilderPO){
       long currentStart = idBuilderPO.getCurrentStart();
       long nextThreshold = idBuilderPO.getNextThreshold();
           if(idBuilderPO.getIsSeq()==1){
               LocalSeqIdBO localSeqIdBO = new LocalSeqIdBO();
               localSeqIdBO.setId(idBuilderPO.getId());
               AtomicLong currentValue = new AtomicLong(currentStart);
               localSeqIdBO.setCurrentStart(currentStart);
               localSeqIdBO.setCurrentValue(currentValue);
               localSeqIdBO.setNextThreshold(nextThreshold);
               localSeqIdMap.put(idBuilderPO.getId(), localSeqIdBO); // 将配置加载到本地内存
           }
           else{
               LocalUnSeqIdBO localUnSeqIdBO = new LocalUnSeqIdBO();
               localUnSeqIdBO.setId(idBuilderPO.getId());
               localUnSeqIdBO.setStep(idBuilderPO.getStep());
               localUnSeqIdBO.setNextThreshold(idBuilderPO.getNextThreshold() + idBuilderPO.getStep());
               localUnSeqIdBO.setCurrentStart(idBuilderPO.getNextThreshold());
               localUnSeqIdBO.setRandomIdInQueue(idBuilderPO.getCurrentStart(),idBuilderPO.getNextThreshold());
               localUnSeqIdMap.put(idBuilderPO.getId(), localUnSeqIdBO);

           }


   }
    @Override
    public void afterPropertiesSet() {
        // 在启动时，初始化本地内存中的 ID 配置信息
        List<IdBuilderPO> idBuilderPOList = idBuilderMapper.selectAll(); // 查询所有 ID 配置信息
        for (IdBuilderPO idBuilderPO : idBuilderPOList) {
            System.out.println(idBuilderPO.getId());
            tryUpdateMysqlRecord(idBuilderPO);
            semaPhoreMap.put(idBuilderPO.getId(),new Semaphore(1));
            }
        }
    }

