package org.qiyu.user.provider.Rpc;


import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import jakarta.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.user.dto.UserDTO;
import org.qiyu.live.user.interfaces.IUserRpc;
import org.qiyu.user.provider.dao.mapper.IUserMapper;
import org.qiyu.user.provider.dao.po.UserPo;
import org.qiyu.user.provider.service.IUserService;

import java.util.List;
import java.util.Map;

@DubboService(protocol = "dubbo")
public class UserRpcImpl implements IUserRpc {
@Resource
private IUserService UserService;
@Resource
private IUserMapper UserMapper;

   public  boolean updateUserInfo(UserDTO userDTO) {

        UserService.updateUserInfo(userDTO);
        return true;
   }

    @Override
    public UserDTO getUserById(Long UserId) {

        return UserService.getUserId(UserId);
    }



    @Override
    public boolean insertOne(UserDTO userDTO) {
       return UserService.insertOne(userDTO);
   }

    @Override
    public Map<Long, UserDTO> batchQuery(List<Long> userIdList) {
        return UserService.batchQueryUser(userIdList);
    }
}
