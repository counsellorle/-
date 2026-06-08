package com.example.order.task;

import com.example.order.usecase.PaymentTimeoutUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 订单定时任务
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Slf4j
@Component
public class OrderScheduledTask {

    @Autowired
    private PaymentTimeoutUseCase paymentTimeoutUseCase;

    /**
     * 每分钟执行一次，处理支付超时订单
     */
    @Scheduled(fixedRate = 60000)
    public void processTimeoutOrders() {
        log.info("开始处理支付超时订单...");
        LocalDateTime timeout = LocalDateTime.now().minusMinutes(30);
        paymentTimeoutUseCase.processTimeoutOrders(timeout);
        log.info("处理支付超时订单完成");
    }
}
