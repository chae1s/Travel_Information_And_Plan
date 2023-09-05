import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router/router'
import axios from 'axios'
import '@/assets/css/fonts.css'
import store from "./store/store.js"

const app = createApp(App)

app.config.globalProperties.axios = axios
app.use(router)
app.use(store)
app.mount('#app')
