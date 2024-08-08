package com.mall.product.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.biz.domain.entity.ProductAttributes;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性表
 *
 * @author naidelii
 */
@Mapper
public interface ProductAttributesMapper extends BaseMapper<ProductAttributes> {

}
