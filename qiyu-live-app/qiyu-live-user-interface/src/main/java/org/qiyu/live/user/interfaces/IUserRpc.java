package org.qiyu.live.user.interfaces;

import org.qiyu.live.user.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface IUserRpc {

   boolean updateUserInfo(UserDTO userDTO) ;


   /**
    * 根据用户id查询用户信息
    */
UserDTO getUserById(Long UserId);
boolean insertOne(UserDTO userDTO);
Map<Long,UserDTO> batchQuery(List<Long> userIdList);
}
