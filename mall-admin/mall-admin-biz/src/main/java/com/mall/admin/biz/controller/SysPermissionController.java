package com.mall.admin.biz.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.api.entity.SysPermission;
import com.mall.admin.biz.domain.dto.SysPermissionSaveDto;
import com.mall.admin.biz.domain.dto.SysPermissionUpdateDto;
import com.mall.admin.biz.domain.vo.SysPermissionInfoVo;
import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;
import com.mall.admin.biz.service.ISysPermissionService;
import com.mall.admin.biz.service.ISysRolePermissionService;
import com.mall.common.base.api.Result;
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
     * 导航菜单
     *
     * @return Result
     */
    @GetMapping("/nav")
    public Result<List<SysPermissionTreeVo>> nav() {
        List<SysPermissionTreeVo> menuList = rolePermissionService.selectUserMenuList();
        return Result.success(menuList);
    }


    /**
     * 查询所有菜单权限列表
     *
     * @return List<SysPermissionListVo>
     */
    @GetMapping("/list")
    public Result<List<SysPermissionListVo>> list() {
        List<SysPermissionListVo> menuList = permissionService.selectPermissionList();
        return Result.success(menuList);
    }

    /**
     * 查询所有菜单列表
     *
     * @return List<SysPermissionListVo>
     */
    @GetMapping("/menuList")
    public Result<List<SysPermissionListVo>> menuList() {
        List<SysPermissionListVo> menuList = permissionService.selectMenuList();
        return Result.success(menuList);
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
     * @param permissionId 菜单权限id
     * @return 用户信息
     */
    @GetMapping("/info/{permissionId}")
    @SaCheckPermission("sys:permission:info")
    public Result<?> info(@PathVariable("permissionId") String permissionId) {
        SysPermissionInfoVo vo = permissionService.getPermissionInfo(permissionId);
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
