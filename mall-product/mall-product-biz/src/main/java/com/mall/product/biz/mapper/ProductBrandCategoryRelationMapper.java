package com.mall.product.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.biz.domain.entity.ProductBrandCategoryRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Mapper
public interface ProductBrandCategoryRelationMapper extends BaseMapper<ProductBrandCategoryRelation> {

    void updateBrand(@Param("brandId") String brandId, @Param("brandName") String brandName);

    void updateCategory(@Param("categoryId") String categoryId, @Param("categoryName") String categoryName);
}
