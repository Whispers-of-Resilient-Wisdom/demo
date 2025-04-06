package org.qiyu.user.provider.Rpc;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.user.dto.UserPhoneDTO;
import org.qiyu.live.user.dto.UserLoginDTO;
import org.qiyu.live.user.interfaces.IUserPhoneRpc;
import org.qiyu.user.provider.service.IUserPhoneService;
import java.util.*;

/**

 */
@DubboService(protocol = "dubbo")
public class UserPhoneRpcImpl implements IUserPhoneRpc {

    @Resource
    private IUserPhoneService userPhoneService;

    @Override
    public UserLoginDTO login(String phone) {

       return  userPhoneService.login(phone);
    }




    @Override
    public List<UserPhoneDTO> queryByUserId(Long userId) {
       return userPhoneService.queryByUserId(userId);
    }

    /**
     * function:通过phone查询用户信息
     * @param phone 电话号码
     * @return UserPhoneDTO
     */
    @Override
    public UserPhoneDTO queryByPhone(String phone)  {
        return  userPhoneService.queryByPhone(phone);
    }
}
