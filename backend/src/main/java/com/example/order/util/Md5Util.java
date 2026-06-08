package com.example.order.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
public class Md5Util {

    /**
     * 计算 MD5
     *
     * @param str 原始字符串
     * @return MD5 值（32 位小写）
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 算法异常", e);
        }
    }

    /**
     * 计算 MD5（16 位小写）
     *
     * @param str 原始字符串
     * @return MD5 值（16 位小写）
     */
    public static String md516(String str) {
        return md5(str).substring(8, 24);
    }
}
