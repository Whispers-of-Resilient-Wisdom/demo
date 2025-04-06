package org.qiyu.live.msg.provider.consumer.handler;


import org.qiyu.live.im.DTO.ImMsgBody;

/**

 */
public interface MessageHandler {

    /**
     * 处理im服务投递过来的消息内容

     */
    void onMsgReceive(ImMsgBody imMsgBody);
}
