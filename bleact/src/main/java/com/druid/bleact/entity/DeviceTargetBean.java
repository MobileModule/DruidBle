package com.druid.bleact.entity;

import com.device.ble.core.BleConnParameter;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceTargetBean implements Serializable {
    public String firstMac = "";
    public ArrayList<BleConnParameter> devices = new ArrayList<>();
}
