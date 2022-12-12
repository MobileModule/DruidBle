package com.device.ble.le;

public interface LeAdvSetListener {
    void advStarted();
    void advStopped();
    void advDataSet();
    void advScanResponseDataSet();
}
