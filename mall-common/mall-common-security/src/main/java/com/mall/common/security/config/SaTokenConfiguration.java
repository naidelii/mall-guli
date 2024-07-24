package com.mall.common.security.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author naidelii
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SaTokenConfiguration implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;


    /**
     * 添加sa-token拦截器（作用时机DispatcherServlet之后），用来检查是否登录
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(StringUtils.split(securityProperties.getHttpIgnores(), ","));
    }

    /**
     * 注册sa-token的过滤器（作用时机DispatcherServlet之前）
     *
     * @return SaServletFilter
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 指定拦截路由
                .addInclude("/**")
                // 指定放行路由
                .addExclude(StringUtils.split(securityProperties.getWebIgnores(), ","));
    }

//    /**
//     * 解决跨域：如果是SpringCloud项目，则在网关进行处理
//     *
//     * @param registry CorsRegistry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .exposedHeaders("*");
//    }

}
