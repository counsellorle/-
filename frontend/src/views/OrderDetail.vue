<template>
  <div class="order-detail-container">
    <el-header class="page-header">
      <h2>订单详情</h2>
      <div class="header-actions">
        <el-button @click="router.push('/orders')">返回订单列表</el-button>
      </div>
    </el-header>

    <el-main v-loading="loading">
      <el-card v-if="order">
        <template #header>
          <div class="card-header">
            <span>订单号：{{ order.orderNo }}</span>
            <el-tag :type="getStatusType(order.orderStatus)">
              {{ getStatusText(order.orderStatus) }}
            </el-tag>
          </div>
        </template>

        <el-descriptions title="订单信息" :column="2" border>
          <el-descriptions-item label="订单金额">¥ {{ order.totalAmount }}</el-descriptions-item>
          <el-descriptions-item label="支付状态">
            <el-tag :type="getPaymentStatusType(order.paymentStatus)">
              {{ getPaymentStatusText(order.paymentStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="收货地址" :span="2">{{ order.receiverAddress }}</el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">{{ order.remark || '无' }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ order.createdAt }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ order.updatedAt }}</el-descriptions-item>
          <el-descriptions-item v-if="order.expectedFinishTime" label="预计完成时间" :span="2">
            {{ order.expectedFinishTime }}
          </el-descriptions-item>
          <el-descriptions-item v-if="order.logisticsNo" label="物流单号" :span="2">
            {{ order.logisticsNo }}
          </el-descriptions-item>
        </el-descriptions>

        <el-divider>商品明细</el-divider>

        <el-table :data="order.items || []" style="width: 100%">
          <el-table-column prop="productName" label="商品名称" />
          <el-table-column prop="unitPrice" label="单价">
            <template #default="{ row }">¥ {{ row.unitPrice }}</template>
          </el-table-column>
          <el-table-column prop="quantity" label="数量" />
          <el-table-column prop="subtotal" label="小计">
            <template #default="{ row }">¥ {{ row.subtotal }}</template>
          </el-table-column>
        </el-table>

        <div class="action-buttons" v-if="order.orderStatus === 1 && order.paymentStatus === 0">
          <el-button type="primary" @click="handlePay">立即支付</el-button>
          <el-button type="danger" @click="handleCancel">取消订单</el-button>
        </div>
      </el-card>
    </el-main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getOrderDetailApi, cancelOrderApi, payOrderApi } from '@/api/order'
import type { Order } from '@/types'

const router = useRouter()
const route = useRoute()

const loading = ref(false)
const order = ref<Order | null>(null)

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

const loadOrderDetail = async () => {
  loading.value = true
  try {
    const orderNo = route.params.orderNo as string
    const res = await getOrderDetailApi(orderNo)
    order.value = res.data
  } catch (error) {
    console.error('加载订单详情失败:', error)
  } finally {
    loading.value = false
  }
}

const handlePay = async () => {
  try {
    await payOrderApi(order.value!.orderNo)
    ElMessage.success('支付成功')
    loadOrderDetail()
  } catch (error) {
    console.error('支付失败:', error)
  }
}

const handleCancel = async () => {
  try {
    await ElMessageBox.prompt('请输入取消原因', '取消订单', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: /.+/,
      inputErrorMessage: '请输入取消原因'
    }).then(async ({ value }) => {
      await cancelOrderApi(order.value!.orderNo, value)
      ElMessage.success('订单已取消')
      loadOrderDetail()
    })
  } catch (error) {
    if (error !== 'cancel') {
      console.error('取消订单失败:', error)
    }
  }
}

onMounted(() => {
  loadOrderDetail()
})
</script>

<style scoped lang="scss">
.order-detail-container {
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

:deep(.el-card) {
  margin-bottom: 20px;
  border-radius: 12px;
  border: none;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  
  &:hover {
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
    transition: all 0.3s ease;
  }
}

:deep(.el-card__header) {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  padding: 18px 24px;
  border-bottom: 2px solid #e0e0e0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  padding: 14px 0;
  border-bottom: 1px solid #f0f0f0;
  
  &:last-child {
    border-bottom: none;
  }
  
  .label {
    color: #666;
    font-size: 14px;
    font-weight: 500;
  }
  
  .value {
    color: #333;
    font-size: 14px;
    font-weight: 600;
  }
}

.action-buttons {
  margin-top: 25px;
  text-align: center;
  padding: 20px 0;
  
  button {
    margin: 0 10px;
    min-width: 120px;
    height: 42px;
    font-size: 15px;
    font-weight: 500;
    
    &.pay-btn {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      
      &:hover {
        opacity: 0.9;
        transform: translateY(-2px);
        box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
      }
    }
    
    &.cancel-btn {
      background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
      border: none;
      color: #fff;
      
      &:hover {
        opacity: 0.9;
        transform: translateY(-2px);
        box-shadow: 0 6px 16px rgba(255, 107, 107, 0.4);
      }
    }
  }
}

:deep(.el-tag) {
  padding: 6px 12px;
  border-radius: 6px;
  font-weight: 500;
  font-size: 13px;
}

:deep(.el-descriptions) {
  .el-descriptions__label {
    font-weight: 500;
    color: #666;
  }
  
  .el-descriptions__content {
    font-weight: 600;
    color: #333;
  }
}
</style>
