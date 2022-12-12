package com.device.ble.cnf;

public class BleConfig {
    public static final int BLE_MAX_WRITE_RETRIES = 2;

    public static final int BLE_WRITE_TIMEOUT_MS = 1600;

    public static final int BLE_WRITE_RETRY_MS = 10;

    public static final int SERVICE_DISCOVERY_TIMEOUT_MS = 1000 * 15;

    public static final int SERVICE_DISCOVERY_TIMEOUT_MS_MIN = 1000 * 5;

    public static final int SERVICE_RESPOND_TIMEOUT_MS = 1000 * 10;

    public static final int MIN_MTU = 23;

    public static final int MAX_MIDDLE = 40;

    public static final int MAX_MTU = 517;
}
