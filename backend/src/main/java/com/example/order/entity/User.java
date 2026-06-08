package com.example.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@TableName("user")
public class User {

    /**
     * 用户 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码（BCrypt 加密）
     */
    private String password;

    /**
     * 角色：1-客户，2-商家
     */
    private Integer role;

    /**
     * 头像 URL
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
