package com.mall.product.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.product.biz.domain.dto.ProductBrandQuery;
import com.mall.product.biz.domain.entity.ProductBrand;
import com.mall.product.biz.domain.vo.ProductBrandListVO;

import java.util.List;

/**
 * 品牌表
 *
 * @author naidelii
 */
public interface IProductBrandService extends IService<ProductBrand> {


    IPage<ProductBrandListVO> selectListPage(Integer pageNo, Integer pageSize, ProductBrandQuery query);

    void deleteByIds(List<String> removeIds);
}

