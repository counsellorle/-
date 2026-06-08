# 企业级订单管理系统 - 原子化任务列表

> **说明：**
> - `[P]` 标记表示可并行执行的任务
> - `→` 表示依赖关系（前置任务 → 当前任务）
> - 每个任务只涉及一个主要文件的修改或创建
> - TDD 铁律：测试任务必须在前，实现任务必须在后

---

## Phase 1: Foundation & Skeleton

### 1.1 后端项目骨架

- [ ] **Task 1.1.1** 创建后端项目目录结构 `backend/`
- [ ] **Task 1.1.2** 创建 `backend/pom.xml`（定义 Spring Boot 3.x、MyBatis-Plus、MySQL、Redis、JWT 等依赖）
- [ ] **Task 1.1.3** 创建 `backend/src/main/java/com/example/order/OrderApplication.java`（Spring Boot 启动类）
- [ ] **Task 1.1.4** 创建 `backend/src/main/resources/application.yml`（基础配置：应用名、端口、环境）
- [ ] **Task 1.1.5** 创建 `backend/src/main/resources/application-dev.yml`（开发环境配置：MySQL、Redis 连接）
- [ ] **Task 1.1.6** 创建 `backend/src/main/resources/application-prod.yml`（生产环境配置）
- [ ] **Task 1.1.7** 创建 `backend/src/test/java/com/example/order/OrderApplicationTests.java`（集成测试基类）

### 1.2 后端分层结构

- [ ] **[P] Task 1.2.1** 创建 `backend/src/main/java/com/example/order/controller/` 包
- [ ] **[P] Task 1.2.2** 创建 `backend/src/main/java/com/example/order/service/` 包
- [ ] **[P] Task 1.2.3** 创建 `backend/src/main/java/com/example/order/mapper/` 包
- [ ] **[P] Task 1.2.4** 创建 `backend/src/main/java/com/example/order/entity/` 包
- [ ] **[P] Task 1.2.5** 创建 `backend/src/main/java/example/order/dto/` 包
- [ ] **[P] Task 1.2.6** 创建 `backend/src/main/java/com/example/order/vo/` 包
- [ ] **[P] Task 1.2.7** 创建 `backend/src/main/java/com/example/order/config/` 包
- [ ] **[P] Task 1.2.8** 创建 `backend/src/main/java/com/example/order/security/` 包
- [ ] **[P] Task 1.2.9** 创建 `backend/src/main/java/com/example/order/common/` 包
- [ ] **[P] Task 1.2.10** 创建 `backend/src/main/java/com/example/order/listener/` 包
- [ ] **[P] Task 1.2.11** 创建 `backend/src/main/java/com/example/order/enums/` 包
- [ ] **[P] Task 1.2.12** 创建 `backend/src/main/resources/mapper/` 目录

### 1.3 公共类定义

- [ ] **Task 1.3.1** 创建 `backend/src/main/java/com/example/order/common/Result.java`（统一返回结构：code、message、data）
- [ ] **Task 1.3.2** 创建 `backend/src/main/java/com/example/order/common/ResultCode.java`（响应码枚举：SUCCESS、ERROR、UNAUTHORIZED 等）
- [ ] **Task 1.3.3** 创建 `backend/src/main/java/com/example/order/common/BusinessException.java`（业务异常类）
- [ ] **Task 1.3.4** 创建 `backend/src/main/java/com/example/order/common/GlobalExceptionHandler.java`（全局异常处理器）
- [ ] **Task 1.3.5** 创建 `backend/src/main/java/com/example/order/config/CorsConfig.java`（跨域配置）

### 1.4 日志配置

- [ ] **Task 1.4.1** 创建 `backend/src/main/resources/logback-spring.xml`（Logback 日志配置）
- [ ] **Task 1.4.2** 创建 `backend/src/main/resources/logback-dev.xml`（开发环境日志配置）

### 1.5 前端项目骨架

