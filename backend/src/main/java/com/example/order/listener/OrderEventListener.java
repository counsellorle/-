package com.example.order.listener;

import com.example.order.event.OrderCancelledEvent;
import com.example.order.event.OrderCompletedEvent;
import com.example.order.event.OrderConfirmedEvent;
import com.example.order.event.OrderPaidEvent;
import com.example.order.event.OrderShippedEvent;
import com.example.order.usecase.SmsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 订单事件监听器
 * 监听订单状态变更事件，触发短信通知
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Component
public class OrderEventListener {

    @Autowired
    private SmsUseCase smsUseCase;

    /**
     * 监听订单支付成功事件
     *
     * @param event 订单支付事件
     */
    @Async
    @EventListener(OrderPaidEvent.class)
    public void handleOrderPaid(OrderPaidEvent event) {
        smsUseCase.sendOrderPaidNotification(event.getUserPhone(), event.getOrderNo(), event.getAmount());
    }

    /**
     * 监听订单确认事件
     *
     * @param event 订单确认事件
     */
    @Async
    @EventListener(OrderConfirmedEvent.class)
    public void handleOrderConfirmed(OrderConfirmedEvent event) {
        smsUseCase.sendOrderConfirmedNotification(event.getUserPhone(), event.getOrderNo(), event.getExpectedFinishTime());
    }

    /**
     * 监听订单发货事件
     *
     * @param event 订单发货事件
     */
    @Async
    @EventListener(OrderShippedEvent.class)
    public void handleOrderShipped(OrderShippedEvent event) {
        smsUseCase.sendOrderShippedNotification(event.getUserPhone(), event.getOrderNo(), event.getLogisticsNo());
    }

    /**
     * 监听订单完成事件
     *
     * @param event 订单完成事件
     */
    @Async
    @EventListener(OrderCompletedEvent.class)
    public void handleOrderCompleted(OrderCompletedEvent event) {
        smsUseCase.sendOrderCompletedNotification(event.getUserPhone(), event.getOrderNo());
    }

    /**
     * 监听订单取消事件
     *
     * @param event 订单取消事件
     */
    @Async
    @EventListener(OrderCancelledEvent.class)
    public void handleOrderCancelled(OrderCancelledEvent event) {
        smsUseCase.sendOrderCancelledNotification(event.getUserPhone(), event.getOrderNo(), event.getCancelReason(), event.getRefundAmount());
    }
}
