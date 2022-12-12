package com.druid.ota.utils;

/**
 * Created by shawn on 5/6/16.
 */
public class OtaLog {
    public static final String LOGTAG = "OtaLog";
    public static boolean debugEnabled ;

    public OtaLog() {
    }

    private static String getDebugInfo() {
        Throwable stack = new Throwable().fillInStackTrace();
        StackTraceElement[] trace = stack.getStackTrace();
        int n = 2;
        return trace[n].getClassName() + " " + trace[n].getMethodName() + "()" + ":" + trace[n].getLineNumber() +
                " ";
    }

    private static String getLogInfoByArray(String[] infos) {
        StringBuilder sb = new StringBuilder();
        for (String info : infos) {
            sb.append(info);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void i(String... s) {
        if (debugEnabled) {
            android.util.Log.i(LOGTAG, getDebugInfo() + getLogInfoByArray(s));
        }
    }

    public static void e(String... s) {
        if (debugEnabled) {
            android.util.Log.e(LOGTAG, getDebugInfo() + getLogInfoByArray(s));
        }
    }

    public static void d(String... s) {
        if (debugEnabled) {
            android.util.Log.d(LOGTAG, getDebugInfo() + getLogInfoByArray(s));
        }
    }

    public static void v(String... s) {
        if (debugEnabled) {
            android.util.Log.v(LOGTAG, getDebugInfo() + getLogInfoByArray(s));
        }
    }

    public static void w(String... s) {
        if (debugEnabled) {
            android.util.Log.w(LOGTAG, getDebugInfo() + getLogInfoByArray(s));
        }
    }

    public static void logException(Throwable tr) {
        if (debugEnabled) {
            android.util.Log.e(LOGTAG, getDebugInfo(), tr);
        }
    }
}
