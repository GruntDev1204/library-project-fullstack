export interface book {
  id: number
  name: string
  id_category: number
  quantily: number
}

export interface categoryInsert {
  name: string
}

export interface categoryData {
  id: number
  name: string
}

export interface bookInsert {
  name: string
  categoryId: number
  avatar: string
  quantity: number
  price: number
  promotionValue: number
}

export interface bookData {
  id: number
  name: string
  categoryName: string
  categoryId: number
  quantity: number
  avatar: string
  price: number
  promotionValue: number
  transactionVolume: number
}

export interface findBookData
  extends Partial<{
    name: string
    amount: string
    categoryId: number
  }> {}

export interface Pagination {
  size: number
  index: number
  sortByCol: string
  sortOption: string
}

export interface TransactionData {
  id: number
  saleValue: number
  email: string
  bookId: number
  numberOfBooks: number
  isPurchased: boolean
  transactionDate: string
  expiredDate: string
  status: string
  singlePrice: number
  bookName: string
}
