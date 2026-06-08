package com.example.order.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 订单取消事件
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Getter
public class OrderCancelledEvent extends ApplicationEvent {

    /**
     * 订单号
     */
    private final String orderNo;

    /**
     * 用户 ID
     */
    private final Long userId;

    /**
     * 用户手机号
     */
    private final String userPhone;

    /**
     * 取消原因
     */
    private final String cancelReason;

    /**
     * 退款金额
     */
    private final String refundAmount;

    public OrderCancelledEvent(Object source, String orderNo, Long userId, String userPhone, String cancelReason, String refundAmount) {
        super(source);
        this.orderNo = orderNo;
        this.userId = userId;
        this.userPhone = userPhone;
        this.cancelReason = cancelReason;
        this.refundAmount = refundAmount;
    }
}
