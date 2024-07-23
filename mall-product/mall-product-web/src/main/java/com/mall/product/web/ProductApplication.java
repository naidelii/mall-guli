package com.mall.product.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author naidelii
 */
@SpringBootApplication(scanBasePackages = {"com.mall.common", "com.mall.product"})
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
