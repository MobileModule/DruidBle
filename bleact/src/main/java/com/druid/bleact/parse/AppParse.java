package com.druid.bleact.parse;


import com.druid.bleact.model.AppModel;
import com.druid.bleact.model.IdentityModel;

/**
 * Created by druid on 2018/11/6.
 */

public class AppParse {
    /**
     * 解析硬件数据
     *
     * @param appReq
     * @return
     */
    public static AppModel getAppModel(protocol_v1.V1App.AppReq appReq) {
        AppModel appModel = new AppModel();

        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = appReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        appModel.identityModel = identityModel;

        if(appReq.hasCommand()){
            appModel.Command=appReq.getCommand();
        }

        return appModel;
    }

    /**
     * 1 - 激活, 2 - 升级, 3 - BLE升级, 4 -  Sensor调试, 5 -  蓝牙收取数据，6 - 打标，9 - 远程控制
     */
    public static byte[] getAppRspModel(IdentityModel model,int type) {
        protocol_v1.V1App.AppRsp.Builder builder = protocol_v1.V1App.AppRsp.newBuilder();
        builder.setLinkType (type);
        protocol_v1.V1IdentityMsg.IdentityMsg.Builder identityBuilder =
                protocol_v1.V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setDeviceID(model.DeviceID);
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }


    /**
     * work_mode
     * 0 - 待定, 1 - 激活, 2 - 不激活
     */

    public static byte[] getAppRspModel(IdentityModel model, int link_type, int work_mode,int parameter) {
        protocol_v1.V1App.AppRsp.Builder builder = protocol_v1.V1App.AppRsp.newBuilder();
        builder.setLinkType(link_type);
        builder.setWorkMode(work_mode);
        if(parameter!=-1) {
            builder.setParameter(parameter);
        }
        protocol_v1.V1IdentityMsg.IdentityMsg.Builder identityBuilder =
                protocol_v1.V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setDeviceID(model.DeviceID);
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    /**
     * work_mode
     * 0 - 待定, 1 - 激活, 2 - 不激活
     */

    public static byte[] getAppRspModelReset(IdentityModel model, int link_type, int parameter) {
        protocol_v1.V1App.AppRsp.Builder builder = protocol_v1.V1App.AppRsp.newBuilder();
        builder.setLinkType(link_type);
        builder.setParameter(parameter);
        protocol_v1.V1IdentityMsg.IdentityMsg.Builder identityBuilder =
                protocol_v1.V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setDeviceID(model.DeviceID);
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }
}
