package com.mall.generator;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author naidelii
 * 代码生成
 */
@SpringBootApplication(scanBasePackages = {"com.mall.common", "com.mall.generator"})
@MapperScan({"com.mall.generator.mapper"})
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
