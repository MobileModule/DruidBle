package com.device.ble.broadcast.v5;

import com.device.ble.utils.ByteUtil;

import java.io.Serializable;

public class BCV5DataEntity implements Serializable {
    public BCV5HeaderEntity header;//1 byte
    public byte[] sn = null;// 5 byte
    public int platform = 0;//1 byte 平台类别
    public int customer_id = 0;//1 byte 客户ID
    public int fw_version = 0;//2 byte
    public int hw_version = 2;//2 byte
    // 共2 byte
    public int power_value = 0;// 15 bit
    public int power_type = 0;// 1 bit //0 vlaue 剩余电量,1 value 剩余电压 单位 mv
    // 共 2 byte
    public int hash_cfg = 0;// 12 bit
    public int hash_adv = 0;// 4 bit
    //共 1 byte
    public int dev_status_time = 0;//4 bit
    public int dev_status_data = 0;//3 bit
    public int dev_status_active = 0;//1 bit //0 关机，1 开机
    //共 7 byte
    // optional data
    //共 2 byte
    public int temperature_value = 0;// 15 bit
    public int temperature_type = 0;// 1 bit
    public float temperature = 0;// 高精度：乘以10，低进度：除以10
    //
    public int odba = 0;//2 byte

    public String printLog() {
        StringBuffer sb = new StringBuffer();
        if (header != null) {
            if (header.version == 1) {
                sb.append("version-->" + this.header.version + "\n");
            }
            if (header.option_data_type == 1) {
                sb.append("option_data_type-->" + this.header.option_data_type + "\n");
            }
            if (sn != null) {
                sb.append("sn-->" + ByteUtil.byteArrayToHexString(sn) + "\n");
            }
            sb.append("platform-->" + platform + "\n");
            sb.append("customer_id-->" + customer_id + "\n");
            sb.append("fw_version-->" + fw_version + "\n");
            sb.append("hw_version-->" + hw_version + "\n");
            sb.append("power_value-->" + power_value + "\n");
            sb.append("power_type-->" + power_type + "\n");
            sb.append("hash_cfg-->" + hash_cfg + "\n");
            sb.append("hash_adv-->" + hash_adv + "\n");
            sb.append("dev_status_time-->" + dev_status_time + "\n");
            sb.append("dev_status_data-->" + dev_status_data + "\n");
            sb.append("dev_status_active-->" + dev_status_active + "\n");
            if (header.option_data_type == 1) {
                sb.append("temperature_value-->" + temperature_value + "\n");
                sb.append("temperature_type-->" + temperature_type + "\n");
                sb.append("odba-->" + odba + "\n");
            }
        }
        return sb.toString();
    }
}
