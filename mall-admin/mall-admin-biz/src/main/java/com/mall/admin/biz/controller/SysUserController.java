package com.mall.admin.biz.controller;

import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.base.api.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Naidelii
 */
@Api(tags = "系统用户")
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController extends AbstractController {

    private final ISysUserService userService;


    /**
     * 获取登录的用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<?> info() {
        return Result.success(getUser());
    }

}
