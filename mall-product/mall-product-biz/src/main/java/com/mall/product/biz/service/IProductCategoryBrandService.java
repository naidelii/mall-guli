package com.mall.product.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.entity.ProductCategoryBrand;
import com.mall.product.biz.domain.vo.ProductCategoryBrandListVO;

import java.util.List;

/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
public interface IProductCategoryBrandService extends IService<ProductCategoryBrand> {


    /**
     * 保存详情
     *
     * @param data 品牌分类关联信息
     */
    void saveDetail(ProductCategoryBrand data);

    /**
     * 根据品牌id查询 品牌相关的分类信息
     *
     * @param brandId 品牌id
     * @return List<ProductCategoryBrandListVO>
     */
    List<ProductCategoryBrandListVO> listByBrandId(String brandId);
}

