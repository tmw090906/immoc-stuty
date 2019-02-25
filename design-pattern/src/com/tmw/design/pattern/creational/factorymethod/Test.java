package com.tmw.design.pattern.creational.factorymethod;

/**
 *  新增Video时，只需要增加类和修改Application
 *  相较简单工厂方法没有违反开闭原则
 */
public class Test {
    public static void main(String[] args) {
        VideoFactory videoFactory = new FEVideoFactory();
        Video video = videoFactory.getVideo();
        video.produce();
    }
}
