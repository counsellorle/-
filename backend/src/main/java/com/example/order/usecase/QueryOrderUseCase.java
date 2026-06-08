package com.example.order.usecase;

import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import com.example.order.repository.OrderItemRepository;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询订单用例
 * 负责订单列表查询、订单详情查询等业务逻辑
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class QueryOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    /**
     * 查询订单详情
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    public Order getOrderDetail(String orderNo) {
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    /**
     * 查询订单商品明细
     *
     * @param orderId 订单 ID
     * @return 商品明细列表
     */
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
