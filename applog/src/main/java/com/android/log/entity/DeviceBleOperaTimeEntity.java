package com.android.log.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class DeviceBleOperaTimeEntity implements Serializable {
    private static final long serialVersionUID = 8822404745689098355L;
    @Id(autoincrement = true)
    public Long id;
    public String batch_uuid = "";//操作序列号
    public long timestamp_start = 0L;//操作开始时间
    public long timestamp_end = 0L;//操作结束时间
    public int opera_type = 0;//操作类型
    public int device_all_num = 0;//操作的全部设备
    @Keep
    public DeviceBleOperaTimeEntity(Long id, String batch_uuid,
            long timestamp_start, long timestamp_end, int opera_type,
            int device_all_num) {
        this.id = id;
        this.batch_uuid = batch_uuid;
        this.timestamp_start = timestamp_start;
        this.timestamp_end = timestamp_end;
        this.opera_type = opera_type;
        this.device_all_num = device_all_num;
    }

    @Generated(hash = 78088800)
    public DeviceBleOperaTimeEntity() {
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
    public int getDevice_all_num() {
        return this.device_all_num;
    }
    public void setDevice_all_num(int device_all_num) {
        this.device_all_num = device_all_num;
    }
    public int getOpera_type() {
        return this.opera_type;
    }
    public void setOpera_type(int opera_type) {
        this.opera_type = opera_type;
    }
}
