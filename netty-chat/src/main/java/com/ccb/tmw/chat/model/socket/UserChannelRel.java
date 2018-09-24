package com.ccb.tmw.chat.model.socket;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户Id 和 channel 的关联处理关系
 */
public class UserChannelRel {


    private static Map<String, Channel> manager = new ConcurrentHashMap();


    public static void put(String userId, Channel channel) {
        manager.put(userId, channel);
    }


    public static Channel get(String userId) {
        return manager.get(userId);
    }



}
