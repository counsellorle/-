package com.example.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 企业级订单管理系统启动类
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan("com.example.order.repository")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
