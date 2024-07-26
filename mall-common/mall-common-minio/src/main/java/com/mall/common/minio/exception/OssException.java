package com.mall.common.minio.exception;


/**
 * @author naidelii
 * 文件存储异常
 */
public class OssException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    public OssException() {
    }

    public OssException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
