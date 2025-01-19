import { createRouter, createWebHistory } from "vue-router"
import Login from "../../components/auth/Login.vue"
import Book from "../../components/body/Book.vue"
import Category from "../../components/body/Category.vue"
import Transaction from "../../components/body/Transaction.vue"
import Home from "../../components/body/Home.vue"

const routes: Array<{ path: string, component: any }> = [
  { path: "/", component: Home },
  { path: "/login", component: Login },
  { path: "/book", component: Book },
  { path: "/category", component: Category },
  { path: "/transaction", component: Transaction },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
