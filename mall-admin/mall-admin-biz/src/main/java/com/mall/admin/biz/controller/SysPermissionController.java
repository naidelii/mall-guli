package com.mall.admin.biz.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Naidelii
 */
@Api(tags = "菜单权限信息")
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

}
