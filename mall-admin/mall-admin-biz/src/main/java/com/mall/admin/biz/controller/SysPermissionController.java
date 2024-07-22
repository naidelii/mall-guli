package com.mall.admin.biz.controller;


import com.mall.admin.biz.domain.vo.SysPermissionListVo;
import com.mall.admin.biz.domain.vo.SysPermissionTreeVo;
import com.mall.admin.biz.service.ISysPermissionService;
import com.mall.common.base.api.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SysPermissionController extends AbstractController {

    private final ISysPermissionService permissionService;


    /**
     * 导航菜单
     *
     * @return Result
     */
    @GetMapping("/nav")
    public Result<List<SysPermissionTreeVo>> nav() {
        List<SysPermissionTreeVo> menuList = permissionService.selectUserMenuList();
        return Result.success(menuList);
    }


    /**
     * 查询所有菜单列表
     *
     * @return List<SysPermissionListVo>
     */
    @GetMapping("/list")
    public Result<List<SysPermissionListVo>> list() {
        List<SysPermissionListVo> menuList = permissionService.selectAllMenuList();
        return Result.success(menuList);

    }
}
