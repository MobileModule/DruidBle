package com.device.ble.core;

import android.text.TextUtils;

import com.device.ble.broadcast.BCProtoDataEntity;
import com.device.ble.entity.HubBroadcastModel;
import com.device.ble.utils.BluetoothBroadcastUtils;

import java.io.Serializable;

public class BleConnParameter implements Serializable {
    public int mark = 0;
    public String sn = "";
    public String mac = "";//必填 大写
    private String ble_name = "";//必填
    public BCProtoDataEntity bcData = null;//必填
    public String deviceId = "";
    public int firmware_version = 0;
    public Object object = null;//自定义对象
    public HubBroadcastModel broadcastDevice = null;//广播透传状态
    public String collaboration_id="";//协作id

    public void setBleName(String ble_name) {//特殊处理
        this.ble_name = ble_name;
    }

    //是否收到生成蓝牙广播名称
    public String getCreateBleFileName(boolean force_basic) {
        String ble_name_create = BluetoothBroadcastUtils.DEVICE_TRANSFER.
                getFileName(ble_name, mac, bcData);
        if (force_basic) {
            ble_name_create = BluetoothBroadcastUtils.DEVICE_TRANSFER.
                    forceBasicFileNameByFileName(ble_name_create);
        }
        return ble_name_create;
    }

    public String getOriginBleName() {
        if (TextUtils.isEmpty(ble_name)) {
            return getCreateBleFileName(false);
        } else {
            return ble_name;
        }
    }
}

