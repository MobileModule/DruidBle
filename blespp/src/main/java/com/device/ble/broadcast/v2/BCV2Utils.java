package com.device.ble.broadcast.v2;

import com.device.ble.utils.DataType;

public class BCV2Utils {
	/**
	 * v2 包头
	 */
	public static BCV2HeaderEntity getV2Header(byte[] manufacturerDatas) {
		BCV2HeaderEntity v2Header = null;
		try {
			// 小端字节
			if (manufacturerDatas.length > 2) {
				int HEADER = DataType.byteArrayTo16(manufacturerDatas, 0);
				v2Header = new BCV2HeaderEntity();
				// header - version
				v2Header.version = (HEADER & 0X0003);
				// header- has_basic_info
				v2Header.has_basic_info = (HEADER & 0X0004) >> 2;
				// header- has_adv_hash
				v2Header.has_adv_hash = (HEADER & 0X0008) >> 3;
				// header- has_cfg_hash
				v2Header.has_cfg_hash = (HEADER & 0X0010) >> 4;
				// header- has_status
				v2Header.has_status = (HEADER & 0X0020) >> 5;
				// header- has_feature
				v2Header.has_feature = (HEADER & 0X0040) >> 6;
				// header- ext1
				v2Header.ext1 = (HEADER & 0X0080) >> 7;
				if(v2Header.ext1==1) {
					// header- has_temperature
					v2Header.has_temperature = (HEADER & 0X0100) >> 8;
					// header- has_pressure
					v2Header.has_pressure = (HEADER & 0X0200) >> 9;
					// header- has_light
					v2Header.has_light = (HEADER & 0X0400) >> 10;
					// header- has_behavior
					v2Header.has_behavior = (HEADER & 0X0800) >> 11;
					// header- has_old_behavior
					v2Header.has_old_behavior = (HEADER & 0X1000) >> 12;
					// header- rfu2
					v2Header.rfu2 = (HEADER & 0X6000) >> 13;
					// header- ext2
					v2Header.ext2 = (HEADER & 0X8000) >> 15;
				}
			}
		} catch (Exception e) {

		}
		return v2Header;
	}

	/**
	 * v2 包长度 单位 byte 数据总长度34
	 */
	public static int getV2PackageLength(byte[] manufacturerDatas) {
		int length = 0;
		BCV2HeaderEntity v2Header = getV2Header(manufacturerDatas);
		if (v2Header != null) {
			if (v2Header.has_basic_info == 1) {
				length = length + 5 + 1 + 2 + 2 + 2;
			}
			if (v2Header.has_adv_hash == 1 || v2Header.has_cfg_hash == 1) {
				length += 2;
			}
			if (v2Header.has_status == 1) {
				length += 1;
			}
			if (v2Header.has_feature == 1) {
				length += 1;
			}
			if (v2Header.has_temperature == 1) {
				length += 1;
			}
			if (v2Header.has_pressure == 1) {
				length += 2;
			}
			if (v2Header.has_light == 1) {
				length += 2;
			}
			if (v2Header.has_behavior == 1) {
				length += 6;
			}
			if (v2Header.has_old_behavior == 1) {
				length += 6;
			}
		}
		return length;
	}

