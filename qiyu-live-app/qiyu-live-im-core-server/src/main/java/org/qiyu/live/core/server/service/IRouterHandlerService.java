package org.qiyu.live.core.server.service;


import org.qiyu.live.im.DTO.ImMsgBody;

/**

 */
public interface IRouterHandlerService {

    /**
     * 当收到业务服务的请求，进行处理
     *

     */
    void onReceive(ImMsgBody imMsgBody);


    /**
     * 发送消息给客户端
     *
   y
     */
    boolean sendMsgToClient(ImMsgBody imMsgBody);
}
