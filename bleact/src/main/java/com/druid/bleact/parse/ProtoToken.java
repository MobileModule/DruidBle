package com.druid.bleact.parse;

import com.device.ble.utils.DataType;

import java.util.Random;

import protocol_v1.V1IdentityMsg;

/**
 * Created by druid on 2019/9/23.
 */

public class ProtoToken {
    public static V1IdentityMsg.IdentityMsg getToken() {
        V1IdentityMsg.IdentityMsg.Builder identity = V1IdentityMsg.IdentityMsg.newBuilder();
        identity.setRspCode(0);
        //
        String deviceId = "13198557467";
        int token = DataType.getAuthToken(deviceId);
        identity.setDeviceID(deviceId);
        identity.setMsgToken(token);
        identity.setMsgIndex(new Random().nextInt(10000));
        return identity.build();
    }

    public static V1IdentityMsg.IdentityMsg getToken(String device_id) {
        V1IdentityMsg.IdentityMsg.Builder identity = V1IdentityMsg.IdentityMsg.newBuilder();
        identity.setRspCode(0);
        //
        String deviceId = device_id;
        int token = DataType.getAuthToken(deviceId);
        identity.setDeviceID(deviceId);
        identity.setMsgToken(token);
        identity.setMsgIndex(new Random().nextInt(10000));
        return identity.build();
    }
}
