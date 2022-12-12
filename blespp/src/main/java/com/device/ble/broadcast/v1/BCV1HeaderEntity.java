package com.device.ble.broadcast.v1;

import java.io.Serializable;

public class BCV1HeaderEntity implements Serializable {
	public int version = 0;
	public int reserved = 0;
	//0:没有,1:有
	public int has_adv_hash = 0;//2 byte
	public int has_mac = 0;//2 byte
	public int has_fw_version = 0;//2 byte
	public int has_cfg_hash = 0;//
	public int has_dev_type = 0;//2 byte

	public int has_temperature = 0;//1 byte
	public int has_pressure = 0;//2 byte
	public int has_voltage = 0;//2 byte
	public int has_power = 0;//1 byte

	public int has_index = 0;//1 byte
	public int has_status = 0;//1 byte
	public int has_behavior = 0;//6 byte
	public int has_old_behavior = 0;//6 byte
	
	public static final int PackageHeaderLength=2;//数据头部包长度：2
	public static final int PackageDataLength=28;//数据域包长度：28
}
