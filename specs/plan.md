# 企业级订单管理系统 技术方案

---

## 1. 技术栈选型

| 层级 | 技术选型 | 说明 |
|------|----------|------|
| **前端** | Vue 3 + TypeScript + Vite | 响应式 SPA，类型安全 |
| **UI 组件库** | Element Plus | 企业级 UI 组件 |
| **状态管理** | Pinia | 轻量级状态管理 |
| **路由** | Vue Router 4 | 前端路由 |
| **HTTP 客户端** | Axios | 请求拦截、响应处理 |
| **后端** | Java 17 + Spring Boot 3.x | 企业级后端框架 |
| **ORM** | MyBatis-Plus | 简化数据库操作 |
| **数据库** | MySQL 8.0+ | 关系型数据库 |
| **缓存** | Redis | 缓存、分布式锁、倒计时 |
| **消息队列** | RabbitMQ / Redis Stream | 异步任务（短信、超时取消） |
| **认证** | JWT + Spring Security | Token 认证 |
| **短信服务** | 阿里云短信 / 腾讯云短信 | 短信通知 |
| **支付** | 支付宝沙箱 / 微信支付沙箱 | 支付接口（开发环境用沙箱） |
| **部署** | Docker + Docker Compose | 容器化部署 |

---

## 2. 系统架构

```
┌─────────────────────────────────────────────────────────┐
│                        前端层                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │   客户端    │  │   商家端    │  │  登录注册页  │     │
│  │  (Vue3)     │  │  (Vue3)     │  │  (Vue3)     │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
└─────────────────────────────────────────────────────────┘
                         │ HTTPS
┌─────────────────────────────────────────────────────────┐
│                      API 网关层                          │
│              (Spring Security + JWT 过滤)                │
└─────────────────────────────────────────────────────────┘
                         │
┌─────────────────────────────────────────────────────────┐
│                     应用服务层                           │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐   │
│  │ 用户服务 │ │ 商品服务 │ │ 订单服务 │ │ 支付服务 │   │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘   │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐                │
│  │ 短信服务 │ │ 物流服务 │ │ 定时任务 │                │
│  └──────────┘ └──────────┘ └──────────┘                │
└─────────────────────────────────────────────────────────┘
                         │
┌─────────────────────────────────────────────────────────┐
│                      数据访问层                          │
│              (MyBatis-Plus + Redis)                      │
└─────────────────────────────────────────────────────────┘
                         │
┌─────────────────────────────────────────────────────────┐
│                       存储层                             │
│         ┌──────────┐  ┌──────────┐  ┌──────────┐       │
│         │  MySQL   │  │  Redis   │  │  消息队列 │       │
│         └──────────┘  └──────────┘  └──────────┘       │
└─────────────────────────────────────────────────────────┘
```

---

## 3. 数据库设计

### 3.1 用户表 (t_user)

| 字段 | 类型 | 说明 | 约束 |
|------|------|------|------|
| id | BIGINT | 主键 | PK |
| phone | VARCHAR(11) | 手机号 | UNIQUE, NOT NULL |
| password | VARCHAR(255) | 加密密码 | NOT NULL |
| role | TINYINT | 角色：1-客户，2-商家 | NOT NULL |
| created_at | DATETIME | 创建时间 | NOT NULL |
| updated_at | DATETIME | 更新时间 | NOT NULL |

### 3.2 商品表 (t_product)

| 字段 | 类型 | 说明 | 约束 |
|------|------|------|------|
| id | BIGINT | 主键 | PK |
| name | VARCHAR(255) | 商品名称 | NOT NULL |
| price | DECIMAL(10,2) | 单价 | NOT NULL |
| description | TEXT | 商品描述 | NULL |
| status | TINYINT | 状态：1-上架，0-下架 | NOT NULL, DEFAULT 1 |
| created_at | DATETIME | 创建时间 | NOT NULL |
| updated_at | DATETIME | 更新时间 | NOT NULL |

### 3.3 订单表 (t_order)

