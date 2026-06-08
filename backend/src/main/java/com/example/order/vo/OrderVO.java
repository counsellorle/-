package com.example.order.vo;

import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单视图 VO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@Builder
public class OrderVO {

    /**
     * 订单 ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单总金额
     */
    private String totalAmount;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 支付状态
     */
    private Integer paymentStatus;

    /**
     * 支付方式
     */
    private Integer paymentMethod;

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预计完成时间
     */
    private LocalDateTime expectedFinishTime;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 支付时间
     */
    private LocalDateTime paidAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 订单商品明细
     */
    private List<OrderItemVO> items;

    /**
     * 从订单实体构建订单 VO
     *
     * @param order 订单实体
     * @param items 商品明细列表
     * @return 订单 VO
     */
    public static OrderVO from(Order order, List<OrderItem> items) {
        return OrderVO.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .totalAmount(order.getTotalAmount().toString())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .paymentMethod(order.getPaymentMethod())
                .receiverAddress(order.getReceiverAddress())
                .remark(order.getRemark())
                .expectedFinishTime(order.getExpectedFinishTime())
                .logisticsNo(order.getLogisticsNo())
                .paidAt(order.getPaidAt())
                .createdAt(order.getCreatedAt())
                .items(OrderItemVO.from(items))
                .build();
    }
}
