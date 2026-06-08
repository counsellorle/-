package com.example.order.enums;

import lombok.Getter;

/**
 * 用户角色枚举
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Getter
public enum UserRole {
    CUSTOMER(1, "客户"),
    MERCHANT(2, "商家");

    private final int code;
    private final String desc;

    UserRole(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据代码获取枚举
     *
     * @param code 代码
     * @return 用户角色
     */
    public static UserRole valueOfCode(int code) {
        for (UserRole role : values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("未知的用户角色代码：" + code);
    }
}
