package com.example.order.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 订单支付事件
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Getter
public class OrderPaidEvent extends ApplicationEvent {

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
     * 订单金额
     */
    private final String amount;

    public OrderPaidEvent(Object source, String orderNo, Long userId, String userPhone, String amount) {
        super(source);
        this.orderNo = orderNo;
        this.userId = userId;
        this.userPhone = userPhone;
        this.amount = amount;
    }
}
