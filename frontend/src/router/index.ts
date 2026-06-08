import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
      meta: { title: '登录' }
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/views/Register.vue'),
      meta: { title: '注册' }
    },
    {
      path: '/',
      redirect: '/products',
      meta: {}
    },
    {
      path: '/products',
      name: 'Products',
      component: () => import('@/views/Products.vue'),
      meta: { title: '商品列表' }
    },
    {
      path: '/orders',
      name: 'Orders',
      component: () => import('@/views/Orders.vue'),
      meta: { title: '我的订单', requiresAuth: true }
    },
    {
      path: '/orders/:orderNo',
      name: 'OrderDetail',
      component: () => import('@/views/OrderDetail.vue'),
      meta: { title: '订单详情', requiresAuth: true }
    },
    {
      path: '/create-order',
      name: 'CreateOrder',
      component: () => import('@/views/CreateOrder.vue'),
      meta: { title: '创建订单', requiresAuth: true }
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('@/views/admin/Dashboard.vue'),
      meta: { title: '商家后台', requiresAuth: true, requiresMerchant: true }
    },
    {
      path: '/admin/orders',
      name: 'AdminOrders',
      component: () => import('@/views/admin/Orders.vue'),
      meta: { title: '订单管理', requiresAuth: true, requiresMerchant: true }
    },
    {
      path: '/admin/products',
      name: 'AdminProducts',
      component: () => import('@/views/admin/Products.vue'),
      meta: { title: '商品管理', requiresAuth: true, requiresMerchant: true }
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  // 设置页面标题
  document.title = `${to.meta.title} - 企业级订单管理系统`
  
  // 检查是否需要登录
  if (to.meta.requiresAuth) {
    if (!userStore.isLoggedIn) {
      next({ name: 'Login', query: { redirect: to.fullPath } })
      return
    }
    
    // 检查是否需要商家角色
    if (to.meta.requiresMerchant && !userStore.isMerchant) {
      next({ name: 'Products' })
      return
    }
  }
  
  next()
})

export default router
