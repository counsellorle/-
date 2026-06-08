package com.example.order.controller;

import com.example.order.common.Result;
import com.example.order.dto.CancelOrderRequest;
import com.example.order.dto.CreateOrderRequest;
import com.example.order.dto.ModifyOrderRequest;
import com.example.order.usecase.CreateOrderUseCase;
import com.example.order.usecase.QueryOrderUseCase;
import com.example.order.vo.OrderCreateVO;
import com.example.order.vo.OrderVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单 Controller（客户端）
 * 负责订单创建、查询、修改、取消、支付等接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private QueryOrderUseCase queryOrderUseCase;

    /**
     * 创建订单接口
     *
     * @param request 创建订单请求体（包含商品列表、收货地址、备注、支付方式）
     * @return 统一返回结构，data 为订单号和支付参数
     */
    @PostMapping
    public Result<OrderCreateVO> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        // TODO: 实际应从登录态获取 userId
        Long userId = 1L;
        
        var items = request.getItems().stream()
                .map(item -> new CreateOrderUseCase.OrderItemRequest(item.getProductId(), item.getQuantity()))
                .toList();
        
        String orderNo = createOrderUseCase.createOrder(
                userId, 
                items, 
                request.getReceiverAddress(), 
                request.getRemark(), 
                request.getPaymentMethod()
        );
        
        OrderCreateVO vo = OrderCreateVO.builder()
                .orderNo(orderNo)
                .timeout(1800)
                .build();
        
        return Result.success(vo);
    }

    /**
     * 订单详情接口
     *
     * @param orderNo 订单号
     * @return 统一返回结构，data 为订单详情
     */
    @GetMapping("/{orderNo}")
    public Result<OrderVO> getOrderDetail(@PathVariable String orderNo) {
        var order = queryOrderUseCase.getOrderDetail(orderNo);
        var items = queryOrderUseCase.getOrderItems(order.getId());
        return Result.success(OrderVO.from(order, items));
    }

    /**
     * 修改订单接口
     *
     * @param orderNo 订单号
     * @param request 修改订单请求体（包含收货地址、备注）
     * @return 统一返回结构
     */
    @PutMapping("/{orderNo}")
    public Result<Void> modifyOrder(@PathVariable String orderNo, @Valid @RequestBody ModifyOrderRequest request) {
        // TODO: 实现修改订单逻辑
        return Result.success();
    }

    /**
     * 取消订单接口
     *
     * @param orderNo 订单号
     * @param request 取消订单请求体（包含取消原因）
     * @return 统一返回结构
     */
    @PutMapping("/{orderNo}/cancel")
    public Result<Void> cancelOrder(@PathVariable String orderNo, @Valid @RequestBody CancelOrderRequest request) {
        // TODO: 实现取消订单逻辑
        return Result.success();
    }

    /**
     * 发起支付接口
     *
     * @param orderNo 订单号
     * @return 统一返回结构，data 为支付参数
     */
    @PostMapping("/{orderNo}/pay")
    public Result<Object> payOrder(@PathVariable String orderNo) {
        // TODO: 实现支付逻辑
        return Result.success();
    }
}
