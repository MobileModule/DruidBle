package com.druid.bleact.model;

import java.io.Serializable;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class RegistorReqModel implements Serializable {
    public IdentityModel identityModel;
    public String IMSI = "";
    public String IMEI = "";
    public String MAC = ""; // 蓝牙MAC地址
    public int DeviceType = 0; // 设备类型
    public int HardwareVersion = 0; // 硬件版本
    public int FirmwareVersion = 0; // 固件版本
    public int Status = 0;  // 自检状态: 0 - 无异常
    public int BatteryVoltage = 0; // 电池电压: 毫伏
    public int BatteryPower = 0; // 电池电量: 百分比
    public int SignalStrength = 0; // 信号强度
    public int BitErrorRate = 0; // 误码率
    public int RadioAccessTechnology = 0; // 网络类型
    public int NetworkOperator = 0; // 运营商代码
}
