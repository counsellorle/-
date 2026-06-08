package com.example.order.usecase;

import com.example.order.common.BusinessException;
import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import com.example.order.entity.Product;
import com.example.order.enums.OrderStatus;
import com.example.order.enums.PaymentStatus;
import com.example.order.repository.OrderItemRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
import com.example.order.service.OrderNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建订单用例
 * 负责订单创建、商品校验、金额计算、订单号生成等业务逻辑
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class CreateOrderUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderNumberGenerator orderNumberGenerator;

    /**
     * 订单项请求
     */
    public static class OrderItemRequest {
        private final Long productId;
        private final Integer quantity;

        public OrderItemRequest(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public Long getProductId() {
            return productId;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }

    /**
     * 创建订单
     *
     * @param userId         用户 ID
     * @param items          商品列表（商品 ID + 数量）
     * @param receiverAddress 收货地址
     * @param remark         备注
     * @param paymentMethod  支付方式
     * @return 订单号
     */
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(Long userId, List<OrderItemRequest> items, 
                              String receiverAddress, String remark, Integer paymentMethod) {
        // 1. 校验商品并计算总金额
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : items) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new BusinessException("商品不存在：" + itemRequest.getProductId()));

            if (product.getStatus() != 1) {
                throw new BusinessException("商品已下架：" + product.getName());
            }

            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(itemRequest.getQuantity()));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(subtotal);
        }

        // 2. 生成订单号
        String orderNo = orderNumberGenerator.generate();

        // 3. 创建订单
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setReceiverAddress(receiverAddress);
        order.setRemark(remark);
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT.getCode());
        order.setPaymentStatus(PaymentStatus.UNPAID.getCode());
        order.setPaymentMethod(paymentMethod);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        int orderSaved = orderRepository.save(order);
        if (orderSaved <= 0) {
            throw new BusinessException("创建订单失败");
        }

        // 4. 创建订单商品明细
        for (OrderItem item : orderItems) {
            item.setOrderId(order.getId());
        }

        int itemsSaved = orderItemRepository.batchSave(orderItems);
        if (itemsSaved <= 0) {
            throw new BusinessException("创建订单商品明细失败");
        }

        return orderNo;
    }
}