- [ ] **Task 1.5.1** 创建前端项目目录 `frontend/`
- [ ] **Task 1.5.2** 创建 `frontend/package.json`（定义 Vue 3、TypeScript、Vite、Element Plus、Pinia、Vue Router、Axios 等依赖）
- [ ] **Task 1.5.3** 创建 `frontend/vite.config.ts`（Vite 配置：代理、插件）
- [ ] **Task 1.5.4** 创建 `frontend/tsconfig.json`（TypeScript 配置）
- [ ] **Task 1.5.5** 创建 `frontend/index.html`（HTML 入口）
- [ ] **Task 1.5.6** 创建 `frontend/src/main.ts`（Vue 应用入口）
- [ ] **Task 1.5.7** 创建 `frontend/src/App.vue`（根组件）
- [ ] **Task 1.5.8** 创建 `frontend/src/style.css`（全局样式）

### 1.6 前端工程结构

- [ ] **[P] Task 1.6.1** 创建 `frontend/src/api/` 目录
- [ ] **[P] Task 1.6.2** 创建 `frontend/src/assets/` 目录
- [ ] **[P] Task 1.6.3** 创建 `frontend/src/components/` 目录
- [ ] **[P] Task 1.6.4** 创建 `frontend/src/views/` 目录
- [ ] **[P] Task 1.6.5** 创建 `frontend/src/views/client/` 目录（客户端页面）
- [ ] **[P] Task 1.6.6** 创建 `frontend/src/views/merchant/` 目录（商家端页面）
- [ ] **[P] Task 1.6.7** 创建 `frontend/src/views/auth/` 目录（认证页面）
- [ ] **[P] Task 1.6.8** 创建 `frontend/src/stores/` 目录（Pinia 状态管理）
- [ ] **[P] Task 1.6.9** 创建 `frontend/src/router/` 目录
- [ ] **[P] Task 1.6.10** 创建 `frontend/src/utils/` 目录
- [ ] **[P] Task 1.6.11** 创建 `frontend/src/types/` 目录

### 1.7 前端基础配置

- [ ] **Task 1.7.1** 创建 `frontend/src/utils/request.ts`（Axios 实例封装：请求拦截、响应拦截、错误处理）
- [ ] **Task 1.7.2** 创建 `frontend/src/types/index.ts`（公共类型定义）
- [ ] **Task 1.7.3** 创建 `frontend/src/router/index.ts`（路由配置骨架）
- [ ] **Task 1.7.4** 创建 `frontend/src/stores/user.ts`（用户状态管理骨架）

### 1.8 Docker 环境

- [ ] **Task 1.8.1** 创建 `docker-compose.yml`（MySQL、Redis、后端、前端编排）
- [ ] **Task 1.8.2** 创建 `backend/Dockerfile`（后端 Docker 镜像）
- [ ] **Task 1.8.3** 创建 `frontend/Dockerfile`（前端 Docker 镜像）
- [ ] **Task 1.8.4** 创建 `.env`（环境变量：数据库密码、JWT 密钥等）

---

## Phase 2: Domain Model & Domain Tests (TDD)

### 2.1 枚举定义

- [ ] **[P] Task 2.1.1** 创建 `backend/src/main/java/com/example/order/enums/OrderStatus.java`（订单状态枚举：待支付、已提交、商家确认、制作中、已发货、已完成、已取消）
- [ ] **[P] Task 2.1.2** 创建 `backend/src/main/java/com/example/order/enums/PaymentStatus.java`（支付状态枚举：未支付、已支付、已退款）
- [ ] **[P] Task 2.1.3** 创建 `backend/src/main/java/com/example/order/enums/PaymentMethod.java`（支付方式枚举：微信、支付宝、银行转账）
- [ ] **[P] Task 2.1.4** 创建 `backend/src/main/java/com/example/order/enums/UserRole.java`（用户角色枚举：客户、商家）
- [ ] **[P] Task 2.1.5** 创建 `backend/src/main/java/com/example/order/enums/ProductStatus.java`（商品状态枚举：上架、下架）

### 2.2 领域实体

