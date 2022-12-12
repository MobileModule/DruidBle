package com.druid.bleact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class SettingModel implements Serializable {
    public int EnvSampleMode = 0;    // 环境采样模式: 0 - 关闭, 1 - 定时采样
    public int EnvSampleInterval = 0;    // 环境采样间隔: 秒
    public int BehaviorSampleMode = 0; // 行为采样模式: 0 - 关闭, 1 - 定时采样
    public int BehaviorSampleInterval = 0;    // 行为采样间隔: 秒
    public int GPSSampleMode = 0;    // GPS采样模式: 0 - 关闭, 1 - 定时采样, 2 - 持续开启(功耗高，不推荐)
    public int GPSSampleInterval = 0;    // GPS采样间隔: 秒
    public int CommunicationMode = 0; // 通信模式: 0 - 未定义, 1 - 定时上传, 2 - 持续在线(适合eDRX), 3 - 准点回传
    public int CommunicationInterval = 0;    // 通信间隔: 秒
    public String CommunicationTimeTable = "";    // 准点回传时间表

    public int ResetDevice = 0; // 复位请求: 0 - 不复位, 1 - 这次通信结束后立即复位

    ArrayList<TimeRangeModel> PowerOffTime = new ArrayList<>();// 计划关机时间范围
    public int PowerOffMode = 0; // 0: disable, 1: unix timestamp, 2：每小时从0分开始的秒数，2: 每天从0点开始的秒数， 3：每月从1号0点开始的秒数

    public int OTAFirmwareVersion = 0; // OTA固件版本
    public String OTAFirmwareID = ""; // OTA固件标识
    public int OTAForceUpgrade = 0;// OTA强制执行(丢弃数据，立即升级)
    public String OTAServerHost = ""; // OTA服务器域名或地址
    public int OTAServerPort = 0; // OTA服务器端口

    public int SMSMode = 0;// 短信上传模式: 0 - 关闭, 1 - 定时上传, 2 - 辅助上传(网络通信失败时上传)
    public int SMSInterval = 0; // 短信上传间隔: 秒

    public int AlarmMode = 0; // 报警模式: 0 - 关闭, 1 - 数传报警, 2 - 短信报警, 3 - 数传+短信

    ArrayList<TimeRangeModel> OriginTime = new ArrayList<>();// 原始数据配置的时间范围
    public int OriginMode = 0;// 0: disable, 1: unix timestamp, 2：每小时从0分开始的秒数，2: 每天从0点开始的秒数， 3：每月从1号0点开始的秒数
    public int EstrusSampleMode = 0; // 发情中间结果模式: 0 - 关闭, 1 - 定时采样
    public int EstrusSampleInterval = 0; // 发情中间结果间隔: 秒
}
