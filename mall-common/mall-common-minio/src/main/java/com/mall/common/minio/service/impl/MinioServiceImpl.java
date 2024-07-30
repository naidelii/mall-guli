package com.mall.common.minio.service.impl;

import com.mall.common.base.exception.GlobalException;
import com.mall.common.minio.config.MinIoProperties;
import com.mall.common.minio.entity.OssFile;
import com.mall.common.minio.service.IOssService;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.util.Map;

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
        return doUpLoadFile(minIoProperties.getBucket(), filePath, originalFilename, stream, "application/octet-stream");
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
        file.setDomain(minIoProperties.getOssHost(bucketName));
        file.setFilePath(filePath);
        return file;
    }

    @Override
    public void downloadFile(HttpServletResponse response, String filePath) {
        GetObjectArgs build = GetObjectArgs.builder()
                .bucket(minIoProperties.getBucket())
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
        String prefix = "";
        if (StringUtils.isNotBlank(dirName)) {
            prefix = File.separator + dirName + File.separator;
        }
        return prefix + timestamp + "_" + originalFilename;
    }

    @Override
    public Map<String, String> generatePolicy(String dirName, String originalFilename) {
        // 设置凭证有效期（5分钟）
        ZonedDateTime expirationDate = ZonedDateTime.now().plusMinutes(5);
        // 创建一个凭证
        PostPolicy postPolicy = new PostPolicy(minIoProperties.getBucket(), expirationDate);
        // 添加条件：值为上传对象的名称（保存在桶中的名字）
        String fileName = generateFileName(dirName, originalFilename);
        postPolicy.addEqualsCondition("key", fileName);
        try {
            // 生成凭证并返回
            Map<String, String> formData = minioClient.getPresignedPostFormData(postPolicy);
            // 将生成的文件名返回
            formData.put("key", fileName);
            return formData;
        } catch (Exception e) {
            log.error("获取文件 {} 的上传凭证失败", originalFilename, e);
            throw new GlobalException("获取凭证失败！");
        }
    }

}
