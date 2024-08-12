package com.mall.auth.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author naidelii
 */
@SpringBootApplication(scanBasePackages = {"com.mall.common", "com.mall.auth"})
@MapperScan("com.mall.auth.biz.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
