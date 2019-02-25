package com.tmw.design.pattern.creational.singleton;

import java.util.HashMap;
import java.util.Map;

public class ContainerSingleton {
    private static Map<String, Object> singletonMap = new HashMap<>();

    public static void putInstance(String key, Object instance) {
        if (!singletonMap.containsKey(key)) {
            singletonMap.put(key, instance);
        }
    }

    public static Object getInstance(String key) {
        return singletonMap.get(key);
    }

}
