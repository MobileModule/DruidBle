package com.device.ble.broadcast;

import com.device.ble.broadcast.v0.BCV0DataEntity;
import com.device.ble.broadcast.v1.BCV1DataEntity;
import com.device.ble.broadcast.v2.BCV2DataEntity;
import com.device.ble.broadcast.v3_1.BCV3_1DataEntity;
import com.device.ble.broadcast.v5.BCV5DataEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class BCProtoDataEntity implements Serializable {
    public int rssi=0;
    public BCType type = BCType.unkown;
    public BCV0DataEntity v0Data = null;
    public BCV1DataEntity v1Data = null;
    public ArrayList<BCV2DataEntity> v2Datas = new ArrayList<>();//单包协议和双包协议
    public BCV3_1DataEntity v3Data = null;//目前仅支持单包数据解析，ring支持该广播
    public BCV5DataEntity v5Data = null;
}