package com.mall.common.base.utils;

import cn.hutool.crypto.SecureUtil;
import com.mall.common.base.constant.SecurityConstants;

import java.util.Objects;
import java.util.Random;

/**
 * @author naidelii
 * 密码工具类
 */
public final class PasswordUtils {

    /**
     * 私有化构造函数
     */
    private PasswordUtils() {
    }

    /**
     * 随机数
     *
     * @param place 定义随机数的位数
     */
    public static String randomGen(int place) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < place; i++) {
            sb.append(SecurityConstants.RANDOM_STR.charAt(random.nextInt(SecurityConstants.RANDOM_STR.length())));
        }
        return sb.toString();
    }

    /**
     * 密码加密
     *
     * @param password 要加密的密码
     * @param salt     密码盐
     * @return 加密后的密码
     */
    public static String encode(String password, String salt) {
        // 密码加盐
        String saltedPassword = confuse(password, salt);
        // 将加入盐值后的密码传入父类的加密方法进行加密
        return SecureUtil.sha256(saltedPassword);
    }

    /**
     * 匹配密码是否正确
     *
     * @param password        未加密的密码（明文）
     * @param salt            密码盐
     * @param encodedPassword 数据库中的密码（密文）
     * @return 匹配结果
     */
    public static boolean matches(String password, String salt, String encodedPassword) {
        // 密码加盐
        String saltedPassword = confuse(password, salt);
        // 将加盐后的进行加密
        String encodePasswordNew = SecureUtil.sha256(saltedPassword);
        // 匹配密码是否相同
        return Objects.equals(encodePasswordNew, encodedPassword);
    }

    /**
     * 混淆密码
     *
     * @param password 未加密的密码（明文）
     * @param salt     密码盐
     * @return 混淆后的密码
     */
    private static String confuse(String password, String salt) {
        // 明文+密码盐+系统混淆参数
        return password + salt + SecurityConstants.PASSWORD_CONFUSE_KEY;
    }

}
