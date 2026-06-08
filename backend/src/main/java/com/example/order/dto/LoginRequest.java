package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 登录请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class LoginRequest {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
