package com.example.order.service;

/**
 * 短信服务接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
public interface SmsService {

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param code  验证码
     */
    void sendVerifyCode(String phone, String code);

    /**
     * 发送通知短信
     *
     * @param phone   手机号
     * @param content 短信内容
     */
    void sendNotification(String phone, String content);
}
