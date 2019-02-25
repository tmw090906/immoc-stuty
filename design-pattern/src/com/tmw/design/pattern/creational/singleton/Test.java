package com.tmw.design.pattern.creational.singleton;

import java.io.*;

public class Test {

    public static void main(String[] args) throws Exception {
        //LazySingleton lazySingleton = LazySingleton.getInstance();
//        Thread t1 = new Thread(new T());
//        Thread t2 = new Thread(new T());
//        t1.start();
//        t2.start();
//
//        System.out.println("progrom end");


        /**
         * 通过序列化和反序列化， 破坏了单例
         */
//        HungrySingleton instance = HungrySingleton.getInstance();
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("HungrySingletonFile"));
//        oos.writeObject(instance);
//
//        File file = new File("HungrySingletonFile");
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
//
//        HungrySingleton newInstance = (HungrySingleton) ois.readObject();

        EnumInstance instance = EnumInstance.getInstance();
        instance.setData(new Object());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("HungrySingletonFile"));
        oos.writeObject(instance);

        File file = new File("HungrySingletonFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

        EnumInstance newInstance = (EnumInstance) ois.readObject();


        System.out.println(newInstance.getData());
        System.out.println(instance.getData());
        System.out.println(newInstance.getData() == instance.getData());
    }

}
