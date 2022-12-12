package com.druid.bleact.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class Behavior2ListModel implements Serializable {
    public IdentityModel identityModel;
    public ArrayList<Behavior2Model> behavior2Models=new ArrayList<>();
}
