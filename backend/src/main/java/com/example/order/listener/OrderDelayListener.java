package com.example.order.listener;

import com.example.order.usecase.PaymentTimeoutUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 订单超时处理组件
 * 使用定时任务处理支付超时订单
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Slf4j
@Component
public class OrderDelayListener {

    @Autowired
    private PaymentTimeoutUseCase paymentTimeoutUseCase;

    /**
     * 每分钟检查一次超时订单
     */
    @Scheduled(fixedRate = 60000)
    public void checkTimeoutOrders() {
        log.info("检查超时订单...");
        paymentTimeoutUseCase.processTimeoutOrders(LocalDateTime.now());
    }
}
