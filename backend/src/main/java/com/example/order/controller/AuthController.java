package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.LoginRequest;
import com.example.order.dto.RegisterRequest;
import com.example.order.dto.SmsLoginRequest;
import com.example.order.usecase.AuthUseCase;
import com.example.order.vo.LoginVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证 Controller
 * 负责用户注册、登录、登出等接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthUseCase authUseCase;

    /**
     * 用户注册接口
     *
     * @param request 注册请求体（包含手机号、密码、角色）
     * @return 统一返回结构，data 为新创建的用户 ID
     */
    @PostMapping("/register")
    public Result<Long> register(@Valid @RequestBody RegisterRequest request) {
        Long userId = authUseCase.register(request.getPhone(), request.getPassword(), 
                request.getRole() != null ? com.example.order.enums.UserRole.valueOfCode(request.getRole()) : com.example.order.enums.UserRole.CUSTOMER);
        return Result.success(userId);
    }

    /**
     * 手机号 + 密码登录接口
     *
     * @param request 登录请求体（包含手机号、密码）
     * @return 统一返回结构，data 为 Token 和用户信息
     */
    @PostMapping("/login/password")
    public Result<LoginVO> loginWithPassword(@Valid @RequestBody LoginRequest request) {
        String token = authUseCase.loginWithPassword(request.getPhone(), request.getPassword());
        var user = authUseCase.getUserById(1L); // TODO: 实际应从数据库查询
        LoginVO loginVO = LoginVO.from(user, token);
        return Result.success(loginVO);
    }

    /**
     * 手机号 + 验证码登录接口
     *
     * @param request 短信登录请求体（包含手机号、验证码）
     * @return 统一返回结构，data 为 Token 和用户信息
     */
    @PostMapping("/login/sms")
    public Result<LoginVO> loginWithSms(@Valid @RequestBody SmsLoginRequest request) {
        String token = authUseCase.loginWithSmsCode(request.getPhone(), request.getCode());
        var user = authUseCase.getUserById(1L); // TODO: 实际应从数据库查询
        LoginVO loginVO = LoginVO.from(user, token);
        return Result.success(loginVO);
    }

    /**
     * 发送短信验证码接口
     *
     * @param phone 手机号
     * @return 统一返回结构
     */
    @PostMapping("/login/sms/send")
    public Result<Void> sendSmsCode(@RequestParam String phone) {
        // TODO: 实现短信验证码发送
        return Result.success();
    }

    /**
     * 登出接口
     *
     * @return 统一返回结构
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        // TODO: JWT 黑名单处理
        return Result.success();
    }
}
