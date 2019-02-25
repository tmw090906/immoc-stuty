package com.tmw.design.principle.liskovsubstitution.methodinput;

import java.util.Map;

public class Child extends Base {

    /*
    这个是重写
    @Override
    public void method(HashMap map) {
        System.out.println("child is running");
    }
    */


    /**
     * 子类入参的参数一定要比父类更宽松
     * @param map
     */
    public void method(Map map) {
        System.out.println("child map method input is running");
    }

}
