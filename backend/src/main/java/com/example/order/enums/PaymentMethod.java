package com.example.order.enums;

/**
 * 支付方式枚举
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
public enum PaymentMethod {
    /**
     * 微信
     */
    WECHAT(1, "微信"),
    /**
     * 支付宝
     */
    ALIPAY(2, "支付宝"),
    /**
     * 银行转账
     */
    BANK_TRANSFER(3, "银行转账");

    private final Integer code;
    private final String desc;

    PaymentMethod(Integer code, String desc) {
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
     * @return 支付方式
     */
    public static PaymentMethod valueOfCode(Integer code) {
        for (PaymentMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        throw new IllegalArgumentException("未知的支付方式：" + code);
    }
}
