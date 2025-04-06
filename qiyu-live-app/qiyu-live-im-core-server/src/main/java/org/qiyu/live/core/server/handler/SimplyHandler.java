package org.qiyu.live.core.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.qiyu.live.core.server.common.ImMsg;

public interface SimplyHandler {
    /**
     * 消息处理函数
     *
     */
    void handler(ChannelHandlerContext ctx, ImMsg imMsg);
}
