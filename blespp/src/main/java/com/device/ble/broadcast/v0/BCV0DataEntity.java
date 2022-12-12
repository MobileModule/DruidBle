package com.device.ble.broadcast.v0;

import java.io.Serializable;

public class BCV0DataEntity implements Serializable {
    public BCV0HeaderEntity header;
    public int mac = 0;// 2 byte
    public int voltage = 0;// 2 byte 单位mv
    public int version = 0;// 2 byte
    public int userhash = 0;// 2 byte
    public int steps = 0;// 4 byte
    public int odba = 0;// 2 byte
    // 共 1 byte
    public int behavior_prediction = 0;// 6 bit
    public int behavior_static_flag = 0;// 1 bit
    public int behavior_estrus_flag = 0;// 1 bit
    //
    public int warning = 0;// 1 byte
    // 共 1 byte
    public int index_time = 0;// 4 bit
    public int index_data = 0;// 4 bit

    public void printLog() {
        if (header != null) {
            if (header.has_mac == 1) {
                System.out.println("mac-->" + this.mac + "");
            }
            if (header.has_voltage == 1) {
                System.out.println("voltage-->" + this.voltage + "");
            }
            if (header.has_version == 1) {
                System.out.println("version-->" + this.version + "");
            }
            if (header.has_userhash == 1) {
                System.out.println("userhash-->" + this.userhash + "");
            }
            if (header.has_steps == 1) {
                System.out.println("steps-->" + this.steps + "");
            }
            if (header.has_odba == 1) {
                System.out.println("odba-->" + this.odba + "");
            }
            if (header.has_behavior == 1) {
                System.out.println("behavior_prediction-->" + this.behavior_prediction + "");
                System.out.println("behavior_static_flag-->" + this.behavior_static_flag + "");
                System.out.println("behavior_estrus_flag-->" + this.behavior_estrus_flag + "");
            }
            if (header.has_warning == 1) {
                System.out.println("warning-->" + this.warning + "");
            }
            if (header.has_index == 1) {
                System.out.println("index_time-->" + this.index_time + "");
                System.out.println("index_data-->" + this.index_data + "");
            }
        }
    }

}