- [ ] **Task 2.2.1** 创建 `backend/src/test/java/com/example/order/entity/UserTest.java`（用户实体测试）
- [ ] **Task 2.2.2** → 创建 `backend/src/main/java/com/example/order/entity/User.java`（用户实体：id、phone、password、role、createdAt、updatedAt）
- [ ] **Task 2.2.3** 创建 `backend/src/test/java/com/example/order/entity/ProductTest.java`（商品实体测试）
- [ ] **Task 2.2.4** → 创建 `backend/src/main/java/com/example/order/entity/Product.java`（商品实体：id、name、price、description、status、createdAt、updatedAt）
- [ ] **Task 2.2.5** 创建 `backend/src/test/java/com/example/order/entity/OrderTest.java`（订单实体测试）
- [ ] **Task 2.2.6** → 创建 `backend/src/main/java/com/example/order/entity/Order.java`（订单实体：id、orderNo、userId、totalAmount、receiverAddress、remark、orderStatus、paymentStatus、paymentMethod、expectedFinishTime、logisticsNo、cancelReason、paidAt、finishedAt、cancelledAt、createdAt、updatedAt）
- [ ] **Task 2.2.7** 创建 `backend/src/test/java/com/example/order/entity/OrderItemTest.java`（订单商品明细实体测试）
- [ ] **Task 2.2.8** → 创建 `backend/src/main/java/com/example/order/entity/OrderItem.java`（订单商品明细实体：id、orderId、productId、productName、unitPrice、quantity、subtotal）
- [ ] **Task 2.2.9** 创建 `backend/src/test/java/com/example/order/entity/SmsLogTest.java`（短信日志实体测试）
- [ ] **Task 2.2.10** → 创建 `backend/src/main/java/com/example/order/entity/SmsLog.java`（短信日志实体：id、phone、templateCode、content、status、sentAt、createdAt）

### 2.3 领域服务抽象

- [ ] **Task 2.3.1** 创建 `backend/src/test/java/com/example/order/service/OrderDomainServiceTest.java`（订单领域服务测试）
- [ ] **Task 2.3.2** → 创建 `backend/src/main/java/com/example/order/service/OrderDomainService.java`（订单领域服务：状态流转规则校验、取消规则校验、修改规则校验）
- [ ] **Task 2.3.3** 创建 `backend/src/test/java/com/example/order/service/PaymentDomainServiceTest.java`（支付领域服务测试）
- [ ] **Task 2.3.4** → 创建 `backend/src/main/java/com/example/order/service/PaymentDomainService.java`（支付领域服务：支付超时校验、退款规则校验）

### 2.4 仓储抽象

- [ ] **Task 2.4.1** 创建 `backend/src/main/java/com/example/order/repository/UserRepository.java`（用户仓储接口）
- [ ] **Task 2.4.2** 创建 `backend/src/main/java/com/example/order/repository/ProductRepository.java`（商品仓储接口）
- [ ] **Task 2.4.3** 创建 `backend/src/main/java/com/example/order/repository/OrderRepository.java`（订单仓储接口）
- [ ] **Task 2.4.4** 创建 `backend/src/main/java/com/example/order/repository/OrderItemRepository.java`（订单商品明细仓储接口）
- [ ] **Task 2.4.5** 创建 `backend/src/main/java/com/example/order/repository/SmsLogRepository.java`（短信日志仓储接口）

---

## Phase 3: Application Use Cases & Application Tests (TDD)

### 3.1 认证用例

- [ ] **Task 3.1.1** 创建 `backend/src/test/java/com/example/order/usecase/AuthUseCaseTest.java`（认证用例测试）
- [ ] **Task 3.1.2** → 创建 `backend/src/main/java/com/example/order/usecase/AuthUseCase.java`（认证用例：注册、密码登录、短信验证码登录、登出）
- [ ] **Task 3.1.3** 创建 `backend/src/main/java/com/example/order/dto/RegisterRequest.java`（注册请求 DTO）
- [ ] **Task 3.1.4** 创建 `backend/src/main/java/com/example/order/dto/LoginRequest.java`（登录请求 DTO）
- [ ] **Task 3.1.5** 创建 `backend/src/main/java/com/example/order/dto/SmsLoginRequest.java`（短信登录请求 DTO）
- [ ] **Task 3.1.6** 创建 `backend/src/main/java/com/example/order/vo/LoginVO.java`（登录响应 VO：token、userInfo）

### 3.2 商品管理用例

