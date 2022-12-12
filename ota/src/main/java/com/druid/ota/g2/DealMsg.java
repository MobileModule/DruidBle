package com.druid.ota.g2;


import android.util.Log;

import com.device.ble.utils.ByteUtil;
import com.druid.ota.cmd.DataType;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * 项目名称：pom
 * 类描述：deal&seal msg
 * 创建人：LeaAnder
 * 创建时间：2016/10/14 11:46
 * 修改人：LeaAnder
 * 修改时间：2016/10/14 11:46
 * 修改备注：
 */
public class DealMsg {
    /**
     * decode received bluetooth message
     */
    public static ArrayList<byte[]> temps = new ArrayList<>();

    public static OtaBean decodeData(byte[] data) {
//        Log.i("BBB", ByteUtil.byteArrayToHexString(data));
        if (temps.size() == 0) {
            if (data.length > 2) {
                OtaBean otaBean = getOtaBean(data);
                if (otaBean == null) {
                    temps.add(data);
                    return null;
                } else {
                    return otaBean;
                }
            } else {
                temps.add(data);
                return null;
            }
        } else {
            if (data.length > 2) {
                OtaBean otaBean = getOtaBean(data);
                if (otaBean == null) {
                    byte[] ddd = getAllDatas(data);
                    otaBean = getOtaBean(ddd);
                    if (otaBean == null) {
                        temps.add(data);
                        return null;
                    } else {
                        return otaBean;
                    }
                } else {
                    return otaBean;
                }
            } else {
                temps.add(data);
                return null;
            }
        }
    }

    private static OtaBean getOtaBean(byte[] datas) {
        try {
            OtaBean otaBean = new OtaBean();
            int MsgType = DataType.byteArrayTo32(datas, 0);
            int MsgLen = DataType.byteArrayTo32(datas, 4);
//            Log.i("经典蓝牙", String.format("解析数据长度%s,解析总长度%s", MsgLen, datas.length));
            if(MsgLen>1024){
                return null;
            }else {
                byte[] d = new byte[MsgLen];
                System.arraycopy(datas, 8, d, 0, MsgLen);
                otaBean.MsgLen = MsgLen;
                otaBean.MsgType = MsgType;
                otaBean.datas = d;
                temps.clear();
                return otaBean;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] getAllDatas(byte[] datas) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (int i = 0; i < temps.size(); i++) {
            byte[] bbb = temps.get(i);
            os.write(bbb, 0, bbb.length);
        }
        os.write(datas, 0, datas.length);
        byte[] pkg = os.toByteArray();
        Log.i("BBB组包", ByteUtil.byteArrayToHexString(pkg));
        return pkg;
    }

    /**
     * encode data send to bluetooth
     */
    public static byte[] encodeData(byte[] data, int MsgType) {
        byte[] encodeBytes = new byte[data.length + 8];
        System.arraycopy(data, 0, encodeBytes, 8, data.length);
        byte[] Type = DataType.data32ToBytes(MsgType);
        byte[] MsgLen = DataType.data32ToBytes(data.length);
        System.arraycopy(Type, 0, encodeBytes, 0, 4);
        System.arraycopy(MsgLen, 0, encodeBytes, 4, 4);
        return encodeBytes;
    }
}
