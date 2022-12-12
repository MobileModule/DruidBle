package com.device.ble.utils;

/**
 * Created by LeaAnder on 2018/3/6.
 */

public class StrUtils {
    public static boolean isEmpty(String str) {
        if (str != null && str.length() > 0 && str != "")
            return false;
        else
            return true;
    }


    public static int getTimeStampSecond() {
        int seconds = (int) (System.currentTimeMillis() / 1000);
        return seconds;
    }

    public static long getTimeStampMillis() {
        return System.currentTimeMillis();
    }

//    public static String getFileName() {
//        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
//    }
}
