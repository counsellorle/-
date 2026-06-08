package com.example.order.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品更新请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class ProductUpdateRequest {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 单价
     */
    @DecimalMin(value = "0.01", message = "单价必须大于 0")
    private BigDecimal price;

    /**
     * 商品描述
     */
    private String description;
}
