package com.device.ble.entity;

public interface FailedReasonState {
    /**
     * 0x01 蓝牙不可用
     * 0x02 蓝牙地址无效
     * 0x03 蓝牙未搜索到
     * 0x04 蓝牙连接失败
     * 0x05 蓝牙重连失败
     * 0x06 蓝牙断开连接
     */
    int BLE_NOT_USED = 0x01;
    int BLE_ADDRESS_INVALID = 0x02;
    int BLE_SEARCH_FAILED = 0x03;
    int BLE_CONN_FAILED = 0x04;
    int BLE_CONN_AGAIN_FAILED = 0x05;
    int BLE_DISCONNECTED = 0x06;
}
