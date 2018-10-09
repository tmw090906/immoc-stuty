package com.ccb.webflux.util;

import com.ccb.webflux.exception.CheckException;

import java.util.stream.Stream;

public class CheckUtils {

    private static final String[] INVALID_NAMES = {"admin", "guanliyuan"};

    /**
     * 交易名字, 不成功抛出校验异常
     * @param name
     */
    public static void checkName(String name) {
        Stream.of(INVALID_NAMES)
                .filter(v -> v.equalsIgnoreCase(name))
                .findAny()
                .ifPresent(v -> {
                    throw new CheckException("name", v);
                });
    }

}
