package com.taozi.config.shiro.filters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taozi.common.system.base.entity.TaoziAppMenu;
import com.taozi.common.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import com.taozi.config.shiro.filters.CustomShiroFilterFactoryBean;
import com.taozi.common.system.util.JwtUtil;
import com.taozi.common.constant.CommonConstant;
import com.taozi.common.util.oConvertUtils;
import com.taozi.config.shiro.JwtToken;
import com.taozi.config.mybatis.TenantContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Description: 鉴权登录拦截器
 * @Author: Scott
 * @Date: 2018/10/7
 **/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 默认开启跨域设置（使用单体）
     * 微服务情况下，此属性设置为false
     */
    private boolean allowOrigin = true;

    public JwtFilter(){}
    public JwtFilter(boolean allowOrigin){
        this.allowOrigin = allowOrigin;
    }

    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            JwtUtil.responseError(response,401, CommonConstant.TOKEN_IS_INVALID_MSG);
            return false;
            //throw new AuthenticationException("Token失效，请重新登录", e);
        }
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(CommonConstant.X_ACCESS_TOKEN);
        // update-begin--Author:lvdandan Date:20210105 for：JT-355 OA聊天添加token验证，获取token参数
        if (oConvertUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter("token");
        }
        // update-end--Author:lvdandan Date:20210105 for：JT-355 OA聊天添加token验证，获取token参数
        if (oConvertUtils.isEmpty(token)) {
            //token = httpServletRequest.getHeader(CommonConstant.X_ACCESS_TOKEN);
            token = httpServletRequest.getHeader("token");
            if (oConvertUtils.isEmpty(token)) {
                Cookie cookies[] = httpServletRequest.getCookies();
                if (cookies != null) {
                    for (int i = 0; i < cookies.length; i++) {
                        if (cookies[i].getName().equals("token")) {
                            token = cookies[i].getValue();
                            break;
                        }
                    }
                }
            }
        }

        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true

        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if(allowOrigin){
            httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
            //update-begin-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
            // 是否允许发送Cookie，默认Cookie不包括在CORS请求之中。设为true时，表示服务器允许Cookie包含在请求中。
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            //update-end-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
        }
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        //update-begin-author:taoyan date:20200708 for:多租户用到
        String tenant_id = httpServletRequest.getHeader(CommonConstant.TENANT_ID);
        TenantContext.setTenant(tenant_id);
        //update-end-author:taoyan date:20200708 for:多租户用到
        return super.preHandle(request, response);
    }
}
