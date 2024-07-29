package com.mall.common.minio.service;

import com.mall.common.minio.entity.OssFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * @author naidelii
 */
public interface IOssService {


    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    boolean bucketExists(String bucketName);

    /**
     * 上传文件
     *
     * @param filePath         文件的路径
     * @param originalFilename 文件原始名称
     * @param stream           文件流
     * @return OssFile
     */
    OssFile upLoadFile(String filePath, String originalFilename, InputStream stream);

    /**
     * 下载文件
     *
     * @param response HttpServletResponse
     * @param filePath 文件路径
     */
    void downloadFile(HttpServletResponse response, String filePath);

    /**
     * 生成文件路径
     *
     * @param dirName          文件夹
     * @param originalFilename 原始文件名
     * @return 新的文件名
     */
    String generateFileName(String dirName, String originalFilename);

    /**
     * 生成文件上传凭证
     *
     * @param fileName 文件名
     * @return Map
     */
    Map<String, String> generatePolicy(String fileName);

}
