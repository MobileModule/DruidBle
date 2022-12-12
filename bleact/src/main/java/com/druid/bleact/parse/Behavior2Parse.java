package com.druid.bleact.parse;

import com.druid.bleact.model.Behavior2ListModel;
import com.druid.bleact.model.Behavior2Model;
import com.druid.bleact.model.IdentityModel;

import protocol_v1.V1Behavior2;
import protocol_v1.V1IdentityMsg;
import protocol_v1.V1SimpleRsp;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class Behavior2Parse {
    public static Behavior2ListModel getBev2ReqModel(V1Behavior2.Behavior2Req behavior2Req) {
        Behavior2ListModel behavior2ListModel = new Behavior2ListModel();
        //
        V1IdentityMsg.IdentityMsg identityMsg = behavior2Req.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        behavior2ListModel.identityModel = identityModel;
        //
        for (int i = 0; i < behavior2Req.getBehaviorInfoCount(); i++) {
            V1Behavior2.Behavior2 behavior2 = behavior2Req.getBehaviorInfo(i);
            Behavior2Model behavior2Model = new Behavior2Model();

            int Timestamp = 0;
            if (behavior2.hasTimestamp()) {
                Timestamp = behavior2.getTimestamp();
            }
            behavior2Model.Timestamp = Timestamp;

            int ODBAX = 0;    // odbax value in 0.0001g
            if (behavior2.hasODBAX()) {
                ODBAX = behavior2.getODBAX();
            }
            behavior2Model.ODBAX = ODBAX;

            int ODBAY = 0;    // odbay value in 0.0001g
            if (behavior2.hasODBAY()) {
                ODBAY = behavior2.getODBAY();
            }
            behavior2Model.ODBAY = ODBAY;

            int ODBAZ = 0;        // odbaz value in 0.0001g
            if (behavior2.hasODBAZ()) {
                ODBAZ = behavior2.getODBAZ();
            }
            behavior2Model.ODBAZ = ODBAZ;

            int MeandlX = 0;        // mean(|X(i) - X(i-1)|) in 0.0001g
            if (behavior2.hasMeandlX()) {
                MeandlX = behavior2.getMeandlX();
            }
            behavior2Model.MeandlX = MeandlX;

            int MeandlY = 0;       // mean(|Y(i) - Y(i-1)|) in 0.0001g
            if (behavior2.hasMeandlY()) {
                MeandlY = behavior2.getMeandlY();
            }
            behavior2Model.MeandlY = MeandlY;

            int MeandlZ = 0;         // mean(|Z(i) - Z(i-1)|) in 0.0001g
            if (behavior2.hasMeandlZ()) {
                MeandlZ = behavior2.getMeandlZ();
            }
            behavior2Model.MeandlZ = MeandlZ;

            int ODBA = 0;
            if (behavior2.hasODBA()) {
                ODBA = behavior2.getODBA();
            }
            behavior2Model.ODBA = ODBA;

            int Steps = 0;
            if (behavior2.hasSteps()) {
                Steps = behavior2.getSteps();
            }
            behavior2Model.Steps = Steps;

            behavior2ListModel.behavior2Models.add(behavior2Model);
        }

        return behavior2ListModel;
    }

    public static byte[] getBev2RspModel(IdentityModel model) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    public static byte[] getBev2RspModelCustom(IdentityModel model, int rspCode) {
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
