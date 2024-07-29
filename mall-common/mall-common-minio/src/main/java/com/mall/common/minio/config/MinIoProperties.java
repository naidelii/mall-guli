package com.mall.common.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * @author naidelii
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {

    /**
     * 对象存储服务的URL（minio地址）
     */
    private String endpoint;

    /**
     * Access key就像用户ID，可以唯一标识你的账户
     */
    private String accessKey;

    /**
     * Secret key是你账户的密码
     */
    private String secretKey;

    /**
     * 默认文件桶
     */
    private String bucket;

    /**
     * 获取域名
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    public String getOssHost(String bucketName) {
        return endpoint + File.separator + bucketName;
    }

}
