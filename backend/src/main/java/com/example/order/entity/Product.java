package com.example.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@TableName("product")
public class Product {

    /**
     * 商品 ID
     */
    @TableId(type = IdType.AUTO)
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
     * 状态：0-下架，1-上架
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
}
