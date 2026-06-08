package com.example.order.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 订单创建响应 VO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@Builder
public class OrderCreateVO {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单总金额
     */
    private String totalAmount;

    /**
     * 支付参数（JSON 字符串）
     */
    private String payParams;

    /**
     * 支付超时时间（秒）
     */
    private Integer timeout;
}
