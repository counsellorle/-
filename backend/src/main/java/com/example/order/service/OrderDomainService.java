package com.example.order.service;

import com.example.order.common.BusinessException;
import com.example.order.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 订单领域服务
 * 负责订单状态流转规则、取消规则、修改规则的校验
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Service
public class OrderDomainService {

    /**
     * 可以取消的订单状态列表
     */
    private static final List<OrderStatus> CANCELABLE_STATUSES = Arrays.asList(
            OrderStatus.SUBMITTED,
            OrderStatus.CONFIRMED
    );

    /**
     * 可以修改的订单状态列表
     */
    private static final List<OrderStatus> MODIFIABLE_STATUSES = Arrays.asList(
            OrderStatus.SUBMITTED,
            OrderStatus.CONFIRMED
    );

    /**
     * 判断订单是否可以取消
     *
     * @param currentStatus 当前订单状态
     * @return true-可以取消，false-不可取消
     */
    public boolean canCancel(OrderStatus currentStatus) {
        return CANCELABLE_STATUSES.contains(currentStatus);
    }

    /**
     * 判断订单是否可以修改
     *
     * @param currentStatus 当前订单状态
     * @return true-可以修改，false-不可修改
     */
    public boolean canModify(OrderStatus currentStatus) {
        return MODIFIABLE_STATUSES.contains(currentStatus);
    }

    /**
     * 校验订单状态流转是否合法
     *
     * @param fromStatus 原状态
     * @param toStatus   目标状态
     * @throws BusinessException 状态流转不合法时抛出
     */
    public void validateStatusFlow(OrderStatus fromStatus, OrderStatus toStatus) {
        // 定义合法的状态流转
        switch (fromStatus) {
            case PENDING_PAYMENT:
                if (toStatus != OrderStatus.SUBMITTED && toStatus != OrderStatus.CANCELLED) {
                    throw new BusinessException("订单状态流转不合法：待支付只能流转到已提交或已取消");
                }
                break;
            case SUBMITTED:
                if (toStatus != OrderStatus.CONFIRMED && toStatus != OrderStatus.CANCELLED) {
                    throw new BusinessException("订单状态流转不合法：已提交只能流转到商家确认或已取消");
                }
                break;
            case CONFIRMED:
                if (toStatus != OrderStatus.PRODUCING && toStatus != OrderStatus.CANCELLED) {
                    throw new BusinessException("订单状态流转不合法：商家确认只能流转到制作中或已取消");
                }
                break;
            case PRODUCING:
                if (toStatus != OrderStatus.SHIPPED) {
                    throw new BusinessException("订单状态流转不合法：制作中只能流转到已发货");
                }
                break;
            case SHIPPED:
                if (toStatus != OrderStatus.COMPLETED) {
                    throw new BusinessException("订单状态流转不合法：已发货只能流转到已完成");
                }
                break;
            case COMPLETED:
            case CANCELLED:
                throw new BusinessException("订单状态流转不合法：已完成或已取消的订单不能再流转");
            default:
                throw new BusinessException("未知的订单状态");
        }
    }
}
