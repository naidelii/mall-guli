package com.mall.product.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.data.utils.PageUtils;
import com.mall.product.biz.domain.dto.ProductBrandQuery;
import com.mall.product.biz.domain.entity.ProductBrand;
import com.mall.product.biz.domain.entity.ProductBrandCategoryRelation;
import com.mall.product.biz.domain.vo.ProductBrandListVO;
import com.mall.product.biz.mapper.ProductBrandCategoryRelationMapper;
import com.mall.product.biz.mapper.ProductBrandMapper;
import com.mall.product.biz.service.IProductBrandService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 品牌表
 *
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand> implements IProductBrandService {

    private final ProductBrandCategoryRelationMapper brandCategoryRelationMapper;

    @Override
    public IPage<ProductBrandListVO> selectListPage(Integer pageNo, Integer pageSize, ProductBrandQuery query) {
        Page<ProductBrand> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<ProductBrand> queryWrapper = new LambdaQueryWrapper<>();
        IPage<ProductBrand> pageList = baseMapper.selectPage(page, queryWrapper);
        List<ProductBrandListVO> userListVos = pageList.getRecords()
                .stream()
                .map(ProductBrandListVO::new)
                .collect(Collectors.toList());
        return PageUtils.buildPage(userListVos, pageList);
    }

    @Override
    public void deleteByIds(List<String> removeIds) {
        baseMapper.deleteBatchIds(removeIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateData(ProductBrand data) {
        // 保证冗余字段的数据一致性
        String name = data.getBrandName();
        if (StringUtils.isNotBlank(name)) {
            // 同步更新其他关联表的数据
            brandCategoryRelationMapper.updateBrand(data.getId(), name);
        }
        baseMapper.updateById(data);
    }

    @Override
    public List<ProductBrand> listByCategoryId(String categoryId) {
        // 根据分类id查询出对应的品牌信息
        LambdaQueryWrapper<ProductBrandCategoryRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductBrandCategoryRelation::getCategoryId, categoryId);
        List<ProductBrandCategoryRelation> categoryBrands = brandCategoryRelationMapper.selectList(queryWrapper);
        if (categoryBrands.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> brandIds = categoryBrands.stream()
                .map(ProductBrandCategoryRelation::getBrandId)
                .collect(Collectors.toSet());
        return baseMapper.selectBatchIds(brandIds);
    }
}