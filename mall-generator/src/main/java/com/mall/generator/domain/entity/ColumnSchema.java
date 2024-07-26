package com.mall.generator.domain.entity;

import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class ColumnSchema {
    private String columnName;

    private String dataType;

    private String columnComment;

    private String columnKey;

    private String extra;
}
