package com.mall.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.constant.HttpConstants;
import com.mall.common.base.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;

/**
 * @author naidelii
 */
@Slf4j
public final class ResponseUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ResponseUtil() {

    }

    /**
     * 返回 JSON 格式的响应
     *
     * @param response ServerHttpResponse 对象
     * @param result   响应数据对象
     * @return DataBuffer
     */
    public static DataBuffer writeJsonResponse(ServerHttpResponse response, Object result) {
        HttpHeaders headers = response.getHeaders();
        headers.set(HttpConstants.CONTENT_TYPE, HttpConstants.APPLICATION_JSON);
        byte[] bytes;
        try {
            bytes = OBJECT_MAPPER.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            // 序列化失败
            log.error("==========writeJsonResponse序列化失败", e);
            throw new GlobalException();
        }
        return response.bufferFactory().wrap(bytes);
    }

}
