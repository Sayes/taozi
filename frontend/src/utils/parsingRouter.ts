import { Component } from 'vue'
import { RouteRecordRaw } from 'vue-router'
import BasicLayout from '@/layout/index.vue'
import { RouterTable, RouterObj } from '@/types/api/login'
import RouteView from '@/layout/routerView/routerView.vue'
import CRUDComponent from '@/components/crud.vue'

const modules = import.meta.glob('/src/views/**/*.vue')

/**
 * @desc: 解析路由
 * @param { Array } routerMap 后台返回的路由表
 * @param { Object } 可选 路由对象
 * @return 返回RouteRecordRaw
 */
export const generator = (routerMap: RouterTable, parent?: RouteRecordRaw): any => {

  interface basic {
    BasicLayout: Component,
    RouteView: Component
  }
  const constantRouterComponents: basic = { BasicLayout, RouteView }
  return routerMap.map((item: any) => {
    const path = item.path || `${parent && parent.path || ''}/${item.key}`
    let routePath = path
    let component = (constantRouterComponents[item.component || item.key]) || (modules[`/src/views${item.component}.vue`])

    if (routePath.startsWith('/page/_crud/')) {
      component = CRUDComponent
    }
    const currentRouter: RouteRecordRaw = {
      path: routePath,
      name: item.key || '',
      component: component,
      meta: {
        id: item.id,
        title: item.name,
        path: path,
        keepAlive: item.keepAlive || false,
        hidden: item.hidden || false
      }
    }
    // 为了防止出现后端返回结果不规范，处理有可能出现拼接出两个 反斜杠
    if (!currentRouter.path.startsWith('http')) {
      currentRouter.path = currentRouter.path.replace('//', '/')
    }
    // 重定向
    item.redirect && (currentRouter.redirect = item.redirect)
    // 是否有子菜单，并递归处理
    if (item.children && item.children.length > 0) {
      currentRouter.children = generator(item.children, currentRouter)
    }
    return currentRouter
  })

}
