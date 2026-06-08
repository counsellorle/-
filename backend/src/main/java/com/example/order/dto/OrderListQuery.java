package com.example.order.dto;

import com.example.order.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单列表查询参数
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class OrderListQuery {

    /**
     * 订单状态（可选）
     */
    private OrderStatus status;

    /**
     * 开始时间（可选）
     */
    private LocalDateTime startTime;

    /**
     * 结束时间（可选）
     */
    private LocalDateTime endTime;

    /**
     * 订单号（可选）
     */
    private String orderNo;

    /**
     * 客户手机号（可选，仅商家使用）
     */
    private String customerPhone;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}
