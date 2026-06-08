package com.example.order.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;

/**
 * 订单号生成服务
 * 使用雪花算法生成全局唯一订单号
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class OrderNumberGenerator {

    private final Snowflake snowflake;

    public OrderNumberGenerator() {
        snowflake = IdUtil.getSnowflake(1, 1);
    }

    /**
     * 生成订单号
     *
     * @return 订单号
     */
    public String generate() {
        return "ORD" + snowflake.nextId();
    }
}
