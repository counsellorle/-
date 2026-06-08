package com.example.order.service.impl;

import com.example.order.service.SmsService;
import org.springframework.stereotype.Service;

/**
 * 短信服务实现类
 * 模拟短信发送，实际应对接阿里云/腾讯云短信服务
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendVerifyCode(String phone, String code) {
        // TODO: 实际应调用阿里云/腾讯云短信 API
        System.out.println("发送验证码短信到：" + phone + "，验证码：" + code);
    }

    @Override
    public void sendNotification(String phone, String content) {
        // TODO: 实际应调用阿里云/腾讯云短信 API
        System.out.println("发送通知短信到：" + phone + "，内容：" + content);
    }
}
