package com.mall.common.minio.service.impl;

import com.mall.common.base.exception.GlobalException;
import com.mall.common.minio.config.MinIoProperties;
import com.mall.common.minio.entity.OssFile;
import com.mall.common.minio.service.IOssService;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MinioServiceImpl implements IOssService {

    private final MinioClient minioClient;

    private final MinIoProperties minIoProperties;

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            BucketExistsArgs build = BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build();
            return minioClient.bucketExists(build);
        } catch (Exception e) {
            log.error("minio bucketExists Exception", e);
            return false;
        }
    }

    @Override
    public OssFile upLoadFile(String filePath, String originalFilename, InputStream stream) {
        return doUpLoadFile(minIoProperties.getBucketName(), filePath, originalFilename, stream, "application/octet-stream");
    }

    public OssFile doUpLoadFile(String bucketName,
                                String filePath,
                                String originalName,
                                InputStream stream,
                                String contentType) {
        if (!bucketExists(bucketName)) {
            log.info("minio bucketName is not create");
            throw new GlobalException("文件上传失败，请联系管理员");
        }
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    // 桶名
                    .bucket(bucketName)
                    // 文件名
                    .object(filePath)
                    // 文件内容
                    .stream(stream, stream.available(), -1)
                    // 文件类型
                    .contentType(contentType)
                    .build();
            minioClient.putObject(args);
            log.info("MinIO upLoadFile success, filePath:{}", filePath);
            return buildOssFile(bucketName, originalName, filePath);
        } catch (Exception e) {
            log.error("MinIO upLoadFile Exception", e);
            throw new GlobalException("文件上传失败，请联系管理员");
        }
    }

    private OssFile buildOssFile(String bucketName, String originalName, String filePath) {
        OssFile file = new OssFile();
        file.setOriginalName(originalName);
        file.setDomain(getOssHost(bucketName));
        file.setFilePath(filePath);
        return file;
    }

    /**
     * 获取域名
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    public String getOssHost(String bucketName) {
        return minIoProperties.getUrl() + File.separator + bucketName;
    }

    @Override
    public void downloadFile(HttpServletResponse response, String filePath) {
        GetObjectArgs build = GetObjectArgs.builder()
                .bucket(minIoProperties.getBucketName())
                .object(filePath)
                .build();
        try (GetObjectResponse is = minioClient.getObject(build)) {
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filePath, "utf-8"));
            try (OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
            log.info("MinIO downloadFile success, filePath:{}", filePath);
        } catch (Exception e) {
            log.error("MinIO downloadFile Exception", e);
            throw new GlobalException("文件下载失败！");
        }
    }

    @Override
    public String generateFileName(String dirName, String originalFilename) {
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();
        // 拼接文件路径
        return File.separator + dirName + File.separator + timestamp + "_" + originalFilename;
    }

}
