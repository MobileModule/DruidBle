package com.druid.bleact.model;

import com.druid.bleact.parse.IdentifyModelParse;

import protocol_v1.V1HubSetting;

public class HubSettingModel {
    public static IdentityModel getHubSettingModel(
            V1HubSetting.HubSettingReq hubSettingReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = hubSettingReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
