package com.druid.ota.utils;

public class V1ParameterUtils {
    /**
     * 0xfffffffe 表示擦除，回复默认
     * 0xffffffff 表示保持原来的不动
     */
    public static protocol_v1.V1Parameter.Parameter.Builder getParameterOTA() {
        protocol_v1.V1Parameter.Parameter.Builder parameter =
                protocol_v1.V1Parameter.Parameter.newBuilder();
        parameter.setHashMillisecond(0);
        parameter.setHashSecond(0xffffffff);
        return parameter;
    }
}
