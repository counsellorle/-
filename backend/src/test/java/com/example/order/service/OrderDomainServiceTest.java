package com.example.order.service;

import com.example.order.common.BusinessException;
import com.example.order.enums.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 订单领域服务测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@DisplayName("订单领域服务测试")
class OrderDomainServiceTest {

    @Autowired
    private OrderDomainService orderDomainService;

    @Test
    @DisplayName("测试已提交状态可以取消")
    void testCanCancelWhenSubmitted() {
        boolean canCancel = orderDomainService.canCancel(OrderStatus.SUBMITTED);
        assertTrue(canCancel);
    }

    @Test
    @DisplayName("测试商家确认状态可以取消")
    void testCanCancelWhenConfirmed() {
        boolean canCancel = orderDomainService.canCancel(OrderStatus.CONFIRMED);
        assertTrue(canCancel);
    }

    @Test
    @DisplayName("测试制作中状态不可以取消")
    void testCannotCancelWhenProducing() {
        boolean canCancel = orderDomainService.canCancel(OrderStatus.PRODUCING);
        assertFalse(canCancel);
    }

    @Test
    @DisplayName("测试已发货状态不可以取消")
    void testCannotCancelWhenShipped() {
        boolean canCancel = orderDomainService.canCancel(OrderStatus.SHIPPED);
        assertFalse(canCancel);
    }

    @Test
    @DisplayName("测试已完成状态不可以取消")
    void testCannotCancelWhenCompleted() {
        boolean canCancel = orderDomainService.canCancel(OrderStatus.COMPLETED);
        assertFalse(canCancel);
    }

    @Test
    @DisplayName("测试已提交状态可以修改")
    void testCanModifyWhenSubmitted() {
        boolean canModify = orderDomainService.canModify(OrderStatus.SUBMITTED);
        assertTrue(canModify);
    }

    @Test
    @DisplayName("测试商家确认状态可以修改")
    void testCanModifyWhenConfirmed() {
        boolean canModify = orderDomainService.canModify(OrderStatus.CONFIRMED);
        assertTrue(canModify);
    }

    @Test
    @DisplayName("测试制作中状态不可以修改")
    void testCannotModifyWhenProducing() {
        boolean canModify = orderDomainService.canModify(OrderStatus.PRODUCING);
        assertFalse(canModify);
    }

    @Test
    @DisplayName("测试状态流转 - 已提交可以流转到商家确认")
    void testStatusFlowFromSubmittedToConfirmed() {
        assertDoesNotThrow(() -> {
            orderDomainService.validateStatusFlow(OrderStatus.SUBMITTED, OrderStatus.CONFIRMED);
        });
    }

    @Test
    @DisplayName("测试状态流转 - 商家确认可以流转到制作中")
    void testStatusFlowFromConfirmedToProducing() {
        assertDoesNotThrow(() -> {
            orderDomainService.validateStatusFlow(OrderStatus.CONFIRMED, OrderStatus.PRODUCING);
        });
    }

    @Test
    @DisplayName("测试状态流转 - 制作中可以流转到已发货")
    void testStatusFlowFromProducingToShipped() {
        assertDoesNotThrow(() -> {
            orderDomainService.validateStatusFlow(OrderStatus.PRODUCING, OrderStatus.SHIPPED);
        });
    }

    @Test
    @DisplayName("测试状态流转 - 已发货可以流转到已完成")
    void testStatusFlowFromShippedToCompleted() {
        assertDoesNotThrow(() -> {
            orderDomainService.validateStatusFlow(OrderStatus.SHIPPED, OrderStatus.COMPLETED);
        });
    }

    @Test
    @DisplayName("测试状态流转 - 非法流转抛出异常")
    void testInvalidStatusFlow() {
        assertThrows(BusinessException.class, () -> {
            orderDomainService.validateStatusFlow(OrderStatus.PRODUCING, OrderStatus.SUBMITTED);
        });
    }
}
