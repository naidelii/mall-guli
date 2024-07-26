package com.mall.generator.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author naidelii
 */
@Data
public class TableSchema {
    private String tableName;

    private String engine;

    private String tableComment;

    private LocalDateTime createTime;

}
