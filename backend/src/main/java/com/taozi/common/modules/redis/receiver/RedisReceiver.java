package com.taozi.common.modules.redis.receiver;


import cn.hutool.core.util.ObjectUtil;
import com.taozi.common.base.BaseMap;
import com.taozi.common.constant.GlobalConstants;
import com.taozi.common.modules.redis.listener.TaoziRedisListerer;
import com.taozi.common.util.SpringContextHolder;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author zyf
 */
@Component
@Data
public class RedisReceiver {


    /**
     * 接受消息并调用业务逻辑处理器
     *
     * @param params
     */
    public void onMessage(BaseMap params) {
        Object handlerName = params.get(GlobalConstants.HANDLER_NAME);
        TaoziRedisListerer messageListener = SpringContextHolder.getHandler(handlerName.toString(), TaoziRedisListerer.class);
        if (ObjectUtil.isNotEmpty(messageListener)) {
            messageListener.onMessage(params);
        }
    }

}
