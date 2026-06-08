package com.example.order.service;

import com.example.order.common.BusinessException;
import com.example.order.enums.PaymentStatus;
import org.springframework.stereotype.Service;

/**
 * 支付领域服务
 * 负责支付超时校验、退款规则校验
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class PaymentDomainService {

    /**
     * 判断是否可以退款
     *
     * @param paymentStatus 当前支付状态
     * @return true-可以退款，false-不可退款
     */
    public boolean canRefund(PaymentStatus paymentStatus) {
        return PaymentStatus.PAID.equals(paymentStatus);
    }

    /**
     * 校验支付状态是否可以退款
     *
     * @param paymentStatus 当前支付状态
     * @throws BusinessException 不可以退款时抛出
     */
    public void validateRefund(PaymentStatus paymentStatus) {
        if (!canRefund(paymentStatus)) {
            throw new BusinessException("当前订单不可退款");
        }
    }
}
