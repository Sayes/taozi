import { createApp } from 'vue/dist/vue.esm-bundler'
import Antd from 'ant-design-vue'
import '@/assets/css/reset.css'
import 'ant-design-vue/dist/antd.css'
import App from './App.vue'
import router from './router'
import store from './store'
import request, { globalAxios } from './request'
//import { Daisy, CompositeSerialEditor } from 'daisy.basis'
//import 'daisy.basis/dist/daisy.basis.css'

const app = createApp(App)

// 使用antd
app.use(Antd)
// 使用vuex
app.use(store)
// 使用全局axios
app.use(globalAxios)
// 使用路由
app.use(router)

// 使用vuex
app.use(store)
//app.use(Daisy, { 'request': request })
app.use({ 'request': request })
//app.component(CompositeSerialEditor.name, CompositeSerialEditor)


router.isReady().then(() => app.mount('#app'))
