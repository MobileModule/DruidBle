package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

import protocol_v1.V1IdentityMsg;
import protocol_v1.V1SimpleRsp;

/**
 * Created by druid on 2019/3/22.
 */

public class SimpleParse {
    public static byte[] getSimpleRspModel(V1IdentityMsg.IdentityMsg identityMsg){
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(identityMsg.getMsgToken());
        identityBuilder.setMsgIndex(identityMsg.getMsgIndex());
        identityBuilder.setDeviceID(identityMsg.getDeviceID());
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    public static byte[] getSimpleRspModel(IdentityModel model) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    public static byte[] getSimpleRspModelCustom(IdentityModel model, int rspCode) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        identityBuilder.setRspCode(rspCode);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }
}
