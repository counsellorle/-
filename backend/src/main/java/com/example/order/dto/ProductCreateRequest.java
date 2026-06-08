package com.example.order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品创建请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class ProductCreateRequest {

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 单价
     */
    @NotNull(message = "单价不能为空")
    @DecimalMin(value = "0.01", message = "单价必须大于 0")
    private BigDecimal price;

    /**
     * 商品描述
     */
    private String description;
}
