package com.example.order.repository;

import com.example.order.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * 用户仓储接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Mapper
public interface UserRepository {

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户
     */
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    Optional<User> findByPhone(@Param("phone") String phone);

    /**
     * 根据 ID 查询用户
     *
     * @param id 用户 ID
     * @return 用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    Optional<User> findById(@Param("id") Long id);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 是否成功
     */
    int save(User user);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 是否成功
     */
    int update(User user);
}
