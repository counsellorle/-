package com.example.order.repository;

import com.example.order.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单商品明细仓储接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Mapper
public interface OrderItemRepository {

    /**
     * 根据订单 ID 查询商品明细
     *
     * @param orderId 订单 ID
     * @return 商品明细列表
     */
    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    /**
     * 批量保存商品明细
     *
     * @param items 商品明细列表
     * @return 是否成功
     */
    int batchSave(List<OrderItem> items);
}
