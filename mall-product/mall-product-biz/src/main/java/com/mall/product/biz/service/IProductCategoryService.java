package com.mall.product.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.domain.vo.ProductCategoryListTreeVO;

import java.util.List;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
public interface IProductCategoryService extends IService<ProductCategory> {

    /**
     * 查询出所有分类及其子分类，以树形结构展示
     *
     * @return 商品分类树
     */
    List<ProductCategoryListTreeVO> listWithTree();

    /**
     * 批量删除
     *
     * @param categoryIds 分类id集合
     */
    void deleteByIds(List<String> categoryIds);

    /**
     * 根据id查询出完整的path
     *
     * @param id 主键
     * @return List<String>
     */
    List<String> getCategoryPathById(String id);
}

