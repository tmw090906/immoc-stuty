package com.ccb.interfaces.iml;

import com.ccb.ApiServer;
import com.ccb.beans.MethodInfo;
import com.ccb.interfaces.ProxyCreator;
import com.ccb.beans.ServerInfo;
import com.ccb.interfaces.RestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class JdkProxyCreator implements ProxyCreator {


    @Override
    public Object craeteProxy(Class<?> type) {
        log.info("craeteProxy" + type);
        // 根据接口得到API服务器信息
        ServerInfo serverInfo = extractServerInfo(type);

        log.info("serverInfo:" + serverInfo);

        // 给每个类写一个实现
        RestHandler handler = new WebClientRestHandler();

        // 初始化服务器信息(初始化webclient)
        handler.init(serverInfo);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{type},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        MethodInfo methodInfo = extractMethodInfo(method, args);
                        log.info("methodInfo:" + methodInfo);
                        // 返回动态代理
                        return handler.invokeRest(methodInfo);
                    }

                    /**
                     * 根据方法定义和参数获得方法信息
                     * @param method
                     * @param args
                     * @return
                     */
                    private MethodInfo extractMethodInfo(Method method,
                                                         Object[] args) {
                        MethodInfo methodInfo = new MethodInfo();
                        extractUrlAndMethod(method, args, methodInfo);
                        extractReturnInfo(method, methodInfo);
                        return methodInfo;
                    }

                    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
                        // 返回Flux还是Mono
                        // isAssignableFrom 判断类型是否某个的子类
                        // instanceof 判断实例是否某个的子类
                        boolean returnFlux = method.getReturnType().isAssignableFrom(Flux.class);
                        methodInfo.setReturnFlux(returnFlux);

                        // 返回对象的实际类型
                        Class<?> elementType = extractElementType(method.getGenericReturnType());
                        methodInfo.setReturnType(elementType);

                    }

                    /**
                     * 得到泛型类型的实际类型
                     * @param genericReturnType
                     * @return
                     */
                    private Class<?> extractElementType(Type genericReturnType) {
                        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                        return (Class<?>) actualTypeArguments[0];
                    }

                    private void extractUrlAndMethod(Method method, Object[] args, MethodInfo methodInfo) {
                        // 得到请求Url和请求方法
                        Annotation[] annotations = method.getAnnotations();
                        for (Annotation annotation : annotations) {
                            // GET
                            if (annotation instanceof GetMapping) {
                                GetMapping get = (GetMapping) annotation;
                                methodInfo.setUrl(get.value()[0]);
                                methodInfo.setMethod(HttpMethod.GET);
                            }
                            // POST
                            else if (annotation instanceof PostMapping) {
                                PostMapping get = (PostMapping) annotation;
                                methodInfo.setUrl(get.value()[0]);
                                methodInfo.setMethod(HttpMethod.GET);
                            }
                            // Delete
                            else if (annotation instanceof DeleteMapping) {
                                DeleteMapping get = (DeleteMapping) annotation;
                                methodInfo.setUrl(get.value()[0]);
                                methodInfo.setMethod(HttpMethod.GET);
                            }
                            // PUT
                            else if (annotation instanceof PutMapping) {
                                PutMapping get = (PutMapping) annotation;
                                methodInfo.setUrl(get.value()[0]);
                                methodInfo.setMethod(HttpMethod.GET);
                            }
                        }
                        // 得到调用的参数和Body
                        Map<String, Object> params = new LinkedHashMap<>();
                        methodInfo.setParams(params);

                        Parameter[] parameters = method.getParameters();
                        for (int i = 0 ; i < parameters.length ; i++) {
                            // 是否带 @PathVariable
                            PathVariable annoPath = parameters[i]
                                    .getAnnotation(PathVariable.class);
                            if (annoPath != null) {
                                params.put(annoPath.value(), args[i]);
                            }

                            // 参数里面带@RequestBody
                            RequestBody annoBody = parameters[i]
                                    .getAnnotation(RequestBody.class);
                            if (annoBody != null) {
                                methodInfo.setBody((Mono<?>) args[i]);
                                methodInfo.setBodyType(
                                        this.extractElementType(parameters[i].getParameterizedType())
                                );
                            }
                        }
                    }

                });


    }


    /**
     * 提取服务信息
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }


}
