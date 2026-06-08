package com.example.order.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Getter
public enum OrderStatus {
    PENDING_PAYMENT(1, "待支付"),
    SUBMITTED(2, "已提交"),
    CONFIRMED(3, "商家确认"),
    PRODUCING(4, "制作中"),
    SHIPPED(5, "已发货"),
    COMPLETED(6, "已完成"),
    CANCELLED(7, "已取消");

    private final int code;
    private final String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据代码获取枚举
     *
     * @param code 代码
     * @return 订单状态
     */
    public static OrderStatus valueOfCode(int code) {
        for (OrderStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的订单状态代码：" + code);
    }
}
