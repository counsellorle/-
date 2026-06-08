<template>
  <div class="orders-container">
    <el-header class="page-header">
      <h2>我的订单</h2>
      <div class="header-actions">
        <el-button @click="router.push('/products')">商品列表</el-button>
        <el-button @click="handleLogout">退出登录</el-button>
      </div>
    </el-header>

    <el-main>
      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="全部" :name="''" />
        <el-tab-pane label="待支付" :name="1" />
        <el-tab-pane label="已提交" :name="2" />
        <el-tab-pane label="商家确认" :name="3" />
        <el-tab-pane label="制作中" :name="4" />
        <el-tab-pane label="已发货" :name="5" />
        <el-tab-pane label="已完成" :name="6" />
      </el-tabs>

      <el-table :data="orders" style="width: 100%" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" />
        <el-table-column prop="totalAmount" label="订单金额">
          <template #default="{ row }">
            ¥ {{ row.totalAmount }}
          </template>
        </el-table-column>
        <el-table-column prop="orderStatus" label="订单状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.orderStatus)">
              {{ getStatusText(row.orderStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="paymentStatus" label="支付状态">
          <template #default="{ row }">
            <el-tag :type="getPaymentStatusType(row.paymentStatus)">
              {{ getPaymentStatusText(row.paymentStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="viewDetail(row)">查看详情</el-button>
            <el-button 
              v-if="row.orderStatus === 1" 
              link 
              type="danger" 
              @click="cancelOrder(row)"
            >
              取消订单
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-empty v-if="orders.length === 0" description="暂无订单" />
    </el-main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getOrderListApi, cancelOrderApi } from '@/api/order'
import { useUserStore } from '@/stores/user'
import type { Order } from '@/types'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const activeTab = ref('')
const orders = ref<Order[]>([])

const getStatusType = (status: number) => {
  const types: Record<number, any> = {
    1: 'warning',
    2: 'info',
    3: 'primary',
    4: 'warning',
    5: 'success',
    6: 'success'
  }
  return types[status] || 'info'
}

const getStatusText = (status: number) => {
  const texts: Record<number, string> = {
    1: '待支付',
    2: '已提交',
    3: '商家确认',
    4: '制作中',
    5: '已发货',
    6: '已完成'
  }
  return texts[status] || '未知'
}

const getPaymentStatusType = (status: number) => {
  const types: Record<number, any> = {
    0: 'warning',
    1: 'success',
    2: 'info'
  }
  return types[status] || 'info'
}

const getPaymentStatusText = (status: number) => {
  const texts: Record<number, string> = {
    0: '未支付',
    1: '已支付',
    2: '已退款'
  }
  return texts[status] || '未知'
}

const loadOrders = async () => {
  loading.value = true
  try {
    const params = activeTab.value ? { status: Number(activeTab.value) } : {}
    const res = await getOrderListApi(params)
    orders.value = res.data
  } catch (error) {
    console.error('加载订单列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleTabChange = () => {
  loadOrders()
}

const viewDetail = (order: Order) => {
  router.push(`/orders/${order.orderNo}`)
}

const cancelOrder = async (order: Order) => {
  try {
    await ElMessageBox.prompt('请输入取消原因', '取消订单', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: /.+/,
      inputErrorMessage: '请输入取消原因'
    }).then(async ({ value }) => {
      await cancelOrderApi(order.orderNo, value)
      ElMessage.success('订单已取消')
      loadOrders()
    })
  } catch (error) {
    if (error !== 'cancel') {
      console.error('取消订单失败:', error)
    }
  }
}

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
  ElMessage.success('已退出登录')
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped lang="scss">
.orders-container {
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

:deep(.el-tabs) {
  margin-bottom: 20px;
  background: #fff;
  padding: 15px 20px;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  
  .el-tabs__item {
    font-size: 15px;
    font-weight: 500;
    padding: 10px 20px;
    
    &.is-active {
      color: #667eea;
      font-weight: 600;
    }
  }
  
  .el-tabs__active-bar {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    height: 3px;
  }
}

:deep(.el-table) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  
  th {
    background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
    color: #2c3e50;
    font-weight: 600;
    font-size: 15px;
  }
  
  td {
    padding: 14px 0;
  }
  
  .el-button {
    font-weight: 500;
    
    &:hover {
      transform: translateY(-1px);
    }
  }
}

:deep(.el-tag) {
  padding: 6px 12px;
  border-radius: 6px;
  font-weight: 500;
  font-size: 13px;
}

:deep(.el-empty) {
  padding: 60px 0;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}
</style>
