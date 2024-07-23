package com.mall.admin.biz.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.admin.api.entity.SysRole;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.dto.SysRoleSaveDto;
import com.mall.admin.biz.domain.dto.SysRoleUpdateDto;
import com.mall.admin.biz.domain.vo.SysRoleInfoVo;
import com.mall.admin.biz.domain.vo.SysRoleListVo;
import com.mall.admin.biz.service.ISysRoleService;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author Naidelii
 */
@Api(tags = "角色信息")
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService roleService;

    /**
     * 角色列表分页
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @param query    查询对象
     * @return IPage<SysRoleListVo>
     */
    @GetMapping("/listPage")
    public Result<IPage<SysRoleListVo>> listPage(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                                 @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                                 SysRoleListQuery query) {
        IPage<SysRoleListVo> pageList = roleService.selectListPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }

    /**
     * 角色列表
     *
     * @return List<SysRoleListVo>
     */
    @GetMapping("/list")
    @SaCheckPermission("sys:role:list")
    public Result<List<SysRoleListVo>> list() {
        List<SysRoleListVo> list = roleService.selectList();
        return Result.success(list);
    }


    /**
     * 新增角色
     *
     * @param roleDto 角色信息
     * @return Result
     */
    @PostMapping("/save")
    @SaCheckPermission("sys:role:save")
    public Result<?> save(@Valid @RequestBody SysRoleSaveDto roleDto) {
        SysRole sysRoleEntity = new SysRole();
        BeanUtil.copyProperties(roleDto, sysRoleEntity);
        roleService.saveRole(sysRoleEntity, roleDto.getPermissionIds());
        return Result.success();
    }

    /**
     * 修改角色
     *
     * @param roleDto 角色信息
     * @return Result
     */
    @PostMapping("/update")
    @SaCheckPermission("sys:role:update")
    public Result<?> save(@Valid @RequestBody SysRoleUpdateDto roleDto) {
        SysRole sysRoleEntity = new SysRole();
        BeanUtil.copyProperties(roleDto, sysRoleEntity);
        roleService.updateRole(sysRoleEntity, roleDto.getPermissionIds());
        return Result.success();
    }


    /**
     * 根据id获取用户信息
     *
     * @param id 角色id
     * @return 角色信息
     */
    @GetMapping("/info/{id}")
    @SaCheckPermission("sys:role:info")
    public Result<?> info(@PathVariable("id") String id) {
        SysRoleInfoVo sysUserVo = roleService.selectInfoById(id);
        return Result.success(sysUserVo);
    }

    /**
     * 删除角色
     */
    @PostMapping("/delete")
    @SaCheckPermission("sys:role:delete")
    public Result<?> delete(@RequestBody Set<String> roleIds) {
        roleService.deleteByIds(roleIds);
        return Result.success();
    }
}
