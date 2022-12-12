package com.device.ble.broadcast.v1;

import java.io.Serializable;

public class BCV1DataEntity implements Serializable {
	public BCV1HeaderEntity header = null;
	public int mac = 0;// 2 byte
	public int fw_version = 0;// 2 byte
	// 共 2 byte
	public int hash_cfg = 0;// 12 bit
	public int hash_adv = 0;// 4 bit
	//
	public int dev_type = 0;// 2 byte
	// 共 1 byte
	public int temperature_value = 0;// 15 bit
	public int temperature_type = 0;// 1 bit
	public float temperature=0;//高精度：乘以10，低进度：除以10
	//
	public int pressure = 0;// 2 byte
	public int voltage = 0;// 2 byte 单位mv
	public int power = 0;// 1 byte
	// 共 1 byte
	public int index_time = 0;// 4 bit
	public int index_data = 0;// 4 bit
	// 共 1 byte
	public int status_hw_error = 0;// 1 bit
	public int status_sw_error = 0;// 1 bit
	public int status_reserved = 0;// 4 bit
	public int status_estrus_flag = 0;// 1 bit
	public int status_static_flag = 0;// 1 bit
	// 共 2 byte
	public int behavior_version = 0;// 2 bit
	public int behavior_eating = 0;// 7 bit
	public int behavior_ruminate = 0;// 7 bit
	//
	public int behavior_odba = 0;// 2 byte
	public int behavior_steps = 0;// 2 byte
	// 共 2 byte
	public int old_behavior_version = 0;// 2 bit
	public int old_behavior_eating = 0;// 7 bit
	public int old_behavior_ruminate = 0;// 7 bit
	//
	public int old_behavior_odba = 0;// 2 byte
	public int old_behavior_steps = 0;// 2 byte

	public void printLog() {
		if (header != null) {
			if (header.has_mac == 1) {
				System.out.println("mac-->" + this.mac + "");
			}
			if (header.has_fw_version == 1) {
				System.out.println("fw_version-->" + this.fw_version + "");
			}
			//
			if (header.has_cfg_hash == 1) {
				System.out.println("hash_cfg-->" + this.hash_cfg + "");
			}
			if (header.has_adv_hash == 1) {
				System.out.println("hash_adv-->" + this.hash_adv + "");
			}
			//
			if (header.has_dev_type == 1) {
				System.out.println("dev_type-->" + this.dev_type + "");
			}
			//
			if (header.has_temperature == 1) {
				System.out.println("temperature_type-->" + this.temperature_type + "");
				System.out.println("temperature_value-->" + this.temperature_value + "");
			}
			//
			if (header.has_pressure == 1) {
				System.out.println("pressure-->" + this.pressure + "");
			}
			if (header.has_voltage == 1) {
				System.out.println("voltage-->" + this.voltage + "");
			}
			if (header.has_power == 1) {
				System.out.println("power-->" + this.power + "");
			}
			//
			if (header.has_index == 1) {
				System.out.println("index_time-->" + this.index_time + "");
				System.out.println("index_data-->" + this.index_data + "");
			}
			//
			if (header.has_status == 1) {
				System.out.println("status_hw_error-->" + this.status_hw_error + "");
				System.out.println("status_sw_error-->" + this.status_sw_error + "");
				System.out.println("status_reserved-->" + this.status_reserved + "");
				System.out.println("status_estrus_flag-->" + this.status_estrus_flag + "");
				System.out.println("status_static_flag-->" + this.status_static_flag + "");
			}
			//
			if (header.has_behavior == 1) {
				System.out.println("behavior_version-->" + this.behavior_version + "");
				System.out.println("behavior_eating-->" + this.behavior_eating + "");
				System.out.println("behavior_ruminate-->" + this.behavior_ruminate + "");
				System.out.println("behavior_odba-->" + this.behavior_odba + "");
				System.out.println("behavior_steps-->" + this.behavior_steps + "");
			}
			//
			if (header.has_old_behavior == 1) {
				System.out.println("old_behavior_version-->" + this.old_behavior_version + "");
				System.out.println("old_behavior_eating-->" + this.old_behavior_eating + "");
				System.out.println("old_behavior_ruminate-->" + this.old_behavior_ruminate + "");
				System.out.println("old_behavior_odba-->" + this.old_behavior_odba + "");
				System.out.println("old_behavior_steps-->" + this.old_behavior_steps + "");
			}
		}
	}
}
