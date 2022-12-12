package com.druid.bleact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LeaAnder on 2018/4/19.
 */

public class OriginListModel implements Serializable {
    public IdentityModel identityModel;
    public ArrayList<OriginModel> XYZInfo=new ArrayList<>();
    public int timestamp=0;  // 开始时间
    public int samplingfreq=0; // 采样频率，每秒次数
}
