package com.example.order.dto;

import com.example.order.enums.ProductStatus;
import lombok.Data;

/**
 * 商品列表查询参数
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class ProductListQuery {

    /**
     * 商品状态（可选）
     */
    private ProductStatus status;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}
