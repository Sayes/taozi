<template>
  <a-config-provider v-bind="getPopupContainer">
    <router-view></router-view>
  </a-config-provider>
</template>

<script lang="ts">
// import 'moment/dist/locale/zh-cn'
// import zhCN from 'ant-design-vue/es/locale/zh_CN'

import zhCN from 'ant-design-vue/es/locale/zh_CN';
import 'dayjs/locale/zh-cn';
import storage from 'store'
import { AUTH_HEADER_PROVIDER, IMAGE_ACTION_PROVIDER, IMAGE_SHOW_PROVIDER } from 'daisy.basis';

import { defineComponent, provide } from 'vue'

export default defineComponent({
  name: 'App',
  mounted() {
    let baseURL: string;
    if (process.env.NODE_ENV == 'development') {
      baseURL = (import.meta.env.VITE_REQUEST_BASE_URL as string);
    } else {
      baseURL = window.location.origin;
    }

    provide(AUTH_HEADER_PROVIDER, () => { return { Authentication: 'Bearer ' + storage.get('token') }; });
    provide(IMAGE_ACTION_PROVIDER, (scenario: string, storageId: string) =>
      baseURL + '/api/v1/general/image/upload?scenario='
      + encodeURIComponent(scenario)
      + '&storageId='
      + encodeURIComponent(storageId || ''));
    provide(IMAGE_SHOW_PROVIDER, (imageSave: any, type: string) =>
      baseURL + '/api/v1/general/image/show?scenario='
      + encodeURIComponent(imageSave.scenario)
      + '&storageId='
      + encodeURIComponent(imageSave.storageId)
      + '&filenameId='
      + encodeURIComponent(imageSave.filenameId)
      + '&type='
      + encodeURIComponent(type || '')
      + '&token='
      + encodeURIComponent(storage.get('token')));
  },
  setup() {
    return {
      getPopupContainer: {
        locale: zhCN
      }
    }
  }
})
</script>

<style>
#app {
  font-family: -apple-system, BlinkMacSystemFont, Segoe UI, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Helvetica Neue, Helvetica, Arial, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #333;
}
</style>
