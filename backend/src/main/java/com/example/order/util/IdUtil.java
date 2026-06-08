package com.example.order.util;

import java.util.UUID;

/**
 * 唯一 ID 生成工具类
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
public class IdUtil {

    /**
     * 生成 UUID（不带横杠）
     *
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成 UUID（带横杠）
     *
     * @return UUID
     */
    public static String uuidWithDash() {
        return UUID.randomUUID().toString();
    }
}
