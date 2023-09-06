import {createApp} from 'vue'
import App from './App.vue'
import router from '@/router/router'
import axios from 'axios'
import '@/assets/css/fonts.css'
import store from './store/store'
import vuetify from './plugins/vuetify'
import {loadFonts} from './plugins/webfontloader'

loadFonts()

// 앱 인스턴스 생성
const app = createApp(App)

// 플러그인 추가
app
    .use(router)
    .use(store)
    .use(vuetify)
    .mount('#app')

// axios 전역 설정, this.axios로 접근 가능
app.config.globalProperties.axios = axios
