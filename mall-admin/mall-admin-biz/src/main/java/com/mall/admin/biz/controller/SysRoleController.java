package com.mall.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.admin.biz.domain.dto.SysRoleListQuery;
import com.mall.admin.biz.domain.vo.SysRoleListVo;
import com.mall.admin.biz.service.ISysRoleService;
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
@Api(tags = "角色信息")
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService roleService;

    /**
     * 角色列表
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @param query    查询对象
     * @return IPage<SysRoleListVo>
     */
    @GetMapping("/listPage")
    public Result<IPage<SysRoleListVo>> list(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                                             @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                                             SysRoleListQuery query) {
        IPage<SysRoleListVo> pageList = roleService.selectListPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }

}
