package com.druid.ota.utils;

import protocol_v1.V1IdentityMsg;
import protocol_v1.V1SimpleRsp;

public class V1SimpleUtils {
    public static byte[] getSimpleRsp(V1IdentityMsg.IdentityMsg identityMsg) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder =
                V1IdentityMsg.IdentityMsg.newBuilder();
        builder.setIden(identityMsg);
        return builder.build().toByteArray();
    }
}
