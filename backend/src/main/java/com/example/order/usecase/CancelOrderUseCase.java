package com.example.order.usecase;

import com.example.order.common.BusinessException;
import com.example.order.entity.Order;
import com.example.order.enums.OrderStatus;
import com.example.order.enums.PaymentStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.service.OrderDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 取消订单用例
 *
 * <p>功能：用户取消订单，支持退款处理。</p>
 * <p>规则：只有在制作完成前（订单状态 < 3）可以取消。</p>
 *
 * @author counsellorle
 * @date 2026-06-08
 */
@Slf4j
@Service
public class CancelOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDomainService orderDomainService;

    /**
     * 取消订单
     *
     * @param orderNo 订单号
     * @param userId  用户 ID
     * @param reason  取消原因
     * @return 取消后的订单号
     * @throws BusinessException 订单不存在、无权操作、不允许取消时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public String cancelOrder(String orderNo, Long userId, String reason) {
        log.info("用户取消订单，orderNo: {}, userId: {}, reason: {}", orderNo, userId, reason);

        // 1. 查询订单
        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("订单不存在"));

        // 2. 校验订单归属
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该订单");
        }

        // 3. 校验订单状态（制作完成前可以取消）
        if (order.getOrderStatus() >= OrderStatus.PRODUCING.getCode()) {
            throw new BusinessException("订单已制作完成，无法取消");
        }

        // 4. 校验支付状态
        if (order.getPaymentStatus() == PaymentStatus.REFUNDED.getCode()) {
            throw new BusinessException("订单已退款，无需重复取消");
        }

        // 5. 更新订单状态
        order.setOrderStatus(OrderStatus.CANCELLED.getCode());
        order.setCancelReason(reason);
        order.setUpdatedAt(LocalDateTime.now());

        // 6. 处理退款（如果已支付）
        if (order.getPaymentStatus() == PaymentStatus.PAID.getCode()) {
            log.info("订单已支付，执行退款逻辑，orderNo: {}", orderNo);
            processRefund(order);
            order.setPaymentStatus(PaymentStatus.REFUNDED.getCode());
        } else {
            // 未支付，直接取消
            order.setPaymentStatus(PaymentStatus.UNPAID.getCode());
        }

        // 7. 保存订单
        int updated = orderRepository.update(order);
        if (updated <= 0) {
            throw new BusinessException("取消订单失败");
        }

        log.info("订单取消成功，orderNo: {}, 新状态：{}", orderNo, order.getOrderStatus());

        // TODO: 发送取消通知短信

        return orderNo;
    }

    /**
     * 处理退款逻辑
     *
     * @param order 订单对象
     */
    private void processRefund(Order order) {
        // TODO: 实际应调用支付平台退款接口
        // 这里仅做模拟实现
        log.info("执行退款，orderNo: {}, 金额：{}", order.getOrderNo(), order.getTotalAmount());

        // 模拟退款处理
        // 1. 调用支付平台退款接口
        // 2. 记录退款流水
        // 3. 更新退款状态

        // 实际实现时需要根据支付方式调用不同的退款接口：
        // - 微信支付：调用微信退款 API
        // - 支付宝：调用支付宝退款 API
        // - 银行卡：原路退回
    }
}
