package com.mall.product.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.entity.ProductBrandCategoryRelation;
import com.mall.product.biz.domain.vo.ProductBrandCategoryRelationListVO;

import java.util.List;

/**
 * 品牌分类关联表
 *
 * @author naidelii
 */
public interface IProductBrandCategoryRelationService extends IService<ProductBrandCategoryRelation> {


    /**
     * 保存详情
     *
     * @param data 品牌分类关联信息
     */
    void saveDetail(ProductBrandCategoryRelation data);

    /**
     * 根据品牌id查询 品牌相关的分类信息
     *
     * @param brandId 品牌id
     * @return List<ProductCategoryBrandListVO>
     */
    List<ProductBrandCategoryRelationListVO> listByBrandId(String brandId);
}

