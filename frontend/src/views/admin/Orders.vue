<template>
  <div class="admin-orders">
    <h2>订单管理</h2>

    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="全部" :name="''" />
      <el-tab-pane label="待处理" :name="2" />
      <el-tab-pane label="制作中" :name="4" />
      <el-tab-pane label="已发货" :name="5" />
      <el-tab-pane label="已完成" :name="6" />
    </el-tabs>

    <el-table :data="orders" style="width: 100%" v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" />
      <el-table-column prop="userId" label="用户 ID" />
      <el-table-column prop="totalAmount" label="订单金额">
        <template #default="{ row }">¥ {{ row.totalAmount }}</template>
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
      <el-table-column label="操作" width="300">
        <template #default="{ row }">
          <el-button link type="primary" @click="viewDetail(row)">查看详情</el-button>
          <el-button 
            v-if="row.orderStatus === 2" 
            link 
            type="success" 
            @click="confirmOrder(row)"
          >
            确认订单
          </el-button>
          <el-button 
            v-if="row.orderStatus === 3" 
            link 
            type="warning" 
            @click="produceOrder(row)"
          >
            制作中
          </el-button>
          <el-button 
            v-if="row.orderStatus === 4" 
            link 
            type="primary" 
            @click="shipOrder(row)"
          >
            发货
          </el-button>
          <el-button 
            v-if="row.orderStatus === 5" 
            link 
            type="success" 
            @click="completeOrder(row)"
          >
            完成
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-empty v-if="orders.length === 0" description="暂无订单" />

    <!-- 确认订单对话框 -->
    <el-dialog v-model="confirmDialogVisible" title="确认订单" width="400px">
      <el-form :model="confirmForm" label-width="100px">
        <el-form-item label="预计完成时间" required>
          <el-date-picker
            v-model="confirmForm.expectedFinishTime"
            type="datetime"
            placeholder="选择预计完成时间"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="confirmDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleConfirmOrder">确定</el-button>
      </template>
    </el-dialog>

    <!-- 发货对话框 -->
    <el-dialog v-model="shipDialogVisible" title="发货" width="400px">
      <el-form :model="shipForm" label-width="80px">
        <el-form-item label="物流单号" required>
          <el-input v-model="shipForm.logisticsNo" placeholder="请输入物流单号" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleShipOrder">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getAdminOrderListApi, confirmOrderApi, produceOrderApi, shipOrderApi, completeOrderApi } from '@/api/order'
import type { Order } from '@/types'

const router = useRouter()

const loading = ref(false)
const activeTab = ref('')
const orders = ref<Order[]>([])

const confirmDialogVisible = ref(false)
const confirmForm = reactive({
  orderNo: '',
  expectedFinishTime: ''
})

const shipDialogVisible = ref(false)
const shipForm = reactive({
  orderNo: '',
  logisticsNo: ''
})

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
    const res = await getAdminOrderListApi(params)
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

const confirmOrder = (order: Order) => {
  confirmForm.orderNo = order.orderNo
  confirmForm.expectedFinishTime = ''
  confirmDialogVisible.value = true
}

const handleConfirmOrder = async () => {
  if (!confirmForm.expectedFinishTime) {
    ElMessage.warning('请选择预计完成时间')
    return
  }
  
  try {
    await confirmOrderApi(confirmForm.orderNo, confirmForm.expectedFinishTime)
    ElMessage.success('订单已确认')
    confirmDialogVisible.value = false
    loadOrders()
  } catch (error) {
    console.error('确认订单失败:', error)
  }
}

const produceOrder = async (order: Order) => {
  try {
    await produceOrderApi(order.orderNo)
    ElMessage.success('订单已推进至制作中')
    loadOrders()
  } catch (error) {
    console.error('推进订单失败:', error)
  }
}

const shipOrder = (order: Order) => {
  shipForm.orderNo = order.orderNo
  shipForm.logisticsNo = ''
  shipDialogVisible.value = true
}

const handleShipOrder = async () => {
  if (!shipForm.logisticsNo) {
    ElMessage.warning('请输入物流单号')
    return
  }
  
  try {
    await shipOrderApi(shipForm.orderNo, shipForm.logisticsNo)
    ElMessage.success('订单已发货')
    shipDialogVisible.value = false
    loadOrders()
  } catch (error) {
    console.error('发货失败:', error)
  }
}

const completeOrder = async (order: Order) => {
  try {
    await completeOrderApi(order.orderNo)
    ElMessage.success('订单已完成')
    loadOrders()
  } catch (error) {
    console.error('完成订单失败:', error)
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped lang="scss">
.admin-orders {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  
  h2 {
    margin: 0;
    color: #fff;
    font-size: 24px;
    font-weight: 600;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  }
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  
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

:deep(.el-dialog) {
  border-radius: 16px;
  overflow: hidden;
  
  .el-dialog__header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 20px 24px;
    
    .el-dialog__title {
      color: #fff;
      font-weight: 600;
    }
    
    .el-dialog__close {
      color: #fff;
      
      &:hover {
        opacity: 0.8;
      }
    }
  }
}
</style>
