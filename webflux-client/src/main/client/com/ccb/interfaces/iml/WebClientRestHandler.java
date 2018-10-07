package com.ccb.interfaces.iml;

import com.ccb.beans.MethodInfo;
import com.ccb.beans.ServerInfo;
import com.ccb.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientRestHandler implements RestHandler {

    private WebClient client;

    /**
     * 初始化WebClient
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.client = WebClient.create(serverInfo.getUrl());
    }

    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        // 返回结果
        Object result;

        WebClient.RequestBodySpec request =  this.client
                // 设置请求方法
                .method(methodInfo.getMethod())
                // 设置Uri 和 参数
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                // 设置请求格式
                .accept(MediaType.APPLICATION_JSON);

        // 发出请求
        WebClient.ResponseSpec retrieve;
        if (methodInfo.getBody() != null) {
            retrieve = request.body(methodInfo.getBody(), methodInfo.getBodyType()).retrieve();
        } else {
            retrieve = request.retrieve();
        }

        // 处理返回Body
        if (methodInfo.isReturnFlux()) {
            result = retrieve.bodyToFlux(methodInfo.getReturnType());
        } else {
            result = retrieve.bodyToMono(methodInfo.getReturnType());
        }
        return result;
    }



}
