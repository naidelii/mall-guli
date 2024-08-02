package com.mall.product.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.dto.ProductAttrQuery;
import com.mall.product.biz.domain.entity.ProductAttr;
import com.mall.product.biz.domain.vo.ProductAttrListVO;
import com.mall.product.biz.domain.vo.ProductAttrVO;

/**
 * 商品属性表
 *
 * @author naidelii
 */
public interface IProductAttrService extends IService<ProductAttr> {


    IPage<ProductAttrListVO> listAttrWithPage(Integer pageNo, Integer pageSize, ProductAttrQuery query, Integer attrType);


    ProductAttrVO getDetailsById(String id);

    void updateData(ProductAttr data, String attrGroupId);

    void saveData(ProductAttr data, String attrGroupId);
}

