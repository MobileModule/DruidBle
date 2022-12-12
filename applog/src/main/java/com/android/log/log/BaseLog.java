package com.android.log.log;


import android.text.TextUtils;

import com.android.log.controller.BleLocalLogController;
import com.android.log.entity.LocalSaveLogEntity;

public class BaseLog {
    //蓝牙底层操作
    public static final String BLE_DRIVE = "DRUID_BLE_DRIVE";
    public static final String BLE_DRIVE_READ = "DRUID_BLE_DRIVE_READ";
    public static final String BLE_DRIVE_WRITE = "DRUID_BLE_DRIVE_WRITE";
    public static final String BLE_CONNECTION_STATUS = "DRUID_BLE_CONNECTION_STATUS";
    //回传日志
    public static final String TERMINAL_DATA_DOWNLOAD = "terminal_data_download";
    public static final String TERMINAL_TURN_ON = "terminal_turn_on";
    public static final String TERMINAL_TURN_OFF = "terminal_turn_off";
    public static final String TERMINAL_SETTING_DELIVERY = "terminal_setting_delivery";
    public static final String TERMINAL_OTA = "terminal_ota";
    public static final String TERMINAL_RESET = "terminal_reset";
    public static final String GATEWAY_DATA_DOWNLOAD = "gateway_data_download";
    public static final String GATEWAY_TURN_ON = "gateway_turn_on";
    public static final String GATEWAY_TURN_OFF = "gateway_turn_off";
    public static final String GATEWAY_SETTING_DELIVERY = "gateway_setting_delivery";
    //数据下载
    public static final String BLE_DATA_DOWNLOAD_TYPE = "DRUID_BLE_DATA_DOWNLOAD_TYPE";
    public static final String BLE_DATA_DOWNLOAD_FILE = "DRUID_BLE_DATA_DOWNLOAD_FILE";
    public static final String BLE_DATA_DOWNLOAD_UPLOAD = "DRUID_BLE_DATA_DOWNLOAD_UPLOAD";
    public static final String BLE_DATA_TAG = "DRUID_BLE_DATA_TAG";
    //下发配置
    public static final String BLE_SETTING_DELIVERY_REGISTER = "DRUID_BLE_SETTING_DELIVERY-->register-->v1";
    public static final String BLE_SETTING_DELIVERY_SETTING = "DRUID_BLE_SETTING_DELIVERY-->setting-->v1";
    public static final String BLE_SETTING_DELIVERY_HUB_SETTING = "DRUID_BLE_SETTING_DELIVERY-->hub_setting-->v1";
    public static final String BLE_SETTING_DELIVERY_HUB_TEMPLATE = "DRUID_BLE_SETTING_DELIVERY-->hub_template-->v1";
    public static final String BLE_SETTING_DELIVERY_HUB_SLAVE_DEVICE = "DRUID_BLE_SETTING_DELIVERY-->hub_slave_device-->v1";
    public static final String BLE_SETTING_DELIVERY_PARAMETER = "DRUID_BLE_SETTING_DELIVERY-->parameter-->v1";
    public static final String BLE_SETTING_DELIVERY_TRIGGER = "DRUID_BLE_SETTING_DELIVERY-->trigger-->v1";
    public static final String BLE_SETTING_DELIVERY_ARGOSAOP = "DRUID_BLE_SETTING_DELIVERY-->argos_aop-->v1";
    public static final String BLE_SETTING_DELIVERY_TYPE = "DRUID_BLE_SETTING_DELIVERY_TYPE";
    //开机
    public static final String BLE_ON_REGISTER_V0 = "DRUID_ON-->register-->v0";
    public static final String BLE_ON_SETTING_V0 = "DRUID_ON-->setting-->v0";
    public static final String BLE_ON_REGISTER_V1 = "DRUID_ON-->register-->v1";
    public static final String BLE_ON_SETTING_V1 = "DRUID_ON-->setting-->v1";
    public static final String BLE_ON_HUB_SETTING_V1 = "DRUID_ON-->hub_setting-->v1";
    public static final String BLE_ON_PARAMETER_V1 = "DRUID_ON-->parameter-->v1";
    public static final String BLE_ON_ARGOSAOP_V1 = "DRUID_ON-->argos_aop-->v1";
    //上传数据
    public static final String TCP_IP = "TCP-IP";
    public static final String TCP_PORT = "TCP-PORT";
    public static final String TCP_UPLOAD_FAILED = "TCP-UploadTask----->Failed";
    public static final String TCP_UPLOAD_SUCCESS = "TCP-UploadTask----->Success";
    public static final String TCP_ERROR = "TCP-ERROR";
    //setting
    public static final String SETTING = "setting--->";
    public static final String SETTING_SERVER = "setting-v1-to-server--->";
    public static final String SETTING_HISTORY = "setting-history--->";
    public static final String SETTING_V0_DATA = "setting-v0-data--->";
    public static final String SETTING_V1_DATA = "setting-v1-data--->";
    public static final String SETTING_V1_TIME = "setting-v1-time--->";
    public static final String PARAMETER_SERVER = "parameter-v1-to-server--->";
    //
    public static final String HTTP_CACHE_DEVICE = "http_cache_device";
    public static final String HTTP_CACHE_HUB = "http_cache_hub";
    public static final String HTTP_CACHE_QUEST = "http_cache_quest";
    public static final String HTTP_CACHE_SETTING = "http_cache_setting";
    public static final String HTTP_CACHE_PARAMETER = "http_cache_parameter";
    public static final String HTTP_CACHE_TRIGGER = "http_cache_trigger";
    public static final String HTTP_CACHE_HUB_SETTING = "http_cache_hub_setting";
    public static final String HTTP_CACHE_HUB_TEMPLATE = "http_cache_hub_template";
    public static final String HTTP_CACHE_HUB_SAVE_DEVICE = "http_cache_hub_save_device";
    //
    public static final String HTTP_NET_IP = "http_net_ip";
    public static final String HTTP_BLE_lOG = "http_ble_log";

    //
    public void ble_i(String tag, String mac, String msg, boolean save) {
        android.util.Log.i(tag, baseLog(mac, msg, save));
    }

    public void ble_d(String tag, String mac, String msg, boolean save) {
        android.util.Log.d(tag, baseLog(mac, msg, save));
    }

    public void ble_e(String tag, String mac, String msg, boolean save) {
        android.util.Log.e(tag, baseLog(mac, msg, save));
    }

    public void ble_w(String tag, String mac, String msg, boolean save) {
        android.util.Log.w(tag, baseLog(mac, msg, save));
    }

    public void ble_v(String tag, String mac, String msg, boolean save) {
        android.util.Log.v(tag, baseLog(mac, msg, save));
    }

    private String baseLog(String mac, String msg, boolean save) {
        String content = msg;
        if (!TextUtils.isEmpty(mac)) {
            content = mac + "---->" + msg;
        }
        if (save) {
            LocalSaveLogEntity log = new LocalSaveLogEntity();
            log.mac = mac;
            log.content = msg;
            log.timestamp = System.currentTimeMillis();
            BleLocalLogController.getInstance().insert(log);
        }
        return content;
    }
}
