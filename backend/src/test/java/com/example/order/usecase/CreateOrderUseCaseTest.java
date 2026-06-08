package com.example.order.usecase;

import com.example.order.common.BusinessException;
import com.example.order.entity.Order;
import com.example.order.entity.Product;
import com.example.order.repository.OrderItemRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
import com.example.order.service.OrderDomainService;
import com.example.order.service.OrderNumberGenerator;
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
 * 创建订单用例测试
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@SpringBootTest
@DisplayName("创建订单用例测试")
class CreateOrderUseCaseTest {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private OrderNumberGenerator orderNumberGenerator;

    @MockBean
    private OrderDomainService orderDomainService;

    @Test
    @DisplayName("测试创建订单成功")
    void testCreateOrderSuccess() {
        Long userId = 1L;
        String receiverAddress = "北京市朝阳区 xxx 路 xxx 号";
        String remark = "请尽快发货";
        Integer paymentMethod = 2;

        List<CreateOrderUseCase.OrderItemRequest> items = Arrays.asList(
            new CreateOrderUseCase.OrderItemRequest(1L, 2),
            new CreateOrderUseCase.OrderItemRequest(2L, 1)
        );

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("商品 1");
        product1.setPrice(new BigDecimal("99.99"));
        product1.setStatus(1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("商品 2");
        product2.setPrice(new BigDecimal("199.99"));
        product2.setStatus(1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(orderNumberGenerator.generate()).thenReturn("ORD202606080001");
        when(orderRepository.save(any(Order.class))).thenReturn(1);
        when(orderItemRepository.batchSave(any())).thenReturn(1);

        String orderNo = createOrderUseCase.createOrder(userId, items, receiverAddress, remark, paymentMethod);

        assertNotNull(orderNo);
        assertEquals("ORD202606080001", orderNo);
    }

    @Test
    @DisplayName("测试商品不存在抛出异常")
    void testCreateOrderWithNonExistentProduct() {
        Long userId = 1L;
        List<CreateOrderUseCase.OrderItemRequest> items = Arrays.asList(
            new CreateOrderUseCase.OrderItemRequest(999L, 1)
        );

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            createOrderUseCase.createOrder(userId, items, "address", "remark", 1);
        });
    }

    @Test
    @DisplayName("测试商品已下架抛出异常")
    void testCreateOrderWithOffShelfProduct() {
        Long userId = 1L;
        List<CreateOrderUseCase.OrderItemRequest> items = Arrays.asList(
            new CreateOrderUseCase.OrderItemRequest(1L, 1)
        );

        Product product = new Product();
        product.setId(1L);
        product.setStatus(0); // 下架

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BusinessException.class, () -> {
            createOrderUseCase.createOrder(userId, items, "address", "remark", 1);
        });
    }
}
