package com.mall.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author naidelii
 */
@SpringBootApplication(scanBasePackages = {"com.mall.common", "com.mall.admin"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}