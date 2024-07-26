package com.mall.generator.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.generator.domain.dto.TableSchemaQuery;
import com.mall.generator.domain.entity.ColumnSchema;
import com.mall.generator.domain.entity.TableSchema;
import com.mall.generator.mapper.TableSchemaMapper;
import com.mall.generator.utils.GenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
@RequiredArgsConstructor
public class SysGeneratorService {

    private final TableSchemaMapper tableSchemaMapper;

    public IPage<TableSchema> selectListPage(Integer pageNo, Integer pageSize, TableSchemaQuery query) {
        IPage<TableSchema> page = new Page<>(pageNo, pageSize);
        return tableSchemaMapper.selectListPage(page, query);
    }

    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            TableSchema tableInfo = tableSchemaMapper.queryTable(tableName);
            List<ColumnSchema> columnSchemas = tableSchemaMapper.queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(tableInfo, columnSchemas, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }


}
