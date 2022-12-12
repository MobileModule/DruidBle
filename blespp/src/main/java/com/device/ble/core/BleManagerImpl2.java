package com.device.ble.core;

import com.device.ble.interfaces.DeviceStatusListener;
import com.device.ble.interfaces.BleCommandListener;
import com.device.ble.interfaces.BleConnectionListener;
import com.device.ble.cmd.BleRequest;
import com.device.ble.utils.DruidBleFunction;

public interface BleManagerImpl2 {
    void setCommandCallback(BleCommandListener bleCommandListener);

    void setDruidBleFunction(DruidBleFunction function);

    void setBleConnParameter(BleConnParameter connParameter);

    void connect(BleConnectionListener callback);

    void setDeviceStatusListener(DeviceStatusListener statusListener);

    void sendCommand(BleRequest cmd);

    boolean thisIsConnected();

    void forceDisConnect(String forceMac);

    void disConnect();

    void closeCache();

    void clearGatt();
}
