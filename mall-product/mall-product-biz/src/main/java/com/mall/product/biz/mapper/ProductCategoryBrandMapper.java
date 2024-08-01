package com.mall.product.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.biz.domain.entity.ProductCategoryBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
@Mapper
public interface ProductCategoryBrandMapper extends BaseMapper<ProductCategoryBrand> {

    void updateBrand(@Param("brandId") String brandId, @Param("brandName") String brandName);

    void updateCategory(@Param("categoryId") String categoryId, @Param("categoryName") String categoryName);
}
