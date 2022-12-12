package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

import protocol_v1.V1Download;

public class DownloadParse {
    public static IdentityModel getDownloadReqModel(V1Download.DownloadReq downloadReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = downloadReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
