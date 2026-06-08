package com.example.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 参数校验配置
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Configuration
public class ValidationConfig {

    /**
     * 方法校验处理器
     *
     * @return MethodValidationPostProcessor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
