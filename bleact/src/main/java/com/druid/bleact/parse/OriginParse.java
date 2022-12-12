package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;
import com.druid.bleact.model.OriginListModel;
import com.druid.bleact.model.OriginModel;

import protocol_v1.V1IdentityMsg;
import protocol_v1.V1Origin;
import protocol_v1.V1SimpleRsp;

/**
 * Created by LeaAnder on 2018/4/19.
 */

public class OriginParse {

    public static OriginListModel getOriginReqModel(V1Origin.OriginReq originReq) {
        OriginListModel originListModel = new OriginListModel();
        V1IdentityMsg.IdentityMsg identityMsg = originReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        originListModel.identityModel = identityModel;
        //
        originListModel.timestamp = originReq.getTimestamp();
        originListModel.samplingfreq = originReq.getSamplingFreq();
        //
        for (int i = 0; i < originReq.getXYZInfoCount(); i++) {
            OriginModel originModel = new OriginModel();
            V1Origin.XYZ xyz = originReq.getXYZInfo(i);
            originModel.x = xyz.getX();
            originModel.y = xyz.getZ();
            originModel.z = xyz.getZ();
            originListModel.XYZInfo.add(originModel);
        }
        return originListModel;
    }

    public static byte[] getOriginRspModel(IdentityModel model) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    public static byte[] getOriginRspModelCustom(IdentityModel model, int rspCode) {
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
