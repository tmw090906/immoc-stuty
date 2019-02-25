package com.tmw.design.pattern.creational.singleton;

import java.io.Serializable;

public class HungrySingleton implements Serializable {
    private static final HungrySingleton hungrySingleton;
    private HungrySingleton() {}

    static {
        hungrySingleton = new HungrySingleton();
    }
    public static HungrySingleton getInstance() {
        return hungrySingleton;
    }

    /**
     * 这是什么？
     * jdk 中 ，ObjectInputSream的readObject会通过反射
     * 查看是否有这个方法
     * 若有这个方法，调用这个方法获取对象
     * @return
     */
    private Object readResolve() {
        return hungrySingleton;
    }
}
