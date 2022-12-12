package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

public class DebugParse {
    public static IdentityModel getDebugReqModel(protocol_v1.V1Debug.DebugReq debugReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = debugReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