- [ ] **Task 3.2.1** 创建 `backend/src/test/java/com/example/order/usecase/ProductUseCaseTest.java`（商品用例测试）
- [ ] **Task 3.2.2** → 创建 `backend/src/main/java/com/example/order/usecase/ProductUseCase.java`（商品用例：创建、更新、删除、上下架、列表查询）
- [ ] **Task 3.2.3** 创建 `backend/src/main/java/com/example/order/dto/ProductCreateRequest.java`（商品创建请求 DTO）
- [ ] **Task 3.2.4** 创建 `backend/src/main/java/com/example/order/dto/ProductUpdateRequest.java`（商品更新请求 DTO）
- [ ] **Task 3.2.5** 创建 `backend/src/main/java/com/example/order/vo/ProductVO.java`（商品视图 VO）
- [ ] **Task 3.2.6** 创建 `backend/src/main/java/com/example/order/dto/ProductListQuery.java`（商品列表查询参数）

### 3.3 订单创建用例

- [ ] **Task 3.3.1** 创建 `backend/src/test/java/com/example/order/usecase/CreateOrderUseCaseTest.java`（创建订单用例测试）
- [ ] **Task 3.3.2** → 创建 `backend/src/main/java/com/example/order/usecase/CreateOrderUseCase.java`（创建订单用例：校验商品、计算金额、生成订单号、创建订单、设置支付超时）
- [ ] **Task 3.3.3** 创建 `backend/src/main/java/com/example/order/dto/CreateOrderRequest.java`（创建订单请求 DTO：商品列表、地址、备注、支付方式）
- [ ] **Task 3.3.4** 创建 `backend/src/main/java/com/example/order/vo/OrderCreateVO.java`（订单创建响应 VO：订单号、支付参数）
- [ ] **Task 3.3.5** 创建 `backend/src/main/java/com/example/order/service/OrderNumberGenerator.java`（订单号生成服务：雪花算法）

### 3.4 订单查询用例

- [ ] **Task 3.4.1** 创建 `backend/src/test/java/com/example/order/usecase/QueryOrderUseCaseTest.java`（订单查询用例测试）
- [ ] **Task 3.4.2** → 创建 `backend/src/main/java/com/example/order/usecase/QueryOrderUseCase.java`（订单查询用例：我的订单列表、订单详情、商家订单列表）
- [ ] **Task 3.4.3** 创建 `backend/src/main/java/com/example/order/dto/OrderListQuery.java`（订单列表查询参数：状态、日期范围、订单号、客户手机号）
- [ ] **Task 3.4.4** 创建 `backend/src/main/java/com/example/order/vo/OrderVO.java`（订单视图 VO：含商品明细、支付信息、物流信息）
- [ ] **Task 3.4.5** 创建 `backend/src/main/java/com/example/order/vo/OrderItemVO.java`（订单商品明细视图 VO）

### 3.5 订单修改用例

- [ ] **Task 3.5.1** 创建 `backend/src/test/java/com/example/order/usecase/ModifyOrderUseCaseTest.java`（订单修改用例测试）
- [ ] **Task 3.5.2** → 创建 `backend/src/main/java/com/example/order/usecase/ModifyOrderUseCase.java`（订单修改用例：修改地址、修改备注、修改数量）
- [ ] **Task 3.5.3** 创建 `backend/src/main/java/com/example/order/dto/ModifyOrderRequest.java`（订单修改请求 DTO）

### 3.6 订单取消用例

- [ ] **Task 3.6.1** 创建 `backend/src/test/java/com/example/order/usecase/CancelOrderUseCaseTest.java`（订单取消用例测试）
- [ ] **Task 3.6.2** → 创建 `backend/src/main/java/com/example/order/usecase/CancelOrderUseCase.java`（订单取消用例：校验取消条件、更新状态、触发退款）
- [ ] **Task 3.6.3** 创建 `backend/src/main/java/com/example/order/dto/CancelOrderRequest.java`（订单取消请求 DTO：取消原因）

### 3.7 订单状态推进用例（商家）

