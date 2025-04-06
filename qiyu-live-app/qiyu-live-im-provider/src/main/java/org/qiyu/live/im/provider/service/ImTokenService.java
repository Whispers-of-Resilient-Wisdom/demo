package org.qiyu.live.im.provider.service;

import org.springframework.stereotype.Service;

@Service
public interface ImTokenService {
    String createImLoginToken(long userId, int appId);

    Long getUserIdByToken(String token);
}
