package com.ccb.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 方法调用信息类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MethodInfo {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法
     */
    private HttpMethod method;

    /**
     * 参数(url)
     */
    private Map<String, Object> params;

    /**
     * 请求Body
     */
    private Mono body;

    /**
     * body类型
     */
    private Class<?> bodyType;

    /**
     * 返回是Flux还是Mono
     */
    private boolean returnFlux;

    /**
     * 返回对象的类型
     */
    private Class<?> returnType;



}
