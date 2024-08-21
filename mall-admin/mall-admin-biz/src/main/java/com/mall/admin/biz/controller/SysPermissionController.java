package com.mall.admin.biz.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.converter.SysPermissionConverter;
import com.mall.admin.biz.domain.dto.SysPermissionSaveDto;
import com.mall.admin.biz.domain.dto.SysPermissionUpdateDto;
import com.mall.admin.biz.domain.vo.SysPermissionInfoVo;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;
import com.mall.admin.biz.service.ISysPermissionService;
import com.mall.admin.biz.service.ISysRolePermissionService;
import com.mall.common.base.api.Result;
import com.mall.common.security.context.SecurityContext;
import com.mall.common.security.domain.LoginUser;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Naidelii
 */
@Api(tags = "菜单权限信息")
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final ISysRolePermissionService rolePermissionService;
    private final ISysPermissionService permissionService;

    /**
     * 查询所有菜单权限列表（列表展示）
     *
     * @return List<SysPermissionListVo>
     */
    @GetMapping("/list")
    public Result<List<SysPermissionListVo>> list() {
        List<SysPermission> menuList = permissionService.listAllPermissions();
        List<SysPermissionListVo> listVos = SysPermissionConverter.buildMenuList(menuList);
        return Result.success(listVos);
    }

    /**
     * 查询所有菜单权限列表（树形结构）
     *
     * @return List<SysPermissionListVo>
     */
    @GetMapping("/getPermissionListToTree")
    public Result<List<SysPermissionTreeVo>> getPermissionListToTree() {
        List<SysPermission> menuList = permissionService.listAllPermissions();
        List<SysPermissionTreeVo> vos = SysPermissionConverter.buildMenuTree(menuList);
        return Result.success(vos);
    }

    /**
     * 导航菜单
     *
     * @return Result
     */
    @GetMapping("/nav")
    public Result<List<SysPermissionListVo>> nav() {
        // 获取当前登录用户
        LoginUser loginUser = SecurityContext.getLoginUser();
        List<SysPermission> menuList = rolePermissionService.listMenusByUserId(loginUser.getId());
        List<SysPermissionListVo> listVos = SysPermissionConverter.buildMenuList(menuList);
        return Result.success(listVos);
    }


    /**
     * 查询所有菜单列表
     *
     * @return List<SysPermissionListVo>
     */
    @GetMapping("/getMenuListToTree")
    public Result<List<SysPermissionTreeVo>> getMenuListToTree() {
        List<SysPermission> menuList = permissionService.listAllMenus();
        List<SysPermissionTreeVo> vos = SysPermissionConverter.buildMenuTree(menuList);
        return Result.success(vos);
    }

    /**
     * 添加菜单权限
     *
     * @param dto 菜单权限信息
     * @return Result
     */
    @PostMapping("/save")
    @SaCheckPermission("sys:permission:save")
    public Result<?> save(@RequestBody SysPermissionSaveDto dto) {
        SysPermission permission = new SysPermission();
        BeanUtil.copyProperties(dto, permission);
        permissionService.savePermission(permission);
        return Result.success();
    }

    /**
     * 修改菜单权限
     *
     * @param dto 菜单权限信息
     * @return Result
     */
    @PostMapping("/update")
    @SaCheckPermission("sys:permission:update")
    public Result<?> update(@RequestBody SysPermissionUpdateDto dto) {
        SysPermission permission = new SysPermission();
        BeanUtil.copyProperties(dto, permission);
        permissionService.updatePermission(permission);
        return Result.success();
    }


    /**
     * 根据id获取菜单权限信息
     *
     * @param id 菜单权限id
     * @return 用户信息
     */
    @GetMapping("/getPermissionById")
    @SaCheckPermission("sys:permission:info")
    public Result<?> getPermissionById(@RequestParam("id") String id) {
        SysPermissionInfoVo vo = permissionService.getDetailsById(id);
        return Result.success(vo);
    }

    /**
     * 根据id删除菜单权限信息
     *
     * @param id 菜单权限id
     * @return 用户信息
     */
    @GetMapping("/delete/{id}")
    @SaCheckPermission("sys:permission:delete")
    public Result<?> delete(@PathVariable("id") String id) {
        permissionService.deleteById(id);
        return Result.success();
    }

}
