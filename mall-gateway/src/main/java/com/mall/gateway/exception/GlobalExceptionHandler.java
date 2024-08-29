package com.mall.gateway.exception;

import com.mall.common.base.api.Result;
import com.mall.common.base.constant.enums.ResultCodeEnum;
import com.mall.common.base.exception.ServiceException;
import com.mall.gateway.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

/**
 * @author naidelii
 */
@Slf4j
@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 处理未经授权的访问尝试（未携带token、token过期）
        if (ex instanceof UnauthorizedException) {
            log.warn("未经授权的访问尝试: {}", ex.getMessage());
            Result<Object> error = Result.error(ResultCodeEnum.UNAUTHORIZED);
            return buildErrorResponse(exchange, error);
        }
        // 处理服务内部异常
        if (ex instanceof ServiceException) {
            ServiceException e = (ServiceException) ex;
            log.error("服务异常: {}", e.getMessage());
            Result<Object> error = Result.error(e.getCode(), e.getMessage());
            return buildErrorResponse(exchange, error);
        }
        // 处理无法连接到服务的异常（例如服务下线但在Nacos中仍然在线）
        if (ex instanceof ConnectException) {
            log.error("服务不可用: {}", ex.getMessage());
            Result<Object> error = Result.error(ResultCodeEnum.SERVICE_UNAVAILABLE);
            return buildErrorResponse(exchange, error);
        }
        // 处理所有其他未预见的异常
        log.error("发生了意外的错误", ex);
        // 返回通用的失败响应
        return buildErrorResponse(exchange, Result.fail());
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, Result<Object> result) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer dataBuffer = ResponseUtil.writeJsonResponse(response, result);
        return response.writeWith(Mono.just(dataBuffer));
    }

}
