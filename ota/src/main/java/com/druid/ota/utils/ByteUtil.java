package com.druid.ota.utils;

/**
 * Created by druid on 2019/3/4.
 */

public class ByteUtil {
    public static final String byteArrayToHexStringPretty(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(" ");
            sb.append(Integer.toHexString(0x100 | (0xff & b)).substring(1));
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    public static final String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString(0x100 | (0xff & b)).substring(1));
        }
        return sb.toString();
    }

    public static final String byteArrayToMacString(byte[] bytes) {
        String mac = byteArrayToHexString(bytes);
        String macSplit = "";
        for (int i = 0; i < mac.length() - 1; i++) {
            if ((i % 2) == 0) {
                macSplit += mac.substring(i, (i + 2)) + ":";
            }
        }
        macSplit = macSplit.substring(0, macSplit.length() - 1);
        return macSplit.toUpperCase().trim();
    }

    public static final byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }
}