- [ ] **Task 3.7.1** 创建 `backend/src/test/java/com/example/order/usecase/OrderStatusAdvanceUseCaseTest.java`（订单状态推进用例测试）
- [ ] **Task 3.7.2** → 创建 `backend/src/main/java/com/example/order/usecase/OrderStatusAdvanceUseCase.java`（订单状态推进用例：确认、推进制作、发货、完成）
- [ ] **Task 3.7.3** 创建 `backend/src/main/java/com/example/order/dto/ConfirmOrderRequest.java`（确认订单请求 DTO：预计完成时间）
- [ ] **Task 3.7.4** 创建 `backend/src/main/java/com/example/order/dto/ShipOrderRequest.java`（发货请求 DTO：物流单号）

### 3.8 支付用例

- [ ] **Task 3.8.1** 创建 `backend/src/test/java/com/example/order/usecase/PaymentUseCaseTest.java`（支付用例测试）
- [ ] **Task 3.8.2** → 创建 `backend/src/main/java/com/example/order/usecase/PaymentUseCase.java`（支付用例：发起支付、处理回调、退款）
- [ ] **Task 3.8.3** 创建 `backend/src/main/java/com/example/order/vo/PayVO.java`（支付响应 VO：支付参数）
- [ ] **Task 3.8.4** 创建 `backend/src/main/java/com/example/order/dto/PayCallbackRequest.java`（支付回调请求 DTO）

### 3.9 短信通知用例

- [ ] **Task 3.9.1** 创建 `backend/src/test/java/com/example/order/usecase/SmsUseCaseTest.java`（短信用例测试）
- [ ] **Task 3.9.2** → 创建 `backend/src/main/java/com/example/order/usecase/SmsUseCase.java`（短信用例：发送验证码、发送通知）
- [ ] **Task 3.9.3** 创建 `backend/src/main/java/com/example/order/listener/OrderEventListener.java`（订单事件监听器：监听订单状态变更，触发短信发送）
- [ ] **Task 3.9.4** 创建 `backend/src/main/java/com/example/order/event/OrderPaidEvent.java`（订单支付事件）
- [ ] **Task 3.9.5** 创建 `backend/src/main/java/com/example/order/event/OrderConfirmedEvent.java`（订单确认事件）
- [ ] **Task 3.9.6** 创建 `backend/src/main/java/com/example/order/event/OrderShippedEvent.java`（订单发货事件）
- [ ] **Task 3.9.7** 创建 `backend/src/main/java/com/example/order/event/OrderCompletedEvent.java`（订单完成事件）
- [ ] **Task 3.9.8** 创建 `backend/src/main/java/com/example/order/event/OrderCancelledEvent.java`（订单取消事件）

### 3.10 支付超时处理用例

- [ ] **Task 3.10.1** 创建 `backend/src/test/java/com/example/order/usecase/PaymentTimeoutUseCaseTest.java`（支付超时用例测试）
- [ ] **Task 3.10.2** → 创建 `backend/src/main/java/com/example/order/usecase/PaymentTimeoutUseCase.java`（支付超时用例：监听 Redis 过期、取消订单）
- [ ] **Task 3.10.3** 创建 `backend/src/main/java/com/example/order/config/RedisConfig.java`（Redis 配置：开启 keyspace notifications）
- [ ] **Task 3.10.4** 创建 `backend/src/main/java/com/example/order/listener/RedisKeyExpiredListener.java`（Redis Key 过期监听器）

---

## Phase 4: API Contracts & Web API (TDD)

### 4.1 认证接口

- [ ] **Task 4.1.1** 创建 `backend/src/test/java/com/example/order/controller/AuthControllerTest.java`（认证接口测试）
- [ ] **Task 4.1.2** → 创建 `backend/src/main/java/com/example/order/controller/AuthController.java`（认证 Controller：注册、登录、登出、发送验证码）
- [ ] **Task 4.1.3** 创建 `backend/src/main/java/com/example/order/vo/UserInfoVO.java`（用户信息 VO）

### 4.2 商品接口

- [ ] **Task 4.2.1** 创建 `backend/src/test/java/com/example/order/controller/ProductControllerTest.java`（商品接口测试）
- [ ] **Task 4.2.2** → 创建 `backend/src/main/java/com/example/order/controller/ProductController.java`（商品 Controller：CRUD、上下架、列表查询）

### 4.3 订单接口（客户）

