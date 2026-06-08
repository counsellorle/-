package com.example.order.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户实体测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@DisplayName("用户实体测试")
class UserTest {

    @Test
    @DisplayName("测试创建用户")
    void testCreateUser() {
        User user = new User();
        user.setPhone("13800138000");
        user.setPassword("encrypted_password");
        user.setRole(1);
        user.setStatus(1);

        assertEquals("13800138000", user.getPhone());
        assertEquals("encrypted_password", user.getPassword());
        assertEquals(1, user.getRole());
        assertEquals(1, user.getStatus());
    }
}
