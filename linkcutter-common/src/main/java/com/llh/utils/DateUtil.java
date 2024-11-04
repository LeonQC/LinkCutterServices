package com.llh.utils;

import java.time.LocalDateTime;

public class DateUtil {

    public static String getMonth(LocalDateTime localDateTime) {
        if(localDateTime == null) {
            return null;
        }
        return localDateTime.getMonth().toString();
    }

    public static String getDay(LocalDateTime localDateTime) {
        if(localDateTime == null) {
            return null;
        }
        return String.valueOf(localDateTime.getDayOfMonth());
    }
}
