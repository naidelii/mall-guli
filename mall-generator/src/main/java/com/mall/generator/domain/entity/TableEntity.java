package com.mall.generator.domain.entity;

import lombok.Data;

import java.util.List;

/**
 * 表数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月20日 上午12:02:55
 */
@Data
public class TableEntity {
    private String tableName;
    private String comments;
    private ColumnEntity pk;
    private boolean hasBigDecimal;
    private boolean hasList;
    private List<ColumnEntity> columns;
    private String pascalCaseClassName;

    private String camelCaseClassName;

}
