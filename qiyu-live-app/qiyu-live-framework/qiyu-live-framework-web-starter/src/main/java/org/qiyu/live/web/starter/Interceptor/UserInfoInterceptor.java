package org.qiyu.live.web.starter.Interceptor;

import com.qiyu.live.common.interfaces.Enum.GatewayHeaderEnum;
import com.qiyu.live.common.interfaces.utils.IsEmptyUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.qiyu.live.web.starter.constants.RequestConstants;
import org.qiyu.live.web.starter.context.QiyuRequestContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户信息拦截器 UserInfoInterceptor
 * <p>
 * 该拦截器在所有 Web 请求进入控制器之前执行，用于从请求头中提取用户 ID，
 * 并将其存储在线程上下文 QiyuRequestContext 中，方便后续业务逻辑获取用户信息。
 * 请求处理完成后，清理线程上下文，避免内存泄漏。
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    /**
     * 前置处理逻辑：在请求到达 Controller 之前执行
     *
     * @param request  当前的 HTTP 请求
     * @param response 当前的 HTTP 响应
     * @param handler  将要执行的处理器（Controller 的方法）
     * @return true 表示继续执行后续的拦截器链或控制器逻辑，false 则中断请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中取出 userId（网关传递过来的用户登录 ID）
        String userIdStr = request.getHeader(GatewayHeaderEnum.USER_LOGIN_ID.getName());

        // 若请求头未携带用户 ID，可能是无需鉴权的白名单接口，直接放行
        if (IsEmptyUtils.isEmpty(userIdStr)) {
            return true;
        }

        // 若存在 userId，将其放入线程上下文，供业务逻辑使用
        QiyuRequestContext.set(RequestConstants.QIYU_USER_ID, Long.valueOf(userIdStr));
        return true;
    }

    /**
     * 后置处理逻辑：在控制器逻辑执行完但尚未返回视图之前执行
     *
     * @param request      当前的 HTTP 请求
     * @param response     当前的 HTTP 响应
     * @param handler      已执行的处理器（Controller 的方法）
     * @param modelAndView 返回的视图模型（若存在）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求处理完毕后，清理线程上下文，避免内存泄漏
        QiyuRequestContext.clear();
    }
}

