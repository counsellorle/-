package com.example.order.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 订单实体测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@DisplayName("订单实体测试")
class OrderTest {

    @Test
    @DisplayName("测试创建订单")
    void testCreateOrder() {
        Order order = new Order();
        order.setOrderNo("ORD202606080001");
        order.setUserId(1L);
        order.setTotalAmount(new BigDecimal("199.98"));
        order.setReceiverAddress("北京市朝阳区 xxx 路 xxx 号");
        order.setRemark("请尽快发货");
        order.setOrderStatus(0);
        order.setPaymentStatus(0);
        order.setPaymentMethod(2);

        assertEquals("ORD202606080001", order.getOrderNo());
        assertEquals(1L, order.getUserId());
        assertEquals(new BigDecimal("199.98"), order.getTotalAmount());
        assertEquals("北京市朝阳区 xxx 路 xxx 号", order.getReceiverAddress());
        assertEquals(0, order.getOrderStatus());
        assertEquals(0, order.getPaymentStatus());
        assertEquals(2, order.getPaymentMethod());
    }

    @Test
    @DisplayName("测试订单状态流转")
    void testOrderStatusFlow() {
        Order order = new Order();
        order.setOrderStatus(0); // 待支付
        
        order.setOrderStatus(1); // 已提交
        assertEquals(1, order.getOrderStatus());
        
        order.setOrderStatus(2); // 商家确认
        assertEquals(2, order.getOrderStatus());
        
        order.setOrderStatus(3); // 制作中
        order.setExpectedFinishTime(LocalDateTime.now().plusDays(3));
        assertNotNull(order.getExpectedFinishTime());
        
        order.setOrderStatus(4); // 已发货
        order.setLogisticsNo("SF1234567890");
        assertEquals("SF1234567890", order.getLogisticsNo());
        
        order.setOrderStatus(5); // 已完成
        order.setFinishedAt(LocalDateTime.now());
        assertNotNull(order.getFinishedAt());
    }

    @Test
    @DisplayName("测试订单取消")
    void testCancelOrder() {
        Order order = new Order();
        order.setOrderStatus(1); // 已提交
        order.setPaymentStatus(1); // 已支付
        
        order.setOrderStatus(6); // 取消
        order.setCancelReason("客户不想要了");
        order.setPaymentStatus(2); // 已退款
        order.setCancelledAt(LocalDateTime.now());
        
        assertEquals(6, order.getOrderStatus());
        assertEquals("客户不想要了", order.getCancelReason());
        assertEquals(2, order.getPaymentStatus());
        assertNotNull(order.getCancelledAt());
    }
}
