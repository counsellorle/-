package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 创建订单请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class CreateOrderRequest {

    /**
     * 商品列表
     */
    @NotEmpty(message = "商品不能为空")
    private List<OrderItemDTO> items;

    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空")
    private String receiverAddress;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付方式：1-微信，2-支付宝，3-银行转账
     */
    @NotNull(message = "支付方式不能为空")
    private Integer paymentMethod;

    /**
     * 商品项 DTO
     */
    @Data
    public static class OrderItemDTO {
        /**
         * 商品 ID
         */
        @NotNull(message = "商品 ID 不能为空")
        private Long productId;

        /**
         * 数量
         */
        @NotNull(message = "数量不能为空")
        private Integer quantity;
    }
}
