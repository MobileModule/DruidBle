package com.druid.ota.bean;

import java.io.Serializable;

/**
 * Created by LeaAnder on 2017/6/1.
 */
public class OtaInfo implements Serializable {
    public int hwv;
    public int fwv;
    public int dt;
    public String mac;
    public String imsi;
    public String uid;
    public int vol;
}
