package org.qiyu.live.im.interfaces;

/**
 * 判断用户是否在线rpc
 *

 */
public interface ImOnlineRpc {

    /**
     * 判断用户是否在线
     *

     */
    boolean isOnline(long userId,int appId);
}
