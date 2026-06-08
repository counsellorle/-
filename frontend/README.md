# 企业级订单管理系统 - 前端

## 技术栈

- Vue 3.4 + TypeScript
- Vite 5.2
- Pinia (状态管理)
- Vue Router 4 (路由)
- Element Plus (UI 组件库)
- Axios (HTTP 客户端)
- Sass (CSS 预处理器)

## 快速开始

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

访问 http://localhost:3000

### 构建生产版本

```bash
npm run build
```

### 代码检查

```bash
npm run lint
```

## 项目结构

```
src/
├── api/              # API 接口
│   ├── auth.ts      # 认证相关
│   ├── product.ts   # 商品相关
│   └── order.ts     # 订单相关
├── router/           # 路由配置
├── stores/           # Pinia 状态管理
├── styles/           # 全局样式
├── types/            # TypeScript 类型定义
├── utils/            # 工具函数
└── views/            # 页面组件
    ├── admin/       # 商家后台页面
    └── ...          # 客户端页面
```

## 功能模块

### 客户端
- 用户注册/登录
- 商品列表浏览
- 创建订单
- 订单列表
- 订单详情
- 订单取消

### 商家端
- 订单管理（确认、制作、发货、完成）
- 商品管理（CRUD、上下架）

## 环境配置

开发环境配置在 `.env` 文件中，默认代理到后端服务 `http://localhost:8080`

## 注意事项

1. 确保后端服务已启动
2. 首次使用需要注册账号
3. 商家功能需要注册时选择商家角色
