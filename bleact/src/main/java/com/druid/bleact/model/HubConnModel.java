package com.druid.bleact.model;

/**
 * Created by druid on 2019/9/23.
 */

public class HubConnModel {
    public IdentityModel identityModel;
    public int event = 2; // 1: 连接成功, 2: 失去连接, 3: 连接超时
}
