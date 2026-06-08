package com.example.order.repository;

import com.example.order.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * 商品仓储接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Mapper
public interface ProductRepository {

    /**
     * 根据 ID 查询商品
     *
     * @param id 商品 ID
     * @return 商品
     */
    @Select("SELECT * FROM product WHERE id = #{id}")
    Optional<Product> findById(@Param("id") Long id);

    /**
     * 查询所有上架商品
     *
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE status = 1 ORDER BY created_at DESC")
    List<Product> findAllOnShelf();

    /**
     * 保存商品
     *
     * @param product 商品
     * @return 是否成功
     */
    int save(Product product);

    /**
     * 更新商品
     *
     * @param product 商品
     * @return 是否成功
     */
    int update(Product product);

    /**
     * 删除商品
     *
     * @param id 商品 ID
     * @return 是否成功
     */
    int delete(@Param("id") Long id);
}
