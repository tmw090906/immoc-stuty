package com.tmw.design.pattern.creational.singleton;

public class ThreadLocalSingleton {

    private static ThreadLocal<ThreadLocalSingleton> threadLocalSingleton
            = ThreadLocal.withInitial(() -> new ThreadLocalSingleton());

    public static ThreadLocalSingleton getInstance() {
        return threadLocalSingleton.get();
    }

    private ThreadLocalSingleton() {}

}