- [ ] **Task 4.3.1** 创建 `backend/src/test/java/com/example/order/controller/OrderControllerTest.java`（订单接口测试）
- [ ] **Task 4.3.2** → 创建 `backend/src/main/java/com/example/order/controller/OrderController.java`（订单 Controller：创建、查询、修改、取消、支付）

### 4.4 订单接口（商家）

- [ ] **Task 4.4.1** 创建 `backend/src/test/java/com/example/order/controller/AdminOrderControllerTest.java`（商家订单接口测试）
- [ ] **Task 4.4.2** → 创建 `backend/src/main/java/com/example/order/controller/AdminOrderController.java`（商家订单 Controller：查询所有订单、确认、推进、发货、完成）

### 4.5 支付回调接口

- [ ] **Task 4.5.1** 创建 `backend/src/test/java/com/example/order/controller/PayCallbackControllerTest.java`（支付回调接口测试）
- [ ] **Task 4.5.2** → 创建 `backend/src/main/java/com/example/order/controller/PayCallbackController.java`（支付回调 Controller：处理支付宝/微信回调）

### 4.6 请求校验

- [ ] **Task 4.6.1** 创建 `backend/src/main/java/com/example/order/config/ValidationConfig.java`（参数校验配置）
- [ ] **Task 4.6.2** 更新所有请求 DTO，添加 `@NotNull`、`@NotBlank`、`@DecimalMin` 等校验注解

### 4.7 接口文档

- [ ] **Task 4.7.1** 创建 `backend/src/main/java/com/example/order/config/SwaggerConfig.java`（Swagger/OpenAPI 配置）

---

## Phase 5: Infrastructure & Integration

### 5.1 MyBatis-Plus 配置

- [ ] **Task 5.1.1** 创建 `backend/src/main/java/com/example/order/config/MybatisPlusConfig.java`（MyBatis-Plus 配置：分页插件、乐观锁）
- [ ] **Task 5.1.2** 创建 `backend/src/main/java/com/example/order/entity/BaseEntity.java`（基础实体：id、createdAt、updatedAt）

### 5.2 数据库表创建

- [ ] **Task 5.2.1** 创建 `backend/src/main/resources/db/schema.sql`（数据库表结构 SQL）
- [ ] **Task 5.2.2** 创建 `backend/src/main/resources/db/data.sql`（初始化数据 SQL：默认商家账号）

### 5.3 仓储实现

- [ ] **Task 5.3.1** 创建 `backend/src/main/java/com/example/order/mapper/UserMapper.java`（用户 Mapper）
- [ ] **Task 5.3.2** 创建 `backend/src/main/java/com/example/order/mapper/ProductMapper.java`（商品 Mapper）
- [ ] **Task 5.3.3** 创建 `backend/src/main/java/com/example/order/mapper/OrderMapper.java`（订单 Mapper）
- [ ] **Task 5.3.4** 创建 `backend/src/main/java/com/example/order/mapper/OrderItemMapper.java`（订单商品明细 Mapper）
- [ ] **Task 5.3.5** 创建 `backend/src/main/java/com/example/order/mapper/SmsLogMapper.java`（短信日志 Mapper）
- [ ] **Task 5.3.6** 创建 `backend/src/main/resources/mapper/UserMapper.xml`（用户 XML 映射）
- [ ] **Task 5.3.7** 创建 `backend/src/main/resources/mapper/ProductMapper.xml`（商品 XML 映射）
- [ ] **Task 5.3.8** 创建 `backend/src/main/resources/mapper/OrderMapper.xml`（订单 XML 映射）
- [ ] **Task 5.3.9** 创建 `backend/src/main/resources/mapper/OrderItemMapper.xml`（订单商品明细 XML 映射）
- [ ] **Task 5.3.10** 创建 `backend/src/main/resources/mapper/SmsLogMapper.xml`（短信日志 XML 映射）

### 5.4 仓储实现（具体类）

