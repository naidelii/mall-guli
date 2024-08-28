package com.mall.product.biz.event;

import com.mall.product.biz.domain.entity.ProductCategory;
import com.mall.product.biz.mapper.ProductBrandCategoryRelationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProductCategoryUpdateListener implements ApplicationListener<ProductCategoryUpdateEvent> {
    private final ProductBrandCategoryRelationMapper brandCategoryRelationMapper;

    @Override
    public void onApplicationEvent(ProductCategoryUpdateEvent event) {
        ProductCategory productCategory = event.getProductCategory();
        log.info("===========商品分类数据变更Event：{}", productCategory);
        brandCategoryRelationMapper.updateCategory(productCategory.getId(), productCategory.getCategoryName());
    }
}
