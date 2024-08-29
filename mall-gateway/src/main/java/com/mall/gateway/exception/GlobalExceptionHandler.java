package com.mall.gateway.exception;

import com.mall.common.base.api.Result;
import com.mall.common.base.exception.ServiceException;
import com.mall.gateway.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author naidelii
 */
@Slf4j
@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof UnauthorizedException) {
            log.warn("Unauthorized access attempt: {}", ex.getMessage());
            return buildErrorResponse(exchange, Result.unauthorized());
        }
        if (ex instanceof ServiceException) {
            ServiceException e = (ServiceException) ex;
            log.error("ServiceException occurred: code={}, message={}", e.getCode(), e.getMessage());
            Result<Object> error = Result.error(e.getCode(), e.getMessage());
            return buildErrorResponse(exchange, error);
        }
        log.error("Unexpected error occurred", ex);
        // 使用默认处理
        return buildErrorResponse(exchange, Result.fail());
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, Result<Object> result) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer dataBuffer = ResponseUtil.writeJsonResponse(response, result);
        return response.writeWith(Mono.just(dataBuffer));
    }

}
