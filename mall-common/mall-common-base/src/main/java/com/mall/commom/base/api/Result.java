package com.mall.commom.base.api;

import com.mall.commom.base.constant.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author naidelii
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 响应Code
     */
    private int code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 私有化构造器
     */
    private Result() {

    }

    private Result(ResultCodeEnum resultCodeEnum, T data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
        this.data = data;
    }

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功响应（无数据）
     *
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS, null);
    }

    /**
     * 成功响应（有数据）
     *
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS, data);
    }

    /**
     * 失败响应（无数据）
     *
     * @return Result
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResultCodeEnum.FAIL, null);
    }

    /**
     * 失败响应（自定义消息）
     *
     * @return Result
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCodeEnum.FAIL.getCode(), msg, null);
    }

    /**
     * 失败响应（自定义状态码和消息）
     *
     * @return Result
     */
    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum) {
        return new Result<>(resultCodeEnum, null);
    }

    /**
     * 身份认证失败响应
     *
     * @return Result
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(ResultCodeEnum.UNAUTHORIZED, null);
    }

}
