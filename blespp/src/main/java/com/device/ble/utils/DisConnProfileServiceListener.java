package com.device.ble.utils;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import java.lang.reflect.Method;

public class DisConnProfileServiceListener implements BluetoothProfile.ServiceListener {
    private BluetoothDevice bluetoothDevice;

    public DisConnProfileServiceListener(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public void onServiceConnected(int profile, BluetoothProfile proxy) {
        try {
            if (profile == BluetoothProfile.HEADSET) {
                BluetoothHeadset bluetoothHeadset = (BluetoothHeadset) proxy;
                boolean isDisConnect = false;
                try {
                    Method connect = bluetoothHeadset.getClass().getDeclaredMethod("disconnect", BluetoothDevice.class);
                    connect.setAccessible(true);
                    isDisConnect = (boolean) connect.invoke(bluetoothHeadset, bluetoothDevice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(DisConnDeviceUtils.TAG, "isDisConnect:" + (isDisConnect ? "断开蓝牙成功" : "断开蓝牙失败") + bluetoothDevice.getAddress());
            }
            if (profile == BluetoothProfile.A2DP) {
                //使用A2DP的协议断开蓝牙设备（使用了反射技术调用断开的方法）
                BluetoothA2dp bluetoothA2dp = (BluetoothA2dp) proxy;
                boolean isDisConnect = false;
                try {
                    Method connect = bluetoothA2dp.getClass().getDeclaredMethod("disconnect", BluetoothDevice.class);
                    connect.setAccessible(true);
                    isDisConnect = (boolean) connect.invoke(bluetoothA2dp, bluetoothDevice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(DisConnDeviceUtils.TAG, "isDisConnect:" + (isDisConnect ? "断开蓝牙成功" : "断开蓝牙失败") + bluetoothDevice.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(int profile) {

    }
}
