package com.mall.common.base.exception;

/**
 * 基础异常
 *
 * @author ruoyi
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    /**
     * 错误提示
     */
    private String msg;

    /**
     * 错误码
     */
    private int code;

    /**
     * 空构造方法，避免反序列化问题
     */
    public BaseException() {

    }

    public BaseException(String message) {
        this.msg = message;
    }

    public BaseException(Integer code, String message) {
        this.msg = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

    public Integer getCode() {
        return this.code;
    }
}