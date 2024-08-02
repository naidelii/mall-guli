package com.mall.product.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.biz.domain.entity.ProductAttrGroupRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 商品属性和商品属性分组关联表
 *
 * @author naidelii
 */
@Mapper
public interface ProductAttrGroupRelationMapper extends BaseMapper<ProductAttrGroupRelation> {

    List<ProductAttrGroupRelation> selectByAttrIds(@Param("attrIds") Set<String> attrIds);

    void updateAttrInfo(@Param("attrId") String attrId, @Param("attrGroupId") String attrGroupId);

    ProductAttrGroupRelation selectByAttrId(String attrId);
}
