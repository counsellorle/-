package com.example.order.config;

import com.example.order.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/orders/**", "/admin/**")
                .excludePathPatterns("/auth/**", "/pay/**", "/products/**");
    }
}
