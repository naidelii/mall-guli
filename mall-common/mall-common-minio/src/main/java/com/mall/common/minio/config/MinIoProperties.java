package com.mall.common.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author naidelii
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {

    /**
     * minio地址
     */
    private String url;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String pass;

    /**
     * 桶名
     */
    private String bucketName;

}
