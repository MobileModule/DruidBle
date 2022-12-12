package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

public class EventParse {
    public static IdentityModel getEventReqModel(protocol_v1.V1Event.EventReq eventReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = eventReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
