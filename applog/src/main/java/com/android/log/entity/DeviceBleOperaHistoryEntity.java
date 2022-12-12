package com.android.log.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class DeviceBleOperaHistoryEntity implements Serializable {
    private static final long serialVersionUID = 1131316232774213218L;
    @Id(autoincrement = true)
    public Long id;
    public String batch_uuid = "";//操作序列号
    public String device_id = "";
    public String mac = "";
    public String uuid = "";
    public String sn = "";
    public int mark = 0;
    public int device_type = 0;
    public int device_type_enum = 0;//0:设备，1：网关，2：quest
    public int firmware_version = 0;//
    public double battery_voltage = 0;
    public int device_status = -1;//-1：未知，0：开机，1：关机
    public long timestamp_start = 0L;//操作开始时间
    public long timestamp_end = 0L;//操作结束时间
    public int opera_success_type = 0;// 0：失败，1：成功，-1：未执行，-2：不支持该操作
    public int opera_type = 0;//操作类型
    public int hardware_version = 0;//硬件版本
    public String result_details = "";//操作详情

    @Keep
    public DeviceBleOperaHistoryEntity(Long id, String batch_uuid, String device_id, String mac, String uuid,
            String sn, int mark, int device_type, int device_type_enum, int firmware_version,
            double battery_voltage, int device_status, long timestamp_start, long timestamp_end,
            int opera_success_type, int opera_type, int hardware_version, String result_details) {
        this.id = id;
        this.batch_uuid = batch_uuid;
        this.device_id = device_id;
        this.mac = mac;
        this.uuid = uuid;
        this.sn = sn;
        this.mark = mark;
        this.device_type = device_type;
        this.device_type_enum = device_type_enum;
        this.firmware_version = firmware_version;
        this.battery_voltage = battery_voltage;
        this.device_status = device_status;
        this.timestamp_start = timestamp_start;
        this.timestamp_end = timestamp_end;
        this.opera_success_type = opera_success_type;
        this.opera_type = opera_type;
        this.hardware_version = hardware_version;
        this.result_details = result_details;
    }

    @Generated(hash = 1901429348)
    public DeviceBleOperaHistoryEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatch_uuid() {
        return this.batch_uuid;
    }

    public void setBatch_uuid(String batch_uuid) {
        this.batch_uuid = batch_uuid;
    }

    public String getDevice_id() {
        return this.device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getMark() {
        return this.mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getDevice_type() {
        return this.device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public int getDevice_type_enum() {
        return this.device_type_enum;
    }

    public void setDevice_type_enum(int device_type_enum) {
        this.device_type_enum = device_type_enum;
    }

    public int getFirmware_version() {
        return this.firmware_version;
    }

    public void setFirmware_version(int firmware_version) {
        this.firmware_version = firmware_version;
    }

    public double getBattery_voltage() {
        return this.battery_voltage;
    }

    public void setBattery_voltage(double battery_voltage) {
        this.battery_voltage = battery_voltage;
    }

    public int getDevice_status() {
        return this.device_status;
    }

    public void setDevice_status(int device_status) {
        this.device_status = device_status;
    }

    public long getTimestamp_start() {
        return this.timestamp_start;
    }

    public void setTimestamp_start(long timestamp_start) {
        this.timestamp_start = timestamp_start;
    }

    public long getTimestamp_end() {
        return this.timestamp_end;
    }

    public void setTimestamp_end(long timestamp_end) {
        this.timestamp_end = timestamp_end;
    }

    public int getOpera_success_type() {
        return this.opera_success_type;
    }

    public void setOpera_success_type(int opera_success_type) {
        this.opera_success_type = opera_success_type;
    }

    public int getOpera_type() {
        return this.opera_type;
    }

    public void setOpera_type(int opera_type) {
        this.opera_type = opera_type;
    }

    public String getResult_details() {
        return this.result_details;
    }

    public void setResult_details(String result_details) {
        this.result_details = result_details;
    }

    public int getHardware_version() {
        return this.hardware_version;
    }

    public void setHardware_version(int hardware_version) {
        this.hardware_version = hardware_version;
    }

}
