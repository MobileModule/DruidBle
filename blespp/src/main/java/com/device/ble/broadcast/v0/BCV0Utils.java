package com.device.ble.broadcast.v0;

import com.device.ble.utils.DataType;

public class BCV0Utils {
	/**
	 * v0 包头
	 */
	public static BCV0HeaderEntity getV0Header(byte[] manufacturerDatas) {
		BCV0HeaderEntity v0Header = null;
		try {
			// 小端字节
			if (manufacturerDatas.length > 2) {
				int HEADER = DataType.byteArrayTo16(manufacturerDatas, 0);
				v0Header = new BCV0HeaderEntity();
				// header - mac
				v0Header.has_mac = (HEADER & 0X0001);
				// header - voltage
				v0Header.has_voltage = (HEADER & 0X0002) >> 1;
				// header - version
				v0Header.has_version = (HEADER & 0X0004) >> 2;
				// header - steps
				v0Header.has_steps = (HEADER & 0X0008) >> 3;
				// header - index
				v0Header.has_index = (HEADER & 0X0010) >> 4;
				// header - userhash
				v0Header.has_userhash = (HEADER & 0X0020) >> 5;
				// header - odba
				v0Header.has_odba = (HEADER & 0X0040) >> 6;
				// header - behavior
				v0Header.has_behavior = (HEADER & 0X0080) >> 7;
				// header - warning
				v0Header.has_warning = (HEADER & 0X0100) >> 8;
			}
		} catch (Exception e) {

		}
		return v0Header;
	}

	/**
	 * v0 包长度 单位 byte 数据总长度17
	 */
	public static int getV0PackageLength(byte[] manufacturerDatas) {
		int length = 0;
		BCV0HeaderEntity v0Header = getV0Header(manufacturerDatas);
		if (v0Header != null) {
			if (v0Header.has_mac == 1) {
				length += 2;
			}
			if (v0Header.has_voltage == 1) {
				length += 2;
			}
			if (v0Header.has_version == 1) {
				length += 2;
			}
			if (v0Header.has_steps == 1) {
				length += 4;
			}
			if (v0Header.has_index == 1) {
				length += 1;
			}
			if (v0Header.has_userhash == 1) {
				length += 2;
			}
			if (v0Header.has_odba == 1) {
				length += 2;
			}
			if (v0Header.has_behavior == 1) {
				length += 1;
			}
			if (v0Header.has_warning == 1) {
				length += 1;
			}
		}
		return length;
	}

	/**
	 * v0 数据域
	 */
	public static BCV0DataEntity getV0Data(byte[] manufacturerDatas) {
		BCV0DataEntity v0Data = null;
		int length = getV0PackageLength(manufacturerDatas);
		try {
			if (length != 0) {
				int allLength = manufacturerDatas.length;
				if ((length + 2) == allLength) {
					BCV0HeaderEntity v0Header = getV0Header(manufacturerDatas);
					if (v0Header != null) {
						v0Data = new BCV0DataEntity();
						v0Data.header = v0Header;
						// 有效数据
						byte[] datas_value = new byte[manufacturerDatas.length - 2];
						System.arraycopy(manufacturerDatas, 2, datas_value, 0, manufacturerDatas.length - 2);
						//
						int position = 0;
						// mac
						if (v0Header.has_mac == 1) {
							v0Data.mac = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
						// voltage
						if (v0Header.has_voltage == 1) {
							v0Data.voltage = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
//							Log.i("v0battery-->"+v0Data.voltage);
						}
						// version
						if (v0Header.has_version == 1) {
							v0Data.version = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
						// steps
						if (v0Header.has_steps == 1) {
							v0Data.steps = DataType.byteArrayTo32(datas_value, position);
							position += 4;// 4 byte
						}
						// index
						if (v0Header.has_index == 1) {
							int index = datas_value[position];
							v0Data.index_time = index & 0x0F;
							v0Data.index_data = (index & 0xF0) >> 4;
							position += 1;// 1 byte
						}
						// userhash
						if (v0Header.has_userhash == 1) {
							v0Data.userhash = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
						// odba
						if (v0Header.has_odba == 1) {
							v0Data.odba = DataType.byteArrayTo16(datas_value, position);
							position += 2;// 2 byte
						}
						// behavior
						if (v0Header.has_behavior == 1) {
							int behavior = datas_value[position];
							v0Data.behavior_prediction = behavior & 0x3F;
							v0Data.behavior_static_flag = (behavior & 0x40) >> 6;
							v0Data.behavior_estrus_flag = (behavior & 0x80) >> 7;
							position += 1;// 1 byte
						}
						// warning
						if (v0Header.has_warning == 1) {
							v0Data.warning = datas_value[position];
							position += 1;// 1 byte
						}
					}
				} else {
//					System.out.println("ble broacast v0-data length error");
				}
			}
		} catch (Exception e) {

		}
		return v0Data;
	}
}
