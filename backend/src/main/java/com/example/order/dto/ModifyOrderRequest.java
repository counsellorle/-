package com.example.order.dto;

import lombok.Data;

/**
 * 订单修改请求 DTO
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
public class ModifyOrderRequest {

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 备注
     */
    private String remark;
}
