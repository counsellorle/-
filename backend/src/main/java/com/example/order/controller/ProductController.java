package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.ProductCreateRequest;
import com.example.order.dto.ProductUpdateRequest;
import com.example.order.usecase.ProductUseCase;
import com.example.order.vo.ProductVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品 Controller
 * 负责商品 CRUD、上下架、列表查询等接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductUseCase productUseCase;

    /**
     * 创建商品接口
     *
     * @param request 创建商品请求体（包含商品名称、单价、描述）
     * @return 统一返回结构，data 为新创建的商品 ID
     */
    @PostMapping
    public Result<Long> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Long productId = productUseCase.createProduct(request.getName(), request.getPrice(), request.getDescription());
        return Result.success(productId);
    }

    /**
     * 更新商品接口
     *
     * @param id      商品 ID
     * @param request 更新商品请求体（包含商品名称、单价、描述）
     * @return 统一返回结构
     */
    @PutMapping("/{id}")
    public Result<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        productUseCase.updateProduct(id, request.getName(), request.getPrice(), request.getDescription());
        return Result.success();
    }

    /**
     * 删除商品接口
     *
     * @param id 商品 ID
     * @return 统一返回结构
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return Result.success();
    }

    /**
     * 上下架商品接口
     *
     * @param id     商品 ID
     * @param status 商品状态：0-下架，1-上架
     * @return 统一返回结构
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateProductStatus(@PathVariable Long id, @RequestParam Integer status) {
        productUseCase.updateProductStatus(id, com.example.order.enums.ProductStatus.valueOfCode(status));
        return Result.success();
    }

    /**
     * 商品列表查询接口
     *
     * @return 统一返回结构，data 为商品列表
     */
    @GetMapping
    public Result<List<ProductVO>> listProducts() {
        List<ProductVO> products = productUseCase.findAllOnShelf().stream()
                .map(ProductVO::from)
                .collect(Collectors.toList());
        return Result.success(products);
    }

    /**
     * 商品详情接口
     *
     * @param id 商品 ID
     * @return 统一返回结构，data 为商品信息
     */
    @GetMapping("/{id}")
    public Result<ProductVO> getProductDetail(@PathVariable Long id) {
        var product = productUseCase.getProductById(id);
        return Result.success(ProductVO.from(product));
    }
}
