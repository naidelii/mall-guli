package com.mall.common.minio.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * OssFile
 *
 * @author meng
 */
@Data
public class OssFile {
    /**
     * 文件地址
     */
    private String filePath;

    /**
     * 域名地址
     */
    private String domain;

    /**
     * 原始文件名
     */
    private String originalName;

    /**
     * 文件hash值
     */
    public String hash;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件上传时间
     */
    private LocalDateTime putTime;

    /**
     * 文件contentType
     */
    private String contentType;
}
