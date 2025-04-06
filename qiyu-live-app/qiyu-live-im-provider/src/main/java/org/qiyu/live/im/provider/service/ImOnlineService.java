package org.qiyu.live.im.provider.service;

/**
 * 判断用户是否在线service
 *

 */
public interface ImOnlineService {

    /**
     * 判断用户是否在线
     *

     */
    boolean isOnline(long userId,int appId);
}
