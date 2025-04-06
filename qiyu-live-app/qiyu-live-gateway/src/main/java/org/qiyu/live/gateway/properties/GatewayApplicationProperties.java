package org.qiyu.live.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * GatewayApplicationProperties
 * <p>
 * 该类用于加载网关应用的配置项，特别是与 URL 白名单相关的配置。通过 Spring Boot 的 `@ConfigurationProperties`
 * 注解来绑定外部配置文件中的属性值。类中的 `notCheckUrlList` 存储了需要跳过 token 校验的 URL 白名单列表。
 * </p>
 */
@ConfigurationProperties(prefix = "qiyu.gateway")  // 从配置文件中加载前缀为 "qiyu.gateway" 的属性
@Configuration  // 标记该类为 Spring 配置类
@RefreshScope  // 支持配置刷新，Spring Cloud 配置中心中的配置变化会自动更新此类的值
public class GatewayApplicationProperties {

    // 用于存储不需要进行 token 校验的 URL 列表
    private List<String> notCheckUrlList;

    /**
     * 获取不需要进行 token 校验的 URL 列表
     *
     * @return 白名单 URL 列表
     */
    public List<String> getNotCheckUrlList() {
        return notCheckUrlList;
    }

    /**
     * 设置不需要进行 token 校验的 URL 列表
     *
     * @param notCheckUrlList 白名单 URL 列表
     */
    public void setNotCheckUrlList(List<String> notCheckUrlList) {
        this.notCheckUrlList = notCheckUrlList;
    }

    /**
     * 重写 `toString` 方法，方便查看当前类的属性值，特别是白名单 URL 列表
     *
     * @return 类的字符串表示
     */
    @Override
    public String toString() {
        return "GatewayApplicationProperties{" +
                "notCheckUrlList=" + notCheckUrlList +
                '}';
    }
}
