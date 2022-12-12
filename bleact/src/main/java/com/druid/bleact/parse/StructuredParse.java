package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

public class StructuredParse {
    public static IdentityModel getStructuredReqModel(protocol_v1.V1Structured.StructuredReq structuredReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = structuredReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
