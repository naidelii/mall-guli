package com.mall.gateway.filter;

import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CacheConstants;
import com.mall.common.base.constant.SecurityConstants;
import com.mall.gateway.config.SecurityProperties;
import com.mall.gateway.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final SecurityProperties securityProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        // 白名单请求
        Set<String> whiteList = securityProperties.getWhiteList();
        // 匹配路径
        boolean isWhiteListed = whiteList.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
        if (isWhiteListed) {
            // 放行白名单请求
            return chain.filter(exchange);
        }
        // 非白名单请求，获取token
        HttpHeaders headers = request.getHeaders();
        String authorizationValues = headers.getFirst(SecurityConstants.AUTHORIZATION);
        // 如果请求没有包含token，则是非法请求
        if (!headers.containsKey(SecurityConstants.AUTHORIZATION)) {
            log.error("=======拦截非法请求：{}", path);
            return handleUnauthorizedRequest(exchange);
        }
        // 如果包含了，则校验是否有效
        Boolean isExits = redisTemplate.hasKey(CacheConstants.TOKEN_CACHE_PREFIX + authorizationValues);
        if (Boolean.FALSE.equals(isExits)) {
            log.error("=======拦截非法token：{}，请求路径：{}", authorizationValues, path);
            return handleUnauthorizedRequest(exchange);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> handleUnauthorizedRequest(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        Result<Object> result = Result.unauthorized();
        DataBuffer dataBuffer = ResponseUtil.writeJsonResponse(response, result);
        return response.writeWith(Mono.just(dataBuffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
