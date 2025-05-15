export interface NotificationProps {
  type?: string
  message?: string
}

export interface pageOptions {
  size?: number
  index?: number
  sortOptions?: string
  sortByCol?: string
}

export interface fetchBook {
  id: number
  name: string
  quantity: number
  avatar: string
  promotionValue: number
  transactionVolume: number
  categoryName: string
  categoryId: number
  price: number
}

export interface userService {
  email: string
  phoneNumber: string
  fullName: string

  userName: string
  password: string
}

export interface transactionService {
  bookId: number
  isPurchased: boolean
  numberOfBooks: number
}
