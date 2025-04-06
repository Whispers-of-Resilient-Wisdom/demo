package org.qiyu.live.gateway.filter;

import com.qiyu.live.common.interfaces.Enum.GatewayHeaderEnum;
import com.qiyu.live.common.interfaces.utils.IsEmptyUtils;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.qiyu.live.account.interfaces.IAccountTokenRPC;
import org.qiyu.live.gateway.properties.GatewayApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import static io.netty.handler.codec.http.cookie.CookieHeaderNames.MAX_AGE;
import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * AccountCheckFilter
 * <p>
 * 该过滤器用于处理跨域请求、token 校验、以及请求白名单等功能。主要作用：
 * - 在请求中进行跨域处理，允许指定源、方法和头信息。
 * - 根据请求的 URL 判断是否在白名单内，白名单内的请求跳过 token 校验。
 * - 对不在白名单内的请求，进行 token 校验。如果 token 有效，将 userId 添加到请求头中并传递到下游服务；如果无效，则拦截请求。
 * </p>
 */
@Component
public class AccountCheckFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCheckFilter.class);

    @DubboReference
    private IAccountTokenRPC accountTokenRPC;  // RPC 服务，调用校验 token 合法性
    @Resource
    private GatewayApplicationProperties gatewayApplicationProperties;  // 配置类，用于获取白名单 URL 列表

    /**
     * function:  Gateway 过滤器，用于处理跨域请求、token 校验以及请求白名单等功能
     *
     * @param exchange 包含请求和响应的交换数据
     * @param chain 过滤器链，用于执行后续过滤器
     * @return Mono<Void> 返回处理结果
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求的 URI 和响应对象
        ServerHttpRequest request = exchange.getRequest();
        String reqUrl = request.getURI().getPath();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();

        // 设置跨域请求相关的响应头
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://127.0.0.1:9199");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET");
        headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);

        // 如果请求 URL 为空，直接跳过过滤器链
        if (IsEmptyUtils.isEmpty(reqUrl)) {
            return Mono.empty();
        }

        // 获取配置的白名单 URL 列表，检查当前请求是否在白名单中
        List<String> notCheckUrlList = gatewayApplicationProperties.getNotCheckUrlList();
        for (String notCheckUrl : notCheckUrlList) {
            if (reqUrl.startsWith(notCheckUrl)) {
                LOGGER.info("请求没有进行token校验，直接传达给业务下游");
                // 如果 URL 在白名单中，跳过 token 校验，直接转发请求
                return chain.filter(exchange);
            }
        }

        // 如果请求不在白名单中，需要校验 token
        List<HttpCookie> httpCookieList = request.getCookies().get("qytk");
        if (CollectionUtils.isEmpty(httpCookieList)) {
            LOGGER.error("请求没有检索到qytk的cookie，被拦截");
            return Mono.empty();  // 如果没有 cookie，拦截请求
        }

        String TokenCookieValue = httpCookieList.get(0).getValue();
        if (IsEmptyUtils.isEmpty(TokenCookieValue) || IsEmptyUtils.isEmpty(TokenCookieValue.trim())) {
            LOGGER.error("请求的cookie中的qytk是空，被拦截");
            return Mono.empty();  // 如果 token 为空，拦截请求
        }

        // 调用 RPC 服务验证 token 的合法性
        Long userId = accountTokenRPC.getUserIdByToken(TokenCookieValue);
        if (userId == null) {
            LOGGER.error("请求的token失效了，被拦截");
            return Mono.empty();  // 如果 token 不合法，拦截请求
        }

        // 如果 token 合法，将 userId 添加到请求头中，并继续处理请求
        ServerHttpRequest.Builder builder = request.mutate();
        builder.header(GatewayHeaderEnum.USER_LOGIN_ID.getName(), String.valueOf(userId));
        return chain.filter(exchange.mutate().request(builder.build()).build());  // 转发请求到下游服务
    }

    /**
     * 获取过滤器的执行顺序
     * @return 过滤器的顺序，数字越小，优先级越高
     */
    @Override
    public int getOrder() {
        return 0;  // 设置为 0，确保该过滤器优先执行
    }
}

