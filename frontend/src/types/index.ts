export interface User {
  id: number
  phone: string
  role: number
  nickname?: string
  avatar?: string
}

export interface LoginVO {
  token: string
  user: User
}

export interface Product {
  id: number
  name: string
  price: number
  description?: string
  status: number
  createdAt: string
  updatedAt: string
}

export interface OrderItem {
  productId: number
  productName: string
  unitPrice: number
  quantity: number
  subtotal: number
}

export interface Order {
  id: number
  orderNo: string
  userId: number
  totalAmount: number
  orderStatus: number
  paymentStatus: number
  paymentMethod?: number
  receiverAddress: string
  remark?: string
  expectedFinishTime?: string
  logisticsNo?: string
  cancelReason?: string
  paidAt?: string
  createdAt: string
  updatedAt: string
  items?: OrderItem[]
}

export interface Result<T> {
  code: number
  message: string
  data: T
}
