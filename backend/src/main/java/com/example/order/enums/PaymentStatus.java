package com.example.order.enums;

/**
 * 支付状态枚举
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
public enum PaymentStatus {
    /**
     * 未支付
     */
    UNPAID(0, "未支付"),
    /**
     * 已支付
     */
    PAID(1, "已支付"),
    /**
     * 已退款
     */
    REFUNDED(2, "已退款");

    private final Integer code;
    private final String desc;

    PaymentStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据编码获取枚举
     *
     * @param code 状态编码
     * @return 支付状态
     */
    public static PaymentStatus valueOfCode(Integer code) {
        for (PaymentStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的支付状态：" + code);
    }
}
