import request from '../request'
import {AxiosResponse} from 'axios'
import {LoginFrom} from '@/types/views/login'
import {ResponseData} from '@/types/api/public'
import {LoginSuccess, UserInfo, RouterData} from '@/types/api/login'

type ConfigType<T = ResponseData> = Promise<AxiosResponse<T>>

/**
 * @desc: 登录
 * @param { Object } data 输入的账号密码
 */
export const login = (data: LoginFrom): ConfigType<LoginSuccess> => {
  return request({
    url: 'v1/sys/user/login',
    method: 'post',
    data
  })
}

/**
 * @desc: 获取用户信息
 * @returns data
 */
export const info = (): ConfigType<UserInfo> => {
  return request({
    url: 'sys/user/info',
    method: 'get',
    headers: {
      RefreshToken: 'ttttttttt'
    }
  })
}

/**
 * @desc: 获取菜单
 */
export const menu = (): ConfigType<RouterData> => {
  return request({
    url: 'sys/user/menu',
    method: 'get'
  })
}

/**
 * @desc: 退出登录
 */
export const logout = (): ConfigType => {
  return request({
    url: 'sys/user/logout',
    method: 'post'
  })
}

/**
 * @desc: 获取新token
 */
export const refreshToken = (refreshToken: string): ConfigType => {
  return request({
    url: 'sys/user/refresh_token',
    method: 'get',
    headers: {
      RefreshToken: refreshToken
    }
  })
}

/**
 * @desc: 获取新refresh token
 */
export const newRefreshToken = (refreshToken: string): ConfigType => {
  return request({
    url: 'sys/user/new_refresh_token',
    method: 'get',
    headers: {
      RefreshToken: refreshToken
    }
  })
}
