package com.mall.admin.biz.service.impl;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.crypto.SecureUtil;
import com.mall.admin.biz.service.IVerifyCodeService;
import com.mall.common.base.constant.CommonConstants;
import com.mall.common.base.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 图形验证码
 *
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Component("Captcha")
public class CaptchaServiceImpl implements IVerifyCodeService {

    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * 后台生成图形验证码
     *
     * @param key：时间戳
     * @return 返回结果
     */
    @Override
    public Map<String, Object> generateCode(String key) {
        RandomGenerator randomGenerator = new RandomGenerator(CommonConstants.RANDOM_STR, 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 20);
        // 调用父类的 setGenerator() 方法，设置验证码的类型
        lineCaptcha.setGenerator(randomGenerator);
        // 获取随机数验证码
        String code = lineCaptcha.getCode();
        log.info("获取图形验证码，Redis checkCode = {}，key = {}", code, key);
        // 存放到redis中的key
        String realKey = SecureUtil.md5(code + key);
        // 将验证码存放到redis中，默认有效期1分钟
        redisTemplate.opsForValue().set(CommonConstants.CAPTCHA_PREFIX + realKey, code, 1, TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>(2);
        map.put("realKey", realKey);
        map.put("img", lineCaptcha.getImageBase64());
        return map;
    }

    @Override
    public void verifyCode(String realKey, String captcha) {
        String codeKey = CommonConstants.CAPTCHA_PREFIX + realKey;
        // 从redis中获取存储的验证码
        Object redisCode = redisTemplate.opsForValue().get(codeKey);
        if (redisCode == null) {
            throw new GlobalException("验证码无效！");
        }
        // 校验验证码是否正确
        if (!StringUtils.equalsIgnoreCase(captcha, redisCode.toString())) {
            throw new GlobalException("验证码错误！");
        }
        // 验证码没问题，则删除
        redisTemplate.delete(codeKey);
    }

}
