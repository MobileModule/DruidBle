package com.druid.bleact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by druid on 2018/11/5.
 */

public class GPSModel implements Serializable {
    public int Timestamp = 0; // 定位时间, unix时间戳
    public int Latitude = 2000000000; // 纬度: 度, 精确到小数点后第7位, 定位失败为200
    public int Longitude = 2000000000; // 经度: 度, 精确到小数点后第7位, 定位失败为200
    public int Altitude = 0; // 海拔高度: 米, 精确到小数点后2位
    public int Quality = 0; // 定位质量: 0 - 无定位, 1 - 普通定位, 2 - 差分定位, 6 - 粗略定位
    public int Mode = 0; // 定位模式: 2 - 2D,  3 - 3D
    public int PDOP = 0; // 位置精确因子: 精确到小数点后2位
    public int HDOP = 0; // 水平精确因子: 精确到小数点后2位
    public int VDOP = 0; // 垂直精确因子: 精确到小数点后2位
    public int SatellitesUsed = 0; // 用于定位的卫星数
    public int SatellitesInView = 0; // 发现的卫星数
    public int Speed = 0; // 速度: 米/秒, 精确到小数点后1位
    public int Course = 0; // 航向: 度, 精确到小数点后1位
    public int HACC = 0; // 水平定位精确: 米, 精确到小数点后1位
    public int VACC = 0; // 垂直定位精确: 米, 精确到小数点后1位
    public int FixTime = 0; // 定位耗时: 秒
    public ArrayList<SatelliteModel> Satellites = new ArrayList<>();// 卫星信息
}
