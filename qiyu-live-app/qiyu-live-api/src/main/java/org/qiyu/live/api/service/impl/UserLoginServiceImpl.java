package org.qiyu.live.api.service.impl;


import com.qiyu.live.common.interfaces.ConvertBeanUtils;
import com.qiyu.live.common.interfaces.VO.WebResponseVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.qiyu.live.account.interfaces.IAccountTokenRPC;
import org.qiyu.live.api.error.ApiErrorEnum;
import org.qiyu.live.api.service.IUserLoginService;
import org.qiyu.live.api.vo.UserLoginVO;
import org.qiyu.live.msg.provider.DTO.MsgCheckDTO;
import org.qiyu.live.msg.provider.Enums.MsgSendResultEnum;
import org.qiyu.live.msg.provider.interfaces.ISmsRpc;
import org.qiyu.live.user.dto.UserLoginDTO;
import org.qiyu.live.user.interfaces.IUserPhoneRpc;
import org.qiyu.live.web.starter.error.ErrorAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
/**
 * UserLoginServiceImpl 负责处理用户登录相关逻辑
 * 实现了 IUserLoginService 接口
 * 提供了发送登录验证码和用户登录的功能
 */
@Service
public class UserLoginServiceImpl implements IUserLoginService {

    // 手机号正则表达式，用于验证手机号格式是否正确
    private static final String PHONE_REG = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
    // 日志记录器，用于记录日志信息
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    // 引用短信服务接口
    @DubboReference
    private ISmsRpc smsRpc;

    // 引用用户手机服务接口
    @DubboReference
    private IUserPhoneRpc userPhoneRPC;

    // 引用账户令牌服务接口
    @DubboReference
    private IAccountTokenRPC accountTokenRPC;

    /**
     * 发送登录验证码
     *
     * @param phone 用户的手机号
     * @return 返回 WebResponseVO 包装的响应结果
     */
    @Override
    public WebResponseVO sendLoginCode(String phone) {
        // 校验手机号不能为空
        ErrorAssert.isNotBlank(phone, ApiErrorEnum.PHONE_IS_EMPTY);
        // 校验手机号格式是否有效
        ErrorAssert.isTure(Pattern.matches(PHONE_REG, phone), ApiErrorEnum.PHONE_IN_VALID);

        // 调用短信服务发送验证码
        MsgSendResultEnum msgSendResultEnum = smsRpc.sendMessage(phone);

        // 根据短信发送结果返回不同的响应
        if (msgSendResultEnum == MsgSendResultEnum.SEND_SUCCESS) {
            return WebResponseVO.success();
        }
        return WebResponseVO.sysError("短信发送太频繁，请稍后再试");
    }

    /**
     * 用户登录
     *
     * @param phone 用户的手机号
     * @param code 用户输入的验证码
     * @param response HttpServletResponse 用于设置登录成功后的 Cookie
     * @return 返回 WebResponseVO 包装的响应结果
     */
    @Override
    public WebResponseVO login(String phone, Integer code, HttpServletResponse response) {
        // 校验手机号不能为空
        ErrorAssert.isNotBlank(phone, ApiErrorEnum.PHONE_IS_EMPTY);
        // 校验手机号格式是否有效
        ErrorAssert.isTure(Pattern.matches(PHONE_REG, phone), ApiErrorEnum.PHONE_IN_VALID);
        // 校验验证码是否有效
        ErrorAssert.isTure(code != null && code > 1000, ApiErrorEnum.SMS_CODE_ERROR);

        // 校验短信验证码是否正确
        MsgCheckDTO msgCheckDTO = smsRpc.checkLoginCode(phone, code);
        if (!msgCheckDTO.isCheckStatus()) {
            return WebResponseVO.bizError(msgCheckDTO.getDesc());
        }

        // 验证码校验通过，进行用户登录
        UserLoginDTO userLoginDTO = userPhoneRPC.login(phone);
        ErrorAssert.isTure(userLoginDTO.isLoginSuccess(), ApiErrorEnum.USER_LOGIN_ERROR);

        // 创建并保存登录令牌
        String token = accountTokenRPC.createAndSaveLoginToken(userLoginDTO.getUserId());

        // 设置登录状态的 Cookie
        Cookie cookie = new Cookie("qytk", token);
        cookie.setDomain("127.0.0.1");  // 设置 Cookie 的域名
        cookie.setPath("/");  // 设置 Cookie 的路径
        cookie.setMaxAge(30 * 24 * 3600);  // 设置 Cookie 的有效期，单位为秒

        // 设置响应头，允许浏览器携带 Cookie
        response.addCookie(cookie);

        // 返回登录成功的响应结果，并将用户信息转换为 VO 对象
        return WebResponseVO.success(ConvertBeanUtils.convert(userLoginDTO, UserLoginVO.class));
    }
}
