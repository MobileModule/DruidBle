package com.druid.ota.cmd;

import java.io.UnsupportedEncodingException;


/**
 * Created by shawn on 5/6/16.
 */
public class Command {
    public static final int TOKEN_LENGTH = 4;
    public static final int HEAD_LENGTH = 24;
    public static final int TOTAL_LENGTH = 28;


    public static final int SETTING_LENGTH = 84;
    public static final int INFO_LENGTH = 108;
    public static final int OTA_LENGTH = 40;


    public static final byte CMD_INFO = 0x01;
    public static final byte CMD_SETTING = 0x06;
    public static final byte CMD_OTA = 0x07;
    public static final byte CMD_ACTIVE = 0x08;

    public static final byte CMD_ADD = (byte) 0x80;

    public static final int MESSAGE_MODE = 0;
    public static final int MESSAGE_INFO = 1;
    public static final int MESSAGE_SETTING = 2;
    public static final int MESSAGE_OTA = 3;

    private static final String TOKEN = "WHAT";
    /* CRC lookup table */
    static int crctab[] =
            {0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50A5, 0x60C6, 0x70E7, 0x8108,
                    0x9129, 0xA14A, 0xB16B, 0xC18C, 0xD1AD, 0xE1CE, 0xF1EF, 0x1231, 0x0210,
                    0x3273, 0x2252, 0x52B5, 0x4294, 0x72F7, 0x62D6, 0x9339, 0x8318, 0xB37B,
                    0xA35A, 0xD3BD, 0xC39C, 0xF3FF, 0xE3DE, 0x2462, 0x3443, 0x0420, 0x1401,
                    0x64E6, 0x74C7, 0x44A4, 0x5485, 0xA56A, 0xB54B, 0x8528, 0x9509, 0xE5EE,
                    0xF5CF, 0xC5AC, 0xD58D, 0x3653, 0x2672, 0x1611, 0x0630, 0x76D7, 0x66F6,
                    0x5695, 0x46B4, 0xB75B, 0xA77A, 0x9719, 0x8738, 0xF7DF, 0xE7FE, 0xD79D,
                    0xC7BC, 0x48C4, 0x58E5, 0x6886, 0x78A7, 0x0840, 0x1861, 0x2802, 0x3823,
                    0xC9CC, 0xD9ED, 0xE98E, 0xF9AF, 0x8948, 0x9969, 0xA90A, 0xB92B, 0x5AF5,
                    0x4AD4, 0x7AB7, 0x6A96, 0x1A71, 0x0A50, 0x3A33, 0x2A12, 0xDBFD, 0xCBDC,
                    0xFBBF, 0xEB9E, 0x9B79, 0x8B58, 0xBB3B, 0xAB1A, 0x6CA6, 0x7C87, 0x4CE4,
                    0x5CC5, 0x2C22, 0x3C03, 0x0C60, 0x1C41, 0xEDAE, 0xFD8F, 0xCDEC, 0xDDCD,
                    0xAD2A, 0xBD0B, 0x8D68, 0x9D49, 0x7E97, 0x6EB6, 0x5ED5, 0x4EF4, 0x3E13,
                    0x2E32, 0x1E51, 0x0E70, 0xFF9F, 0xEFBE, 0xDFDD, 0xCFFC, 0xBF1B, 0xAF3A,
                    0x9F59, 0x8F78, 0x9188, 0x81A9, 0xB1CA, 0xA1EB, 0xD10C, 0xC12D, 0xF14E,
                    0xE16F, 0x1080, 0x00A1, 0x30C2, 0x20E3, 0x5004, 0x4025, 0x7046, 0x6067,
                    0x83B9, 0x9398, 0xA3FB, 0xB3DA, 0xC33D, 0xD31C, 0xE37F, 0xF35E, 0x02B1,
                    0x1290, 0x22F3, 0x32D2, 0x4235, 0x5214, 0x6277, 0x7256, 0xB5EA, 0xA5CB,
                    0x95A8, 0x8589, 0xF56E, 0xE54F, 0xD52C, 0xC50D, 0x34E2, 0x24C3, 0x14A0,
                    0x0481, 0x7466, 0x6447, 0x5424, 0x4405, 0xA7DB, 0xB7FA, 0x8799, 0x97B8,
                    0xE75F, 0xF77E, 0xC71D, 0xD73C, 0x26D3, 0x36F2, 0x0691, 0x16B0, 0x6657,
                    0x7676, 0x4615, 0x5634, 0xD94C, 0xC96D, 0xF90E, 0xE92F, 0x99C8, 0x89E9,
                    0xB98A, 0xA9AB, 0x5844, 0x4865, 0x7806, 0x6827, 0x18C0, 0x08E1, 0x3882,
                    0x28A3, 0xCB7D, 0xDB5C, 0xEB3F, 0xFB1E, 0x8BF9, 0x9BD8, 0xABBB, 0xBB9A,
                    0x4A75, 0x5A54, 0x6A37, 0x7A16, 0x0AF1, 0x1AD0, 0x2AB3, 0x3A92, 0xFD2E,
                    0xED0F, 0xDD6C, 0xCD4D, 0xBDAA, 0xAD8B, 0x9DE8, 0x8DC9, 0x7C26, 0x6C07,
                    0x5C64, 0x4C45, 0x3CA2, 0x2C83, 0x1CE0, 0x0CC1, 0xEF1F, 0xFF3E, 0xCF5D,
                    0xDF7C, 0xAF9B, 0xBFBA, 0x8FD9, 0x9FF8, 0x6E17, 0x7E36, 0x4E55, 0x5E74,
                    0x2E93, 0x3EB2, 0x0ED1, 0x1EF0

            };

