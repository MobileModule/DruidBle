package com.device.ble.broadcast.v3_1;

import com.device.ble.utils.ByteUtil;

import java.io.Serializable;

public class BCV3_1DataEntity implements Serializable {
	public BCV3_1HeaderEntity header = null;
	public byte[] sn = null;// 5 byte
	public int fw_version = 0;// 1 byte
	public int hw_version = 0;// 2 byte
	// 共2 byte
	public int power_value = 0;// 15 bit
	public int power_type = 0;// 1 bit //0 vlaue 剩余电量,1 value 剩余电压 单位 mv
	// 共 2 byte
	public int hash_cfg = 0;// 12 bit
	public int hash_adv = 0;// 4 bit
	//
	public int dev_status = 0;// 1 byte
	public int conn_index = 0;// 4 bit
	public int sys_state = 0;// 4 bit //1 - 整测完成，等待升级 ,2 - 库存,3 - 激活,4 - 失活
	// 共 1 byte
	public int temperature_value = 0;// 15 bit
	public int temperature_type = 0;// 1 bit
	public float temperature = 0;// 高精度：乘以10，低进度：除以10
	// 共3 byte
	public int behavior_version = 0;// 4 bit //0:ring,1:以色列
	public int behavior_time = 0;// 4 bit
	public int behavior_odba = 0;// 2 byte
	// 共1 byte
	public int alarm_hw_error = 0;// 1 bit
	public int alarm_sw_error = 0;// 1 bit
	public int alarm_reserved = 0;// 4 bit
	public int alarm_estrus_flag = 0;// 1 bit
	public int alarm_static_flag = 0;// 1 bit
	// 共1 byte
	public int platform = 0;// 1 byte

	public void printLog() {
		if (header != null) {
			System.out.println("version-->" + this.header.version + "");
			System.out.println("reserved-->" + this.header.reserved + "");
			System.out.println("has_behavior-->" + this.header.has_behavior + "");
			System.out.println("has_env-->" + this.header.has_env + "");
			System.out.println("has_alarm-->" + this.header.has_alarm + "");
			System.out.println("has_platform-->" + this.header.has_platform + "");
			System.out.println("expand_flag-->" + this.header.expand_flag + "");
			System.out.println("expand-->" + this.header.expand + "");
			//
			System.out.println("sn-->" + ByteUtil.byteArrayToHexString(this.sn) + "");
			System.out.println("fw_version-->" + this.fw_version + "");
			System.out.println("hw_version-->" + this.hw_version + "");
			//
			System.out.println("power_value-->" + this.power_value + "");
			System.out.println("power_type-->" + this.power_type + "");
			//
			System.out.println("hash_cfg-->" + this.hash_cfg + "");
			System.out.println("hash_adv-->" + this.hash_adv + "");
			//
			System.out.println("dev_status-->" + this.dev_status + "");
			System.out.println("conn_index-->" + this.conn_index + "");
			System.out.println("sys_state-->" + this.sys_state + "");
			//
			System.out.println("temperature_value-->" + this.temperature_value + "");
			System.out.println("temperature_type-->" + this.temperature_type + "");
			System.out.println("temperature-->" + this.temperature + "");
			//
			if (this.header.has_behavior == 1) {
				System.out.println("behavior_version-->" + this.behavior_version + "");
				System.out.println("behavior_time-->" + this.behavior_time + "");
				System.out.println("behavior_odba-->" + this.behavior_odba + "");
			}
			//
			if (this.header.has_alarm == 1) {
				System.out.println("alarm_hw_error-->" + this.alarm_hw_error + "");
				System.out.println("alarm_sw_error-->" + this.alarm_sw_error + "");
				System.out.println("alarm_reserved-->" + this.alarm_reserved + "");
				System.out.println("alarm_estrus_flag-->" + this.alarm_estrus_flag + "");
				System.out.println("alarm_static_flag-->" + this.alarm_static_flag + "");
			}
			//
			if (this.header.has_platform == 1) {
				System.out.println("platform-->" + this.platform + "");
			}
		}
	}
}
