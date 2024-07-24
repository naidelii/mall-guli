package com.mall.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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

}
