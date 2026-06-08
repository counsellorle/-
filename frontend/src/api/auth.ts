import { post } from '@/utils/request'
import type { LoginVO, Result } from '@/types'

/**
 * 登录接口
 */
export function loginApi(phone: string, password: string) {
  return post<Result<LoginVO>>('/auth/login/password', { phone, password })
}

/**
 * 注册接口
 */
export function registerApi(phone: string, password: string, role: number) {
  return post<Result<number>>('/auth/register', { phone, password, role })
}

/**
 * 发送短信验证码
 */
export function sendSmsCodeApi(phone: string) {
  return post('/auth/login/sms/send', null, { params: { phone } })
}

/**
 * 短信登录
 */
export function smsLoginApi(phone: string, code: string) {
  return post<Result<LoginVO>>('/auth/login/sms', { phone, code })
}

/**
 * 登出
 */
export function logoutApi() {
  return post('/auth/logout')
}
