package com.device.ble.cmd;

import android.util.Log;

import com.device.ble.utils.CRCUtil;
import com.device.ble.utils.DataType;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dale on 21/4/15.
 */
public class BleResponse implements Serializable {
    private static final String TAG = BleResponse.class.getName();

    // ---------------------------------------------------------------------------------------------
    private int seq = 0;
    private int pkgCmd = 0;//数据包类功能码
    private int protoCmd = 0;//指令类型
    private byte[] protoData = null;//proto数据
    private byte[] firstData = null;//v1协议的第一层数据
    private ProtocolType.Version version;//协议版本

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    private String mac;

    public String getOriginBleName() {
        return ble_name;
    }

    public void setOriginBleName(String ble_name) {
        this.ble_name = ble_name;
    }

    private String ble_name;

    /**
     * 第一层v0+v1 蓝牙传输最外层数据（蓝牙分包发送）分组信息(1byte)+分组数据(mtu DATAS)
     * 第二层v0+v1 数据最外层 命令序号(seq 1byte)+功能码(cmd 1byte)+数据长度(len 2byte)+数据(DATA)+校验(crc 2byte)
     * 第三层v0 DATA 指令类型(4 bytes)+ 数据包长度(4 bytes)+protobuf
     * 第三层v1 DATA 版本协议（2 bytes）+厂家ID（2 bytes）+指令类型（2 bytes）+数据包长度（2 bytes）+
     * 是否加密（1 byte 0：no 1：yes）+预留位（1 byte）+校验位（2 bytes crc16）+protobuf
     */

    //v0和v1的兼容支持，通过数据的第3、4个字节来判断，short类型，为0就是v0协议，为1就是v1协议
    private boolean parse(byte[] pkg) {
        int len = DataType.byteArrayTo16(pkg, 2);
        if (len > 1024 || len + 6 != pkg.length) {
            Log.e(TAG, "组包长度错误");
            return false;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.reset();
        os.write(pkg, 4 + 2, 2);
        byte[] typeBytes = os.toByteArray();
        int typeVserion = DataType.byteArrayTo16(typeBytes, 0);
        if (typeVserion == 0) {
            return parseV0(pkg);
        } else {
            os.reset();
            os.write(pkg, 4 + 0, 2);
            typeBytes = os.toByteArray();
            typeVserion = DataType.byteArrayTo16(typeBytes, 0);
            if (typeVserion == 1) {
                return parseV1(pkg);
            } else {
                Log.i(TAG, "当前不支持该协议解析");
                return false;
            }
        }
    }

    private boolean parseV0(byte[] pkg) {
        // Check length
        int len = DataType.byteArrayTo16(pkg, 2);
        if (len > 1024 || len + 6 != pkg.length) {
            return false;
        }

        // Check crc
        if (!CRCUtil.check_crc16(pkg)) {
            return false;
        }

        // Parse
        seq = pkg[0];
        protoCmd = pkg[1];
        pkgCmd = 0;

        if (len == 0) {
            protoData = null;
            firstData = null;
        } else {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            os.reset();
            os.write(pkg, 4, pkg.length - 6);
            protoData = os.toByteArray();
            firstData = pkg;
        }
        version = ProtocolType.Version.v1_3g_flex;
        return true;
    }

    private boolean parseV1(byte[] pkg) {
        // Check length
        int len = DataType.byteArrayTo16(pkg, 2);
        if (len > 1024 || len + 6 != pkg.length) {
            Log.e(TAG, "组包长度错误");
            return false;
        }

        // Check end
        int end = DataType.byteArrayTo16(pkg, pkg.length - 2);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(pkg, 0, pkg.length - 2);
        int crc = CRCUtil.calc_crc16(os.toByteArray(), os.toByteArray().length, 0);
        os.reset();
        os = null;
        if (end != crc) {
            Log.e(TAG, "crc错误");
            return false;
        }

        // Parse
        seq = pkg[0];
        pkgCmd = pkg[1];
        protoCmd = DataType.byteArrayTo16(pkg, 8);
        if (len == 0) {
            protoData = null;
            firstData = null;
        } else {
            os = new ByteArrayOutputStream();
            os.reset();
            /**
             *                                                 08 00 10 ff ff 03 18 bd 80 9e d5 05
             *             01 00 00 00 01 00 0c 00 00 00 a9 6f 08 00 10 ff ff 03 18 bd 80 9e d5 05
             * 01 01 18 00 01 00 00 00 01 00 0c 00 00 00 a9 6f 08 00 10 ff ff 03 18 bd 80 9e d5 05 ff ff
             */
            os.write(pkg, 4 + 12, pkg.length - 6 - 12);
            protoData = os.toByteArray();

            ByteArrayOutputStream osFirst = new ByteArrayOutputStream();
            osFirst.reset();
            osFirst.write(pkg, 4, pkg.length - 6);
            firstData = osFirst.toByteArray();
        }
        version = ProtocolType.Version.v1_mini_other;
        return true;
    }

    ArrayList<byte[]> segments = new ArrayList<>();

    public boolean update(byte[] segment) {
        /**
         *  0x13 简单命令（直接重组命令）
         */
        if (segment[0] == 0x13 || segment[0] == 0x03) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            os.write(segment, 1, segment.length - 1);
            return parse(os.toByteArray());
        }
        /**
         *  0x11 命令的第一个分组(清空缓存，保存第一组）
         */
        if (segment[0] == 0x11 || segment[0] == 0x01) {
            segments.clear();
        }

        /**
         * 0x10 命令的中间分组（保存中间组）
         */
        segments.add(segment);
//        System.out.println(ByteUtil.byteArrayToHexString(segment));

        /**
         * 0x12 命令的最后一个分组（重组命令）
         */
        if (segment[0] == 0x12 || segment[0] == 0x02) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            for (byte[] seg : segments) {
                os.write(seg, 1, seg.length - 1);
            }
            return parse(os.toByteArray());
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getPkgCmd() {
        return pkgCmd;
    }

    public void setPkgCmd(int pkgCmd) {
        this.pkgCmd = pkgCmd;
    }

    public int getProtoCmd() {
        return protoCmd;
    }

    public void setProtoCmd(int protoCmd) {
        this.protoCmd = protoCmd;
    }

    public byte[] getData() {
        return protoData;
    }

    public void setData(byte[] protoData) {
        this.protoData = protoData;
    }

    public byte[] getFirstData() {
        return firstData;
    }

    public void setFirstData(byte[] firstData) {
        this.firstData = firstData;
    }

    public ProtocolType.Version getVersion() {
        return version;
    }

    public void setVersion(ProtocolType.Version version) {
        this.version = version;
    }
    // ---------------------------------------------------------------------------------------------
}
