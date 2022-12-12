package com.android.log.log;

import android.content.Context;
import android.text.TextUtils;

import com.android.log.controller.BleHistoryController;
import com.android.log.controller.BleHistoryTimeController;
import com.android.log.controller.BleLocalLogController;
import com.android.log.controller.DbLogController;
import com.android.log.entity.BleLogEntity;
import com.android.log.utils.LogTimeUtils;
import com.android.log.utils.PhoneUtils;

public class MultiLog extends BaseLog {
    public static String TAG = MultiLog.class.getName();

    private MultiLog(Context context) {
        this.context = context;
    }

    private static MultiLog mInstance = null;
    private Context context = null;

    public static void initialize(Context context) {
        if (mInstance == null) {
            mInstance = new MultiLog(context);
            DbLogController.initialize(context);
            BleLocalLogController.initialize(context);
            BleHistoryController.initialize(context);
            BleHistoryTimeController.initialize(context);
        }
    }

    public static MultiLog instance() {
        return mInstance;
    }

    public void saveServerLog(String tag, String user_id, String mac,
                              String device_id, String result, String details) {
        if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(device_id) || TextUtils.isEmpty(mac)) {
            return;
        }
        BleLogEntity bleLog = new BleLogEntity();
        bleLog.user_id = user_id;
        bleLog.device_id = device_id;
        bleLog.mac = mac.toLowerCase();//小写
        bleLog.phone_series = PhoneUtils.getPhoneSerial(context);
        bleLog.phone_model = "Android";
        bleLog.phone_id = PhoneUtils.getUniqueID(context);
        long timestamp = System.currentTimeMillis();
        bleLog.timestamp = LogTimeUtils.getRF3399Time(timestamp);
        bleLog.operation_type = tag;
        bleLog.operation_tag = tag;
        bleLog.operation_result = result;
        bleLog.operation_details = details;
        DbLogController.getInstance().insert(bleLog);
    }

}
