package com.mall.admin.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.*;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;
import com.mall.admin.biz.domain.vo.SysUserListVo;
import com.mall.admin.biz.service.ISysUserRoleService;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.data.utils.PageUtils;
import com.mall.common.security.context.SecurityContext;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Naidelii
 */
@Api(tags = "系统用户")
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService userService;
    private final ISysUserRoleService userRoleService;

    /**
     * 用户列表
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @param query    查询对象
     * @return IPage<SysUserListVo>
     */
    @GetMapping("/listPage")
    public Result<IPage<SysUserListVo>> list(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                             @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                             SysUserListQuery query) {
        IPage<SysUser> pageList = userService.listUsersByPage(pageNo, pageSize, query);
        List<SysUserListVo> userListVos = pageList.getRecords()
                .stream()
                .map(SysUserListVo::new)
                .collect(Collectors.toList());
        IPage<SysUserListVo> pageVo = PageUtils.buildPage(userListVos, pageList);
        return Result.success(pageVo);
    }


    /**
     * 获取登录的用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getCurrentUser")
    public Result<?> getCurrentUser() {
        return Result.success(SecurityContext.getLoginUser());
    }

    /**
     * 根据id获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/getUserById")
    @SaCheckPermission("sys:user:info")
    public Result<?> getUserById(@RequestParam("id") String id) {
        SysUser sysUser = userService.getById(id);
        SysUserInfoVo vo = new SysUserInfoVo(sysUser);
        List<SysRole> rolelist = userRoleService.listRolesByUserId(id);
        Set<String> roleIds = rolelist.stream()
                .map(SysRole::getId)
                .collect(Collectors.toSet());
        vo.setRoleIds(roleIds);
        return Result.success(vo);
    }


    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return Result
     */
    @PostMapping("/save")
    @SaCheckPermission("sys:user:save")
    public Result<?> save(@Valid @RequestBody SysUserSaveDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(userDto, sysUser);
        userService.saveUser(sysUser, userDto.getRoleIds());
        return Result.success();
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @SaCheckPermission("sys:user:update")
    public Result<?> update(@Valid @RequestBody SysUserUpdateDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(userDto, sysUser);
        Set<String> roleIdList = userDto.getRoleIds();
        userService.updateUser(sysUser, roleIdList);
        return Result.success();
    }


    /**
     * 更新自己的登录密码
     *
     * @param dto 用户信息
     * @return userService
     */
    @PostMapping("/updatePassword")
    public Result<?> updatePassword(@Valid @RequestBody SysUserUpdatePasswordDto dto) {
        String userId = SecurityContext.getLoginUser().getId();
        userService.updatePassword(userId, dto.getPassword(), dto.getNewPassword());
        return Result.success();
    }

    /**
     * 重置用户密码
     *
     * @param dto 用户信息
     * @return userService
     */
    @PostMapping("/resetPassword")
    @SaCheckRole(CommonConstants.SUPER_ADMIN_ROLE)
    public Result<?> resetPassword(@Valid @RequestBody SysUserResetPasswordDto dto) {
        userService.resetPassword(dto.getId(), dto.getNewPassword());
        return Result.success();
    }

    /**
     * 批量删除用户
     *
     * @param ids 用户id集合
     * @return Result
     */
    @PostMapping("/deleteBatch")
    @SaCheckPermission("sys:user:delete")
    public Result<?> deleteBatch(@RequestBody Set<String> ids) {
        String userId = SecurityContext.getLoginUser().getId();
        if (CollUtil.contains(ids, userId)) {
            return Result.fail("当前用户不能删除");
        }
        if (CollUtil.contains(ids, CommonConstants.SUPER_ADMIN)) {
            return Result.fail("管理员不能删除");
        }
        userService.deleteUserByIds(ids);
        return Result.success();
    }

}
