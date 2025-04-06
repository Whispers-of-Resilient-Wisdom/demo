package org.qiyu.live.im.router.provider.service;



import org.qiyu.live.im.DTO.ImMsgBody;

import java.util.List;

/**

 */
public interface ImRouterService {


    /**
     * 发送消息
     *
     * @param imMsgBody
     * @return
     */
    boolean sendMsg(ImMsgBody imMsgBody);

    /**
     * 批量发送消息，群聊场景
     *
     * @param imMsgBody
     */
    void batchSendMsg(List<ImMsgBody> imMsgBody);
}
