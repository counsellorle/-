<template>
  <div class="create-order-container">
    <el-header class="page-header">
      <h2>创建订单</h2>
      <div class="header-actions">
        <el-button @click="router.push('/products')">返回商品列表</el-button>
      </div>
    </el-header>

    <el-main>
      <el-card>
        <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
          <el-form-item label="商品列表">
            <el-table :data="cartItems" style="width: 100%">
              <el-table-column prop="productName" label="商品名称" />
              <el-table-column prop="unitPrice" label="单价">
                <template #default="{ row }">¥ {{ row.unitPrice }}</template>
              </el-table-column>
              <el-table-column prop="quantity" label="数量">
                <template #default="{ row }">
                  <el-input-number v-model="row.quantity" :min="1" :max="99" @change="calculateTotal" />
                </template>
              </el-table-column>
              <el-table-column prop="subtotal" label="小计">
                <template #default="{ row }">¥ {{ row.subtotal }}</template>
              </el-table-column>
            </el-table>
          </el-form-item>

          <el-form-item label="收货地址" prop="receiverAddress">
            <el-input v-model="form.receiverAddress" type="textarea" :rows="3" placeholder="请输入收货地址" />
          </el-form-item>

          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注（可选）" />
          </el-form-item>

          <el-form-item label="支付方式" prop="paymentMethod">
            <el-radio-group v-model="form.paymentMethod">
              <el-radio :label="1">微信支付</el-radio>
              <el-radio :label="2">支付宝</el-radio>
              <el-radio :label="3">银行转账</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item>
            <div class="total-amount">订单总金额：<span class="amount">¥ {{ totalAmount }}</span></div>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" :loading="loading" @click="handleSubmit">提交订单</el-button>
            <el-button @click="router.push('/products')">取消</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getProductDetailApi } from '@/api/product'
import { createOrderApi } from '@/api/order'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const route = useRoute()

const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({
  receiverAddress: '',
  remark: '',
  paymentMethod: 1
})

const cartItems = ref<any[]>([])
const totalAmount = ref(0)

const rules: FormRules = {
  receiverAddress: [
    { required: true, message: '请输入收货地址', trigger: 'blur' }
  ],
  paymentMethod: [
    { required: true, message: '请选择支付方式', trigger: 'change' }
  ]
}

const calculateTotal = () => {
  totalAmount.value = cartItems.value.reduce((sum, item) => sum + item.subtotal, 0)
}

const loadProduct = async () => {
  const productId = route.query.productId as string
  const quantity = Number(route.query.quantity) || 1
  
  if (productId) {
    try {
      const res = await getProductDetailApi(Number(productId))
      const product = res.data
      cartItems.value.push({
        productId: product.id,
        productName: product.name,
        unitPrice: product.price,
        quantity: quantity,
        subtotal: product.price * quantity
      })
      calculateTotal()
    } catch (error) {
      console.error('加载商品信息失败:', error)
    }
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    if (cartItems.value.length === 0) {
      ElMessage.warning('请选择商品')
      return
    }
    
    loading.value = true
    try {
      const items = cartItems.value.map(item => ({
        productId: item.productId,
        quantity: item.quantity
      }))
      
      const res = await createOrderApi({
        items,
        receiverAddress: form.receiverAddress,
        remark: form.remark,
        paymentMethod: form.paymentMethod
      })
      
      ElMessage.success('订单创建成功')
      
      // 跳转到订单详情页
      router.push(`/orders/${res.data.orderNo}`)
    } catch (error) {
      console.error('创建订单失败:', error)
    } finally {
      loading.value = false
    }
  })
}

onMounted(() => {
  loadProduct()
})
</script>

<style scoped lang="scss">
.create-order-container {
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

.total-amount {
  font-size: 18px;
  color: #333;
  
  .amount {
    font-size: 24px;
    color: $danger-color;
    font-weight: bold;
  }
}
</style>
