package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

/**
 * Created by druid on 2019/3/22.
 */

public class ParameterParse {
    public static IdentityModel getParameterModel(protocol_v1.V1Parameter.ParameterReq parameterReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = parameterReq.getIden();
        IdentityModel identityModel =IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
