package org.qiyu.live.core.server.rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.core.server.service.IRouterHandlerService;
import org.qiyu.live.im.DTO.ImMsgBody;
import org.qiyu.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;


import java.util.List;

/**

 */
@DubboService(protocol = "dubbo")
public class RouterHandlerRpcImpl implements IRouterHandlerRpc {

    @Resource
    private IRouterHandlerService routerHandlerService;

    @Override
    public void sendMsg(ImMsgBody imMsgBody) {
        routerHandlerService.onReceive(imMsgBody);
    }

    @Override
    public void batchSendMsg(List<ImMsgBody> imMsgBodyList) {
        imMsgBodyList.forEach(imMsgBody -> {
            routerHandlerService.onReceive(imMsgBody);
        });
    }
}
