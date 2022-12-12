package com.druid.bleact.parse;

/**
 * Created by druid on 2019/9/23.
 */

public class HubBleScanParse {
    public static final int TIMEOUT = 60;//默认超时60秒

    // 0 for stop, 1 for Start
    public static byte[] getBleScanReq(int cmd) {
        protocol_v1.V1BleScan.BleScanReq.Builder builder =
                protocol_v1.V1BleScan.BleScanReq.newBuilder();
        builder.setCmd(cmd);
        builder.setTimeout(TIMEOUT);
        builder.setIden(ProtoToken.getToken());
        return builder.build().toByteArray();
    }

    // 0 for stop, 1 for Start
    public static byte[] getBleScanReq(int cmd,int timeout) {
        protocol_v1.V1BleScan.BleScanReq.Builder builder =
                protocol_v1.V1BleScan.BleScanReq.newBuilder();
        builder.setCmd(cmd);
        builder.setTimeout(timeout);
        builder.setIden(ProtoToken.getToken());
        return builder.build().toByteArray();
    }
}
