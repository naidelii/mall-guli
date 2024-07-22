package com.mall.admin.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.admin.biz.domain.dto.SysUserListQuery;
import com.mall.admin.biz.domain.vo.SysUserListVo;
import com.mall.admin.biz.service.ISysUserService;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
