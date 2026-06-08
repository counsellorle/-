package com.example.order.usecase;

import com.example.order.entity.SmsLog;
import com.example.order.repository.SmsLogRepository;
import com.example.order.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 短信用例
 * 负责发送短信验证码、订单通知等业务逻辑
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class SmsUseCase {

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsLogRepository smsLogRepository;

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    public String sendVerifyCode(String phone) {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        
        smsService.sendVerifyCode(phone, code);
        
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone(phone);
        smsLog.setTemplateCode("VERIFY_CODE");
        smsLog.setContent("您的验证码是：" + code);
        smsLog.setStatus(1);
        smsLog.setSentAt(LocalDateTime.now());
        smsLogRepository.save(smsLog);

        return code;
    }

    /**
     * 发送订单支付成功通知
     *
     * @param phone    手机号
     * @param orderNo  订单号
     * @param amount   金额
     */
    public void sendOrderPaidNotification(String phone, String orderNo, String amount) {
        String content = String.format("您的订单 %s 已支付成功，金额 %s 元。", orderNo, amount);
        
        smsService.sendNotification(phone, content);
        
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone(phone);
        smsLog.setTemplateCode("ORDER_PAID");
        smsLog.setContent(content);
        smsLog.setStatus(1);
        smsLog.setSentAt(LocalDateTime.now());
        smsLogRepository.save(smsLog);
    }

    /**
     * 发送订单确认通知
     *
     * @param phone              手机号
     * @param orderNo            订单号
     * @param expectedFinishTime 预计完成时间
     */
    public void sendOrderConfirmedNotification(String phone, String orderNo, String expectedFinishTime) {
        String content = String.format("您的订单 %s 已确认，预计完成时间：%s。", orderNo, expectedFinishTime);
        
        smsService.sendNotification(phone, content);
        
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone(phone);
        smsLog.setTemplateCode("ORDER_CONFIRMED");
        smsLog.setContent(content);
        smsLog.setStatus(1);
        smsLog.setSentAt(LocalDateTime.now());
        smsLogRepository.save(smsLog);
    }

    /**
     * 发送订单发货通知
     *
     * @param phone       手机号
     * @param orderNo     订单号
     * @param logisticsNo 物流单号
     */
    public void sendOrderShippedNotification(String phone, String orderNo, String logisticsNo) {
        String content = String.format("您的订单 %s 已发货，物流单号：%s。", orderNo, logisticsNo);
        
        smsService.sendNotification(phone, content);
        
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone(phone);
        smsLog.setTemplateCode("ORDER_SHIPPED");
        smsLog.setContent(content);
        smsLog.setStatus(1);
        smsLog.setSentAt(LocalDateTime.now());
        smsLogRepository.save(smsLog);
    }

    /**
     * 发送订单完成通知
     *
     * @param phone   手机号
     * @param orderNo 订单号
     */
    public void sendOrderCompletedNotification(String phone, String orderNo) {
        String content = String.format("您的订单 %s 已完成，感谢您的购买！", orderNo);
        
        smsService.sendNotification(phone, content);
        
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone(phone);
        smsLog.setTemplateCode("ORDER_COMPLETED");
        smsLog.setContent(content);
        smsLog.setStatus(1);
        smsLog.setSentAt(LocalDateTime.now());
        smsLogRepository.save(smsLog);
    }

    /**
     * 发送订单取消通知
     *
     * @param phone        手机号
     * @param orderNo      订单号
     * @param cancelReason 取消原因
     * @param refundAmount 退款金额
     */
    public void sendOrderCancelledNotification(String phone, String orderNo, String cancelReason, String refundAmount) {
        String content = String.format("您的订单 %s 已取消，原因：%s，退款金额 %s 元。", orderNo, cancelReason, refundAmount);
        
        smsService.sendNotification(phone, content);
        
        SmsLog smsLog = new SmsLog();
        smsLog.setPhone(phone);
        smsLog.setTemplateCode("ORDER_CANCELLED");
        smsLog.setContent(content);
        smsLog.setStatus(1);
        smsLog.setSentAt(LocalDateTime.now());
        smsLogRepository.save(smsLog);
    }
}
