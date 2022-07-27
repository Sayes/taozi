declare module '*.vue' {
  import { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module 'daisy.basis';
declare module 'vue/dist/vue.esm-bundler';