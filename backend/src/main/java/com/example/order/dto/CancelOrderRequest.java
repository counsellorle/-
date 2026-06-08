package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 订单取消请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class CancelOrderRequest {

    /**
     * 取消原因
     */
    @NotBlank(message = "取消原因不能为空")
    private String reason;
}
