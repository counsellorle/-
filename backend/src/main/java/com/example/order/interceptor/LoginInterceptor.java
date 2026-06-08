package com.example.order.interceptor;

import com.example.order.common.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 * 验证用户登录状态，解析 Token 获取用户信息
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求，验证登录
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return 是否继续
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // TODO: 实现 JWT Token 验证
        // 从 Header 中获取 Token
        String token = request.getHeader("Authorization");
        
        if (token == null || token.isEmpty()) {
            throw new BusinessException("请先登录");
        }
        
        // TODO: 解析 Token，获取用户信息并存入 ThreadLocal
        
        return true;
    }

    /**
     * 清理 ThreadLocal
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // TODO: 清理用户信息 ThreadLocal
    }
}
