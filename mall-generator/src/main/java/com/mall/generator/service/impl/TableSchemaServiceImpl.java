package com.mall.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.generator.domain.entity.TableSchema;
import com.mall.generator.mapper.TableSchemaMapper;
import com.mall.generator.service.ITableSchemaService;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Service
public class TableSchemaServiceImpl extends ServiceImpl<TableSchemaMapper, TableSchema> implements ITableSchemaService {

}
