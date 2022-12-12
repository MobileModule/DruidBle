package com.device.ble.broadcast.v3_1;

import java.io.Serializable;

public class BCV3_1HeaderEntity implements Serializable {
	public int version = 0;
	public int reserved = 0;
	//// 0:没有,1:有
	public int has_behavior = 0;
	public int has_env = 0;
	public int has_alarm = 0;
	public int has_platform = 0;
	public int expand_flag = 0;
	public int expand = 0;
}
