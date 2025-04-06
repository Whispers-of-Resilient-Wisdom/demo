package org.qiyu.live.api.controller;

import com.qiyu.live.common.interfaces.VO.WebResponseVO;
import jakarta.annotation.Resource;

import org.qiyu.live.api.service.IHomePageService;
import org.qiyu.live.api.vo.HomePageVO;

import org.qiyu.live.web.starter.context.QiyuRequestContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * HomePageController 负责处理与首页相关的 API 请求
 * 提供了初始化首页所需的信息，包括用户登录状态和用户的首页数据
 */
@RestController
@RequestMapping("/live/api/home")
public class HomePageController {

    // 引用首页服务接口，用于处理首页相关的业务逻辑
    @Resource
    private IHomePageService homePageService;

    /**
     * 初始化首页信息
     *
     * @return 返回包含首页数据和登录状态的 WebResponseVO
     */
    @PostMapping("/initPage")
    public WebResponseVO initPage() {
        // 获取当前登录用户的用户ID
        Long userId = QiyuRequestContext.getUserId();

        // 创建一个默认的 HomePageVO 对象
        HomePageVO homePageVO = new HomePageVO();

        // 默认设置为未登录状态
        homePageVO.setLoginStatus(false);

        // 如果用户ID不为空，调用业务逻辑初始化首页数据
        if (userId != null) {
            // 调用 homePageService 获取首页数据
            homePageVO = homePageService.initPage(userId);

            // 设置为已登录状态
            homePageVO.setLoginStatus(true);
        }

        // 返回封装了首页数据的 WebResponseVO 对象
        return WebResponseVO.success(homePageVO);
    }
}
