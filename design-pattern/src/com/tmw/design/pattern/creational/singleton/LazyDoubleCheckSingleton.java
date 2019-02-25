package com.tmw.design.pattern.creational.singleton;

public class LazyDoubleCheckSingleton {

    private volatile static LazyDoubleCheckSingleton lazyDoubleCheckSingleton = null;

    private LazyDoubleCheckSingleton(){}

    public  static LazyDoubleCheckSingleton getInstance() {
        if (lazyDoubleCheckSingleton == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (lazyDoubleCheckSingleton == null) {
                    lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();
                    // 1.分配内存给对象
                    // 2.初始化对象
                    // 3.设置lazyDoubleCheckSingleton指向刚才分配的内存地址
                    // step2 和 step3 可能会重排序， 是一个隐患
                    // intra-thread semantics
                }
            }
        }
        return lazyDoubleCheckSingleton;
    }

}
