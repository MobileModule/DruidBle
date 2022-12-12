package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

/**
 * Created by druid on 2019/3/22.
 */

public class WarnningParse {
    public static IdentityModel getWarrningReqModel(protocol_v1.V1Warning.WarningReq warningReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = warningReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
