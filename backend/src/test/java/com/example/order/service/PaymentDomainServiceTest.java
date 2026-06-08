package com.example.order.service;

import com.example.order.enums.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 支付领域服务测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@DisplayName("支付领域服务测试")
class PaymentDomainServiceTest {

    @Autowired
    private PaymentDomainService paymentDomainService;

    @Test
    @DisplayName("测试已支付状态可以退款")
    void testCanRefundWhenPaid() {
        boolean canRefund = paymentDomainService.canRefund(PaymentStatus.PAID);
        assertTrue(canRefund);
    }

    @Test
    @DisplayName("测试未支付状态不可以退款")
    void testCannotRefundWhenUnpaid() {
        boolean canRefund = paymentDomainService.canRefund(PaymentStatus.UNPAID);
        assertFalse(canRefund);
    }

    @Test
    @DisplayName("测试已退款状态不可以重复退款")
    void testCannotRefundWhenRefunded() {
        boolean canRefund = paymentDomainService.canRefund(PaymentStatus.REFUNDED);
        assertFalse(canRefund);
    }
}
