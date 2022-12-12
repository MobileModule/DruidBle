package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

/**
 * Created by druid on 2019/7/12.
 */

public class CellularParse {
    public static IdentityModel getParameterModel(protocol_v1.V1Cellular.CellularReq cellularReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = cellularReq.getIden();
        IdentityModel identityModel =IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }
}
