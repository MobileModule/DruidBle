package com.druid.bleact.model;

import java.io.Serializable;

public class Origin22Model implements Serializable {
    public int StartSecond = 0;    // 记录开始时间整数部分(s)
    public int StartMilliSecond = 0;  // 记录开始时间小数部分(ms)
    public long Timestamp = 0; //开始时间(ms)
    public int Duration = 0;    // 持续时间(单位是ms)
    public int SamplingFreq = 25; // 采样频率，每秒次数
    public int X = 0;           // X 轴数据
    public int Y = 0;           // Y 轴数据
    public int Z = 0;           // Z 轴数据
}