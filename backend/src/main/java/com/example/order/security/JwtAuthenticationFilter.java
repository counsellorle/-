package com.example.order.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT 认证过滤器
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);

            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(token);
                
                // 设置认证信息
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("JWT 认证失败", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 从请求中获取 JWT Token
     *
     * @param request 请求
     * @return Token
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
