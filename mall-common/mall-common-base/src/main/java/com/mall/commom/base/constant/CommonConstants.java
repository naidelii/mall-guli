package com.mall.commom.base.constant;

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
     * 顶级父级编码
     */
    String PARENT_CODE = "0";

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
}
