package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发货请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class ShipOrderRequest {

    /**
     * 物流单号
     */
    @NotBlank(message = "物流单号不能为空")
    private String logisticsNo;
}
