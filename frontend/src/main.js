import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router/router'
import axios from 'axios'
import '@/assets/css/fonts.css'
import store from './store'

const app = createApp(App)
app
    .use(router)
    .use(store)
    .mount('#app')


app.config.globalProperties.axios = axios

