package org.qiyu.live.account.provider.service;

public interface IAccountTokenService {

    /**
     * 创建一个登录token
     *

     */
    String createAndSaveLoginToken(Long userId);

    /**
     * 校验用户token
     *
     */
    Long getUserIdByToken(String tokenKey);
}