    static int calc_crc16(byte[] buf, int len, int offset) {
        int crc = 0xFFFF;
        int i;
        for (i = 0; i < len; i++) {
            crc = ((crc << 8) & 0xFFFF) ^ crctab[((crc >> 8) ^ buf[i + offset]) & 0xFF];
            //crc = (crc << 8) ^ crctab[((crc >> 8) ^ (buf[i + offset] & 0xff)) & 0xff];
        }
        return crc;
    }

    public static final int byteArrayToInt32(byte[] bytes, int offset) {
        int result = 0;
        result |= (bytes[offset + 3] & 0xff);
        result <<= 8;

        result |= (bytes[offset + 2] & 0xff);
        result <<= 8;

        result |= (bytes[offset + 1] & 0xff);
        result <<= 8;

        result |= (bytes[offset] & 0xff);

        return result;
    }

    public static final int byteArrayToInt16(byte[] bytes, int offset) {
        int result = 0;

        result |= (bytes[offset + 1] & 0xff);
        result <<= 8;

        result |= (bytes[offset] & 0xff);

        return result;
    }


    public static boolean checkToken(byte[] bytes) {
        String token = null;
        try {
            token = new String(bytes, 0, 4, "UTF-8");
            if (token.equals(TOKEN)) {
                return true;
            } else {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkCrc16(byte[] bytes) {
        int crc16 = byteArrayToInt16(bytes, 4);
        if (crc16 == calc_crc16(bytes, bytes.length - 6, 6))
            return true;
        return false;
    }

    public static String parseId(byte[] bytes) {
        byte[] data = new byte[12];
        for (int i = 0; i < 12; i++) {
            data[i] = bytes[12 + i];
        }
        return bytesToHexString(data).toUpperCase();
    }

    public static int parsePv(byte[] bytes) {
        int pv = byteArrayToInt16(bytes, 8);
        return pv;
    }

    public static int parseDt(byte[] bytes) {
        int dt = bytes[24];
        return dt;
    }

    public static int parseHwv(byte[] bytes) {
        int hwv = bytes[25];
        return hwv;
    }

    public static int parseFwv(byte[] bytes) {
        int fwv = bytes[26];
        return fwv;
    }

    public static int parseStatus(byte[] bytes) {
        int status = bytes[28] & 0xff;
        return status;
    }

    public static int parsePower(byte[] bytes) {
        int power = 0;

        power |= (bytes[31] & 0xff);
        power <<= 8;

        power |= (bytes[30] & 0xff);

        return power;
    }

    public static String parseIMSI(byte[] bytes) {
        String imsi = new String(bytes, 32, 24);
        String a[] = imsi.split("\0");
        if (a.length != 0)
            return a[0];
        return null;
    }

    public static String parseNumber(byte[] bytes) {
        String number = new String(bytes, 56, 24);
        String a[] = number.split("\0");
        if (a.length != 0)
            return a[0];
        return null;
    }

    public static String parseMac(byte[] bytes) {
        String mac = new String(bytes, 80, 24);
        String a[] = mac.split("\0");
        if (a.length != 0)
            return a[0].toUpperCase();
        return null;
    }


    public static byte[] setReply(byte[] data) {
        byte[] reply;
        switch (data[27] & 0xff) {
            case CMD_INFO: {
                reply = new byte[TOTAL_LENGTH];
                copyByte(data, reply, TOTAL_LENGTH, 0);
                reply[TOTAL_LENGTH - 1] = (byte) ((data[TOTAL_LENGTH - 1] & 0xFF) | CMD_ADD);
                setLength(reply);
                setCrc16(reply);
                return reply;
            }
            case CMD_ACTIVE: {
                reply = new byte[TOTAL_LENGTH];
                return reply;
            }
            case CMD_SETTING: {
                reply = new byte[TOTAL_LENGTH];
                return reply;
            }
            default:
                return null;
        }
    }

    public static void setLength(byte[] data) {
        int length = data.length;
        data[6] = (byte) (length & 0xFF);
        data[7] = (byte) ((length >> 8) & 0xFF);
    }

    public static void setCrc16(byte[] data) {
        int crc16 = calc_crc16(data, data.length - 6, 6);
        data[4] = (byte) (crc16 & 0xFF);
        data[5] = (byte) ((crc16 >> 8) & 0xFF);
    }

    public static void copyByte(byte[] src, byte[] dst, int length, int offset) {
        for (int i = 0; i < length; i++) {
            dst[i + offset] = src[i];
        }
    }


    public static void copyOta(byte[] src, byte[] dst, int length, int offset) {
        for (int i = 0; i < length; i++) {
            dst[40 + i] = src[offset + i];
        }
    }

    public static void setOtaLength(byte[] src, int length, int offset) {
        src[offset] = (byte) (length & 0xFF);
        src[offset + 1] = (byte) ((length >> 8) & 0xFF);
        src[offset + 2] = (byte) ((length >> 16) & 0xFF);
        src[offset + 3] = (byte) ((length >> 24) & 0xFF);
    }

    public static byte parseOtaType(byte[] src) {
        byte type = (byte) (src[36] & 0xFF);
        return type;
    }

    public static int parseOtaOffset(byte[] src) {
        int offset = byteArrayToInt32(src, 28);
        return offset;
    }

    public static int parseOtaLength(byte[] src) {
        int length = byteArrayToInt32(src, 32);
        return length;
    }

    public static byte[] base64Encode(byte[] data) {
        //return Base64.encode(data, 0, data.length, Base64.DEFAULT);char[] d = getChars(data);
/*        OtaLog.v("char t0 bytes " + Base64.encode(data).length);

        byte[] noop = getBytes(org.ow2.util.base64.Base64.encode(data));
        return LineBase64(noop);*/
        //return LineBase64(Base64.encode(data, Base64.CRLF));
        return null;
    }

    public static byte[] base64Decode(byte[] data) {
/*        char[] d = getChars(data);
        OtaLog.v("char length " + d.length);
        return org.ow2.util.base64.Base64.decode(d);*/
        //return Base64.decode(data, Base64.CRLF);
        return null;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static byte[] getBytes(char[] chars) {
        byte[] out = new byte[chars.length];
        for (int i = 0; i < chars.length; i++)
            out[i] = (byte) chars[i];
        return out;
    }

    private static char[] getChars(byte[] bytes) {
        char[] out = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++)
            out[i] = (char) bytes[i];
        return out;
    }

    public static byte[] LineBase64(byte[] data) {
        byte[] out = new byte[data.length + 1];
        copyByte(data, out, data.length, 0);
        out[out.length - 1] = '\n';
        return out;
    }

    public static String getFirmwareID(byte[] data) {
        int time = DataType.byteArrayTo32(data, 24);
        String timeStr = time + "";
        return timeStr;
    }

//    public static String getFirmwareID(byte[] data) {
//        int time = DataType.byteArrayTo32(data, 23);
//        String timeStr = time + "";
//        char[] d = timeStr.toCharArray();
//        Hashtable<Integer, String> table = new Hashtable<Integer, String>();
//        int i = 0;
//        for (i = 0; i < 9; i++) {
//            table.put(i, "Druid" + i);
//        }
//        for (int j = 0; j < d.length; j++) {
//            table.put(i + j, d[j] + "");
//        }
//
//        String h = table.toString().hashCode() + "";
//        if (h.contains("-")) {
//            h = h.replace("-", "");
//        }
//        return h;
//    }
}
