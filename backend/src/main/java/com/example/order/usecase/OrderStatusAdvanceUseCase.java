package com.example.order.usecase;

import com.example.order.common.BusinessException;
import com.example.order.entity.Order;
import com.example.order.enums.OrderStatus;
import com.example.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单状态推进用例
 *
 * <p>功能：商家推进订单状态（确认 → 制作中 → 已发货 → 已完成）。</p>
 * <p>规则：只能按顺序推进，不能越级。</p>
 *
 * @author counsellorle
 * @date 2026-06-08
 */
@Slf4j
@Service
public class OrderStatusAdvanceUseCase {

    @Autowired
    private OrderRepository orderRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 确认订单（商家）
     *
     * @param orderNo 订单号
     * @param expectedFinishTime 预计完成时间
     * @return 确认后的订单号
     * @throws BusinessException 订单不存在、状态不正确时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public String confirmOrder(String orderNo, LocalDateTime expectedFinishTime) {
        log.info("商家确认订单，orderNo: {}, expectedFinishTime: {}", orderNo, expectedFinishTime);

        Order order = getOrderAndValidate(orderNo);

        // 校验订单状态（必须是已提交状态）
        if (order.getOrderStatus() != OrderStatus.SUBMITTED.getCode()) {
            throw new BusinessException("订单状态不正确，无法确认");
        }

        // 更新订单状态
        order.setOrderStatus(OrderStatus.CONFIRMED.getCode());
        if (expectedFinishTime != null) {
            order.setExpectedFinishTime(expectedFinishTime);
        }
        order.setUpdatedAt(LocalDateTime.now());

        int updated = orderRepository.update(order);
        if (updated <= 0) {
            throw new BusinessException("确认订单失败");
        }

        log.info("订单确认成功，orderNo: {}, 新状态：{}", orderNo, order.getOrderStatus());

        // TODO: 发送确认通知短信

        return orderNo;
    }

    /**
     * 推进到制作中
     *
     * @param orderNo 订单号
     * @return 推进后的订单号
     * @throws BusinessException 订单不存在、状态不正确时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public String startProduction(String orderNo) {
        log.info("订单开始制作，orderNo: {}", orderNo);

        Order order = getOrderAndValidate(orderNo);

        // 校验订单状态（必须是已确认状态）
        if (order.getOrderStatus() != OrderStatus.CONFIRMED.getCode()) {
            throw new BusinessException("订单状态不正确，无法开始制作");
        }

        // 更新订单状态
        order.setOrderStatus(OrderStatus.PRODUCING.getCode());
        order.setUpdatedAt(LocalDateTime.now());

        int updated = orderRepository.update(order);
        if (updated <= 0) {
            throw new BusinessException("开始制作失败");
        }

        log.info("订单开始制作，orderNo: {}, 新状态：{}", orderNo, order.getOrderStatus());

        // TODO: 发送制作通知短信

        return orderNo;
    }

    /**
     * 订单发货
     *
     * @param orderNo 订单号
     * @param trackingNo 物流单号
     * @return 发货后的订单号
     * @throws BusinessException 订单不存在、状态不正确时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public String shipOrder(String orderNo, String trackingNo) {
        log.info("订单发货，orderNo: {}, trackingNo: {}", orderNo, trackingNo);

        Order order = getOrderAndValidate(orderNo);

        // 校验订单状态（必须是制作中状态）
        if (order.getOrderStatus() != OrderStatus.PRODUCING.getCode()) {
            throw new BusinessException("订单状态不正确，无法发货");
        }

        // 校验物流单号
        if (trackingNo == null || trackingNo.isEmpty()) {
            throw new BusinessException("物流单号不能为空");
        }

        // 更新订单状态
        order.setOrderStatus(OrderStatus.SHIPPED.getCode());
        order.setLogisticsNo(trackingNo);
        order.setUpdatedAt(LocalDateTime.now());

        int updated = orderRepository.update(order);
        if (updated <= 0) {
            throw new BusinessException("发货失败");
        }

        log.info("订单发货成功，orderNo: {}, 物流单号：{}, 新状态：{}", orderNo, trackingNo, order.getOrderStatus());

        // TODO: 发送发货通知短信

        return orderNo;
    }

    /**
     * 确认收货（完成订单）
     *
     * @param orderNo 订单号
     * @return 完成后的订单号
     * @throws BusinessException 订单不存在、状态不正确时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public String completeOrder(String orderNo) {
        log.info("订单完成，orderNo: {}", orderNo);

        Order order = getOrderAndValidate(orderNo);

        // 校验订单状态（必须是已发货状态）
        if (order.getOrderStatus() != OrderStatus.SHIPPED.getCode()) {
            throw new BusinessException("订单状态不正确，无法完成");
        }

        // 更新订单状态
        order.setOrderStatus(OrderStatus.COMPLETED.getCode());
        order.setUpdatedAt(LocalDateTime.now());

        int updated = orderRepository.update(order);
        if (updated <= 0) {
            throw new BusinessException("完成订单失败");
        }

        log.info("订单完成，orderNo: {}, 新状态：{}", orderNo, order.getOrderStatus());

        // TODO: 发送完成通知短信

        return orderNo;
    }

    /**
     * 查询订单并校验
     *
     * @param orderNo 订单号
     * @return 订单对象
     * @throws BusinessException 订单不存在时抛出
     */
    private Order getOrderAndValidate(String orderNo) {
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException("订单不存在"));
    }
}
