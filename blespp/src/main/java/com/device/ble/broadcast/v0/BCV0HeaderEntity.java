package com.device.ble.broadcast.v0;

import java.io.Serializable;

public class BCV0HeaderEntity implements Serializable { //共两个字节
    //0:没有,1:有，数据域总长度 17
    public int has_mac = 0;// 2 byte
    public int has_voltage = 0;//2 byte
    public int has_version = 0;//2 byte
    public int has_steps = 0;//4 byte
    public int has_index = 0;//1 byte
    public int has_userhash = 0;//2 byte
    public int has_odba = 0;//2 byte
    public int has_behavior = 0;//1 byte
    public int has_warning = 0;//1 byte

    public static final int PackageHeaderLength = 2;//数据头部包长度：2
    public static final int PackageDataLength = 17;//数据域包长度：17
}
