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
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  
  h2 {
    margin: 0;
    color: #fff;
    font-size: 24px;
    font-weight: 600;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  }
  
  .header-actions {
    display: flex;
    gap: 12px;
    
    .el-button {
      background: rgba(255, 255, 255, 0.2);
      border: 1px solid rgba(255, 255, 255, 0.4);
      color: #fff;
      
      &:hover {
        background: rgba(255, 255, 255, 0.3);
        transform: translateY(-1px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      }
    }
  }
}

.el-main {
  padding: 30px 40px;
}

.product-card {
  margin-bottom: 25px;
  border-radius: 15px;
  overflow: hidden;
  border: none;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
  }
  
  .product-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 20px;
    background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  }
  
  .product-name {
    font-size: 18px;
    font-weight: 600;
    color: #2c3e50;
    flex: 1;
  }
  
  .product-info {
    padding: 20px;
    min-height: 120px;
  }
  
  .product-price {
    font-size: 26px;
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    font-weight: 700;
    margin-bottom: 12px;
  }
  
  .product-desc {
    font-size: 14px;
    color: #546e7a;
    line-height: 1.6;
    height: 44px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
  
  .buy-btn {
    width: 100%;
    height: 42px;
    font-size: 15px;
    font-weight: 500;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    border-radius: 8px;
    
    &:hover:not(:disabled) {
      opacity: 0.9;
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
    }
    
    &:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
}

:deep(.el-card__header) {
  padding: 0;
  border-bottom: 1px solid #e0e0e0;
}

:deep(.el-card__footer) {
  padding: 15px 20px;
  background: #fafbfc;
}

:deep(.el-tag) {
  padding: 6px 12px;
  border-radius: 6px;
  font-weight: 500;
}
</style>
