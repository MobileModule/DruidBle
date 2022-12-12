package com.druid.ota.bean;

import java.io.Serializable;

/**
 * Created by LeaAnder on 2017/6/1.
 */
public class BinInfo implements Serializable {
    public int fwv_bin;
    public int hwv_bin;
    public int dt_bin;
    public byte[] datas;
    public BinInfo(int fwv_bin,int hwv_bin,int dt_bin){
        this.fwv_bin=fwv_bin;
        this.hwv_bin=hwv_bin;
        this.dt_bin=dt_bin;
    }

    public BinInfo(int fwv_bin,int hwv_bin,int dt_bin,byte[] datas){
        this.fwv_bin=fwv_bin;
        this.hwv_bin=hwv_bin;
        this.dt_bin=dt_bin;
        this.datas=datas;
    }
}
