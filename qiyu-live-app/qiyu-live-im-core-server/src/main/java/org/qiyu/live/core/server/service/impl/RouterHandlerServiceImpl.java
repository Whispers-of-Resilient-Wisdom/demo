package org.qiyu.live.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.qiyu.live.core.server.common.ChannelHandlerContextCache;
import org.qiyu.live.core.server.common.ImMsg;
import org.qiyu.live.core.server.service.IMsgAckCheckService;
import org.qiyu.live.core.server.service.IRouterHandlerService;
import org.qiyu.live.im.DTO.ImMsgBody;
import org.qiyu.live.im.enmu.ImMsgCodeEnum;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
给消息发送给目标用户
 */
@Service
public class RouterHandlerServiceImpl implements IRouterHandlerService {

    @Resource
    private IMsgAckCheckService msgAckCheckService;

    @Override
    public void onReceive(ImMsgBody imMsgBody) {
        //需要进行消息通知的userid
        if(sendMsgToClient(imMsgBody)) {
            //当im服务器推送了消息给到客户端，然后我们需要记录下ack
            msgAckCheckService.recordMsgAck(imMsgBody, 1);
            msgAckCheckService.sendDelayMsg(imMsgBody);
        }
    }

    @Override
    public boolean sendMsgToClient(ImMsgBody imMsgBody) {

        Long ObjectId = imMsgBody.getUserId();
        //找到目标

        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(ObjectId);
        if (ctx != null) {
            String msgId = UUID.randomUUID().toString();
            imMsgBody.setMsgId(msgId);
            ImMsg respMsg = ImMsg.build(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), JSON.toJSONString(imMsgBody));
            ctx.writeAndFlush(respMsg);
            return true;
        }
        return false;
    }
}
