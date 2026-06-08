package com.example.order.vo;

import com.example.order.entity.User;
import lombok.Builder;
import lombok.Data;

/**
 * 登录响应 VO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@Builder
public class LoginVO {

    /**
     * Token
     */
    private String token;

    /**
     * 用户信息
     */
    private User user;

    /**
     * 从用户实体和 Token 构建 LoginVO
     *
     * @param user  用户实体
     * @param token Token
     * @return LoginVO
     */
    public static LoginVO from(User user, String token) {
        return LoginVO.builder()
                .token(token)
                .user(user)
                .build();
    }
}
