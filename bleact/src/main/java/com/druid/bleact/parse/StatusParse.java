package com.druid.bleact.parse;


import com.druid.bleact.model.IdentityModel;
import com.druid.bleact.model.StatusListModel;
import com.druid.bleact.model.StatusModel;

import java.io.Serializable;

import protocol_v1.V1IdentityMsg;
import protocol_v1.V1SimpleRsp;
import protocol_v1.V1Error;

/**
 * Created by druid on 2018/11/5.
 */

public class StatusParse implements Serializable {
    public static StatusListModel getStatusReqModel(V1Error.ErrorReq statusReq) {
        V1IdentityMsg.IdentityMsg identityMsg = statusReq.getIden();
        StatusListModel statusListModel = new StatusListModel();
        //
        IdentityModel identityModel =IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        statusListModel.identityModel=identityModel;
        for (int i = 0; i < statusReq.getErrorInfoCount(); i++) {
            StatusModel statusModel = new StatusModel();
            V1Error.Error status = statusReq.getErrorInfo(i);
            if (status.hasTimestamp()) {
                statusModel.Timestamp = status.getTimestamp();
            }
            if (status.hasType()) {
                statusModel.StatusType = status.getType();
            }
            if (status.hasValue()) {
                statusModel.StatusValue = status.getValue();
            }
            if (status.hasFirmwareVersion()) {
                statusModel.FirmwareVersion = status.getFirmwareVersion();
            }
            if (status.hasVoltage()) {
                statusModel.Voltage = status.getVoltage();
            }
            statusListModel.StatusInfo.add(statusModel);
        }
        return statusListModel;
    }

    public static byte[] getStatusRspModel(IdentityModel model) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    public static byte[] getStatusRspModelCustom(IdentityModel model,int rspCode) {
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
