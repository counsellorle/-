package com.example.order.usecase;

import com.example.order.common.BusinessException;
import com.example.order.entity.Order;
import com.example.order.enums.OrderStatus;
import com.example.order.enums.PaymentStatus;
import com.example.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 支付处理用例
 *
 * <p>功能：处理支付回调、更新订单支付状态。</p>
 *
 * @author counsellorle
 * @date 2026-06-08
 */
@Slf4j
@Service
public class PaymentUseCase {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 处理支付成功回调
     *
     * @param orderNo 订单号
     * @param transactionId 支付平台交易号
     * @param paymentMethod 支付方式（1-微信，2-支付宝，3-银行卡）
     * @return 处理后的订单号
     * @throws BusinessException 订单不存在、重复支付时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public String handlePaymentSuccess(String orderNo, String transactionId, Integer paymentMethod) {
        log.info("处理支付成功回调，orderNo: {}, transactionId: {}, paymentMethod: {}", orderNo, transactionId, paymentMethod);

        // 1. 查询订单
        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("订单不存在"));

        // 2. 检查是否已支付
        if (order.getPaymentStatus() == PaymentStatus.PAID.getCode()) {
            log.warn("订单已支付，无需重复处理，orderNo: {}", orderNo);
            return orderNo;
        }

        // 3. 检查订单状态（只有待支付和已提交的订单可以支付）
        if (order.getOrderStatus() != OrderStatus.PENDING_PAYMENT.getCode() && 
            order.getOrderStatus() != OrderStatus.SUBMITTED.getCode()) {
            throw new BusinessException("订单状态不正确，无法支付");
        }

        // 4. 更新支付状态
        order.setPaymentStatus(PaymentStatus.PAID.getCode());
        order.setPaymentMethod(paymentMethod);
        order.setUpdatedAt(LocalDateTime.now());
        
        // TODO: 保存支付流水记录
        log.info("保存支付流水，orderNo: {}, transactionId: {}", orderNo, transactionId);

        // 5. 更新订单状态（从待支付变为已提交）
        if (order.getOrderStatus() == OrderStatus.PENDING_PAYMENT.getCode()) {
            order.setOrderStatus(OrderStatus.SUBMITTED.getCode());
        }

        // 6. 保存订单
        int updated = orderRepository.update(order);
        if (updated <= 0) {
            throw new BusinessException("更新支付状态失败");
        }

        log.info("支付处理成功，orderNo: {}, 新状态：{}", orderNo, order.getOrderStatus());

        // TODO: 发送支付成功通知短信
        // TODO: 触发订单支付成功事件

        return orderNo;
    }

    /**
     * 处理支付失败回调
     *
     * @param orderNo 订单号
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentFailed(String orderNo, String errorCode, String errorMsg) {
        log.warn("支付失败，orderNo: {}, errorCode: {}, errorMsg: {}", orderNo, errorCode, errorMsg);

        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("订单不存在"));

        // 记录支付失败信息
        // TODO: 保存支付失败记录
        // TODO: 发送支付失败通知

        log.info("支付失败处理完成，orderNo: {}", orderNo);
    }

    /**
     * 处理退款回调
     *
     * @param orderNo 订单号
     * @param refundId 退款单号
     * @param refundAmount 退款金额
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleRefundCallback(String orderNo, String refundId, String refundAmount) {
        log.info("处理退款回调，orderNo: {}, refundId: {}, refundAmount: {}", orderNo, refundId, refundAmount);

        Order order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("订单不存在"));

        // 校验退款状态
        if (order.getPaymentStatus() != PaymentStatus.REFUNDED.getCode()) {
            throw new BusinessException("订单退款状态不正确");
        }

        // TODO: 保存退款流水记录
        log.info("退款回调处理完成，orderNo: {}", orderNo);
    }
}
