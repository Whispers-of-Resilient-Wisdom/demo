package org.qiyu.live.web.starter.config;


import org.qiyu.live.web.starter.Interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Web 配置类
 * <p>
 * 用于配置 Spring MVC 的拦截器等组件。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 用户信息拦截器 Bean
     * <p>
     * 将 UserInfoInterceptor 注册为 Spring 的 Bean，方便在拦截器链中使用。
     *
     * @return UserInfoInterceptor 实例
     */
    @Bean
    public UserInfoInterceptor qiyuUserInfoInterceptor() {
        return new UserInfoInterceptor();
    }

    /**
     * 添加拦截器配置
     * <p>
     * 注册 qiyuUserInfoInterceptor 拦截器，拦截所有路径（/**），但排除 /error 路径。
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(qiyuUserInfoInterceptor())
                .addPathPatterns("/**")  // 拦截所有路径
                .excludePathPatterns("/error");  // 排除 /error 路径
    }
}
