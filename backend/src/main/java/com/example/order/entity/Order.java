package com.example.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@TableName("`order`")
public class Order {

    /**
     * 订单 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态：1-待支付，2-已提交，3-商家确认，4-制作中，5-已发货，6-已完成
     */
    private Integer orderStatus;

    /**
     * 支付状态：0-未支付，1-已支付，2-已退款
     */
    private Integer paymentStatus;

    /**
     * 支付方式：1-微信，2-支付宝，3-银行转账
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
     * 取消原因
     */
    private String cancelReason;

    /**
     * 支付时间
     */
    private LocalDateTime paidAt;

    /**
     * 完成时间
     */
    private LocalDateTime finishedAt;

    /**
     * 取消时间
     */
    private LocalDateTime cancelledAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
