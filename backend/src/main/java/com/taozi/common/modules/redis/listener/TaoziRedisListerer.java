package com.taozi.common.modules.redis.listener;

import com.taozi.common.base.BaseMap;

/**
 * 自定义消息监听
 */
public interface TaoziRedisListerer {

    void onMessage(BaseMap message);

}
