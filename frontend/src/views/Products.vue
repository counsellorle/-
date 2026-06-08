<template>
  <div class="products-container">
    <el-header class="page-header">
      <h2>商品列表</h2>
      <div class="header-actions">
        <el-button @click="router.push('/orders')">我的订单</el-button>
        <el-button @click="handleLogout">退出登录</el-button>
      </div>
    </el-header>

    <el-main>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="product in products" :key="product.id">
          <el-card class="product-card" shadow="hover">
            <template #header>
              <div class="product-header">
                <span class="product-name">{{ product.name }}</span>
                <el-tag :type="product.status === 1 ? 'success' : 'info'">
                  {{ product.status === 1 ? '上架中' : '已下架' }}
                </el-tag>
              </div>
            </template>

            <div class="product-info">
              <p class="product-price">¥ {{ product.price }}</p>
              <p class="product-desc">{{ product.description || '暂无描述' }}</p>
            </div>

            <template #footer>
              <el-button 
                type="primary" 
                :disabled="product.status !== 1"
                @click="handleBuy(product)"
                class="buy-btn"
              >
                购买
              </el-button>
            </template>
          </el-card>
        </el-col>
      </el-row>

      <el-empty v-if="products.length === 0" description="暂无商品" />
    </el-main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getProductListApi } from '@/api/product'
import { useUserStore } from '@/stores/user'
import type { Product } from '@/types'

const router = useRouter()
const userStore = useUserStore()

const products = ref<Product[]>([])

const loadProducts = async () => {
  try {
    const res = await getProductListApi()
    products.value = res.data || []
  } catch (error) {
    console.error('加载商品列表失败:', error)
    products.value = []
  }
}

const handleBuy = (product: Product) => {
  // 跳转到创建订单页面，并传递商品信息
  router.push({
    path: '/create-order',
    query: { productId: product.id, quantity: 1 }
  })
}

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
  ElMessage.success('已退出登录')
}

onMounted(() => {
  loadProducts()
})
</script>

<style scoped lang="scss">
.products-container {
  min-height: 100vh;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  
  h2 {
    margin: 0;
    color: #333;
  }
  
  .header-actions {
    display: flex;
    gap: 10px;
  }
}

.el-main {
  padding: 20px;
}

.product-card {
  margin-bottom: 20px;
  
  .product-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .product-name {
    font-size: 16px;
    font-weight: bold;
    color: #333;
  }
  
  .product-info {
    padding: 10px 0;
  }
  
  .product-price {
    font-size: 20px;
    color: $danger-color;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .product-desc {
    font-size: 14px;
    color: #666;
    height: 40px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
  
  .buy-btn {
    width: 100%;
  }
}
</style>
