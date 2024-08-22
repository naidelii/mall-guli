package com.mall.common.base.constant;

/**
 * @author naidelii
 */
public interface CacheConstants {
    /**
     * 用户信息
     */
    String SYS_USERS_CACHE_PREFIX = "sys:cache:user";

    /**
     * token
     */
    String TOKEN_CACHE_PREFIX = "Authorization:login:token:";

    /**
     * 用户菜单缓存
     */
    String USER_MENU_LIST_CACHE = "sys:cache:user:menu:list";

    /**
     * 商品三级分类数据缓存
     */
    String PRODUCT_CATEGORY_LIST_CACHE = "sys:cache:product:category:list";

    /**
     * 没有查询条件，缓存了所有数据
     */
    String ALL_DATA_CACHE_KEY = "'listAllData'";


}
