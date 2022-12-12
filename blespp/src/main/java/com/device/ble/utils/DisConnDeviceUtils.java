package com.device.ble.utils;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DisConnDeviceUtils {
    public static final String TAG = "BLE_HOLD_DEVICE";

    public static void closeConnectedDevice(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            List<BluetoothDevice> devices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT);
            List<BluetoothDevice> connectedDevices=new ArrayList<>();
            for (BluetoothDevice device : devices) {
                int connStatus = bluetoothManager.getConnectionState(device, BluetoothProfile.GATT);
                if (connStatus == BluetoothProfile.STATE_CONNECTED) {
                    Log.i(TAG, "none close: " + device.getAddress());
                    connectedDevices.add(device);
//                    device.
                }
            }
            //
            if(connectedDevices.size()>0){

            }
        }
    }

}
