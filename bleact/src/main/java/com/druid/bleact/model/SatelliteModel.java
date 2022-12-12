package com.druid.bleact.model;

import java.io.Serializable;

/**
 * Created by druid on 2018/11/5.
 */

public class SatelliteModel implements Serializable {
    public int Id = 0; // 卫星ID
    public int Elevation = -1; // 仰角: 度, -1表示未知
    public int Azimuth = -1; // 方位角: 度, -1表示未知
    public int SNR = -1; // 信噪比, -1表示未知
    public int Used = 0; // 是否用于定位: 0 - 否, 1 - 是
    public int Type = 0; // 卫星类型: BD - 北斗, GP - GPS, GL - GLONASS, GN - 多系统
}
