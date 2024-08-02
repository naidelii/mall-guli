package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.product.biz.domain.entity.ProductAttrGroupRelation;
import com.mall.product.biz.mapper.ProductAttrGroupRelationMapper;
import com.mall.product.biz.service.IProductAttrGroupRelationService;
import org.springframework.stereotype.Service;


/**
 * 商品属性和商品属性分组关联表
 *
 * @author naidelii
 */
@Service
public class ProductAttrGroupRelationServiceImpl extends ServiceImpl<ProductAttrGroupRelationMapper, ProductAttrGroupRelation> implements IProductAttrGroupRelationService {

    @Override
    public void saveOrUpdateAttrInfo(String attrId, String attrGroupId) {
        // 查询该属性是否存在
        LambdaQueryWrapper<ProductAttrGroupRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAttrGroupRelation::getAttrId, attrId);
        ProductAttrGroupRelation relation = baseMapper.selectOne(queryWrapper);
        if (relation == null) {
            ProductAttrGroupRelation data = new ProductAttrGroupRelation();
            data.setAttrGroupId(attrGroupId);
            data.setAttrId(attrId);
            baseMapper.insert(data);
        } else {
            relation.setAttrGroupId(attrGroupId);
            baseMapper.updateById(relation);
        }
    }
}