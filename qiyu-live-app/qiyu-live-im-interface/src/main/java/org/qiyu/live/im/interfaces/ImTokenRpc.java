package org.qiyu.live.im.interfaces;

public interface ImTokenRpc {
    /**
     * 创建用户服务的token

     */
    public String createImLoginToken(long userId, int appId);

    /**
     * 从token获取userId
     */
    public Long getUserIdByToken(String token);

}