| 字段 | 类型 | 说明 | 约束 |
|------|------|------|------|
| id | BIGINT | 主键 | PK |
| order_no | VARCHAR(32) | 订单编号 | UNIQUE, NOT NULL |
| user_id | BIGINT | 客户 ID | FK, NOT NULL |
| total_amount | DECIMAL(10,2) | 订单总金额 | NOT NULL |
| receiver_address | VARCHAR(500) | 收货地址 | NOT NULL |
| remark | VARCHAR(1000) | 备注 | NULL |
| order_status | TINYINT | 订单状态 | NOT NULL |
| payment_status | TINYINT | 支付状态 | NOT NULL |
| payment_method | TINYINT | 支付方式 | NULL |
| expected_finish_time | DATETIME | 预计完成时间 | NULL |
| logistics_no | VARCHAR(100) | 物流单号 | NULL |
| cancel_reason | VARCHAR(500) | 取消原因 | NULL |
| paid_at | DATETIME | 支付时间 | NULL |
| finished_at | DATETIME | 完成时间 | NULL |
| cancelled_at | DATETIME | 取消时间 | NULL |
| created_at | DATETIME | 创建时间 | NOT NULL |
| updated_at | DATETIME | 更新时间 | NOT NULL |

**订单状态枚举：**
- 0: 待支付
- 1: 已提交
- 2: 商家确认
- 3: 制作中
- 4: 已发货
- 5: 已完成
- 6: 已取消

**支付状态枚举：**
- 0: 未支付
- 1: 已支付
- 2: 已退款

**支付方式枚举：**
- 1: 微信
- 2: 支付宝
- 3: 银行转账

### 3.4 订单商品明细表 (t_order_item)

| 字段 | 类型 | 说明 | 约束 |
|------|------|------|------|
| id | BIGINT | 主键 | PK |
| order_id | BIGINT | 订单 ID | FK, NOT NULL |
| product_id | BIGINT | 商品 ID | FK, NOT NULL |
| product_name | VARCHAR(255) | 商品名称（快照） | NOT NULL |
| unit_price | DECIMAL(10,2) | 单价（快照） | NOT NULL |
| quantity | INT | 数量 | NOT NULL |
| subtotal | DECIMAL(10,2) | 小计金额 | NOT NULL |

### 3.5 短信发送记录表 (t_sms_log)

| 字段 | 类型 | 说明 | 约束 |
|------|------|------|------|
| id | BIGINT | 主键 | PK |
| phone | VARCHAR(11) | 手机号 | NOT NULL |
| template_code | VARCHAR(50) | 模板编码 | NOT NULL |
| content | VARCHAR(500) | 短信内容 | NOT NULL |
| status | TINYINT | 状态：0-发送中，1-成功，2-失败 | NOT NULL |
| sent_at | DATETIME | 发送时间 | NULL |
| created_at | DATETIME | 创建时间 | NOT NULL |

---

## 4. API 接口设计

### 4.1 认证模块

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| POST | /api/auth/register | 客户注册（手机号 + 密码） | 公开 |
| POST | /api/auth/login/password | 手机号 + 密码登录 | 公开 |
| POST | /api/auth/login/sms/send | 发送短信验证码 | 公开 |
| POST | /api/auth/login/sms | 手机号 + 验证码登录 | 公开 |
| POST | /api/auth/logout | 登出 | 登录用户 |

### 4.2 商品模块（商家）

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | /api/products | 商品列表（分页、筛选） | 公开 |
| POST | /api/products | 创建商品 | 商家 |
| PUT | /api/products/{id} | 更新商品 | 商家 |
| DELETE | /api/products/{id} | 删除商品 | 商家 |
| PUT | /api/products/{id}/status | 上下架商品 | 商家 |

### 4.3 订单模块（客户）

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | /api/orders | 我的订单列表（分页、筛选） | 客户 |
| GET | /api/orders/{id} | 订单详情 | 客户（本人订单） |
| POST | /api/orders | 创建订单 | 客户 |
| PUT | /api/orders/{id} | 修改订单 | 客户（本人订单，制作前） |
| PUT | /api/orders/{id}/cancel | 取消订单 | 客户（本人订单，制作前） |
| POST | /api/orders/{id}/pay | 发起支付 | 客户（本人订单，待支付） |

