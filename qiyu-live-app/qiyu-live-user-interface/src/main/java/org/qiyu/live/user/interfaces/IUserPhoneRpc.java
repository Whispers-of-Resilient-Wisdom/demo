package org.qiyu.live.user.interfaces;

import org.qiyu.live.user.dto.UserLoginDTO;
import org.qiyu.live.user.dto.UserPhoneDTO;
import java.util.List;
/**

 */
public interface IUserPhoneRpc {
    /**
     * 手机号登录+注册
     *

     */
    UserLoginDTO login(String phone);
    /**
     * 根据用户 id 查询手机信息

     *

     */
    List<UserPhoneDTO> queryByUserId(Long userId);
    /**
     * 根据手机号查询userid
     *

     */
    UserPhoneDTO queryByPhone(String phone);
}