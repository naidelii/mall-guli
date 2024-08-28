package com.mall.product.biz.event;

import com.mall.product.biz.domain.entity.ProductCategory;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author naidelii
 * 商品分类数据更新
 */
@Getter
public class ProductCategoryUpdateEvent extends ApplicationEvent {

    private final ProductCategory productCategory;

    public ProductCategoryUpdateEvent(Object source, ProductCategory productCategory) {
        super(source);
        this.productCategory = productCategory;
    }
}
