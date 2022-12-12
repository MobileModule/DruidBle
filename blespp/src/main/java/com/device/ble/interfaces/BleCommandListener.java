package com.device.ble.interfaces;

import com.device.ble.cmd.BleRequest;
import com.device.ble.cmd.BleResponse;
import com.device.ble.core.BleConnParameter;
import com.device.ble.utils.DruidBleFunction;

/**
 * Created by dale on 21/4/15.
 */
public interface BleCommandListener {
     void onResponse(BleConnParameter connParameter, DruidBleFunction function,BleResponse resp);
     void onResponseFailed( BleConnParameter connParameter, DruidBleFunction function,BleRequest cmd);
}
