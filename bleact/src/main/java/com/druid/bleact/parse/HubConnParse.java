package com.druid.bleact.parse;


import com.druid.bleact.model.HubConnModel;
import com.druid.bleact.model.IdentityModel;

import protocol_v1.V1BleConnect;
import protocol_v1.V1IdentityMsg;

/**
 * Created by druid on 2019/9/23.
 */

public class HubConnParse {
    /**
     * 蓝牙远程连接
     */
    public static byte[] getHubConnReq(int linkType,String mac) {
        protocol_v1.V1BleConnect.BleConnectReq.Builder builder =
                protocol_v1.V1BleConnect.BleConnectReq.newBuilder();
        builder.setMAC(mac);
        builder.setLinkType(5);//5 - 收数据, 6 - 打标
        builder.setWorkMode(0);
        builder.setTimeout(5);
        builder.setIden(ProtoToken.getToken());
        return builder.build().toByteArray();
    }

    /**
     * 蓝牙远程连接
     */
    public static byte[] getHubConnMarkReq(int linkType,String mac) {
        protocol_v1.V1BleConnect.BleConnectReq.Builder builder =
                protocol_v1.V1BleConnect.BleConnectReq.newBuilder();
        builder.setMAC(mac);
        builder.setLinkType(5);//5 - 收数据, 6 - 打标
        builder.setWorkMode(0);
        builder.setIden(ProtoToken.getToken());
        return builder.build().toByteArray();
    }


    public static byte[] getHubDisconnReq(String mac) {
        protocol_v1.V1BleConnect.BleConnectReq.Builder builder =
                protocol_v1.V1BleConnect.BleConnectReq.newBuilder();
        builder.setMAC(mac);
        builder.setLinkType(0);//0:停止 1 - 开关机, 2 - OTA升级, 3 - BLE升级, 4 -  Sensor调试
        //5 - 收数据, 6 - 打标, 9 - 远程控制
        builder.setIden(ProtoToken.getToken());
        return builder.build().toByteArray();
    }

    public static HubConnModel getHubConnRspModel(V1BleConnect.BleConnectRsp bleConnectRsp) {
        HubConnModel model = new HubConnModel();
        //
        V1IdentityMsg.IdentityMsg identityMsg = bleConnectRsp.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        model.identityModel = identityModel;
        //
        model.event = bleConnectRsp.getEvent();
        return model;
    }
}
