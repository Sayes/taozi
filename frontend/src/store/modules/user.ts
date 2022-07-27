import storage from 'store'
import {AllState} from '../index'
import {ActionContext} from 'vuex'
import {message} from 'ant-design-vue'
import {LoginFrom} from '@/types/views/login'
import {RouterTable} from '@/types/api/login'
import {generator} from '@/utils/parsingRouter'
import {login, info, menu, logout, refreshToken, newRefreshToken} from '@/api/login'

// 处理用户登录、登出、个人信息、权限路由

export type UserState = {
  token: string,
  // tokenExpireAt: number,
  refreshToken: string,
  // refreshTokenExpireAt: number,
  name: string,
  displayName: string,
  avatar: string,
  roles: string[],
  routers?: RouterTable,
  refreshTokenPromise: Promise<string> | null,
  newRefreshTokenPromise: Promise<string> | null,
}

const state: UserState = {
  // 标识
  token: storage.get('token'),
  // tokenExpireAt: storage.get('tokenExpireAt'),
  refreshToken: storage.get('refreshToken'),
  // refreshTokenExpireAt: storage.get('refreshTokenExpireAt'),
  // 账号
  name: '',
  // 昵称
  displayName: '',
  // 头像
  avatar: '',
  // 角色(鉴权)
  roles: [],
  // 路由表(原始未解析)
  routers: [],
  // 刷新token Promise
  refreshTokenPromise: null,
  // 获取新refresh token Promise
  newRefreshTokenPromise: null,
}

const getExpireTimeFromJwt = (jwt: string): number => {
  const jwtPayload = JSON.parse(atob(jwt.split('.')[1]));
  let exp = jwtPayload['exp'];
  if (exp < 10000000000) { // 单位秒转毫秒
    exp *= 1000;
  }
  return exp;
}

const user = {

  namespaced: true,

  state,

  mutations: {

    // 设置token
    setToken(state: UserState, token: string) {
      storage.set('token', token);
      state.token = token;
    },
    // 设置refresh token
    setRefreshToken(state: UserState, refreshToken: string) {
      storage.set('refreshToken', refreshToken);
      state.refreshToken = refreshToken;
    },

    // 设置用户信息
    setInfo(state: UserState, info: UserState) {
      const {name, displayName, avatar, roles} = info
      state.name = name
      state.displayName = displayName
      state.avatar = avatar
      state.roles = roles
    },

    // 设置路由表(原始未解析)
    setRouters(state: UserState, routers: RouterTable) {
      state.routers = routers
    },

    // 用户退出登录
    clearState(state: UserState) {
      storage.remove('token')
      storage.remove('refreshToken')
      // 为了重新加载用户信息及路由组
      state.name = ''
    },

    // 刷新token
    refreshToken(state: UserState) {
      if (!state.refreshTokenPromise) {
        storage.remove('token')
        state.refreshTokenPromise = new Promise((resolve, reject) => {
          refreshToken(state.refreshToken).then(r => {
            resolve(r.data.data.token);
            state.refreshTokenPromise = null;
          }).catch(err => {
            reject(err);
            state.refreshTokenPromise = null;
          })
        })
      }
    },
    // 刷新token
    newRefreshToken(state: UserState) {
      if (!state.newRefreshTokenPromise) {
        const refreshTokenOnState = state.refreshToken;
        storage.remove('refreshToken');
        state.newRefreshTokenPromise = new Promise((resolve, reject) => {
          newRefreshToken(refreshTokenOnState).then(r => {
            resolve(r.data.data.refreshToken);
            state.newRefreshTokenPromise = null;
          }).catch(err => {
            reject(err);
            state.newRefreshTokenPromise = null;
          })
        })
      }
    },

  },

  actions: {

    // 登录
    login(context: ActionContext<UserState, AllState>, params: LoginFrom) {
      return new Promise((resolve, reject) => {
        login(params).then(e => {
          const data = e.data
          context.commit('setToken', data.data.token)
          context.commit('setRefreshToken', data.data.refreshToken)
          resolve(data)
        }).catch(err => {
          reject(err)
        })
      })
    },

    // 获取用户信息
    userInfo(context: ActionContext<UserState, AllState>) {
      return new Promise((resolve, reject) => {
        info().then(e => {
          const info = e.data.data
          context.commit('setInfo', info)
          resolve(e)
        }).catch(err => {
          message.error(err.message || err.data.message)
          if (err.data && err.data.code !== -401) {
            reject(err)
          }
        })
      })
    },

    // 获取菜单
    menu(context: ActionContext<UserState, AllState>) {
      return new Promise((resolve) => {
        menu().then(e => {
          const routeTable = (<any>e.data.data).routerList
          context.commit('setRouters', routeTable)
          // 初始化侧边菜单
          context.rootState.menu.menuRouter = routeTable[0]['children'] || []
          context.rootState.menu.menuId = routeTable[0]['id']
          resolve(generator(routeTable))
        }).catch(err => {
          message.error(err.message || err.data.message)
        })
      })
    },

    // 退出登录
    logout(context: ActionContext<UserState, AllState>) {
      return new Promise((resolve, reject) => {
        logout().then(e => {
          context.commit('clearState')
          resolve(e)
        }).catch(err => {
          context.commit('clearState')
          message.error(err.message || err.data.message)
          reject(err)
        })
      })
    },

    // 检查token是否过期
    checkToken(context: ActionContext<UserState, AllState>) {
      // 检测并刷新token操作的定义
      const refreshTokenPromise = (context: ActionContext<UserState, AllState>): Promise<string> => {
        const tokenExpireAt = getExpireTimeFromJwt(state.token);
        if (tokenExpireAt - new Date().getTime() < 60 * 1000) {
          // token 即将过期
          context.commit('refreshToken');
          return new Promise<string>(((resolve, reject) => {
            state.refreshTokenPromise!.then(newToken => {
              context.commit('setToken', newToken)
              resolve(newToken);
            }).catch(err => {
              console.error(err);
              context.commit('clearState');
              reject(err);
            })
          }));
        } else {
          return Promise<string>.resolve(state.token);
        }
      }

      // 检测refresh token本身过期
      const refreshTokenExpireAt = getExpireTimeFromJwt(state.refreshToken);
      if (refreshTokenExpireAt - new Date().getTime() < 60 * 60 * 1000) {
        // refresh token 本身即将过期
        context.commit('newRefreshToken');
        return new Promise<string>(((resolve, reject) => {
          state.newRefreshTokenPromise!.then(newRefreshToken => {
            context.commit('setRefreshToken', newRefreshToken);
            refreshTokenPromise(context).then(token => {
              resolve(token);
            }).catch(err => {
              reject(err);
            });
          }).catch(err => {
            context.commit('clearState');
            reject(err);
          });
        }));
      } else {
        return refreshTokenPromise(context);
      }
    },
  }
}

export default user
