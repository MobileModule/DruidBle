package com.device.ble.broadcast.v2;

import java.io.Serializable;

public class BCV2HeaderEntity implements Serializable {
	public int version = 0;
	//// 0:没有,1:有
	public int has_basic_info = 0;// 5+1+2+2+2 //12 byte
	public int has_adv_hash = 0;// 2 byte
	public int has_cfg_hash = 0;//
	public int has_status = 0;// 1 byte
	public int has_feature = 0;// 1 byte

	public int ext1 = 0;

	public int has_temperature = 0;// 1 byte
	public int has_pressure = 0;// 2 byte
	public int has_light = 0;// 2 byte
	public int has_behavior = 0;// 6 byte
	public int has_old_behavior = 0;// 6 byte

	public int rfu2 = 0;
	public int ext2 = 0;

	public static final int PackageHeaderLength = 2;// 数据头部包长度：2
	public static final int PackageDataLength = 33;// 数据域包长度：33
}
