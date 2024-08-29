package com.mall.gateway.exception;

import com.mall.common.base.constant.enums.ResultCodeEnum;
import com.mall.common.base.exception.BaseException;

/**
 * @author naidelii
 * 身份未认证异常
 */
public final class UnauthorizedException extends BaseException {

    public UnauthorizedException() {
        super(ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getMessage());
    }
}
