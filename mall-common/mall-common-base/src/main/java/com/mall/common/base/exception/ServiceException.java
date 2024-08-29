package com.mall.common.base.exception;

import com.mall.common.base.constant.enums.ResultCodeEnum;

/**
 * 业务异常
 *
 * @author ruoyi
 */
public final class ServiceException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ServiceException(Integer code, String message) {
        super(code, message);
    }

    public ServiceException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getCode(), resultCodeEnum.getMessage());
    }

}