### 4.4 订单模块（商家）

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | /api/admin/orders | 所有订单列表（分页、筛选） | 商家 |
| GET | /api/admin/orders/{id} | 订单详情 | 商家 |
| PUT | /api/admin/orders/{id}/confirm | 确认订单 | 商家 |
| PUT | /api/admin/orders/{id}/produce | 推进至制作中 | 商家 |
| PUT | /api/admin/orders/{id}/ship | 发货（填物流单号） | 商家 |
| PUT | /api/admin/orders/{id}/complete | 标记已完成 | 商家 |

### 4.5 支付回调

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| POST | /api/pay/callback | 支付平台回调 | 公开（签名验证） |

---

## 5. 核心业务流程

### 5.1 下单流程

```
1. 客户选择商品 → 2. 填写地址/备注 → 3. 提交订单
   ↓
4. 生成订单号（雪花算法） → 5. 写入订单表 + 订单项表
   ↓
6. 设置 Redis 倒计时 key（30 分钟） → 7. 返回支付参数
   ↓
8. 客户端调起支付 → 9. 支付成功回调 → 10. 更新订单状态为"已提交"
   ↓
11. 发送短信通知（支付成功）
```

### 5.2 支付超时自动取消

```
方案：Redis Key 过期监听 + 消息队列

1. 创建订单时设置 Redis key: order:pay_timeout:{orderNo}，TTL=1800 秒
2. 配置 Redis keyspace notifications 监听 key 过期事件
3. 或使用 RabbitMQ 延迟队列：消息延迟 30 分钟消费
4. 消费者检查订单支付状态，未支付则更新为"已取消"，触发退款
5. 发送短信通知（订单取消）
```

### 5.3 订单状态推进

```
商家操作 → Controller → Service 校验当前状态 → 更新状态 → 发送短信

确认订单：已提交 → 商家确认（填写预计完成时间）
推进制作：商家确认 → 制作中
发货：制作中 → 已发货（填写物流单号）
完成：已发货 → 已完成
```

### 5.4 取消订单流程

```
1. 客户提交取消请求（含原因）
   ↓
2. 校验订单状态（仅"已提交"/"商家确认"可取消）
   ↓
3. 更新订单状态为"已取消"，支付状态为"已退款"
   ↓
4. 调用支付平台退款接口（异步）
   ↓
5. 发送短信通知（订单取消 + 退款）
```

### 5.5 短信通知触发

```
事件 → 发布领域事件 → 监听器消费 → 调用短信服务 → 写入短信日志表

触发点：
- 支付成功
- 商家确认
- 已发货
- 订单完成
- 订单取消
```

---

## 6. 项目结构

```
f:\ssd
├── specs/
│   ├── spec.md          # 需求规格说明书
│   └── plan.md          # 技术方案（本文档）
├── AGENTS.md            # 代码注释规范
├── frontend/            # 前端项目
│   ├── src/
│   │   ├── api/         # API 请求封装
│   │   ├── assets/      # 静态资源
│   │   ├── components/  # 公共组件
│   │   ├── views/       # 页面组件
│   │   │   ├── client/  # 客户端页面
│   │   │   ├── merchant/| 商家端页面
│   │   │   └── auth/    # 认证页面
│   │   ├── stores/      # Pinia 状态管理
│   │   ├── router/      # 路由配置
│   │   ├── utils/       # 工具函数
│   │   └── types/       # TypeScript 类型定义
│   └── package.json
└── backend/             # 后端项目
    ├── src/main/java/com/example/order/
    │   ├── controller/  # Controller 层
    │   ├── service/     # Service 层
    │   ├── mapper/      # MyBatis Mapper
    │   ├── entity/      # 实体类
    │   ├── dto/         # 数据传输对象
    │   ├── vo/          # 视图对象
    │   ├── config/      # 配置类
    │   ├── security/    # 安全相关
    │   ├── common/      # 公共类（统一返回、异常）
    │   └── listener/    # 事件监听器
    └── pom.xml
```

