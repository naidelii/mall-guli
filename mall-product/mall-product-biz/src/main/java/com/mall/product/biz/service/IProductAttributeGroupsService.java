package com.mall.product.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.dto.ProductAttrGroupQuery;
import com.mall.product.biz.domain.entity.ProductAttributeGroups;
import com.mall.product.biz.domain.vo.ProductAttrGroupListVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupVO;
import com.mall.product.biz.domain.vo.ProductAttrGroupWithAttrsVO;

import java.util.List;

/**
 * 商品属性分组
 *
 * @author naidelii
 */
public interface IProductAttributeGroupsService extends IService<ProductAttributeGroups> {

    /**
     * 分页查询商品属性分组信息
     *
     * @param pageNo   当前页码
     * @param pageSize 每页大小
     * @param query    查询参数
     * @return IPage<ProductAttrGroupListVO>
     */
    IPage<ProductAttrGroupListVO> listAttrGroupWithPage(Integer pageNo, Integer pageSize, ProductAttrGroupQuery query);

    /**
     * 获取详情信息
     *
     * @param id 主键
     * @return ProductAttrGroupVo
     */
    ProductAttrGroupVO getDetailsById(String id);

    List<ProductAttrGroupListVO> listAttrGroupByCategoryId(String categoryId);

    List<ProductAttrGroupWithAttrsVO> listAttrGroupWithAttrsByCategoryId(String categoryId);

}

