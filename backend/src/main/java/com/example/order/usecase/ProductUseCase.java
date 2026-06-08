package com.example.order.usecase;

import com.example.order.common.BusinessException;
import com.example.order.entity.Product;
import com.example.order.enums.ProductStatus;
import com.example.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品用例
 * 负责商品创建、更新、删除、上下架、查询等业务逻辑
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class ProductUseCase {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 创建商品
     *
     * @param name        商品名称
     * @param price       单价
     * @param description 商品描述
     * @return 商品 ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createProduct(String name, BigDecimal price, String description) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStatus(ProductStatus.ON_SHELF.getCode());

        int saved = productRepository.save(product);
        if (saved <= 0) {
            throw new BusinessException("创建商品失败");
        }

        return product.getId();
    }

    /**
     * 更新商品
     *
     * @param productId   商品 ID
     * @param name        商品名称
     * @param price       单价
     * @param description 商品描述
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProduct(Long productId, String name, BigDecimal price, String description) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("商品不存在"));

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.update(product) > 0;
    }

    /**
     * 删除商品
     *
     * @param productId 商品 ID
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProduct(Long productId) {
        return productRepository.delete(productId) > 0;
    }

    /**
     * 查询所有上架商品
     *
     * @return 商品列表
     */
    public List<Product> findAllOnShelf() {
        return productRepository.findAllOnShelf();
    }

    /**
     * 更新商品状态（上下架）
     *
     * @param productId 商品 ID
     * @param status    商品状态
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProductStatus(Long productId, ProductStatus status) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("商品不存在"));

        product.setStatus(status.getCode());
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.update(product) > 0;
    }

    /**
     * 根据 ID 查询商品
     *
     * @param productId 商品 ID
     * @return 商品信息
     */
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("商品不存在"));
    }
}
