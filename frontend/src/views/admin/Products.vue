<template>
  <div class="admin-products">
    <h2>商品管理</h2>

    <el-button type="primary" @click="handleAdd" style="margin-bottom: 20px">
      <el-icon><Plus /></el-icon>
      添加商品
    </el-button>

    <el-table :data="products" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="商品名称" />
      <el-table-column prop="price" label="单价">
        <template #default="{ row }">¥ {{ row.price }}</template>
      </el-table-column>
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '上架中' : '已下架' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button 
            link 
            :type="row.status === 1 ? 'warning' : 'success'" 
            @click="handleToggleStatus(row)"
          >
            {{ row.status === 1 ? '下架' : '上架' }}
          </el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-empty v-if="products.length === 0" description="暂无商品" />

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑商品' : '添加商品'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input-number v-model="form.price" :min="0.01" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入商品描述" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getProductListApi, createProductApi, updateProductApi, deleteProductApi, updateProductStatusApi } from '@/api/product'
import type { Product } from '@/types'
import type { FormInstance, FormRules } from 'element-plus'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const products = ref<Product[]>([])

const formRef = ref<FormInstance>()
const form = reactive({
  id: 0,
  name: '',
  price: 0,
  description: ''
})

const rules: FormRules = {
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' }
  ],
  price: [
    { required: true, message: '请输入单价', trigger: 'blur' }
  ]
}

const loadProducts = async () => {
  loading.value = true
  try {
    const res = await getProductListApi()
    products.value = res.data
  } catch (error) {
    console.error('加载商品列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  isEdit.value = false
  form.id = 0
  form.name = ''
  form.price = 0
  form.description = ''
  dialogVisible.value = true
}

const handleEdit = (product: Product) => {
  isEdit.value = true
  form.id = product.id
  form.name = product.name
  form.price = product.price
  form.description = product.description || ''
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    submitLoading.value = true
    try {
      if (isEdit.value) {
        await updateProductApi(form.id, {
          name: form.name,
          price: form.price,
          description: form.description
        })
        ElMessage.success('更新成功')
      } else {
        await createProductApi({
          name: form.name,
          price: form.price,
          description: form.description
        })
        ElMessage.success('添加成功')
      }
      
      dialogVisible.value = false
      loadProducts()
    } catch (error) {
      console.error('提交失败:', error)
    } finally {
      submitLoading.value = false
    }
  })
}

const handleToggleStatus = async (product: Product) => {
  const newStatus = product.status === 1 ? 0 : 1
  try {
    await updateProductStatusApi(product.id, newStatus)
    ElMessage.success('操作成功')
    loadProducts()
  } catch (error) {
    console.error('操作失败:', error)
  }
}

const handleDelete = async (product: Product) => {
  try {
    await ElMessageBox.confirm('确定要删除该商品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteProductApi(product.id)
    ElMessage.success('删除成功')
    loadProducts()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

onMounted(() => {
  loadProducts()
})
</script>

<style scoped lang="scss">
.admin-products {
  h2 {
    margin-bottom: 20px;
    color: #333;
  }
}
</style>
