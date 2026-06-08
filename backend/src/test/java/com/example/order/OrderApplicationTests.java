package com.example.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 集成测试基类
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@ActiveProfiles("test")
public class OrderApplicationTests {

    @Test
    void contextLoads() {
    }
}
