package com.example.order.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 订单确认事件
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Getter
public class OrderConfirmedEvent extends ApplicationEvent {

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
     * 预计完成时间
     */
    private final String expectedFinishTime;

    public OrderConfirmedEvent(Object source, String orderNo, Long userId, String userPhone, String expectedFinishTime) {
        super(source);
        this.orderNo = orderNo;
        this.userId = userId;
        this.userPhone = userPhone;
        this.expectedFinishTime = expectedFinishTime;
    }
}
