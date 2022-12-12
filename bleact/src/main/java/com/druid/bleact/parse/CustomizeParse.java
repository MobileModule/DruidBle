package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

import protocol_v1.V1CustomizeData;

public class CustomizeParse {
    public static IdentityModel getCustomizeReqModel(V1CustomizeData.CustomizeDataReq customizeReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = customizeReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
