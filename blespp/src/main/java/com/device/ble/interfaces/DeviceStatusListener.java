package com.device.ble.interfaces;

/**
 * Created by druid on 2020/3/2.
 * 设备状态返回-只支持mini设备
 */
public interface DeviceStatusListener {
    //active：当前设备开关机状态，active_：目标开关机状态
    void deviceStatusError(String mac, boolean active, boolean active_);
}
