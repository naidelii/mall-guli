package com.mall.admin.biz.service;

import java.util.Map;

/**
 * 验证码服务
 *
 * @author naidelii
 */
public interface IVerifyCodeService {

    /**
     * 生成验证码
     *
     * @param key key
     * @return 返回结果
     */
    Map<String, Object> generateCode(String key);

    /**
     * 校验验证码
     *
     * @param realKey key
     * @param captcha 验证码
     */
    void verifyCode(String realKey, String captcha);
}
