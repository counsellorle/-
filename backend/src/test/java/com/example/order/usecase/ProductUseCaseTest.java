package com.example.order.usecase;

import com.example.order.entity.Product;
import com.example.order.enums.ProductStatus;
import com.example.order.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 商品用例测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@DisplayName("商品用例测试")
class ProductUseCaseTest {

    @Autowired
    private ProductUseCase productUseCase;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("测试创建商品成功")
    void testCreateProductSuccess() {
        String name = "测试商品";
        BigDecimal price = new BigDecimal("99.99");
        String description = "商品描述";

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName(name);
        savedProduct.setPrice(price);
        savedProduct.setDescription(description);
        savedProduct.setStatus(ProductStatus.ON_SHELF.getCode());

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(1L);
            return 1;
        });

        Long productId = productUseCase.createProduct(name, price, description);

        assertNotNull(productId);
        assertEquals(1L, productId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("测试更新商品成功")
    void testUpdateProductSuccess() {
        Long productId = 1L;
        String name = "更新后的商品";
        BigDecimal price = new BigDecimal("199.99");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setStatus(ProductStatus.ON_SHELF.getCode());

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.update(any(Product.class))).thenReturn(1);

        boolean success = productUseCase.updateProduct(productId, name, price, "新描述");

        assertTrue(success);
    }

    @Test
    @DisplayName("测试删除商品成功")
    void testDeleteProductSuccess() {
        Long productId = 1L;

        when(productRepository.delete(productId)).thenReturn(1);

        boolean success = productUseCase.deleteProduct(productId);

        assertTrue(success);
    }

    @Test
    @DisplayName("测试查询上架商品列表")
    void testFindAllOnShelf() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("商品 1");
        product1.setPrice(new BigDecimal("99.99"));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("商品 2");
        product2.setPrice(new BigDecimal("199.99"));

        when(productRepository.findAllOnShelf()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productUseCase.findAllOnShelf();

        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("测试上下架商品")
    void testUpdateProductStatus() {
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setStatus(ProductStatus.ON_SHELF.getCode());

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.update(any(Product.class))).thenReturn(1);

        boolean success = productUseCase.updateProductStatus(productId, ProductStatus.OFF_SHELF);

        assertTrue(success);
    }
}
