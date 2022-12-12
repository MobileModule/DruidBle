package com.druid.ota.utils;

import com.device.ble.utils.DataType;

import protocol_v1.V1IdentityMsg;

public class V1IdentityMsgUtils {
    public static V1IdentityMsg.IdentityMsg getIdentityMsg(V1IdentityMsg.IdentityMsg identityMsg) {
        V1IdentityMsg.IdentityMsg.Builder builder = V1IdentityMsg.IdentityMsg.newBuilder();
        builder.setMsgIndex(identityMsg.getMsgIndex());
        if (identityMsg.hasDeviceID()) {
            builder.setDeviceID(identityMsg.getDeviceID());
            builder.setMsgToken(DataType.getAuthToken2(identityMsg.getDeviceID()));
        }
        if (identityMsg.hasRspCode()) {
            builder.setRspCode(identityMsg.getRspCode());
        }
        return builder.build();
    }
}
