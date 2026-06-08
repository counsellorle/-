package com.example.order.vo;

import com.example.order.entity.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单商品明细视图 VO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@Builder
public class OrderItemVO {

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
     * 从订单商品明细实体列表构建 VO 列表
     *
     * @param items 订单商品明细列表
     * @return VO 列表
     */
    public static List<OrderItemVO> from(List<OrderItem> items) {
        return items.stream()
                .map(item -> OrderItemVO.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .unitPrice(item.getUnitPrice())
                        .quantity(item.getQuantity())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());
    }
}
