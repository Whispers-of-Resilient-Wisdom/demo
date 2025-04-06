package org.qiyu.live.api.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.qiyu.live.user.dto.UserDTO;
import org.qiyu.live.user.interfaces.IUserRpc;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
/**
 * UserController 提供了与用户相关的 API 接口
 * 包含获取用户信息、更新用户信息、插入用户信息和批量查询用户信息等功能
 */
@RestController
@RequestMapping("/live/api")
public class UserController {

    // 引用用户服务接口，用于获取用户信息、更新用户信息等
    @DubboReference
    private IUserRpc userRpc;

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 返回用户的详细信息
     */
    @GetMapping("/userId")
    public UserDTO getUserId(Long userId) {
        // 调用 userRpc 服务获取用户信息
        return userRpc.getUserById(userId);
    }

    /**
     * 更新用户信息
     *
     * @param userid 用户ID
     * @return 返回操作是否成功
     */
    @GetMapping("/update")
    public boolean update(Long userid) {
        // 创建一个新的 UserDTO 对象，模拟更新用户信息
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userid);
        userDTO.setNickName("1");
        userDTO.setTrueName("1");
        userDTO.setSex(1); // 1: 男，2: 女
        userDTO.setAvatar("1");
        userDTO.setBornDate(new java.util.Date());
        userDTO.setCreateTime(new java.util.Date());
        userDTO.setUpdateTime(new java.util.Date());
        userDTO.setBornCity(10);
        userDTO.setWorkCity(10);

        // 调用 userRpc 更新用户信息
        return userRpc.updateUserInfo(userDTO);
    }

    /**
     * 插入一条新的用户信息
     *
     * @param userid 用户ID
     * @return 返回操作是否成功
     */
    @GetMapping("/insertOne")
    public boolean insertOne(Long userid) {
        // 创建一个新的 UserDTO 对象，模拟插入一条用户信息
        UserDTO userDto = new UserDTO();
        userDto.setUserId(userid);
        userDto.setNickName("测试");
        userDto.setSex(1); // 1: 男，2: 女
        userDto.setWorkCity(1);

        // 调用 userRpc 插入用户信息
        return userRpc.insertOne(userDto);
    }

    /**
     * 批量查询多个用户信息
     *
     * @param userIDList 用户ID列表
     * @return 返回用户ID与用户信息的映射
     */
    @GetMapping("/batchQuery")
    public Map<Long, UserDTO> batchQuery(@RequestParam List<Long> userIDList) {
        // 打印用户ID列表（用于调试）
        System.out.println(userIDList);

        // 调用 userRpc 批量查询用户信息
        return userRpc.batchQuery(userIDList);
    }
}
