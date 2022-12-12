package com.device.ble.interfaces;

import com.device.ble.cmd.BleResponse;
import com.device.ble.core.BleConnParameter;
import com.device.ble.utils.DruidBleFunction;

public interface BleRspChangeListener {
    void unAvailable(BleConnParameter connParameter, DruidBleFunction function);

    void validBle(BleConnParameter connParameter, DruidBleFunction function);

    void invalideMac(BleConnParameter connParameter, DruidBleFunction function);

    void bleNofound(BleConnParameter connParameter, DruidBleFunction function);

    void startConn(BleConnParameter connParameter, DruidBleFunction function);

    void connected(BleConnParameter connParameter, DruidBleFunction function);

    void connFailed(BleConnParameter connParameter, DruidBleFunction function);

    void disconn(BleConnParameter connParameter, DruidBleFunction function);

    void onUnsupport(BleConnParameter connParameter, DruidBleFunction function);

    void receiveData(BleConnParameter connParameter, DruidBleFunction function, BleResponse response);
}
