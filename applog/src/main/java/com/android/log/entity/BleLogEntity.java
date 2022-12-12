package com.android.log.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class BleLogEntity implements Serializable {
    private static final long serialVersionUID = 1131316232774213216L;
    @Id(autoincrement = true)
    public Long id;
    public String user_id = "";
    public String device_id = "";
    public String mac = "";
    public String operation_type = "";
    public String operation_result = "";
    public String operation_tag = "";
    public String operation_details = "";
    public String phone_series = "";
    public String phone_model = "";
    public String phone_id = "";
    public String timestamp = "";
    public boolean checked = false;

    @Keep
    public BleLogEntity(Long id, String user_id, String device_id, String mac,
            String operation_type, String operation_result, String operation_tag,
            String operation_details, String phone_series, String phone_model,
            String phone_id, String timestamp, boolean checked) {
        this.id = id;
        this.user_id = user_id;
        this.device_id = device_id;
        this.mac = mac;
        this.operation_type = operation_type;
        this.operation_result = operation_result;
        this.operation_tag = operation_tag;
        this.operation_details = operation_details;
        this.phone_series = phone_series;
        this.phone_model = phone_model;
        this.phone_id = phone_id;
        this.timestamp = timestamp;
        this.checked = checked;
    }


    @Generated(hash = 315561205)
    public BleLogEntity() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getOperation_type() {
        return this.operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getOperation_result() {
        return this.operation_result;
    }

    public void setOperation_result(String operation_result) {
        this.operation_result = operation_result;
    }

    public String getOperation_details() {
        return this.operation_details;
    }

    public void setOperation_details(String operation_details) {
        this.operation_details = operation_details;
    }

    public String getPhone_series() {
        return this.phone_series;
    }

    public void setPhone_series(String phone_series) {
        this.phone_series = phone_series;
    }

    public String getPhone_model() {
        return this.phone_model;
    }

    public void setPhone_model(String phone_model) {
        this.phone_model = phone_model;
    }

    public String getPhone_id() {
        return this.phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperation_tag() {
        return this.operation_tag;
    }

    public void setOperation_tag(String operation_tag) {
        this.operation_tag = operation_tag;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
