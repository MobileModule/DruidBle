package com.android.log.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceBleOperaMultiEntity implements Serializable {
    public ArrayList<DeviceBleOperaHistoryEntity> historys = new ArrayList<>();
    public DeviceBleOperaTimeEntity time = null;
}
