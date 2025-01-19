import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './assets/service/router'
const mainApp = createApp(App)

mainApp.use(router)
mainApp.mount('#app')
