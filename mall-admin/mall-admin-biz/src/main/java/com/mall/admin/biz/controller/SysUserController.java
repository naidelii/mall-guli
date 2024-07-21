package com.mall.admin.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.base.api.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 用户列表
     */
    @GetMapping("/listPage")
    @SaCheckPermission("sys:user:list")
    public Result<?> list(@RequestParam(name = "page", defaultValue = "1") Integer pageNo,
                          @RequestParam(name = "limit", defaultValue = "10") Integer pageSize) {
        Page<SysUser> page = new Page<>(pageNo, pageSize);
        IPage<SysUserInfoVo> pageList = userService.selectListPage(page);
        return Result.success(pageList);
    }


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
