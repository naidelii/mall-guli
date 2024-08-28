package com.mall.common.base.constant;

/**
 * @author naidelii
 * 基本常量
 */
public interface CommonConstants {

    /**
     * 超级管理员ID
     */
    String SUPER_ADMIN = "1";

    /**
     * 超级管理员角色
     */
    String SUPER_ADMIN_ROLE = "admin";

    /**
     * 管理员拥有所有权限
     */
    String ALL_PERMS = "*:*:*";

    /**
     * 顶级父级编码
     */
    String PARENT_CODE = "0";

    /**
     * 默认的分类等级
     */
    Integer DEFAULT_CATEGORY_LEVEL = 1;

    /**
     * 随机数字和英文字母
     */
    String RANDOM_STR = "0123456789qwertyuioplkjhgfdsazxcvbnmQAZWSXEDCRFVTGBYHNUJMIKLOP";


    /**
     * 请求参数校验失败返回错误信息
     */
    String PARAM_VERIFY_ERROR_STR = "请求参数不符合要求";


    /**
     * 验证码前缀
     */
    String CAPTCHA_PREFIX = "captcha:";

    /**
     * Redis所有Keys
     */
    String REDIS_CONFIG_PREFIX = "sys:config:";

    /**
     * 用户已冻结
     */
    int FREEZE = 0;

    /**
     * 创建人
     */
    String CREATE_BY = "create_by";

    /**
     * 创建时间
     */
    String CREATE_TIME = "create_time";

    /**
     * 更新人
     */
    String UPDATE_BY = "update_by";

    /**
     * 更新时间
     */
    String UPDATE_TIME = "update_time";

    /**
     * 页码参数名称
     */
    String PAGE_NO_PARAM = "pageNo";

    /**
     * 页码参数默认值
     */
    String PAGE_NO_DEFAULT = "1";

    /**
     * 每页大小参数名称
     */
    String PAGE_SIZE_PARAM = "pageSize";

    /**
     * 每页大小参数默认值
     */
    String PAGE_SIZE_DEFAULT = "10";

}
