<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">企业级订单管理系统</h2>
      <p class="login-subtitle">用户登录</p>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" maxlength="11" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleLogin" class="login-btn">
            登录
          </el-button>
        </el-form-item>

        <div class="login-footer">
          <span>还没有账号？</span>
          <router-link to="/register">立即注册</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { loginApi } from '@/api/auth'
import { useUserStore } from '@/stores/user'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({
  phone: '',
  password: ''
})

const rules: FormRules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少 6 位', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    try {
      const res = await loginApi(form.phone, form.password)
      userStore.login(res.data.token, res.data.user)
      ElMessage.success('登录成功')
      
      // 跳转到重定向页面或默认页面
      const redirect = route.query.redirect as string
      router.push(redirect || '/products')
    } catch (error) {
      console.error('登录失败:', error)
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped lang="scss">
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  overflow: hidden;
  
  &::before {
    content: '';
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
    animation: rotate 30s linear infinite;
  }
}

@keyframes rotate {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.login-card {
  width: 450px;
  padding: 40px;
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  position: relative;
  z-index: 1;
  
  &:hover {
    box-shadow: 0 25px 70px rgba(0, 0, 0, 0.35);
    transform: translateY(-2px);
    transition: all 0.3s ease;
  }
}

.login-title {
  text-align: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 10px;
  font-size: 28px;
  font-weight: 600;
}

.login-subtitle {
  text-align: center;
  color: #666;
  margin-bottom: 30px;
  font-size: 16px;
}

.login-btn {
  width: 100%;
  height: 45px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 10px;
  
  &:hover {
    opacity: 0.9;
    transform: translateY(-1px);
    box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
  }
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  color: #666;
  font-size: 14px;
  
  a {
    color: #667eea;
    text-decoration: none;
    margin-left: 5px;
    font-weight: 500;
    
    &:hover {
      text-decoration: underline;
    }
  }
}

:deep(.el-form-item__label) {
  font-weight: 500;
  color: #333;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  
  &:hover {
    box-shadow: 0 2px 12px rgba(102, 126, 234, 0.3);
  }
}
</style>
