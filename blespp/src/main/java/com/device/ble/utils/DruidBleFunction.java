package com.device.ble.utils;

/**
 * Created by druid on 2020/3/2.
 */

public enum DruidBleFunction {
    DEVICE_ON,//设备开机
    DEVICE_OFF,//设备关机
    DEVICE_MARK,//设备实时数据打标
    DEVICE_COLLECT_DATAS,//数据收取
    DEVICE_OTA,//设备ota升级
    DEVICE_SEARCH,//设备查找
    DEVICE_RESET,//设备重置
    HUB_ON,//蓝牙桩开机
    HUB_OFF,//蓝牙桩关机
    DEVICE_SETTING,//设备setting下发
    HUB_SETTING,//蓝牙桩setting下发
    HUB_MARK,//蓝牙桩远程打标
    HUB_COLLECT_DATAS,//蓝牙桩收取数据
    HUB_QUEST,//网关中继功能
    QUEST_SEARCH,//中继搜索终端
    UNKNOWN
}
