package com.device.ble.entity;

import com.device.ble.cmd.BleResponse;
import java.io.Serializable;

/**
 * Created by LeaAnder on 2018/3/13.
 */

public class CMDResp implements Serializable {
    private String mac="";
    private int protoRespType = 0;//协议包的命令类型
    private int pkgRespType = 0;//整包的命令类型
    private Object datas;//序列化的实例
    private byte[] bytesDatas;//设备返回的bytes原始数据
    private BleResponse response;

    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getProtoRespType() {
        return protoRespType;
    }
    public void setProtoRespType(int protoRespType) {
        this.protoRespType = protoRespType;
    }

    public int getPkgRespType() {
        return pkgRespType;
    }
    public void setPkgRespType(int pkgRespType) {
        this.pkgRespType = pkgRespType;
    }

    public Object getDatas() {
        return datas;
    }
    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public byte[] getBytesDatas() {
        return bytesDatas;
    }
    public void setBytesDatas(byte[] bytesDatas) {
        this.bytesDatas = bytesDatas;
    }

    public BleResponse getResponse() {
        return response;
    }
    public void setResponse(BleResponse response) {
        this.response = response;
    }
}
