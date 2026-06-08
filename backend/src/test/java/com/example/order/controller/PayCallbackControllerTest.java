package com.example.order.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 支付回调接口测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("支付回调接口测试")
class PayCallbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试支付宝回调成功")
    void testAlipayCallbackSuccess() throws Exception {
        mockMvc.perform(post("/pay/alipay/callback")
                        .param("out_trade_no", "ORD202606080001")
                        .param("trade_no", "202606080001")
                        .param("trade_status", "TRADE_SUCCESS"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("测试微信回调成功")
    void testWechatCallbackSuccess() throws Exception {
        String json = "{\"out_trade_no\":\"ORD202606080001\",\"return_code\":\"SUCCESS\"}";
        
        mockMvc.perform(post("/pay/wechat/callback")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }
}
