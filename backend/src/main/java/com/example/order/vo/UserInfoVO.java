package com.example.order.vo;

import lombok.Data;

/**
 * 用户信息 VO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class UserInfoVO {

    /**
     * 用户 ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色：1-客户，2-商家
     */
    private Integer role;
}
