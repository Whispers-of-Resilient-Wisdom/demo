package org.qiyu.live.im.router.provider.service.impl;

import com.qiyu.live.common.interfaces.utils.IsEmptyUtils;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.qiyu.live.im.DTO.ImMsgBody;
import org.qiyu.live.im.core.server.interfaces.constants.ImCoreServerConstants;
import org.qiyu.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import org.qiyu.live.im.router.provider.service.ImRouterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**

 */
@Service
public class ImRouterServiceImpl implements ImRouterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImRouterServiceImpl.class);
    @DubboReference(protocol = "dubbo")
    private IRouterHandlerRpc routerHandlerRpc;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean sendMsg(ImMsgBody imMsgBody) {
        //假设我们有100个userid -> 10台im服务器上 100个ip做分类 -> 最终ip的数量一定是<=10
        String bindAddress = stringRedisTemplate.opsForValue().get(ImCoreServerConstants.IM_BIND_IP_KEY + imMsgBody.getAppId() + ":" + imMsgBody.getUserId());
        if (IsEmptyUtils.isEmpty(bindAddress)) {
            return false;
        }

        bindAddress = bindAddress.substring(0,bindAddress.indexOf("%"));
        RpcContext.getContext().set("ip", bindAddress);
        routerHandlerRpc.sendMsg(imMsgBody);
        return true;
    }



    @Override
    public void batchSendMsg(List<ImMsgBody> imMsgBodyList) {
        List<Long> userIdList = imMsgBodyList.stream().map(ImMsgBody::getUserId).collect(Collectors.toList());
        //根据userId 将不同的userId的immsgbody分类存入map
        Map<Long, ImMsgBody> userIdMsgMap = imMsgBodyList.stream().collect(Collectors.toMap(ImMsgBody::getUserId, x -> x));
        //保证整个list集合的appId得是同一个
        Integer appId = imMsgBodyList.get(0).getAppId();
        List<String> cacheKeyList = new ArrayList<>();
        userIdList.forEach(userId -> {
            String cacheKey = ImCoreServerConstants.IM_BIND_IP_KEY + appId + ":" + userId;
            cacheKeyList.add(cacheKey);
        });
        //批量取出每个用户绑定的ip地址
        List<String> ipList = stringRedisTemplate.opsForValue().multiGet(cacheKeyList).stream().filter(x -> x != null).collect(Collectors.toList());
        Map<String, List<Long>> userIdMap = new HashMap<>();
        ipList.forEach(ip -> {//// todo
            String currentIp = ip.substring(0, ip.indexOf("%"));
            Long userId = Long.valueOf(ip.substring(ip.indexOf("%") + 1));
            List<Long> currentUserIdList = userIdMap.get(currentIp);
            if (currentUserIdList == null) {
                currentUserIdList = new ArrayList<>();
            }
            currentUserIdList.add(userId);
            userIdMap.put(currentIp, currentUserIdList);
        });//收集ip对应的userID集合

        //将连接同一台ip地址的imMsgBody组装到同一个list集合中，然后进行统一的发送
        for (String currentIp : userIdMap.keySet()) {
            RpcContext.getContext().set("ip", currentIp);
            List<ImMsgBody> batchSendMsgGroupByIpList = new ArrayList<>();
            List<Long> ipBindUserIdList = userIdMap.get(currentIp);
            for (Long userId : ipBindUserIdList) {
                ImMsgBody imMsgBody = userIdMsgMap.get(userId);
                batchSendMsgGroupByIpList.add(imMsgBody);
            }
            routerHandlerRpc.batchSendMsg(batchSendMsgGroupByIpList);
        }
    }
}
