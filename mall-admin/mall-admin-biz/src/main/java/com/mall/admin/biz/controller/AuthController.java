package com.mall.admin.biz.controller;


import com.mall.admin.biz.domain.dto.LoginUserDto;
import com.mall.admin.biz.service.IAuthService;
import com.mall.admin.biz.service.IVerifyCodeService;
import com.mall.common.base.api.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author naidelii
 */
@Api(tags = "用户认证")
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IVerifyCodeService verifyCodeService;
    private final IAuthService authService;


    /**
     * @param key 验证码key
     * @return 生成的验证码信息
     */
    @GetMapping("/captcha")
    public Result<Map<String, Object>> captcha(@RequestParam String key) {
        Map<String, Object> result = verifyCodeService.generateCode(key);
        return Result.success(result);
    }

    /**
     * 登录
     *
     * @param loginUser 用户信息
     * @return token
     */
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginUserDto loginUser) {
        log.info("登录的用户,{}", loginUser);
        String token = authService.authenticateUser(loginUser);
        return Result.success(token);
    }

}