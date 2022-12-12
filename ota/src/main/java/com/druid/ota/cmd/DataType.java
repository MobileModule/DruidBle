package com.druid.ota.cmd;

/**
 * Created by LeaAnder on 2017/6/1.
 */
public class DataType {
    public static byte[] data16ToBytes(int data) {
        byte[] src = new byte[]{(byte)(data & 255), (byte)(data >> 8 & 255)};
        return src;
    }

    public static byte[] data32ToBytes(int value) {
        byte[] src = new byte[]{(byte)(value & 255), (byte)(value >> 8 & 255), (byte)(value >> 16 & 255), (byte)(value >> 24 & 255)};
        return src;
    }

    public static final int byteArrayTo32(byte[] bytes, int offset) {
        byte result = 0;
        int result1 = result | bytes[offset + 3] & 255;
        result1 <<= 8;
        result1 |= bytes[offset + 2] & 255;
        result1 <<= 8;
        result1 |= bytes[offset + 1] & 255;
        result1 <<= 8;
        result1 |= bytes[offset] & 255;
        return result1;
    }

    public static final int byteArrayTo16(byte[] bytes, int offset) {
        byte result = 0;
        int result1 = result | bytes[offset + 1] & 255;
        result1 <<= 8;
        result1 |= bytes[offset] & 255;
        return result1;
    }

    public static final int byteArrayTo8(byte[] bytes, int offset) {
        byte dt = bytes[offset];
        return dt;
    }
}
