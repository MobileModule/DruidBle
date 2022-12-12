package com.druid.bleact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class EnvListModel implements Serializable {
    public IdentityModel identityModel;
    public ArrayList<EnvReqModel> envs=new ArrayList<>();
}
