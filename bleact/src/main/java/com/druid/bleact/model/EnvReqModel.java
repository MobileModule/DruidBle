package com.druid.bleact.model;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class EnvReqModel {
    public int Timestamp = 0;
    public int BatteryPower = 0; // 剩余电量: 百分比
    public int BatteryVoltage = 0; // 电池电压: 毫伏
    public int ChargeVoltage = 0; // 充电电压: 毫伏
    public int ChargeCurrent = 0; // 充电电流: 毫安, 精确到小数点后两位(*✖️100)
    public int InnerTemperature = 0; // 内部温度: 摄氏度, 精确到小数点后一位(*10)
    public int InnerHumidity = 0; // 内部相对湿度: 精确到1%
    public int InnerPressure = 0; // 内部气压: 单位：帕
    public int AmbientLight = 0; // 光照强度: 单位：勒克斯
}
