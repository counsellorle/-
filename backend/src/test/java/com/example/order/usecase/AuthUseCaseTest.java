package com.example.order.usecase;

import cn.hutool.crypto.digest.BCrypt;
import com.example.order.common.BusinessException;
import com.example.order.entity.User;
import com.example.order.enums.UserRole;
import com.example.order.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 认证用例测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@DisplayName("认证用例测试")
class AuthUseCaseTest {

    @Autowired
    private AuthUseCase authUseCase;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("测试客户注册成功")
    void testRegisterCustomerSuccess() {
        String phone = "13800138000";
        String password = "password123";

        when(userRepository.findByPhone(phone)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return 1;
        });

        Long userId = authUseCase.register(phone, password, UserRole.CUSTOMER);

        assertNotNull(userId);
        assertEquals(1L, userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("测试手机号重复注册抛出异常")
    void testRegisterDuplicatePhone() {
        String phone = "13800138000";
        String password = "password123";

        User existingUser = new User();
        existingUser.setPhone(phone);

        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(existingUser));

        assertThrows(BusinessException.class, () -> {
            authUseCase.register(phone, password, UserRole.CUSTOMER);
        });
    }

    @Test
    @DisplayName("测试密码登录成功")
    void testLoginWithPasswordSuccess() {
        String phone = "13800138000";
        String password = "password123";
        String hashedPassword = BCrypt.hashpw(password);

        User user = new User();
        user.setId(1L);
        user.setPhone(phone);
        user.setPassword(hashedPassword);
        user.setRole(1);

        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

        String token = authUseCase.loginWithPassword(phone, password);

        assertNotNull(token);
    }

    @Test
    @DisplayName("测试密码错误抛出异常")
    void testLoginWithWrongPassword() {
        String phone = "13800138000";
        String password = "wrong_password";

        User user = new User();
        user.setPhone(phone);
        user.setPassword("$2a$10$encrypted_password");

        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> {
            authUseCase.loginWithPassword(phone, password);
        });
    }

    @Test
    @DisplayName("测试用户不存在抛出异常")
    void testLoginWithNonExistentUser() {
        String phone = "13800138000";
        String password = "password123";

        when(userRepository.findByPhone(phone)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            authUseCase.loginWithPassword(phone, password);
        });
    }
}
