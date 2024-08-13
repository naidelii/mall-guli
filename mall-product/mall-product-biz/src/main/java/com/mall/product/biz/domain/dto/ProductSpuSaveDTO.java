package com.mall.product.biz.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * spu信息
 *
 * @author naidelii
 */
@Data
public class ProductSpuSaveDTO {

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 所属分类id
     */
    private String categoryId;

    /**
     * 所属品牌id
     */
    private String brandId;

    /**
     * 商品简短描述
     */
    private String shortDescription;

    /**
     * 上架状态（0：下架，1：上架）
     */
    private Integer publishStatus;

    /**
     * 商品详细介绍（富文本）
     */
    private String detailedDescription;

    /**
     * SPU图片集合
     */
    private List<ProductSpuImagesDTO> imageList;


    @Data
    private static class ProductSpuImagesDTO {
        /**
         * 图片名称
         */
        private String imageName;

        /**
         * 图片地址
         */
        private String imageUrl;
    }
}
