package com.druid.bleact.parse;


import com.druid.bleact.model.EnvListModel;
import com.druid.bleact.model.EnvReqModel;
import com.druid.bleact.model.IdentityModel;

import protocol_v1.V1Env;
import protocol_v1.V1IdentityMsg;
import protocol_v1.V1SimpleRsp;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class EvnParse {

    public static EnvListModel getEnvReqModel(V1Env.EnvReq envReq) {
        EnvListModel model = new EnvListModel();
        //
        V1IdentityMsg.IdentityMsg identityMsg = envReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        model.identityModel = identityModel;

        //
        for (int i = 0; i < envReq.getEnvInfoCount(); i++) {
            V1Env.Env env = envReq.getEnvInfo(i);
            EnvReqModel envReqModel = new EnvReqModel();
            int Timestamp = 0;
            if (env.hasTimestamp()) {
                Timestamp = env.getTimestamp();
            }
            envReqModel.Timestamp = Timestamp;

            int BatteryPower = 0; // 剩余电量: 百分比
            if (env.hasBatteryPower()) {
                BatteryPower = env.getBatteryPower();
            }
            envReqModel.BatteryPower = BatteryPower;

            int BatteryVoltage = 0; // 电池电压: 毫伏
            if (env.hasBatteryVoltage()) {
                BatteryVoltage = env.getBatteryVoltage();
            }
            envReqModel.BatteryVoltage = BatteryVoltage;

            int ChargeVoltage = 0; // 充电电压: 毫伏
            if (env.hasChargeVoltage()) {
                ChargeVoltage = env.getChargeVoltage();
            }
            envReqModel.ChargeVoltage = ChargeVoltage;

            int ChargeCurrent = 0; // 充电电流: 毫安, 精确到小数点后两位(*✖️100)
            if (env.hasChargeCurrent()) {
                ChargeCurrent = env.getChargeCurrent();
            }
            envReqModel.ChargeCurrent = ChargeCurrent;

            int InnerTemperature = 0; // 内部温度: 摄氏度, 精确到小数点后一位(*10)
            if (env.hasInnerTemperature()) {
                InnerTemperature = env.getInnerTemperature();
            }
            envReqModel.InnerTemperature = InnerTemperature;

            int InnerHumidity = 0; // 内部相对湿度: 精确到1%
            if (env.hasInnerHumidity()) {
                InnerHumidity = env.getInnerHumidity();
            }
            envReqModel.InnerHumidity = InnerHumidity;

            int InnerPressure = 0; // 内部气压: 单位：帕
            if (env.hasInnerPressure()) {
                InnerPressure = env.getInnerPressure();
            }
            envReqModel.InnerPressure = InnerPressure;

            int AmbientLight = 0; // 光照强度: 单位：勒克斯
            if (env.hasAmbientLight()) {
                AmbientLight = env.getAmbientLight();
            }
            envReqModel.AmbientLight = AmbientLight;
            model.envs.add(envReqModel);
        }
        return model;
    }

    public static byte[] getEnvRspModel(IdentityModel model) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    public static byte[] getEnvRspModelCustom(IdentityModel model, int rspCode) {
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
