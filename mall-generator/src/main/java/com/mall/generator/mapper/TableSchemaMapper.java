package com.mall.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.generator.domain.dto.TableSchemaQuery;
import com.mall.generator.domain.entity.ColumnSchema;
import com.mall.generator.domain.entity.TableSchema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author naidelii
 */
public interface TableSchemaMapper extends BaseMapper<TableSchema> {

    IPage<TableSchema> selectListPage(@Param("page") IPage<TableSchema> page, @Param("query") TableSchemaQuery query);

    TableSchema queryTable(@Param("tableName") String tableName);

    List<ColumnSchema> queryColumns(@Param("tableName") String tableName);
}
