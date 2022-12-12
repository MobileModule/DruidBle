package com.device.ble.entity;

public interface BleConnectState {
    int STATE_NOT_FOUND = 1;//设备未找到
    int STATE_CONNECTING = 2;//设备连接中
    int STATE_GATT_DISCOVERY = 3;//发现服务
    int STATE_GATT_DISCOVERY_FAILED = 4;//发现服务失败
    int STATE_GATT_SERVICE_ENABLE_FAILED = 5;//设置服务失败
    int STATE_CONNECT_FAILED = 6;//连接失败
    int STATE_DISCONNECTED = 7;//蓝牙断开连接
    int STATE_CONNECTED = 8;//蓝牙连接成功
}