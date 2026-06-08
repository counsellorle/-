package com.example.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 确认订单请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class ConfirmOrderRequest {

    /**
     * 预计完成时间
     */
    @NotNull(message = "预计完成时间不能为空")
    private LocalDateTime expectedFinishTime;
}
