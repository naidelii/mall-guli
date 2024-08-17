package com.mall.common.redis.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.mall.common.base.constant.DataConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author naidelii
 * Redis工具类
 */
@Slf4j
public final class RedisUtils {

    private RedisUtils() {

    }

    private static volatile RedisTemplate<String, Object> REDIS_TEMPLATE;


    /**
     * 获取 RedisTemplate 实例
     *
     * @return RedisTemplate<String, Object> 实例
     */
    private static RedisTemplate<String, Object> getInstance() {
        if (REDIS_TEMPLATE == null) {
            synchronized (RedisUtils.class) {
                if (REDIS_TEMPLATE == null) {
                    REDIS_TEMPLATE = SpringUtil.getBean(DataConstants.REDIS_TEMPLATE_NAME);
                }
            }
        }
        return REDIS_TEMPLATE;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key      键
     * @param time     时间(秒)
     * @param timeUnit 时间单位
     * @return true 成功, false 失败
     */
    public static boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                getInstance().expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to set expiration for key {}: {}", key, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true 成功, false 失败
     */
    public static boolean set(String key, Object value) {
        try {
            getInstance().opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("Failed to set value for key {}: {}", key, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key      键
     * @param value    值
     * @param time     时间 time要大于0 如果time小于等于0 将设置无限期
     * @param timeUnit 时间单位
     * @return true成功 false 失败
     */
    public static boolean set(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        try {
            if (time > 0) {
                getInstance().opsForValue().set(key, value, time, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to set value for key {}: {}", key, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        try {
            return StringUtils.isBlank(key) ? null : getInstance().opsForValue().get(key);
        } catch (Exception e) {
            log.error("Failed to get value for key {}: {}", key, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 检查 Redis 中是否存在指定的键
     *
     * @param key 键
     * @return true 存在, false 不存在
     */
    public static Boolean hasKey(String key) {
        try {
            return getInstance().hasKey(key);
        } catch (Exception e) {
            log.error("Failed to check existence of key {}: {}", key, e.getMessage(), e);
            return Boolean.FALSE;
        }
    }


    /**
     * 根据 key 删除缓存
     *
     * @param key 键
     * @return true 成功, false 失败
     */
    public static Boolean delete(String key) {
        try {
            return getInstance().delete(key);
        } catch (Exception e) {
            log.error("Failed to delete key {}: {}", key, e.getMessage(), e);
            return Boolean.FALSE;
        }
    }
}
