package com.device.ble.entity;

import com.device.ble.utils.BluetoothBroadcastUtils;

import java.io.Serializable;

/**
 * Created by druid on 2019/9/23.
 */

public class HubBroadcastModel implements Serializable {
    public String MAC = "";
    public String QUEST_MAC = "";
    public String device_name = "";// 设备名 broadcast UUID
    public String uuid = "";//
    public long timestamp = 0;
    public int FirmwareVersion = 0; // 固件版本
    public int HardwareVersion = 0;//硬件版本
    public int BatteryVoltage = 0;// 电池电压: 毫伏, 精度到0.001V
    public int BleRSSI = 0; // 接收信号强度(dBm)
    public int DeviceStatus = 0; // 设备状态，0：失活  1：激活   2：库存
    public int DeviceType = -1;
    //所属平台: 0.Unknow, 1.druid_bird, 2.druid_cattle, 3.druid_basic, 4.factory, 10.cas_bird, 11.koeco_bird
    public BluetoothBroadcastUtils.PLATFORM.Platform platform=null;

    public void parseDeviceStatus(boolean isOn) {
        if (isOn) {
            DeviceStatus = 1;
        } else {
            DeviceStatus = 0;
        }
    }

    public String toString() {
        return "MAC:" + MAC + "-->" +
                "device_name:" + device_name + "-->" +
                "uuid:" + uuid + "-->" +
                "timestamp:" + timestamp + "," +
                "FirmwareVersion:" + FirmwareVersion + "-->" +
                "HardwareVersion:" + HardwareVersion + "-->" +
                "BatteryVoltage:" + BatteryVoltage + "-->" +
                "BleRSSI:" + BleRSSI + "-->" +
                "DeviceStatus:" + DeviceStatus + "-->" +
                "DeviceType:" + DeviceType + "-->" +
                "platform:" + platform + "";
    }
}
