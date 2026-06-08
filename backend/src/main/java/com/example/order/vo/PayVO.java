package com.example.order.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 支付响应 VO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@Builder
public class PayVO {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付金额
     */
    private String amount;

    /**
     * 支付参数（JSON 字符串，用于前端调起支付）
     */
    private String payParams;
}
