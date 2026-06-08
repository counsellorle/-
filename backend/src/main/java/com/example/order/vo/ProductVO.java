package com.example.order.vo;

import com.example.order.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品视图 VO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@Builder
public class ProductVO {

    /**
     * 商品 ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 状态：1-上架，0-下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 从商品实体构建商品 VO
     *
     * @param product 商品实体
     * @return 商品 VO
     */
    public static ProductVO from(Product product) {
        return ProductVO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
