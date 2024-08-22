package com.mall.product.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.entity.ProductCategory;

import java.util.List;
import java.util.Set;

/**
 * 商品分类表
 *
 * @author naidelii
 * @since 2024-07-15 18:06:50
 */
public interface IProductCategoryService extends IService<ProductCategory> {

    /**
     * 根据id进行删除
     *
     * @param id 主键
     */
    void deleteById(String id);

    /**
     * 批量删除
     *
     * @param categoryIds 分类id集合
     */
    void deleteByIds(Set<String> categoryIds);

    /**
     * 根据id查询出完整的path
     *
     * @param id 主键
     * @return List<String>
     */
    List<String> getCategoryPathById(String id);

    /**
     * 更新分类数据
     *
     * @param data 分类数据
     */
    void updateData(ProductCategory data);

    /**
     * 查询所有的三级分类数据
     *
     * @return List<ProductCategory>
     */
    List<ProductCategory> listAllData();

}

