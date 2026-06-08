package com.example.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单商品明细实体
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@TableName("order_item")
public class OrderItem {

    /**
     * 订单项 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单 ID
     */
    private Long orderId;

    /**
     * 商品 ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 小计金额
     */
    private BigDecimal subtotal;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
