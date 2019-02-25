package com.tmw.design.pattern.creational.singleton;

public class T implements Runnable {
    @Override
    public void run() {
        StaticInnerClassSingleton lazySingleton = StaticInnerClassSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + ": " + lazySingleton);
    }
}
