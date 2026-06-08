package com.example.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短信日志实体
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Data
@TableName("sms_log")
public class SmsLog {

    /**
     * 日志 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 模板代码
     */
    private String templateCode;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 发送状态：0-失败，1-成功
     */
    private Integer status;

    /**
     * 发送时间
     */
    private LocalDateTime sentAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
