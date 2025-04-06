package org.qiyu.live.api.service;

import com.qiyu.live.common.interfaces.VO.WebResponseVO;
import jakarta.servlet.http.HttpServletResponse;
/**
 * IUserLoginService 定义了用户登录相关的服务接口
 * 包含发送登录验证码和通过手机号及验证码登录的方法
 */
public interface IUserLoginService {

    /**
     * 发送登录验证码
     *
     * @param phone 用户手机号
     * @return 返回 WebResponseVO 对象，包含操作结果
     */
    WebResponseVO sendLoginCode(String phone);

    /**
     * 使用手机号和验证码进行登录
     *
     * @param phone 用户手机号
     * @param code 登录验证码
     * @param response HTTP 响应对象，用于设置 Cookie 等信息
     * @return 返回 WebResponseVO 对象，包含操作结果及用户登录信息
     */
    WebResponseVO login(String phone, Integer code, HttpServletResponse response);
}
