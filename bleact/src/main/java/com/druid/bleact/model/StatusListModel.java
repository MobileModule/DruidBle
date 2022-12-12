package com.druid.bleact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by druid on 2018/11/5.
 */

public class StatusListModel implements Serializable {
    public IdentityModel identityModel;
    public ArrayList<StatusModel> StatusInfo=new ArrayList<>();
}
