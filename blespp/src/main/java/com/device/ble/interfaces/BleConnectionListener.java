package com.device.ble.interfaces;

import com.device.ble.core.BleConnParameter;
import com.device.ble.utils.DruidBleFunction;

/**
 * Created by dale on 22/4/15.
 */
public interface BleConnectionListener {
    void onConnected(BleConnParameter connParameter, DruidBleFunction function);//设备连接成功

    void onFailed(int code, BleConnParameter connParameter, DruidBleFunction function);//设备连接失败

    void onDisconnected(BleConnParameter connParameter, DruidBleFunction function);//设备连接成功后断开连接

    void onUnsupport(BleConnParameter connParameter, DruidBleFunction function);//设备不支持某种功能
}
