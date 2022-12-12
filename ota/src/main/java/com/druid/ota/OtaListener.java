package com.druid.ota;

import com.druid.ota.bean.OtaInfo;

/**
 * Created by LeaAnder on 2017/6/1.
 */
public interface OtaListener {
    void unAvailable();//蓝牙不可用
    void receiveData(byte[] datas);//接收到数据
    void connect();//连接成功
    void disconnect();//连接断开
    void connFailed();//连接成功
    void deviceInfo(OtaInfo otaInfo);//蓝牙回传设备信息
    void binTips(int code);//0:固件无效,1:请下载最新固件
    void upload(int progress);//上传的字节
    void availableBin();//固件有效开始下载
    void completeDownload();//固件完成下载
}