---

## 7. 安全设计

### 7.1 认证与授权

- JWT Token 认证，有效期 2 小时，支持刷新
- 密码 BCrypt 加密存储
- 短信验证码 Redis 存储，5 分钟过期，单次有效
- 接口权限控制：@PreAuthorize("hasRole('CUSTOMER')")

### 7.2 数据安全

- 订单查询强制校验用户 ID，防止越权
- 金额计算使用 BigDecimal，禁止浮点数
- SQL 防注入（MyBatis 参数化查询）
- 敏感接口限流（登录、支付、取消）

### 7.3 支付安全

- 支付回调验签
- 订单金额校验（防止篡改）
- 幂等性保证（订单号唯一）

---

## 8. 性能与可靠性

### 8.1 缓存策略

- 商品列表：Redis 缓存 5 分钟
- 订单详情：Redis 缓存 1 分钟（高频访问）
- 用户信息：Redis 缓存 30 分钟

### 8.2 数据库索引

```sql
-- 订单表
CREATE INDEX idx_user_id ON t_order(user_id);
CREATE INDEX idx_order_status ON t_order(order_status);
CREATE INDEX idx_created_at ON t_order(created_at);
CREATE INDEX idx_order_no ON t_order(order_no);

-- 订单商品明细表
CREATE INDEX idx_order_id ON t_order_item(order_id);
```

### 8.3 事务管理

- 创建订单：订单表 + 订单项表，同一事务
- 支付回调：更新订单状态 + 支付时间，同一事务
- 取消订单：更新订单状态 + 退款，同一事务

---

## 9. 开发计划与任务拆分

### Phase 1: 基础框架搭建（3 天）

- [ ] 初始化前后端项目
- [ ] 配置数据库、Redis、MyBatis-Plus
- [ ] 实现 JWT 认证 + Spring Security
- [ ] 统一返回结构、全局异常处理
- [ ] 用户注册/登录接口

### Phase 2: 商品管理（2 天）

- [ ] 商品 CRUD 接口（商家）
- [ ] 商品列表查询（公开）
- [ ] 前端商品管理页面（商家）

### Phase 3: 订单核心流程（5 天）

- [ ] 创建订单接口
- [ ] 订单列表/详情接口
- [ ] 修改/取消订单接口
- [ ] 订单状态推进接口（商家）
- [ ] 前端订单页面（客户端 + 商家端）

### Phase 4: 支付集成（3 天）

- [ ] 对接支付宝/微信沙箱
- [ ] 支付回调处理
- [ ] 退款接口
- [ ] 支付超时自动取消（Redis 延迟队列）

### Phase 5: 短信通知（2 天）

- [ ] 集成短信服务
- [ ] 事件驱动短信发送
- [ ] 短信日志记录

### Phase 6: 测试与优化（3 天）

- [ ] 单元测试（Service 层）
- [ ] 接口测试（Postman/Apifox）
- [ ] 前端 E2E 测试
- [ ] 性能优化（缓存、索引）

### Phase 7: 部署上线（2 天）

- [ ] Docker 镜像构建
- [ ] Docker Compose 编排
- [ ] 生产环境配置
- [ ] 日志监控

**总计：约 20 个工作日**

---

## 10. 风险与应对

| 风险 | 影响 | 应对措施 |
|------|------|----------|
| 支付沙箱不稳定 | 开发进度受阻 | 准备 Mock 支付服务 |
| 短信服务延迟 | 用户体验差 | 异步发送 + 失败重试 |
| 支付超时取消延迟 | 订单状态不一致 | 定时任务兜底扫描 |
| 并发下单 | 超卖/数据不一致 | 数据库乐观锁 + 分布式锁 |

---

## 11. 后续扩展方向

- 多商家支持（多租户）
- 订单导出 Excel
- 数据统计看板（订单量、销售额）
- 移动端适配（小程序/H5）
- 物流轨迹查询（对接快递 100）

---

> 本文档与 spec.md 强追踪，后续所有代码实现、接口设计、页面设计、测试用例必须基于本文档。任何变更需同步更新本文档。
