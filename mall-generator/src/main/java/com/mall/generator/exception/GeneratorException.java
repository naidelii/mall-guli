package com.mall.generator.exception;

/**
 * 自定义异常
 *
 * @author naidelii
 */
public class GeneratorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    public GeneratorException() {
    }

    public GeneratorException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
