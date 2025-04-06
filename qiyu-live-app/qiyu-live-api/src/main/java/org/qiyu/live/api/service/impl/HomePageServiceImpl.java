package org.qiyu.live.api.service.impl;


import org.apache.dubbo.config.annotation.DubboReference;
import org.qiyu.live.api.service.IHomePageService;
import org.qiyu.live.api.vo.HomePageVO;
import org.qiyu.live.user.constants.UserTagsEnum;
import org.qiyu.live.user.dto.UserDTO;
import org.qiyu.live.user.interfaces.IUserRpc;
import org.qiyu.live.user.interfaces.IUserTagRpc;
import org.springframework.stereotype.Service;

/**
 * HomePageServiceImpl 负责处理首页相关的业务逻辑
 * 实现了 IHomePageService 接口
 * 提供了初始化首页所需的信息，包括用户信息和VIP状态等
 */
@Service
public class HomePageServiceImpl implements IHomePageService {

    // 引用用户服务接口，用于获取用户信息
    @DubboReference
    private IUserRpc userRpc;

    // 引用用户标签服务接口，用于检查用户标签
    @DubboReference
    private IUserTagRpc userTagRpc;

    /**
     * 初始化首页信息
     *
     * @param userId 当前用户ID
     * @return 返回首页展示的用户信息，包括头像、昵称及VIP状态等
     */
    @Override
    public HomePageVO initPage(Long userId) {
        // 通过用户ID获取用户详细信息
        UserDTO userDTO = userRpc.getUserById(userId);

        // 创建首页展示对象
        HomePageVO homePageVO = new HomePageVO();

        if (userDTO != null) {
            // 设置用户的头像、用户ID和昵称
            homePageVO.setAvatar(userDTO.getAvatar());
            homePageVO.setUserId(userId);
            homePageVO.setNickName(userDTO.getNickName());

            // 检查用户是否为VIP，VIP用户有权限开启直播
            homePageVO.setShowStartLivingBtn(userTagRpc.containTag(userId, UserTagsEnum.IS_VIP));
        }

        // 返回初始化后的首页信息
        return homePageVO;
    }
}
