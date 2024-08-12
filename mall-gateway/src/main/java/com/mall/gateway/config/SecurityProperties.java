package com.mall.gateway.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author naidelii
 */
@ConfigurationProperties(prefix = "security")
@Data
@Configuration
public class SecurityProperties {

    /**
     * 静态资源不拦截链接
     */
    private String webIgnores;

    /**
     * 请求不拦截链接
     */
    private String httpIgnores;

    public Set<String> getWhiteList() {
        return new HashSet<>(Arrays.asList(StringUtils.split(this.httpIgnores, ",")));
    }

}
