package com.example.order.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 商品实体测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@DisplayName("商品实体测试")
class ProductTest {

    @Test
    @DisplayName("测试创建商品")
    void testCreateProduct() {
        Product product = new Product();
        product.setName("测试商品");
        product.setPrice(new BigDecimal("99.99"));
        product.setDescription("商品描述");
        product.setStatus(1);

        assertEquals("测试商品", product.getName());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals("商品描述", product.getDescription());
        assertEquals(1, product.getStatus());
    }
}
