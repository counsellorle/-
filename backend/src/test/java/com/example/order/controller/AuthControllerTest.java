package com.example.order.controller;

import com.example.order.dto.LoginRequest;
import com.example.order.dto.RegisterRequest;
import com.example.order.repository.UserRepository;
import com.example.order.usecase.AuthUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 认证接口测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("认证接口测试")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthUseCase authUseCase;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("测试注册成功")
    void testRegisterSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13800138000");
        request.setPassword("password123");
        request.setRole(1);

        when(userRepository.findByPhone(any())).thenReturn(java.util.Optional.empty());
        when(authUseCase.register(any(), any(), any())).thenReturn(1L);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    @DisplayName("测试登录成功")
    void testLoginSuccess() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setPhone("13800138000");
        request.setPassword("password123");

        when(authUseCase.loginWithPassword(any(), any())).thenReturn("mock_token");

        mockMvc.perform(post("/auth/login/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").value("mock_token"));
    }

    @Test
    @DisplayName("测试参数校验失败")
    void testValidationFailed() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("");
        request.setPassword("password123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
