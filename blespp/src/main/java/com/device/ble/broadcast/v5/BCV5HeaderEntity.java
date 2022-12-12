package com.device.ble.broadcast.v5;

import java.io.Serializable;

public class BCV5HeaderEntity implements Serializable {
    public int version = 0;//5 bit
    public int option_data_type = 0;//3 bit. 0 时表示没有optional data
}
