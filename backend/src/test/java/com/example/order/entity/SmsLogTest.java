package com.example.order.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 短信日志实体测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@DisplayName("短信日志实体测试")
class SmsLogTest {

    @Test
    @DisplayName("测试创建短信日志")
    void testCreateSmsLog() {
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone("13800138000");
        smsLog.setTemplateCode("SMS_123456789");
        smsLog.setContent("您的验证码是 123456");
        smsLog.setStatus(0);

        assertEquals("13800138000", smsLog.getPhone());
        assertEquals("SMS_123456789", smsLog.getTemplateCode());
        assertEquals("您的验证码是 123456", smsLog.getContent());
        assertEquals(0, smsLog.getStatus());
    }

    @Test
    @DisplayName("测试短信发送成功")
    void testSmsSentSuccess() {
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone("13800138000");
        smsLog.setTemplateCode("SMS_123456789");
        smsLog.setContent("订单已发货");
        smsLog.setStatus(1); // 成功

        assertEquals(1, smsLog.getStatus());
    }
}
