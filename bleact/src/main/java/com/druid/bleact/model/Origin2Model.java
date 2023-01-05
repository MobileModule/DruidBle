package com.druid.bleact.model;

import java.io.Serializable;

public class Origin2Model implements Serializable {
    public int Timestamp = 0;  // 采样开始时间
    public int StartTick = 0;  // 记录起始时间(单位是0.125s)
    public int EndTick = 0;    // 记录结束时间(单位是0.125s)
    public int SamplingFreq = 25 ; // 采样频率，每秒次数
    public int X = 0;           // X 轴数据
    public int Y = 0;           // Y 轴数据
    public int Z = 0;           // Z 轴数据
}