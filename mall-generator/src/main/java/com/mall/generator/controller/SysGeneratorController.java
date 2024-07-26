package com.mall.generator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.common.base.api.Result;
import com.mall.common.base.constant.CommonConstants;
import com.mall.generator.domain.dto.TableSchemaQuery;
import com.mall.generator.domain.entity.TableSchema;
import com.mall.generator.service.impl.SysGeneratorService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
@RequestMapping("/sys/generator")
@RequiredArgsConstructor
public class SysGeneratorController {

    private final SysGeneratorService generatorService;

    @GetMapping("/listPage")
    @ResponseBody
    public Result<?> list(@RequestParam(name = CommonConstants.PAGE_NO_PARAM, defaultValue = CommonConstants.PAGE_NO_DEFAULT) Integer pageNo,
                          @RequestParam(name = CommonConstants.PAGE_SIZE_PARAM, defaultValue = CommonConstants.PAGE_SIZE_DEFAULT) Integer pageSize,
                          TableSchemaQuery query) {
        IPage<TableSchema> pageList = generatorService.selectListPage(pageNo, pageSize, query);
        return Result.success(pageList);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, HttpServletResponse response) throws IOException {
        byte[] data = generatorService.generatorCode(tables.split(","));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
