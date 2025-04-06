package org.qiyu.user.provider.service;

import org.qiyu.live.user.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public  UserDTO getUserId(Long UserId);
    public void  updateUserInfo(UserDTO userDTO);
    public boolean insertOne(UserDTO userDTO);
    Map<Long,UserDTO> batchQueryUser(List<Long> userIdList);
}
