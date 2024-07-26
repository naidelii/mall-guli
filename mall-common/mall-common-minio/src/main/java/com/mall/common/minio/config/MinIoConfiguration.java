package com.mall.common.minio.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author naidelii
 */
@Configuration
@EnableConfigurationProperties(MinIoProperties.class)
public class MinIoConfiguration {

    @Autowired
    private MinIoProperties minIoProperties;

    @Bean
    @Primary
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minIoProperties.getUrl())
                .credentials(minIoProperties.getName(), minIoProperties.getPass())
                .build();
    }
}
