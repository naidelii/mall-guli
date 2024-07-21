package com.mall.common.security.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.hutool.core.util.StrUtil;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.constant.enums.ResultCodeEnum;
import com.mall.common.base.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author naidelii
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 处理Exception异常
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        log.error("=========Exception：{}", e.getMessage());
        return Result.fail();
    }


    /**
     * 处理没有权限
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler({NotPermissionException.class, NotRoleException.class})
    public Result<Object> handleNotPermissionException(Exception e) {
        log.error("=========handleNotPermissionException：{}", e.getMessage());
        return Result.fail(ResultCodeEnum.FORBIDDEN);
    }

    /**
     * 处理未登录
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<Object> handleNotLoginException(Exception e) {
        log.error("=========handleNotLoginException：{}", e.getMessage());
        return Result.fail(ResultCodeEnum.UNAUTHORIZED);
    }

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return Result
     */
    @ExceptionHandler(GlobalException.class)
    public Result<?> handlerBootException(GlobalException e) {
        log.error("=========自定义异常：{}", e.getMessage());
        return Result.fail(e.getMessage());
    }


    /**
     * 处理参数校验错误
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler({BindException.class})
    public Result<Object> handleBindException(BindException e) {
        log.error("=========handleBindException：{}", e.getMessage());
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String defaultMessage = objectError.getDefaultMessage();
        String errorMsg = StrUtil.isNotBlank(defaultMessage) ? defaultMessage : CommonConstants.PARAM_VERIFY_ERROR_STR;
        return Result.fail(errorMsg);
    }

    /**
     * 处理参数校验错误（@RequestBody）
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("=========handleMethodArgumentNotValidException：{}", e.getMessage());
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String defaultMessage = objectError.getDefaultMessage();
        String errorMsg = StrUtil.isNotBlank(defaultMessage) ? defaultMessage : CommonConstants.PARAM_VERIFY_ERROR_STR;
        return Result.fail(errorMsg);
    }


}
