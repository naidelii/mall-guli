package com.mall.common.base.exception;

/**
 * @author naidelii
 * 通用异常
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String msg;


    public GlobalException() {
    }

    public GlobalException(String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
