package com.taozi.common.system.base.entity;

import com.alibaba.fastjson.JSONObject;
import com.taozi.common.system.vo.LoginUser;
import com.taozi.common.util.RestUtil;
import com.taozi.config.TaoziBaseConfig;
import com.taozi.config.shiro.ShiroRealm;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Configuration
public class TaoziAppMenu {
    public JSONObject appmenu;
    public boolean cached = false;
    @Autowired
    TaoziBaseConfig taoziBaseConfig;

    public TaoziAppMenu() {
        appmenu = new JSONObject();
    }

    public void queryAppMenu(String token) {
        if (!cached) {
            HttpHeaders headers = RestUtil.getHeaderApplicationJson();
            headers.add("token", token);
            ResponseEntity<JSONObject> result = RestUtil.request(taoziBaseConfig.getHostDadao() + "/testroad/sys/user/getMenus", HttpMethod.GET, headers, null, null, JSONObject.class);
            appmenu = result.getBody();
            cached = true;
        }
        cached = true;
    }

    public boolean checkItem(net.sf.json.JSONObject item, String url, String op, LoginUser user) {
        String href = item.get("href").toString();
        if (href.contains(url)) {
            String strPermission = item.get("permission").toString();
            String[] permissionList = strPermission.split(",");
            for (String it : permissionList) {
                if (it.equals(user.getUsername() + ":" + op)) {
                    return true;
                }
            }
        }

        JSONArray children = JSONArray.fromObject(item.get("children"));
        for (int i = 0; i < children.size(); i++) {
            net.sf.json.JSONObject itemChild = children.getJSONObject(i);
            if (itemChild != null) {
                if (checkItem(itemChild, url, op, user)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean allowAccess(HttpServletRequest req, String url, String op) {
        return true;
        /*
        String token = req.getHeader("token");
        ShiroRealm sr = new ShiroRealm();
        LoginUser user = sr.checkUserTokenIsEffect(token);
        queryAppMenu(token);
        for (Map.Entry entry : appmenu.entrySet()) {
            if (entry.getKey() == "menuList") {
                JSONArray lst = JSONArray.fromObject(entry.getValue());
                for (int i = 0; i < lst.size(); i++) {
                    net.sf.json.JSONObject item = lst.getJSONObject(i);
                    if (checkItem(item, url, op, user)) {
                        return true;
                    }
                }
            }
        }
        return false;
        */
    }

    public void flushMenu() {
        cached = false;
    }

}
