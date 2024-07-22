package com.mall.admin.biz.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.admin.api.entity.SysUser;
import com.mall.admin.biz.domain.dto.SysUserListQuery;
import com.mall.admin.biz.domain.dto.SysUserSaveDto;
import com.mall.admin.biz.domain.dto.SysUserUpdateDto;
import com.mall.admin.biz.domain.vo.SysUserInfoVo;
import com.mall.admin.biz.domain.vo.SysUserListVo;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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
        IPage<SysUserListVo> pageList = userService.selectListPage(pageNo, pageSize, query);
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

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @SaCheckPermission("sys:user:info")
    public Result<?> info(@PathVariable("userId") String userId) {
        SysUserInfoVo sysUserVo = userService.getUserInfo(userId);
        return Result.success(sysUserVo);
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
     * 删除用户
     */
    @PostMapping("/delete")
    @SaCheckPermission("sys:user:delete")
    public Result<?> delete(@RequestBody Set<String> userIds) {
        if (CollUtil.contains(userIds, getUserId())) {
            return Result.fail("当前用户不能删除");
        }
        if (CollUtil.contains(userIds, CommonConstants.SUPER_ADMIN)) {
            return Result.fail("管理员不能删除");
        }
        userService.deleteUserByIds(userIds);
        return Result.success();
    }


}
