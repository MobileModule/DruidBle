package com.device.ble.cmd;

import com.device.ble.utils.CRCUtil;
import com.device.ble.utils.DataType;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by dale on 20/4/15.
 */
public class BleRequest {
    public static final int CMD_1 = 0x01;//功能码

    public static final int VERSION = 0x01;//版本协议
    public static final int FACTORY_ID = 0x00;//厂家ID
    public static final int ENCRYPTION = 0x00;//是否加密 0否，1是
    public static final int RESERVE = 0x00;//预留位

    private int seq = 0;
    private int cmd = 0;
    private int pkgCmd = 0;
    private byte[] data = null;

    public BleRequest(int seq, int pkgCmd, int cmd, byte[] data) {
        this.seq = seq;
        this.pkgCmd = pkgCmd;
        this.cmd = cmd;
        this.data = data;
    }

    public BleRequest(byte[] data) {
        this.data = data;
    }

    public int getCmd() {
        return cmd;
    }

    public byte[] getData() {
        return data;
    }

    /**
     * 第一层v0+v1 蓝牙传输最外层数据（蓝牙分包发送）分组信息(1byte)+分组数据(mtu DATAS)
     * 第二层v0+v1 数据最外层 命令序号(seq 1byte)+功能码(cmd 1byte)+数据长度(len 2byte)+数据(DATA)+校验(crc 2byte)
     * 第三层v0 DATA 指令类型(4 bytes)+ 数据包长度(4 bytes)+protobuf
     * 第三层v1 DATA 版本协议（2 bytes）+厂家ID（2 bytes）+指令类型（2 bytes）+数据包长度（2 bytes）+
     * 是否加密（1 byte 0：no 1：yes）+预留位（1 byte）+校验位（2 bytes crc16）+protobuf
     */

    public ArrayList<byte[]> asSegments(ProtocolType.Version version, int mtu) {
        if (version == ProtocolType.Version.v1_3g_flex) {
            return asSegmentsV0(mtu);
        }
        if (version == ProtocolType.Version.v1_mini_other) {
            return asSegmentsV1(mtu);
        }
        return new ArrayList<>();
    }

    public ArrayList<byte[]> asSegmentsV0(int mtu) {
        ArrayList<byte[]> segments = new ArrayList<>();

        // Step one - package
        int data_len = (data == null) ? 0 : data.length;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write((byte) seq);
        os.write((byte) pkgCmd);
        os.write((byte) (data_len & 0xff));
        os.write((byte) ((data_len >> 8) & 0xff));

        if (data_len > 0) {
            os.write(data, 0, data_len);
        }

        int crc = CRCUtil.crc16(os.toByteArray());
        os.write((byte) (crc & 0xff));
        os.write((byte) ((crc >> 8) & 0xff));

        byte[] pkg = os.toByteArray();
        int len = pkg.length;

        // Step two - segments
        mtu -= 1;
        int i = 0;
        while (i < len) {
            int seg_len = (i + mtu > len) ? len - i : mtu;
            int seg_hdr = 0x10;
            if (i == 0) {
                seg_hdr |= 0x01;
            }
            if (i + seg_len >= len) {
                seg_hdr |= 0x02;
            }
            os.reset();
            os.write(seg_hdr);
            os.write(pkg, i, seg_len);
            segments.add(os.toByteArray());
            i += seg_len;
        }

        return segments;
    }

    public ArrayList<byte[]> asSegmentsV1(int mtu) {
        ArrayList<byte[]> segments = new ArrayList<>();
        byte[] data = doDeal();
        // Step one - package
        int data_len = (data == null) ? 0 : data.length;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write((byte) seq);
        os.write((byte) pkgCmd);
        os.write((byte) (data_len & 0xff));
        os.write((byte) ((data_len >> 8) & 0xff));

        if (data_len > 0) {
            os.write(data, 0, data_len);
        }

        int crc = CRCUtil.calc_crc16(os.toByteArray(), os.toByteArray().length, 0);
        os.write((byte) (crc & 0xff));
        os.write((byte) ((crc >> 8) & 0xff));

        byte[] pkg = os.toByteArray();
        int len = pkg.length;
        // Step two - segments
        mtu -= 1;
        int i = 0;
        while (i < len) {
            int seg_len = (i + mtu > len) ? len - i : mtu;
            int seg_hdr = 0x10;
            if (i == 0) {
                seg_hdr |= 0x01;
            }
            if (i + seg_len >= len) {
                seg_hdr |= 0x02;
            }
            os.reset();
            os.write(seg_hdr);
            os.write(pkg, i, seg_len);
            segments.add(os.toByteArray());
            i += seg_len;
        }

        return segments;
    }

    /**
     * proto数据封装成协议
     * 版本协议（2 bytes）+厂家ID（2 bytes）+指令类型（2 bytes）+数据包长度（2 bytes）
     * +是否加密（1 byte 0：no 1：yes）+预留位（1 byte）+校验位（2 bytes crc16）+protobuf
     */
   public byte[] doDeal() {

        // Step one - package
        int data_len = (this.data == null) ? 0 : this.data.length;
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] version = DataType.data16ToBytes(VERSION);
        os.write(version, 0, version.length);

        byte[] factory_id = DataType.data16ToBytes(FACTORY_ID);
        os.write(factory_id, 0, factory_id.length);

        byte[] cmd_my = DataType.data16ToBytes(cmd);
        os.write(cmd_my, 0, cmd_my.length);

        byte[] length = DataType.data16ToBytes(data_len);
        os.write(length, 0, length.length);

        os.write((byte) ENCRYPTION);
        os.write((byte) RESERVE);

        //crc_16
        int crc = CRCUtil.calc_crc16(os.toByteArray(), os.toByteArray().length, 0);
        int crc_val = CRCUtil.calc_crc16_val(crc, this.data, data_len, 0);
        byte[] crcs = DataType.data16ToBytes(crc_val);
        os.write(crcs, 0, crcs.length);

        if (data_len > 0) {
            os.write(this.data, 0, data_len);
        }

        byte[] pkg = os.toByteArray();

        return pkg;
    }
}
