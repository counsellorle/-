package com.example.order.dto;

import lombok.Data;

import java.util.Map;

/**
 * 支付回调请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class PayCallbackRequest {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付平台交易号
     */
    private String tradeNo;

    /**
     * 回调参数
     */
    private Map<String, String> params;
}
