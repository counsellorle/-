# 数据库版本：V1.0.0
# 创建时间：2026-06-08
# 描述：初始化数据库表结构

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户 ID',
    `phone` VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号',
    `password` VARCHAR(100) NOT NULL COMMENT '密码（BCrypt 加密）',
    `role` TINYINT NOT NULL DEFAULT 1 COMMENT '角色：1-客户，2-商家',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_phone` (`phone`),
    INDEX `idx_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商品表
CREATE TABLE IF NOT EXISTS `product` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品 ID',
    `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `description` TEXT COMMENT '商品描述',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 订单表
CREATE TABLE IF NOT EXISTS `order` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单 ID',
    `order_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户 ID',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    `order_status` TINYINT NOT NULL DEFAULT 1 COMMENT '订单状态：1-待支付，2-已提交，3-商家确认，4-制作中，5-已发货，6-已完成',
    `payment_status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态：0-未支付，1-已支付，2-已退款',
    `payment_method` TINYINT DEFAULT NULL COMMENT '支付方式：1-微信，2-支付宝，3-银行转账',
    `receiver_address` VARCHAR(255) NOT NULL COMMENT '收货地址',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `expected_finish_time` DATETIME DEFAULT NULL COMMENT '预计完成时间',
    `logistics_no` VARCHAR(50) DEFAULT NULL COMMENT '物流单号',
    `cancel_reason` VARCHAR(255) DEFAULT NULL COMMENT '取消原因',
    `paid_at` DATETIME DEFAULT NULL COMMENT '支付时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_order_no` (`order_no`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_order_status` (`order_status`),
    INDEX `idx_payment_status` (`payment_status`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单商品明细表
CREATE TABLE IF NOT EXISTS `order_item` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单项 ID',
    `order_id` BIGINT NOT NULL COMMENT '订单 ID',
    `product_id` BIGINT NOT NULL COMMENT '商品 ID',
    `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
    `unit_price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `quantity` INT NOT NULL COMMENT '数量',
    `subtotal` DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_order_id` (`order_id`),
    INDEX `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品明细表';

-- 短信日志表
CREATE TABLE IF NOT EXISTS `sms_log` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志 ID',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `template_code` VARCHAR(50) NOT NULL COMMENT '模板代码',
    `content` VARCHAR(500) NOT NULL COMMENT '短信内容',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '发送状态：0-失败，1-成功',
    `sent_at` DATETIME NOT NULL COMMENT '发送时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_phone` (`phone`),
    INDEX `idx_sent_at` (`sent_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信日志表';
