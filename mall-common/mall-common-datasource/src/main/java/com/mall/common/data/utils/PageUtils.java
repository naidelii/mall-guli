package com.mall.common.data.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author naidelii
 */
public final class PageUtils {
    private PageUtils() {

    }

    public static <T> IPage<T> buildPage(List<T> list, IPage<?> pageList) {
        IPage<T> page = new Page<>(pageList.getCurrent(), pageList.getSize(), pageList.getTotal());
        page.setRecords(list);
        return page;
    }

}
