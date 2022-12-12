package com.druid.ota.cmd;

import android.app.Activity;
import android.net.Uri;

import com.druid.ota.bean.BinInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by LeaAnder on 2017/6/1.
 */
public class OtaFileUtils {
    public static BinInfo getOTABin(byte[] datas) {
        if(datas!=null){
            if(datas.length>24){
                int fwv_bin = Command.byteArrayToInt32(datas, 12);//固件版本
                int hwv_bin = Command.byteArrayToInt32(datas, 16);//硬件版本
                int dt_bin = Command.byteArrayToInt32(datas, 20);//
                return new BinInfo(fwv_bin, hwv_bin, dt_bin, datas);
            }
        }
        return null;
    }

    public static byte[] readBytes(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            int len = fis.available();
            byte[] buffer = new byte[len];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static byte[] readBytes(Activity activity, Uri uri) {
        try {
            InputStream fis = activity.getContentResolver().openInputStream(uri);
            int len = fis.available();
            byte[] buffer = new byte[len];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static BinInfo getBinInfo(String path) {
        byte[] datas = readBytes(path);
        return getOTABin(datas);
    }

    public static BinInfo getBinInfo(Activity activity, Uri uri) {
        byte[] datas = readBytes(activity, uri);
        return getOTABin(datas);
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
