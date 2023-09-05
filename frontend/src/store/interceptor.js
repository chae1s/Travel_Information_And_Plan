import axios from 'axios';
import store from '@/store/store.js'; // Vuex 스토어를 가져옵니다.

// Axios 인스턴스를 생성합니다.
const instance = axios.create({
  baseURL: process.env.VUE_APP_API_URL, // API의 기본 URL을 설정합니다.
  timeout: 5000, // 요청 타임아웃 시간을 설정합니다.
});

// Request Interceptor를 추가합니다.
instance.interceptors.request.use(
  (config) => {
    // Do something before request is sent
    // 요청 헤더에 토큰을 추가합니다.
    config.headers.Authorization = "Bearer " + store.state.token;
    console.log(config.headers.Authorization);
    return config;
  },
  (error) => {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Response Interceptor를 추가합니다.
instance.interceptors.response.use(
  (response) => {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  (error) => {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  }
);

export default instance; // Axios 인스턴스를 내보냅니다.

// import store from '@/store/index.js';

// export function setInterceptors(instance) {
//   // Add a request interceptor
//   instance.interceptors.request.use( // login API 요청후 
//     function(config) {
//       // Do something before request is sent
//       config.headers.Authorization = store.state.token; // 요청시 토큰 셋팅
//       return config;
//     },
//     function(error) {
//       // Do something with request error
//       return Promise.reject(error);
//     },
//   );

//   // Add a response interceptor
//   instance.interceptors.response.use(
//     function(response) {
//       // Any status code that lie within the range of 2xx cause this function to trigger
//       // Do something with response data
//       return response;
//     },
//     function(error) {
//       // Any status codes that falls outside the range of 2xx cause this function to trigger
//       // Do something with response error
//       return Promise.reject(error);
//     },
//   );
//   return instance;
// }