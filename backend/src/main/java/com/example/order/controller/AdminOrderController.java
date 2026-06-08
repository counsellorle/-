package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.ConfirmOrderRequest;
import com.example.order.dto.ShipOrderRequest;
import com.example.order.usecase.QueryOrderUseCase;
import com.example.order.vo.OrderVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 订单 Controller（商家端）
 * 负责商家查询所有订单、确认订单、推进订单状态、发货、完成等接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private QueryOrderUseCase queryOrderUseCase;

    /**
     * 商家订单列表接口
     *
     * @param status 订单状态（可选）
     * @return 统一返回结构，data 为订单列表
     */
    @GetMapping
    public Result<List<OrderVO>> listOrders(@RequestParam(required = false) Integer status) {
        // TODO: 实现订单列表查询
        return Result.success(Collections.emptyList());
    }

    /**
     * 商家订单详情接口
     *
     * @param orderNo 订单号
     * @return 统一返回结构，data 为订单详情
     */
    @GetMapping("/{orderNo}")
    public Result<OrderVO> getOrderDetail(@PathVariable String orderNo) {
        // TODO: 实现订单详情查询
        return Result.success(null);
    }

    /**
     * 确认订单接口
     *
     * @param orderNo 订单号
     * @param request 确认订单请求体（包含预计完成时间）
     * @return 统一返回结构
     */
    @PutMapping("/{orderNo}/confirm")
    public Result<Void> confirmOrder(@PathVariable String orderNo, @Valid @RequestBody ConfirmOrderRequest request) {
        // TODO: 实现确认订单逻辑
        return Result.success();
    }

    /**
     * 推进至制作中接口
     *
     * @param orderNo 订单号
     * @return 统一返回结构
     */
    @PutMapping("/{orderNo}/produce")
    public Result<Void> produceOrder(@PathVariable String orderNo) {
        // TODO: 实现推进订单逻辑
        return Result.success();
    }

    /**
     * 发货接口
     *
     * @param orderNo 订单号
     * @param request 发货请求体（包含物流单号）
     * @return 统一返回结构
     */
    @PutMapping("/{orderNo}/ship")
    public Result<Void> shipOrder(@PathVariable String orderNo, @Valid @RequestBody ShipOrderRequest request) {
        // TODO: 实现发货逻辑
        return Result.success();
    }

    /**
     * 标记订单已完成接口
     *
     * @param orderNo 订单号
     * @return 统一返回结构
     */
    @PutMapping("/{orderNo}/complete")
    public Result<Void> completeOrder(@PathVariable String orderNo) {
        // TODO: 实现完成订单逻辑
        return Result.success();
    }
}
