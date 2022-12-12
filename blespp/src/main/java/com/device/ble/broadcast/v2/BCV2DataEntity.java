package com.device.ble.broadcast.v2;

import com.device.ble.utils.ByteUtil;

import java.io.Serializable;

public class BCV2DataEntity implements Serializable {
	public BCV2HeaderEntity header = null;
	// basic_info start
	public byte[] sn = null;// 5 byte
	// 共1 byte
	public int conn_index = 0;// 4 bit
	public int sys_state = 0;// 4 bit //1 - 整测完成，等待升级  ,2 - 库存,3 - 激活,4 - 失活
	//
	public int hw_version = 0;// 2 byte
	public int fw_version = 0;// 2 byte
	// 共2 byte
	public int power_value = 0;// 15 bit
	public int power_type = 0;// 1 bit //0 vlaue 剩余电量,1 value 剩余电压 单位 mv
	// basic_info end

	// 共 2 byte
	public int hash_cfg = 0;// 12 bit
	public int hash_adv = 0;// 4 bit
	// 共1 byte
	public int status_hw_error = 0;// 1 bit
	public int status_sw_error = 0;// 1 bit
	public int status_reserved = 0;// 4 bit
	public int status_estrus_flag = 0;// 1 bit
	public int status_static_flag = 0;// 1 bit
	//
	public int feature = 0;// 1 byte
	// 共 1 byte
	public int temperature_value = 0;// 15 bit
	public int temperature_type = 0;// 1 bit
	public float temperature = 0;// 高精度：乘以10，低进度：除以10
	//
	public int pressure = 0;// 2 byte
	//
	public int light = 0;// 2 byte

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

	public String printLog() {
		StringBuilder sb = new StringBuilder();
		if (header != null) {
			if (header.has_basic_info == 1) {
				sb.append("sn-->" + ByteUtil.byteArrayToHexString(this.sn) + "\r\n");
				sb.append("conn_index-->" + this.conn_index + "\r\n");
				sb.append("sys_state-->" + this.sys_state + "\r\n");
				sb.append("hw_version-->" + this.hw_version + "\r\n");
				sb.append("fw_version-->" + this.fw_version + "\r\n");
				sb.append("power_value-->" + this.power_value + "\r\n");
				sb.append("power_type-->" + this.power_type + "\r\n");
			}
			//
			if (header.has_cfg_hash == 1) {
				sb.append("hash_cfg-->" + this.hash_cfg + "\r\n");
			}
			if (header.has_adv_hash == 1) {
				sb.append("hash_adv-->" + this.hash_adv + "\r\n");
			}
			//
			if (header.has_status == 1) {
				sb.append("status_hw_error-->" + this.status_hw_error + "\r\n");
				sb.append("status_sw_error-->" + this.status_sw_error + "\r\n");
				sb.append("status_reserved-->" + this.status_reserved + "\r\n");
				sb.append("status_estrus_flag-->" + this.status_estrus_flag + "\r\n");
				sb.append("status_static_flag-->" + this.status_static_flag + "\r\n");
			}
			//
			if (header.has_feature == 1) {
				sb.append("feature-->" + this.feature + "\r\n");
			}
			//
			if (header.has_temperature == 1) {
				sb.append("temperature_type-->" + this.temperature_type + "\r\n");
				sb.append("temperature_value-->" + this.temperature_value + "\r\n");
			}
			//
			if (header.has_pressure == 1) {
				sb.append("pressure-->" + this.pressure + "\r\n");
			}
			if (header.has_light == 1) {
				sb.append("light-->" + this.light + "\r\n");
			}
			//
			if (header.has_behavior == 1) {
				sb.append("behavior_version-->" + this.behavior_version + "\r\n");
				sb.append("behavior_eating-->" + this.behavior_eating + "\r\n");
				sb.append("behavior_ruminate-->" + this.behavior_ruminate + "\r\n");
				sb.append("behavior_odba-->" + this.behavior_odba + "\r\n");
				sb.append("behavior_steps-->" + this.behavior_steps + "\r\n");
			}
			//
			if (header.has_old_behavior == 1) {
				sb.append("old_behavior_version-->" + this.old_behavior_version + "\r\n");
				sb.append("old_behavior_eating-->" + this.old_behavior_eating + "\r\n");
				sb.append("old_behavior_ruminate-->" + this.old_behavior_ruminate + "\r\n");
				sb.append("old_behavior_odba-->" + this.old_behavior_odba + "\r\n");
				sb.append("old_behavior_steps-->" + this.old_behavior_steps + "\r\n");
			}
		}
		return sb.toString();
	}

}
