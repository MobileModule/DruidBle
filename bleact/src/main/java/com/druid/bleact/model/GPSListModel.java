package com.druid.bleact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by druid on 2018/11/5.
 */

public class GPSListModel implements Serializable {
    public IdentityModel identityModel;
    public ArrayList<GPSModel> GPSInfo=new ArrayList<>(); // gps信息
    public int SMS = 101; // 是否短信回传
}
