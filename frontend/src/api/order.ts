import { get, post, put } from '@/utils/request'
import type { Order, Result } from '@/types'

/**
 * 创建订单
 */
export function createOrderApi(data: {
  items: { productId: number; quantity: number }[]
  receiverAddress: string
  remark?: string
  paymentMethod: number
}) {
  return post<Result<{ orderNo: string; timeout: number }>>('/orders', data)
}

/**
 * 获取订单详情
 */
export function getOrderDetailApi(orderNo: string) {
  return get<Result<Order>>(`/orders/${orderNo}`)
}

/**
 * 获取订单列表（客户端）
 */
export function getOrderListApi(params?: { status?: number }) {
  return get<Result<Order[]>>('/orders', params)
}

/**
 * 修改订单
 */
export function modifyOrderApi(orderNo: string, data: { receiverAddress?: string; remark?: string }) {
  return put(`/orders/${orderNo}`, data)
}

/**
 * 取消订单
 */
export function cancelOrderApi(orderNo: string, reason: string) {
  return put(`/orders/${orderNo}/cancel`, { reason })
}

/**
 * 发起支付
 */
export function payOrderApi(orderNo: string) {
  return post(`/orders/${orderNo}/pay`)
}

/**
 * 商家订单列表
 */
export function getAdminOrderListApi(params?: { status?: number }) {
  return get<Result<Order[]>>('/admin/orders', params)
}

/**
 * 商家订单详情
 */
export function getAdminOrderDetailApi(orderNo: string) {
  return get<Result<Order>>(`/admin/orders/${orderNo}`)
}

/**
 * 确认订单
 */
export function confirmOrderApi(orderNo: string, expectedFinishTime: string) {
  return put(`/admin/orders/${orderNo}/confirm`, { expectedFinishTime })
}

/**
 * 推进至制作中
 */
export function produceOrderApi(orderNo: string) {
  return put(`/admin/orders/${orderNo}/produce`)
}

/**
 * 发货
 */
export function shipOrderApi(orderNo: string, logisticsNo: string) {
  return put(`/admin/orders/${orderNo}/ship`, { logisticsNo })
}

/**
 * 完成订单
 */
export function completeOrderApi(orderNo: string) {
  return put(`/admin/orders/${orderNo}/complete`)
}
