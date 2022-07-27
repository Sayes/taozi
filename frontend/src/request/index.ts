import {App} from 'vue';
import storage from 'store';
import store from '@/store'
import router from '@/router';
import {regAxios} from './install';
import {message} from 'ant-design-vue';
import axios, {AxiosRequestConfig, AxiosResponse, AxiosError} from 'axios';


// 创建axios实例
var request: any;
if (process.env.NODE_ENV == 'development') {
  request = axios.create({
    baseURL: (import.meta.env.VITE_REQUEST_BASE_URL as string) + '/api/v1',
    timeout: 6000,
  })
} else {
  request = axios.create({
    baseURL: window.location.origin + '/api/v1',
    timeout: 6000,
  })
}

/**
 * @desc: 异常拦截处理器
 * @param { Object } error 错误信息
 */
const errorHandler = (error: AxiosError): AxiosError | Promise<AxiosError> => {
  console.error(error);
  if (401 === error.response?.status) {
    storage.remove('token');
    router.push({path: '/login', query: {redirect: router.currentRoute.value.fullPath}});
    message.error('登录失效，请重新登录');
  } else {
    message.error(error.message);
  }
  return Promise.reject(error);
}

const whiteList = [
  '/general/user/verify_code',
  '/general/user/login',
  '/general/user/refresh_token',
  '/general/user/new_refresh_token',
  'general/user/verify_code',
  'general/user/login',
  'general/user/refresh_token',
  'general/user/new_refresh_token',
];
/**
 * @desc: 请求发送前拦截
 * @param { Object } config 配置参数
 */
request.interceptors.request.use((config: AxiosRequestConfig): AxiosRequestConfig | Promise<AxiosRequestConfig> => {
  if (whiteList.indexOf(config.url!) > -1) {
    return config;
  }
  return new Promise((resolve, reject) => {
    store.dispatch('user/checkToken').then(token => {
      // @ts-ignore
      config.headers['Authentication'] = 'Bearer ' + token
      resolve(config);
    }, err => {
      console.error(err);
      storage.remove('token');
      router.push({path: '/login', query: {redirect: router.currentRoute.value.fullPath}});
      message.error('登录失效，请重新登录');
      reject(err);
    })
  })
}, errorHandler);

/**
 * @desc: 服务端响应后拦截
 * @param { Object } response 返回的数据
 */
request.interceptors.response.use((response: AxiosResponse): AxiosResponse | Promise<AxiosResponse> => {
  if (response.data.code === 0) {
    return response;
    // } else if (response.data.code === 401) {
    //
    //   // 登录失效
    //   storage.remove('token')
    //   router.push({path: '/login', query: {redirect: router.currentRoute.value.fullPath}})
    //   return Promise.reject(response)
  } else {
    return Promise.reject(response);
  }
}, errorHandler)

export const globalAxios = {
  install(app: App) {
    app.use(regAxios, request);
  }
}

export default request;
