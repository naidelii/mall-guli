package com.mall.product.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.biz.domain.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    /**
     * 根据parentId查询子分类
     *
     * @param parentId 父级id
     * @return List<ProductCategory>
     */
    List<ProductCategory> listByParentId(@Param("parentId") String parentId);
}