	/**
	 * v2 数据域
	 */
	public static BCV2DataEntity getV2Data(byte[] manufacturerDatas) {
		BCV2DataEntity v2Data = null;
		try {
			int length = getV2PackageLength(manufacturerDatas);
			if (length != 0) {
				int allLength = manufacturerDatas.length;
				BCV2HeaderEntity v2Header = getV2Header(manufacturerDatas);
				int header_length = v2Header.ext1 == 0 ? 1 : 2;
				if ((length + header_length) == allLength) {
					if (v2Header != null) {
						v2Data = new BCV2DataEntity();
						v2Data.header = v2Header;
						// 有效数据
						byte[] datas_value = new byte[manufacturerDatas.length - header_length];
						System.arraycopy(manufacturerDatas, header_length, datas_value, 0, manufacturerDatas.length - header_length);
						// basic_info
						int position = 0;
						if (v2Header.has_basic_info == 1) {
							// sn
							v2Data.sn = new byte[5];
							System.arraycopy(datas_value, position, v2Data.sn, 0, v2Data.sn.length);
							position += 5;// 5 byte
							//
							int value_status = datas_value[position];
							v2Data.conn_index = value_status & 0x0F;
							v2Data.sys_state = (value_status & 0xF0) >> 4;
							position += 1;// 1 byte
							//
							v2Data.hw_version = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
							//
							v2Data.fw_version = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
							//
							int value_power = DataType.byteArrayTo16(datas_value, position);
							v2Data.power_value = (value_power & 0x7FFF);
							v2Data.power_type = (value_power & 0x8000) >> 15;
							position += 2;// 2 byte
						}
						// hash
						if (v2Header.has_cfg_hash == 1) {
							// TODO
							int hash = DataType.byteArrayTo16(datas_value, position);
							if (v2Header.has_adv_hash == 1) {// 两个都有
								v2Data.hash_cfg = hash & 0x0FFF;
								v2Data.hash_adv = (hash & 0xF000) >> 12;
							} else {
								v2Data.hash_cfg = hash;
							}
							position += 2;// 2 byte
						} else {
							// TODO
							if (v2Header.has_adv_hash == 1) {
								int hash = DataType.byteArrayTo16(datas_value, position);
								v2Data.hash_adv = (hash & 0xF000) >> 12;
								position += 2;// 2 byte
							}
						}
						// status
						if (v2Header.has_status == 1) {
							// TODO
							int status = DataType.byteArrayTo8(datas_value, position);
							v2Data.status_hw_error = status & 0x01;
							v2Data.status_sw_error = (status & 0x02) >> 1;
							v2Data.status_reserved = (status & 0x3C) >> 2;
							v2Data.status_estrus_flag = (status & 0x40) >> 6;
							v2Data.status_static_flag = (status & 0x80) >> 7;
							position += 1;// 1 byte
						}
						// feature
						if (v2Header.has_feature == 1) {
							v2Data.feature = datas_value[position];
							position += 1;// 1 byte
						}

						// temperature
						if (v2Header.has_temperature == 1) {
							// TODO
							int temperature = datas_value[position];
							int type = (temperature & 0x80) >> 7;
							if (type == 1) {// 高精度
								temperature = (temperature & 0x7F);
								v2Data.temperature_type = 1;
								v2Data.temperature_value = 306 + temperature;
								v2Data.temperature = v2Data.temperature_value * 10.0f;
							} else {
								v2Data.temperature_type = 0;
								v2Data.temperature_value = (temperature - 41) * 10;
								v2Data.temperature = v2Data.temperature_value / 10.0f;
							}
							position += 1;// 1 byte
						}
						// pressure
						if (v2Header.has_pressure == 1) {
							v2Data.pressure = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
						// light
						if (v2Header.has_light == 1) {
							v2Data.light = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
						// behavior
						if (v2Header.has_behavior == 1) {
							// TODO
							int behavior = DataType.byteArrayTo16(datas_value, position);
							v2Data.behavior_version = behavior & 0x0003;
							v2Data.behavior_eating = (behavior & 0x01FC) >> 2;
							v2Data.behavior_ruminate = (behavior & 0xFE00) >> 9;
							position += 2;// 2 byte
							//
							v2Data.behavior_odba = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
							//
							v2Data.behavior_steps = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
						// old_behavior
						if (v2Header.has_old_behavior == 1) {
							// TODO
							int old_behavior = DataType.byteArrayTo16(datas_value, position);
							v2Data.old_behavior_version = old_behavior & 0x0003;
							v2Data.old_behavior_eating = (old_behavior & 0x01FC) >> 2;
							v2Data.old_behavior_ruminate = (old_behavior & 0xFE00) >> 9;
							position += 2;// 2 byte
							//
							v2Data.old_behavior_odba = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
							//
							v2Data.old_behavior_steps = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
					}

				} else {
//				String error = String.format("ble broadcast v2-data length error:%s",
//						ByteUtil.byteArrayToHexString(manufacturerDatas));
//				System.out.println(error);
//				System.out.println("ble broadcast v2-data length error");
				}
			}
		}catch (Exception ex){

		}
		return v2Data;
	}
}
