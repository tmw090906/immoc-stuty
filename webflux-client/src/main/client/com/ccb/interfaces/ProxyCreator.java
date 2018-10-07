package com.ccb.interfaces;

public interface ProxyCreator {

    /**
     * 创建代理类
     * @param type
     * @return
     */
    Object craeteProxy(Class<?> type);

}
