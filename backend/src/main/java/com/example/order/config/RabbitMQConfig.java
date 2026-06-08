package com.example.order.config;

/**
 * RabbitMQ 配置
 * 用于订单延迟队列（支付超时自动取消）
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
// 暂时禁用 RabbitMQ，使用定时任务方案
// @Configuration
public class RabbitMQConfig {

    /**
     * 订单延迟队列
     */
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    
    /**
     * 订单延迟交换机
     */
    public static final String ORDER_DELAY_EXCHANGE = "order.delay.exchange";
    
    /**
     * 订单延迟路由键
     */
    public static final String ORDER_DELAY_ROUTING_KEY = "order.delay";

    // RabbitMQ 配置已禁用，使用定时任务替代
}
