import { get, post, put, del } from '@/utils/request'
import type { Product, Result } from '@/types'

/**
 * 获取商品列表
 */
export function getProductListApi() {
  return get<Result<Product[]>>('/products')
}

/**
 * 获取商品详情
 */
export function getProductDetailApi(id: number) {
  return get<Result<Product>>(`/products/${id}`)
}

/**
 * 创建商品
 */
export function createProductApi(data: { name: string; price: number; description?: string }) {
  return post<Result<number>>('/products', data)
}

/**
 * 更新商品
 */
export function updateProductApi(
  id: number,
  data: { name?: string; price?: number; description?: string }
) {
  return put(`/products/${id}`, data)
}

/**
 * 删除商品
 */
export function deleteProductApi(id: number) {
  return del(`/products/${id}`)
}

/**
 * 上下架商品
 */
export function updateProductStatusApi(id: number, status: number) {
  return put(`/products/${id}/status`, null, { params: { status } })
}
