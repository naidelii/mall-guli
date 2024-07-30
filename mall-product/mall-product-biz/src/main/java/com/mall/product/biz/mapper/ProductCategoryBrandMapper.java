package com.mall.product.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.biz.domain.entity.ProductCategoryBrand;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Mapper
public interface ProductCategoryBrandMapper extends BaseMapper<ProductCategoryBrand> {

}
