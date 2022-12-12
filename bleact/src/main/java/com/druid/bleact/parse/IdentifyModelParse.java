package com.druid.bleact.parse;

import com.device.ble.utils.DataType;
import com.druid.bleact.model.IdentityModel;

/**
 * Created by druid on 2019/7/12.
 */

public class IdentifyModelParse {
    public static IdentityModel getIdentifyModel(protocol_v1.V1IdentityMsg.IdentityMsg identityMsg){
        IdentityModel identityModel = new IdentityModel();
        //  identityModel.MsgToken = identityMsg.getMsgToken();
        identityModel.MsgIndex = identityMsg.getMsgIndex();

        String DeviceID = "";
        if (identityMsg.hasDeviceID()) {
            DeviceID = identityMsg.getDeviceID();
        }

        identityModel.DeviceID = DeviceID;

//        Log.i("DeviceId:", DeviceID);
        identityModel.MsgToken = DataType.getAuthToken2(identityModel.DeviceID);

        int RspCode = -1;
        if (identityMsg.hasRspCode()) {
            RspCode = identityMsg.getRspCode();
        }
        identityModel.RspCode = RspCode;
        return identityModel;
    }
}
