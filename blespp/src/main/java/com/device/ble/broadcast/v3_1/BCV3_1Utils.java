package com.device.ble.broadcast.v3_1;


import com.device.ble.utils.DataType;

public class BCV3_1Utils {
	/**
	 * v3包头
	 */
	public static BCV3_1HeaderEntity getV3Header(byte[] manufacturerDatas) {
		BCV3_1HeaderEntity v3Header = null;
		try {
			// 小端字节
			if (manufacturerDatas.length > 2) {
				int HEADER = DataType.byteArrayTo16(manufacturerDatas, 0);
				v3Header = new BCV3_1HeaderEntity();
				v3Header.version = HEADER & 0X0003;
				v3Header.reserved = (HEADER & 0X0004) >> 2;
				v3Header.has_behavior = (HEADER & 0X0008) >> 3;
				v3Header.has_env = (HEADER & 0X0010) >> 4;
				v3Header.has_alarm = (HEADER & 0X0020) >> 5;
				v3Header.has_platform = (HEADER & 0X0040) >> 6;
				v3Header.expand_flag = (HEADER & 0X0080) >> 7;
				if (v3Header.expand_flag == 1) {
					v3Header.expand = (HEADER & 0XFF00) >> 8;
				}
			}
		} catch (Exception e) {

		}
		return v3Header;
	}

	/**
	 * v3 包长度 单位 byte
	 */
	public static int getV3PackageLength(byte[] manufacturerDatas) {
		int length = 0;
		BCV3_1HeaderEntity v3Header = getV3Header(manufacturerDatas);
		if (v3Header != null) {
			// SN
			length += 5;
			// Firmware version
			length += 1;
			// Hardware version
			length += 2;
			// power
			length += 2;
			// hash
			length += 2;
			// device status
			length += 1;
			// Temperature
			length += 1;
			
			if (v3Header.has_behavior == 1) {
				length += 3;
			}
			if (v3Header.has_alarm == 1) {
				length += 1;
			}
			if (v3Header.has_env == 1) {

			}
			if (v3Header.has_platform == 1) {
				length += 1;
			}

		}
		return length;
	}

	/**
	 * v3 数据域
	 */
	public static BCV3_1DataEntity getV3Data(byte[] manufacturerDatas) {
		BCV3_1DataEntity v3Data = null;
		try {
			int length = getV3PackageLength(manufacturerDatas);
			if (length != 0) {
				int allLength = manufacturerDatas.length;
				BCV3_1HeaderEntity v3Header = getV3Header(manufacturerDatas);
				int header_length = v3Header.expand_flag == 0 ? 1 : 2;
				// if ((length + header_length) == allLength) {
				int checksum = 0x0A;
				for (int i = 0; i < manufacturerDatas.length - 1; i++) {
					checksum += manufacturerDatas[i];
				}
				int lastSize = (manufacturerDatas[manufacturerDatas.length - 1] & 0x00FF);
				if ((checksum & 0x00FF) == lastSize) {
					if (v3Header != null) {
						v3Data = new BCV3_1DataEntity();
						v3Data.header = v3Header;
						// 有效数据
						byte[] datas_value = new byte[manufacturerDatas.length - header_length];
						System.arraycopy(manufacturerDatas, header_length, datas_value, 0,
								manufacturerDatas.length - header_length);
						int position = 0;
						// sn
						v3Data.sn = new byte[5];
						System.arraycopy(datas_value, position, v3Data.sn, 0, v3Data.sn.length);
						position += 5;// 5 byte
						// fw_version
						v3Data.fw_version = datas_value[position];
						position += 1;// 1 byte +1000;
						v3Data.hw_version = DataType.byteArrayTo16(datas_value, position);
						position += 2;// 2 byte
						// power
						int value_power = DataType.byteArrayTo16(datas_value, position);
						v3Data.power_value = (value_power & 0x7FFF);
						v3Data.power_type = (value_power & 0x8000) >> 15;
						position += 2;// 2 byte //0 vlaue 剩余电量,1 value 剩余电压 单位 mv
						// hash
						int hash = DataType.byteArrayTo16(datas_value, position);
						v3Data.hash_cfg = hash & 0x0FFF;
						v3Data.hash_adv = (hash & 0xF000) >> 12;
						position += 2;// 2 byte
						// device status
						int dev_status = datas_value[position];
						v3Data.dev_status = dev_status;
						v3Data.conn_index = dev_status & 0x0F;
						v3Data.sys_state = (dev_status & 0xF0) >> 4;
						position += 1;// 1 byte
						// temperature
						int temperature = datas_value[position];
						int type = (temperature & 0x80) >> 7;
						if (type == 1) {// 高精度
							temperature = (temperature & 0x7F);
							v3Data.temperature_type = 1;
							v3Data.temperature_value = 306 + temperature;
							v3Data.temperature = v3Data.temperature_value * 10.0f;
						} else {
							v3Data.temperature_type = 0;
							v3Data.temperature_value = (temperature - 41) * 10;
							v3Data.temperature = v3Data.temperature_value / 10.0f;
						}
						position += 1;// 1 byte
						//behavior
						if (v3Header.has_behavior == 1) {
							int behavior_status = DataType.byteArrayTo8(datas_value, position);
							v3Data.behavior_version = behavior_status & 0x0F;
							if (v3Data.behavior_version == 0) {
								v3Data.behavior_time = (behavior_status & 0xF0) >> 4;
								position += 1;// 1 byte
								int behavior_odba = DataType.byteArrayTo16(datas_value, position);
								v3Data.behavior_odba = behavior_odba;
								position += 2;// 2 byte
							}
						}
						// alarm
						if (v3Header.has_alarm == 1) {
							int status = DataType.byteArrayTo8(datas_value, position);
							v3Data.alarm_hw_error = status & 0x01;
							v3Data.alarm_sw_error = (status & 0x02) >> 1;
							v3Data.alarm_reserved = (status & 0x3C) >> 2;
							v3Data.alarm_estrus_flag = (status & 0x40) >> 6;
							v3Data.alarm_static_flag = (status & 0x80) >> 7;
							position += 1;// 1 byte
						}
						// platform
						if (v3Header.has_platform == 1) {
							int platform = DataType.byteArrayTo8(datas_value, position);
							v3Data.platform = platform;
							position += 1;// 1 byte
						}
					}

				} else {
//				System.out.println("ble broadcast v3-data length error");
				}
			}
		}catch (Exception ex){

		}
		return v3Data;
	}

}
