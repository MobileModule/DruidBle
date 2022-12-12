package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;

import protocol_v1.V1SlaveDevice;
import protocol_v1.V1TimeHash;

public class SlaveDeviceParse {
    public static IdentityModel getSlaveDeviceReqModel(V1SlaveDevice.SlaveDeviceReq slaveDeviceReq) {
        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = slaveDeviceReq.getIden();
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        return identityModel;
    }

    public static V1SlaveDevice.SlaveDeviceRsp getTransferSlaveDeviceRsp(V1SlaveDevice.SlaveDeviceReq slaveDeviceReq){
        V1SlaveDevice.SlaveDeviceRsp.Builder rspBuilder=V1SlaveDevice.SlaveDeviceRsp.newBuilder();
        rspBuilder.setIden(slaveDeviceReq.getIden());
        V1TimeHash.Hash.Builder hashBuilder= V1TimeHash.Hash.newBuilder();
        //收数据时收到SlaveDevice命令，把QueueHash.second 配为0xffffffff
        //QueueHash.A 改为0xffffffff表示不用修改，和以前setting一样
        hashBuilder.setA(0xffffffff);
        rspBuilder.setQueueHash(hashBuilder);
        return rspBuilder.build();
    }
}
