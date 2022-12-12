package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;
import com.druid.bleact.model.SettingModel;

import protocol_v1.V1IdentityMsg;
import protocol_v1.V1Setting;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class SettingParse {
    public static IdentityModel getSettingReqModel(V1Setting.SettingReq settingReq) {
        V1IdentityMsg.IdentityMsg identityMsg = settingReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        return identityModel;
    }

    public static byte[] getSettingRspModel(IdentityModel identityModel, SettingModel settingModel) {
        V1Setting.SettingRsp.Builder builder = V1Setting.SettingRsp.newBuilder();

        V1IdentityMsg.IdentityMsg.Builder identityMsgBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityMsgBuilder.setMsgToken(identityModel.MsgToken);
        identityMsgBuilder.setMsgIndex(identityModel.MsgIndex);
        builder.setIden(identityMsgBuilder.build());

        //
        if (settingModel == null) {
            V1Setting.Setting.Builder settingBuilder = V1Setting.Setting.newBuilder();
            settingBuilder.setEnvSampleMode(1);
            settingBuilder.setEnvSampleInterval(2*60);

            settingBuilder.setBehaviorSampleMode(1);
            settingBuilder.setBehaviorSampleInterval(31);

            settingBuilder.setGPSSampleMode(1);
            settingBuilder.setGPSSampleInterval(1*60);

            builder.setSettingInfo(settingBuilder.build());
        }

        return builder.build().toByteArray();
    }
}
