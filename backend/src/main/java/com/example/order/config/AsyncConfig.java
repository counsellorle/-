package com.example.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 异步与定时任务配置
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

    // 异步任务配置
    // 定时任务配置
}
