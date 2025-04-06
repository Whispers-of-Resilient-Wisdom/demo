package org.qiyu.user.provider.service;

import org.qiyu.live.user.dto.UserLoginDTO;
import org.qiyu.live.user.dto.UserPhoneDTO;

import java.util.List;

public interface IUserPhoneService {
    /**
     * 手机号注册
     *
     */
    UserPhoneDTO insert(String phone, Long userId);
    /**
     * 更具用户 id 查询手机信息
     *

     */
    List<UserPhoneDTO> queryByUserId(Long userId);
    /**
     * 根据手机号查询

     */
    UserPhoneDTO queryByPhone(String phone);

    UserLoginDTO login(String phone);
}