package com.example.order.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 订单商品明细实体测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@DisplayName("订单商品明细实体测试")
class OrderItemTest {

    @Test
    @DisplayName("测试创建订单商品明细")
    void testCreateOrderItem() {
        OrderItem item = new OrderItem();
        item.setOrderId(1L);
        item.setProductId(100L);
        item.setProductName("测试商品");
        item.setUnitPrice(new BigDecimal("99.99"));
        item.setQuantity(2);
        item.setSubtotal(new BigDecimal("199.98"));

        assertEquals(1L, item.getOrderId());
        assertEquals(100L, item.getProductId());
        assertEquals("测试商品", item.getProductName());
        assertEquals(new BigDecimal("99.99"), item.getUnitPrice());
        assertEquals(2, item.getQuantity());
        assertEquals(new BigDecimal("199.98"), item.getSubtotal());
    }

    @Test
    @DisplayName("测试小计金额计算")
    void testSubtotalCalculation() {
        BigDecimal unitPrice = new BigDecimal("50.00");
        int quantity = 3;
        BigDecimal expectedSubtotal = unitPrice.multiply(new BigDecimal(quantity));

        assertEquals(new BigDecimal("150.00"), expectedSubtotal);
    }
}
