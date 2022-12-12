package com.android.log.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LogTimeUtils {
    public static String getTime(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String date = simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(timestamp))));
        return date;
    }

    public static String getRF3399Time(long timestamp){
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        localFormater.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
        String localTime = localFormater.format(new Date(Long.parseLong(String.valueOf(timestamp))));
        return localTime;
    }
}
