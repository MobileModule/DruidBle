package com.druid.ota.utils;


public class V1SettingUtils {
    /**
     * 0xfffffffe 表示擦除，回复默认
     * 0xffffffff 表示保持原来的不动
     */
    public static protocol_v1.V1Setting.Setting.Builder getSettingOTA() {
        //
        protocol_v1.V1Setting.Setting.Builder setting = protocol_v1.V1Setting.Setting.newBuilder();
        setting.setHashMillisecond(0);
        setting.setHashSecond(0xffffffff);
        return setting;
    }
}
