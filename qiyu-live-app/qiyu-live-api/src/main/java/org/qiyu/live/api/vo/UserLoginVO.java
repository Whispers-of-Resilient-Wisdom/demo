package org.qiyu.live.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录VO类，用于封装用户登录的响应信息
 */
@Setter
@Getter
public class UserLoginVO {

    /**
     * 用户ID，唯一标识一个用户
     */
    private Long userId;


    @Override
    public String toString() {
        return "UserLoginVO{" +
                "userId=" + userId +
                '}';
    }
}