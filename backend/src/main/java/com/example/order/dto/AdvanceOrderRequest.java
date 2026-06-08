package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 订单状态推进请求 DTO
 *
 * <p>用途：商家推进订单状态时携带的参数。</p>
 *
 * @author counsellorle
 * @date 2026-06-08
 */
@Data
public class AdvanceOrderRequest {

    /**
     * 预计完成时间（商家确认时填写）
     */
    private String expectedFinishTime;

    /**
     * 物流单号（发货时填写）
     */
    private String trackingNo;

    /**
     * 备注信息
     */
    private String remark;
}
