package com.druid.bleact.model;

import java.io.Serializable;

/**
 * Created by druid on 2018/11/5.
 */

public class StatusModel implements Serializable {
    public int Timestamp = 0;
    public int StatusType = 0;         //138 is mark downloaded setting
    public int StatusValue = 0;
    public int FirmwareVersion = 0;
    public int Voltage = 0;
}
