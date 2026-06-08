import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { User } from '@/types'

/**
 * 用户状态管理
 */
export const useUserStore = defineStore('user', () => {
  // 状态
  const token = ref<string>('')
  const userInfo = ref<User | null>(null)

  // 计算属性
  const isLoggedIn = computed(() => !!token.value)
  const isCustomer = computed(() => userInfo.value?.role === 1)
  const isMerchant = computed(() => userInfo.value?.role === 2)

  // 方法
  /**
   * 登录
   */
  function login(newToken: string, user: User) {
    token.value = newToken
    userInfo.value = user
    localStorage.setItem('token', newToken)
    localStorage.setItem('userInfo', JSON.stringify(user))
  }

  /**
   * 从本地存储恢复登录状态
   */
  function restoreLogin() {
    const savedToken = localStorage.getItem('token')
    const savedUserInfo = localStorage.getItem('userInfo')
    
    if (savedToken && savedUserInfo) {
      token.value = savedToken
      userInfo.value = JSON.parse(savedUserInfo)
    }
  }

  /**
   * 登出
   */
  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    isCustomer,
    isMerchant,
    login,
    restoreLogin,
    logout,
  }
})
