package com.mall.product.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.dto.ProductAttrQuery;
import com.mall.product.biz.domain.entity.ProductAttributes;
import com.mall.product.biz.domain.vo.ProductAttrListVO;
import com.mall.product.biz.domain.vo.ProductAttrRelationVO;
import com.mall.product.biz.domain.vo.ProductAttrVO;

import java.util.List;

/**
 * 商品属性表
 *
 * @author naidelii
 */
public interface IProductAttributesService extends IService<ProductAttributes> {


    IPage<ProductAttrListVO> listAttrWithPage(Integer pageNo, Integer pageSize, ProductAttrQuery query, Integer attrType);


    ProductAttrVO getDetailsById(String id);

    void updateData(ProductAttributes data);

    void saveData(ProductAttributes data);

    List<ProductAttrRelationVO> listAttrGroupsWithAttributes(String attrGroupId);

    IPage<ProductAttrRelationVO> listUnrelatedAttributes(Integer pageNo, Integer pageSize, String attrGroupId, String categoryId);
}