- [ ] **Task 5.4.1** 创建 `backend/src/main/java/com/example/order/repository/impl/UserRepositoryImpl.java`
- [ ] **Task 5.4.2** 创建 `backend/src/main/java/com/example/order/repository/impl/ProductRepositoryImpl.java`
- [ ] **Task 5.4.3** 创建 `backend/src/main/java/com/example/order/repository/impl/OrderRepositoryImpl.java`
- [ ] **Task 5.4.4** 创建 `backend/src/main/java/com/example/order/repository/impl/OrderItemRepositoryImpl.java`
- [ ] **Task 5.4.5** 创建 `backend/src/main/java/com/example/order/repository/impl/SmsLogRepositoryImpl.java`

### 5.5 安全认证实现

- [ ] **Task 5.5.1** 创建 `backend/src/main/java/com/example/order/security/JwtTokenProvider.java`（JWT Token 生成与校验）
- [ ] **Task 5.5.2** 创建 `backend/src/main/java/com/example/order/security/JwtAuthenticationFilter.java`（JWT 认证过滤器）
- [ ] **Task 5.5.3** 创建 `backend/src/main/java/com/example/order/security/UserDetailsImpl.java`（Spring Security UserDetails 实现）
- [ ] **Task 5.5.4** 创建 `backend/src/main/java/com/example/order/security/UserDetailsServiceImpl.java`（UserDetailsService 实现）
- [ ] **Task 5.5.5** 创建 `backend/src/main/java/com/example/order/config/SecurityConfig.java`（Spring Security 配置）
- [ ] **Task 5.5.6** 创建 `backend/src/main/java/com/example/order/annotation/RequireRole.java`（角色权限注解）
- [ ] **Task 5.5.7** 创建 `backend/src/main/java/com/example/order/aspect/RequireRoleAspect.java`（角色权限切面）

### 5.6 短信服务实现

- [ ] **Task 5.6.1** 创建 `backend/src/main/java/com/example/order/service/SmsService.java`（短信服务接口）
- [ ] **Task 5.6.2** 创建 `backend/src/main/java/com/example/order/service/impl/SmsServiceImpl.java`（短信服务实现：阿里云/腾讯云）
- [ ] **Task 5.6.3** 创建 `backend/src/main/java/com/example/order/config/SmsConfig.java`（短信服务配置）

### 5.7 支付服务实现

- [ ] **Task 5.7.1** 创建 `backend/src/main/java/com/example/order/service/PaymentService.java`（支付服务接口）
- [ ] **Task 5.7.2** 创建 `backend/src/main/java/com/example/order/service/impl/PaymentServiceImpl.java`（支付服务实现：支付宝沙箱）
- [ ] **Task 5.7.3** 创建 `backend/src/main/java/com/example/order/config/PaymentConfig.java`（支付服务配置）

### 5.8 集成测试支撑

- [ ] **Task 5.8.1** 创建 `backend/src/test/java/com/example/order/BaseIntegrationTest.java`（集成测试基类：测试容器、数据清理）
- [ ] **Task 5.8.2** 创建 `backend/src/test/resources/application-test.yml`（测试环境配置）

---

## Phase 6: Frontend UI & Interaction

### 6.1 路由与布局

- [ ] **Task 6.1.1** 创建 `frontend/src/router/guards.ts`（路由守卫：登录校验、角色校验）
- [ ] **Task 6.1.2** 创建 `frontend/src/views/Layout.vue`（主布局：侧边栏、顶栏、内容区）
- [ ] **Task 6.1.3** 创建 `frontend/src/views/client/Layout.vue`（客户端布局）
- [ ] **Task 6.1.4** 创建 `frontend/src/views/merchant/Layout.vue`（商家端布局）

### 6.2 认证页面

- [ ] **Task 6.2.1** 创建 `frontend/src/views/auth/Login.vue`（登录页：手机号 + 密码、手机号 + 验证码）
- [ ] **Task 6.2.2** 创建 `frontend/src/views/auth/Register.vue`（注册页）
- [ ] **Task 6.2.3** 创建 `frontend/src/api/auth.ts`（认证 API Service）

### 6.3 商品管理页面（商家）

- [ ] **Task 6.3.1** 创建 `frontend/src/views/merchant/ProductList.vue`（商品列表页）
- [ ] **Task 6.3.2** 创建 `frontend/src/views/merchant/ProductForm.vue`（商品创建/编辑表单）
- [ ] **Task 6.3.3** 创建 `frontend/src/api/product.ts`（商品 API Service）

