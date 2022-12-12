package com.druid.bleact.parse;

import protocol_v1.V1TimeHash;
import protocol_v1.V1Trigger;

public class TriggerParse {
    public static V1Trigger.TriggerRsp getTriggerRsp(V1Trigger.TriggerReq triggerReq){
        V1Trigger.TriggerRsp.Builder rspBuilder=V1Trigger.TriggerRsp.newBuilder();
        rspBuilder.setIden(triggerReq.getIden());
        V1TimeHash.HashLite.Builder hashBuilder= V1TimeHash.HashLite.newBuilder();
        //收数据时收到SlaveDevice命令，把QueueHash.second 配为0xffffffff
        //QueueHash.A 改为0xffffffff表示不用修改，和以前setting一样
        hashBuilder.setMillisecond(0xffffffff);
        rspBuilder.setTriggerHash(hashBuilder);
        return rspBuilder.build();
    }
}
