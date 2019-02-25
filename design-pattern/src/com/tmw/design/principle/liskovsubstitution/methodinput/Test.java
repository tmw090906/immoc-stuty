package com.tmw.design.principle.liskovsubstitution.methodinput;

import java.util.HashMap;
import java.util.TreeMap;

public class Test {

    public static void main(String[] args) {

        Child child = new Child();

        child.method(new HashMap());

        child.method(new TreeMap());

    }

}