### 6.4 商品选择页面（客户）

- [ ] **Task 6.4.1** 创建 `frontend/src/views/client/ProductList.vue`（商品浏览页）
- [ ] **Task 6.4.2** 创建 `frontend/src/views/client/ShoppingCart.vue`（购物车/选商品页）

### 6.5 订单创建页面（客户）

- [ ] **Task 6.5.1** 创建 `frontend/src/views/client/CreateOrder.vue`（创建订单页：确认商品、填写地址、备注）
- [ ] **Task 6.5.2** 创建 `frontend/src/views/client/Pay.vue`（支付页）
- [ ] **Task 6.5.3** 创建 `frontend/src/api/order.ts`（订单 API Service）

### 6.6 订单列表页面（客户）

- [ ] **Task 6.6.1** 创建 `frontend/src/views/client/OrderList.vue`（我的订单列表页：筛选、搜索）
- [ ] **Task 6.6.2** 创建 `frontend/src/views/client/OrderDetail.vue`（订单详情页）
- [ ] **Task 6.6.3** 创建 `frontend/src/views/client/ModifyOrder.vue`（修改订单页）

### 6.7 订单管理页面（商家）

- [ ] **Task 6.7.1** 创建 `frontend/src/views/merchant/OrderList.vue`（商家订单列表页：筛选、搜索、按客户搜索）
- [ ] **Task 6.7.2** 创建 `frontend/src/views/merchant/OrderDetail.vue`（商家订单详情页）
- [ ] **Task 6.7.3** 创建 `frontend/src/views/merchant/ConfirmOrder.vue`（确认订单页：填写预计完成时间）
- [ ] **Task 6.7.4** 创建 `frontend/src/views/merchant/ShipOrder.vue`（发货页：填写物流单号）

### 6.8 前端状态管理

- [ ] **Task 6.8.1** 创建 `frontend/src/stores/product.ts`（商品状态管理）
- [ ] **Task 6.8.2** 创建 `frontend/src/stores/order.ts`（订单状态管理）

### 6.9 前端工具与组件

- [ ] **Task 6.9.1** 创建 `frontend/src/utils/formatters.ts`（格式化工具：金额、时间、状态）
- [ ] **Task 6.9.2** 创建 `frontend/src/components/OrderStatusTag.vue`（订单状态标签组件）
- [ ] **Task 6.9.3** 创建 `frontend/src/components/PaymentStatusTag.vue`（支付状态标签组件）
- [ ] **Task 6.9.4** 创建 `frontend/src/components/DateTimePicker.vue`（日期时间选择组件）

### 6.10 前端联调

- [ ] **Task 6.10.1** 配置 `frontend/vite.config.ts` 代理指向后端开发服务器
- [ ] **Task 6.10.2** 创建 `frontend/.env.development`（开发环境变量）
- [ ] **Task 6.10.3** 创建 `frontend/.env.production`（生产环境变量）

---

## 任务依赖关系总结

```
Phase 1 (Foundation)
  ↓
Phase 2 (Domain Model) → 依赖 Phase 1 的包结构
  ↓
Phase 3 (Use Cases) → 依赖 Phase 2 的实体和仓储抽象
  ↓
Phase 4 (API) → 依赖 Phase 3 的用例
  ↓
Phase 5 (Infrastructure) → 依赖 Phase 2/3 的抽象
  ↓
Phase 6 (Frontend) → 依赖 Phase 4 的 API
```

---

## 任务统计

| Phase | 任务数 | 测试任务数 | 实现任务数 |
|-------|--------|-----------|-----------|
| Phase 1 | 38 | 1 | 37 |
| Phase 2 | 24 | 5 | 19 |
| Phase 3 | 40 | 10 | 30 |
| Phase 4 | 11 | 5 | 6 |
| Phase 5 | 27 | 2 | 25 |
| Phase 6 | 26 | 0 | 26 |
| **总计** | **166** | **23** | **143** |

---

> **TDD 铁律：** 所有标记为测试的任务必须先于对应的实现任务执行。AI 在执行时应严格遵循此顺序。
