package com.druid.bleact.model;

import com.device.ble.entity.HubBroadcastModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by druid on 2019/9/23.
 */

public class HubBroadcastListModel implements Serializable {
    public IdentityModel identityModel;
    public ArrayList<HubBroadcastModel> broadcasts=new ArrayList<>();
}
