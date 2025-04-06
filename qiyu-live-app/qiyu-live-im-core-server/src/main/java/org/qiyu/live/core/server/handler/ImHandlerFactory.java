package org.qiyu.live.core.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.qiyu.live.core.server.common.ImMsg;

/**

 */
public interface ImHandlerFactory {

    /**
     * 按照ImMsg的code去筛选
     *

     */
    void    doMsgHandler(ChannelHandlerContext channelHandlerContext, ImMsg imMsg);
}
