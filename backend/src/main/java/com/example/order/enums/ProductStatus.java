package com.example.order.enums;

import lombok.Getter;

/**
 * 商品状态枚举
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Getter
public enum ProductStatus {
    OFF_SHELF(0, "下架"),
    ON_SHELF(1, "上架");

    private final int code;
    private final String desc;

    ProductStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据代码获取枚举
     *
     * @param code 代码
     * @return 商品状态
     */
    public static ProductStatus valueOfCode(int code) {
        for (ProductStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的商品状态代码：" + code);
    }
}
