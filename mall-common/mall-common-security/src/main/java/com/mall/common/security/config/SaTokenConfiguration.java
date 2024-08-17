package com.mall.common.security.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import com.mall.common.base.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author naidelii
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SaTokenConfiguration implements WebMvcConfigurer {

    /**
     * 添加sa-token拦截器（作用时机DispatcherServlet之后），用来注解鉴权
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        // token名称 (同时也是cookie名称)
        config.setTokenName(SecurityConstants.AUTHORIZATION);
        // token风格
        config.setTokenStyle("uuid");
        // token有效期，单位s 默认30天，不支持自动续签（30分钟）
        config.setTimeout(30 * 60);
        // 自动续签，指定时间内有操作，则会自动续签
        config.setAutoRenew(true);
        // 是否尝试从请求体里读取token
        config.setIsReadBody(false);
        // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsConcurrent(false);
        // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setIsShare(false);
        // 是否输出操作日志
        config.setIsLog(false);
        return config;
    }
}
