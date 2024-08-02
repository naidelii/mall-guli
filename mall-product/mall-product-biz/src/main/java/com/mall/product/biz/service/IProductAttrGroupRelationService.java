package com.mall.product.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.entity.ProductAttrGroupRelation;

/**
 * 商品属性和商品属性分组关联表
 *
 * @author naidelii
 */
public interface IProductAttrGroupRelationService extends IService<ProductAttrGroupRelation> {

    void saveOrUpdateAttrInfo(String attrId, String attrGroupId);
}

