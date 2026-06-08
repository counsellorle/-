package com.example.order.repository;

import com.example.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * 订单仓储接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Mapper
public interface OrderRepository {

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 订单号
     * @return 订单
     */
    @Select("SELECT * FROM `order` WHERE order_no = #{orderNo}")
    Optional<Order> findByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据 ID 查询订单
     *
     * @param id 订单 ID
     * @return 订单
     */
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Optional<Order> findById(@Param("id") Long id);

    /**
     * 保存订单
     *
     * @param order 订单
     * @return 是否成功
     */
    int save(Order order);

    /**
     * 更新订单
     *
     * @param order 订单
     * @return 是否成功
     */
    int update(Order order);
}
