# 企业级订单管理系统

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.x-green.svg)](https://vuejs.org/)
[![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.5-red.svg)](https://baomidou.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-orange.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📖 项目简介

基于 **Spring Boot 3.x** 和 **Vue 3** 的企业级订单管理系统，支持客户下单、商家管理、订单状态跟踪、支付处理等核心功能。系统采用前后端分离架构，遵循 DDD（领域驱动设计）原则，实现了完整的订单生命周期管理。

### ✨ 主要特性

- 👥 **双角色支持**：客户（下单、查看订单）和商家（管理订单、商品）
- 📦 **订单全流程**：待支付 → 已提交 → 商家确认 → 制作中 → 已发货 → 已完成
- 💳 **支付集成**：支持超时自动取消、退款处理
- 📱 **短信通知**：订单状态变更自动发送短信
- 🔒 **安全认证**：JWT Token + Spring Security
- 📊 **实时跟踪**：订单进度实时可视化
- 🧪 **高测试覆盖**：51 个自动化测试，100% 通过率

---

## 🛠️ 技术栈

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| **Java** | 17 | 编程语言 |
| **Spring Boot** | 3.x | 核心框架 |
| **Spring Security** | 6.x | 安全认证 |
| **MyBatis-Plus** | 3.5.5 | ORM 框架 |
| **MySQL** | 8.0+ | 关系型数据库 |
| **Redis** | 7.x | 缓存（可选） |
| **RabbitMQ** | 3.x | 消息队列（可选） |
| **Flyway** | 9.x | 数据库迁移 |
| **Hutool** | 5.x | 工具类库 |
| **Lombok** | 1.18.x | 代码简化 |
| **Mockito** | 5.x | 单元测试 |
| **JUnit 5** | 5.x | 测试框架 |

### 前端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| **Vue** | 3.x | 渐进式框架 |
| **TypeScript** | 5.x | 类型系统 |
| **Vite** | 4.x | 构建工具 |
| **Element Plus** | 2.x | UI 组件库 |
| **Pinia** | 2.x | 状态管理 |
| **Vue Router** | 4.x | 路由管理 |
| **Axios** | 1.x | HTTP 客户端 |
| **Sass** | 1.x | CSS 预处理器 |

---

## 📁 项目结构

```
order-management-system/
├── backend/                          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/order/
│   │   │   │   ├── OrderApplication.java       # 启动类
│   │   │   │   ├── common/                     # 通用类（结果、异常）
│   │   │   │   ├── config/                     # 配置类（安全、拦截器）
│   │   │   │   ├── controller/                 # 控制器层
│   │   │   │   ├── dto/                        # 数据传输对象
│   │   │   │   ├── entity/                     # 实体类
│   │   │   │   ├── enums/                      # 枚举类型
│   │   │   │   ├── handler/                    # 异常处理器
│   │   │   │   ├── interceptor/                # 拦截器
│   │   │   │   ├── mapper/                     # MyBatis Mapper
│   │   │   │   ├── repository/                 # 仓储接口
│   │   │   │   ├── security/                   # 安全相关（JWT）
│   │   │   │   ├── service/                    # 领域服务
│   │   │   │   ├── usecase/                    # 应用用例
│   │   │   │   ├── util/                       # 工具类
│   │   │   │   └── vo/                         # 视图对象
│   │   │   └── resources/
│   │   │       ├── application.yml             # 配置文件
│   │   │       ├── db/migration/               # 数据库迁移脚本
│   │   │       └── mapper/                     # MyBatis XML
│   │   └── test/                               # 测试代码
│   ├── pom.xml                                 # Maven 配置
│   └── Dockerfile                              # Docker 配置
├── frontend/                         # 前端项目
│   ├── src/
│   │   ├── api/                      # API 调用
│   │   ├── assets/                   # 静态资源
│   │   ├── components/               # 组件
│   │   ├── router/                   # 路由配置
│   │   ├── stores/                   # 状态管理
│   │   ├── styles/                   # 全局样式
│   │   ├── types/                    # TypeScript 类型
│   │   ├── utils/                    # 工具函数
│   │   ├── views/                    # 页面视图
│   │   │   ├── admin/                # 商家管理页面
│   │   │   ├── Login.vue             # 登录页
│   │   │   ├── Register.vue          # 注册页
│   │   │   └── ...
│   │   ├── App.vue                   # 根组件
│   │   └── main.ts                   # 入口文件
│   ├── package.json                  # 依赖配置
│   ├── tsconfig.json                 # TS 配置
│   ├── vite.config.ts                # Vite 配置
│   └── Dockerfile                    # Docker 配置
├── specs/                            # 项目文档
│   ├── spec.md                       # 需求规格说明书
│   ├── plan.md                       # 技术方案
│   ├── tasks.md                      # 任务列表
│   └── test-report.md                # 测试报告
├── AGENTS.md                         # 代码注释规范
├── docker-compose.yml                # Docker 编排
├── .gitignore                        # Git 忽略文件
└── README.md                         # 项目说明
```

---

## 🚀 快速开始

### 环境要求

- **JDK**: 17+
- **Node.js**: 18+
- **MySQL**: 8.0+
- **Maven**: 3.8+
- **npm**: 9+

### 1. 克隆项目

```bash
git clone https://github.com/counsellorle/-.git
cd order-management-system
```

### 2. 数据库配置

#### 创建数据库

```sql
CREATE DATABASE order_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 修改数据库配置

编辑 `backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/order_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password  # 修改为您的数据库密码
```

### 3. 后端启动

```bash
cd backend

# 编译项目
mvn clean install

# 运行应用
mvn spring-boot:run
```

启动成功后会看到：
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.x.x)

OrderApplication started successfully!
```

### 4. 前端启动

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

启动成功后会看到：
```
  VITE v4.x.x  ready in xxx ms

  ➜  Local:   http://localhost:3000/
  ➜  Network: use --host to expose
```

### 5. 访问系统

浏览器访问：http://localhost:3000

**默认测试账号：**
- 客户账号：`13800138000` / `password123`
- 商家账号：`13900139000` / `password123`

---

## 📋 功能模块

### 客户功能

- ✅ 用户注册/登录（手机号 + 密码、手机号 + 验证码）
- ✅ 商品列表浏览
- ✅ 创建订单（选择商品、填写数量、收货地址）
- ✅ 查看个人订单列表
- ✅ 查看订单详情（订单状态、支付状态、物流信息）
- ✅ 取消订单（制作完成前）
- ✅ 修改订单（制作完成前）

### 商家功能

- ✅ 商品管理（创建、编辑、上下架）
- ✅ 查看所有客户订单
- ✅ 订单确认（设置预计完成时间）
- ✅ 订单状态推进（确认 → 制作中 → 已发货 → 已完成）
- ✅ 订单筛选（按状态、客户、时间）
- ✅ 发货管理（填写物流单号）

### 系统功能

- ✅ 支付超时自动取消（30 分钟）
- ✅ 订单状态变更短信通知
- ✅ JWT Token 认证
- ✅ 角色权限控制
- ✅ 统一异常处理
- ✅ 统一响应格式

---

## 🧪 测试

### 后端测试

```bash
cd backend

# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=AuthUseCaseTest

# 生成测试覆盖率报告
mvn clean test jacoco:report
```

**测试结果：**
```
Tests run: 51, Failures: 0, Errors: 0, Skipped: 0
```

测试覆盖：
- ✅ 实体类测试（User, Product, Order, SmsLog）
- ✅ 领域服务测试（OrderDomainService, PaymentDomainService）
- ✅ 应用用例测试（AuthUseCase, ProductUseCase, CreateOrderUseCase）
- ✅ 控制器测试（AuthController, ProductController, OrderController）
- ✅ 集成测试

### 前端测试

```bash
cd frontend

# 运行单元测试
npm run test

# 运行 E2E 测试
npm run test:e2e
```

---

## 📊 API 文档

### 认证接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 注册 | POST | `/api/auth/register` | 用户注册 |
| 密码登录 | POST | `/api/auth/login` | 手机号 + 密码登录 |
| 验证码登录 | POST | `/api/auth/sms-login` | 手机号 + 验证码登录 |
| 发送验证码 | POST | `/api/auth/sms-code` | 发送短信验证码 |

### 商品接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 商品列表 | GET | `/api/products` | 获取商品列表 |
| 商品详情 | GET | `/api/products/{id}` | 获取商品详情 |
| 创建商品 | POST | `/api/products` | 创建商品（商家） |
| 更新商品 | PUT | `/api/products/{id}` | 更新商品（商家） |

### 订单接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 创建订单 | POST | `/api/orders` | 创建新订单 |
| 订单列表 | GET | `/api/orders` | 获取个人订单列表 |
| 订单详情 | GET | `/api/orders/{orderNo}` | 获取订单详情 |
| 取消订单 | PUT | `/api/orders/{orderNo}/cancel` | 取消订单 |
| 修改订单 | PUT | `/api/orders/{orderNo}/modify` | 修改订单 |

### 商家订单接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 订单列表 | GET | `/api/admin/orders` | 获取所有订单 |
| 订单详情 | GET | `/api/admin/orders/{orderNo}` | 获取订单详情 |
| 确认订单 | PUT | `/api/admin/orders/{orderNo}/confirm` | 确认订单 |
| 推进状态 | PUT | `/api/admin/orders/{orderNo}/advance` | 推进订单状态 |
| 发货 | PUT | `/api/admin/orders/{orderNo}/ship` | 订单发货 |

---

## 🗂️ 数据库设计

### 核心表结构

#### 1. user（用户表）

```sql
CREATE TABLE `user` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL UNIQUE COMMENT '手机号',
  `password` varchar(255) NOT NULL COMMENT '密码（加密）',
  `role` int NOT NULL DEFAULT 1 COMMENT '角色：1-客户，2-商家',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='用户表';
```

#### 2. product（商品表）

```sql
CREATE TABLE `product` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '单价',
  `stock` int NOT NULL DEFAULT 0 COMMENT '库存',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态：0-下架，1-上架',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='商品表';
```

#### 3. order（订单表）

```sql
CREATE TABLE `order` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL UNIQUE COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户 ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `order_status` int NOT NULL DEFAULT 0 COMMENT '订单状态：0-待支付，1-已提交，2-商家确认，3-制作中，4-已发货，5-已完成，6-已取消',
  `payment_status` int NOT NULL DEFAULT 0 COMMENT '支付状态：0-未支付，1-已支付，2-已退款',
  `payment_method` int COMMENT '支付方式：1-微信，2-支付宝，3-银行卡',
  `receiver_address` varchar(255) NOT NULL COMMENT '收货地址',
  `remark` varchar(500) COMMENT '备注',
  `cancel_reason` varchar(255) COMMENT '取消原因',
  `expected_finish_time` datetime COMMENT '预计完成时间',
  `tracking_no` varchar(64) COMMENT '物流单号',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT='订单表';
```

#### 4. order_item（订单项表）

```sql
CREATE TABLE `order_item` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `order_id` bigint NOT NULL COMMENT '订单 ID',
  `product_id` bigint NOT NULL COMMENT '商品 ID',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称（快照）',
  `quantity` int NOT NULL COMMENT '数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '单价（快照）',
  `total_price` decimal(10,2) NOT NULL COMMENT '小计金额'
) COMMENT='订单项表';
```

#### 5. sms_log（短信日志表）

```sql
CREATE TABLE `sms_log` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `template_code` varchar(50) NOT NULL COMMENT '模板代码',
  `content` varchar(500) NOT NULL COMMENT '短信内容',
  `status` int NOT NULL DEFAULT 0 COMMENT '发送状态：0-失败，1-成功',
  `sent_at` datetime COMMENT '发送时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) COMMENT='短信日志表';
```

---

## 🐳 Docker 部署

### 使用 Docker Compose 启动

```bash
# 启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

### 服务列表

| 服务 | 端口 | 说明 |
|------|------|------|
| **mysql** | 3306 | MySQL 数据库 |
| **backend** | 8080 | Spring Boot 后端 |
| **frontend** | 3000 | Vue 前端 |

---

## 📝 开发规范

### 代码注释规范

本项目严格遵循 [`AGENTS.md`](AGENTS.md) 中的注释规范：

- ✅ 所有类必须添加 JavaDoc 注释
- ✅ 所有接口必须添加 JavaDoc 注释
- ✅ 所有 public 方法必须添加 JavaDoc 注释
- ✅ Controller 接口需要额外说明用途、参数、返回结构

示例：

```java
/**
 * 创建队伍接口。
 *
 * <p>用途：前端提交创建队伍信息，后端完成参数校验、落库，并返回新队伍 id。</p>
 *
 * @param teamAddRequest 创建队伍请求体（包含队伍名称、人数上限、过期时间、状态等）
 * @param request        Http 请求对象（用于获取当前登录用户）
 * @return 统一返回结构，data 为新创建的队伍 id
 * @throws BusinessException 参数错误 / 未登录 / 业务校验不通过时抛出
 */
@PostMapping("/add")
public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request) {
    ...
}
```

### 分层架构

```
Controller 层（API 接口）
    ↓
UseCase 层（应用用例）
    ↓
Service 层（领域服务）
    ↓
Repository 层（仓储接口）
    ↓
Mapper 层（数据访问）
```

---

## 📊 项目进度

### 当前状态：Alpha 版本（69.6% 完成）

#### ✅ 已完成

- Phase 1: 基础框架搭建（95.6%）
- Phase 2: 领域模型与测试（100%）
- Phase 3: 应用用例（75%）
- Phase 4: API 接口（80%）
- Phase 5: 基础设施（66.7%）
- Phase 6: 前端 UI（37.5%）

#### ⏳ 待完成

- [ ] 订单取消功能实现
- [ ] 订单状态推进功能实现
- [ ] 支付回调处理实现
- [ ] 前端页面完善
- [ ] 短信服务集成
- [ ] 生产环境配置
- [ ] Docker 部署优化

详细进度请查看 [`specs/test-report.md`](specs/test-report.md)

---

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

---

## 📄 开源协议

本项目采用 [MIT License](LICENSE) 开源协议。

---

## 👥 作者

- **counsellorle** - [GitHub](https://github.com/counsellorle)

---

## 📧 联系方式

如有问题或建议，欢迎通过以下方式联系：

- 📧 Email: 2014173164@qq.com
- 💬 Issues: [GitHub Issues](https://github.com/counsellorle/-/issues)

---

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Vue.js](https://vuejs.org/)
- [Element Plus](https://element-plus.org/)
- [MyBatis-Plus](https://baomidou.com/)
- [Vite](https://vitejs.dev/)

---

**Made with ❤️ by counsellorle**
