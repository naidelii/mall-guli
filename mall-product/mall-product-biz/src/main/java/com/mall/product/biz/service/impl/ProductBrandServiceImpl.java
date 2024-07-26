package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductBrandQuery;
import com.mall.product.biz.domain.entity.ProductBrand;
import com.mall.product.biz.domain.vo.ProductBrandListVo;
import com.mall.product.biz.mapper.ProductBrandMapper;
import com.mall.product.biz.service.IProductBrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 品牌表
 *
 * @author naidelii
 */
@Service
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand> implements IProductBrandService {

    @Override
    public IPage<ProductBrandListVo> selectListPage(Integer pageNo, Integer pageSize, ProductBrandQuery query) {
        Page<ProductBrand> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductBrand> queryWrapper = new LambdaQueryWrapper<>();
        IPage<ProductBrand> pageList = baseMapper.selectPage(page, queryWrapper);
        List<ProductBrandListVo> userListVos = pageList.getRecords()
                .stream()
                .map(ProductBrandListVo::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

    @Override
    public void deleteByIds(List<String> removeIds) {
        baseMapper.deleteBatchIds(removeIds);
    }
}