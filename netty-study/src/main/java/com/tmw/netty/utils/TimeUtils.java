package com.tmw.netty.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {


    private static final String STANDARD_DATE_STR = "yyyy-MM-dd HH:mm:ss";

    /**
     * date -> yyyy-MM-dd HH:mm:SS
     */
    public static String dateToStandardStr(Date date) {
        return new SimpleDateFormat(STANDARD_DATE_STR).format(date);
    }

}
