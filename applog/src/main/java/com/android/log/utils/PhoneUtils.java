package com.android.log.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class PhoneUtils {
    public static final String getMobileBrand() {
        return Build.BRAND;
    }

    public static final String getMobileModel() {
        return Build.MODEL;
    }

    public static final String getMobileSDK() {
        return Build.VERSION.SDK;
    }

    public static final String getAppVersionName(Context context) {
        PackageManager packageManager = null;
        String versionName = "-1";
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            versionName = packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // L.e("getAppVersion", e);
        }
        return versionName;
    }

    public static String getPhoneSerial(Context context) {
        return getMobileBrand() + "__" + getMobileModel() + "__" +
                getMobileSDK() + "__" + getAppVersionName(context);
    }

    public static String getUniqueID(Context context) {
        String id = null;
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
            try {
                UUID uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                id = uuid.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(id)) {
            id = getUUID();
        }
        return TextUtils.isEmpty(id) ? UUID.randomUUID().toString() : id;
    }

    @SuppressLint("MissingPermission")
    private static String getUUID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = android.os.Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

}
