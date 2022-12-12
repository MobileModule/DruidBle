package com.device.ble.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertisingSet;
import android.bluetooth.le.AdvertisingSetCallback;
import android.bluetooth.le.AdvertisingSetParameters;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Build;
import android.os.ParcelUuid;
import androidx.annotation.RequiresApi;
import android.util.Log;

import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LePhyAdv extends AdvertisingSetCallback {
    public static final String LOG_TAG = "BLE 5.0";
    private BluetoothAdapter adapter = null;
    private BluetoothLeAdvertiser advertiser = null;
    private boolean advertiserStarted = false;
    private AdvertisingSet currentAdvertisingSet = null;
    private int maxDataLength = 0;
    private LeAdvSetListener listener = null;

    public LePhyAdv(LeAdvSetListener listener) {
        this.listener = listener;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.maxDataLength = adapter.getLeMaximumAdvertisingDataLength();
    }

    public boolean support1MPhy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean support = true;
            if (!BluetoothAdapter.getDefaultAdapter().isLe2MPhySupported()) {
                Log.e(LOG_TAG, "2M PHY not supported!");
                support = false;
            }

            if (!BluetoothAdapter.getDefaultAdapter().isLeCodedPhySupported()) {
                Log.e(LOG_TAG, "LE Coded Phy not supported!");
                support = false;
            }

            if (!BluetoothAdapter.getDefaultAdapter().isLeExtendedAdvertisingSupported()) {
                Log.e(LOG_TAG, "LE Extended Advertising not supported!");
                support = false;
            }

            if (!BluetoothAdapter.getDefaultAdapter().isLePeriodicAdvertisingSupported()) {
                Log.e(LOG_TAG, "LE Periodic Advertising not supported!");
                support = false;
            }
            return support;
        } else {
            return false;
        }
    }

    public boolean support2MPhy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean support = true;
            if (!BluetoothAdapter.getDefaultAdapter().isLe2MPhySupported()) {
                Log.e(LOG_TAG, "2M PHY not supported!");
                support = false;
            }

            if (!BluetoothAdapter.getDefaultAdapter().isLeExtendedAdvertisingSupported()) {
                Log.e(LOG_TAG, "LE Extended Advertising not supported!");
                support = false;
            }

            return support;
        } else {
            return false;
        }
    }

    public boolean supportCodedPhy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean support = true;
            if (!BluetoothAdapter.getDefaultAdapter().isLeCodedPhySupported()) {
                Log.e(LOG_TAG, "LE Coded Phy not supported!");
                support = false;
            }

            if (!BluetoothAdapter.getDefaultAdapter().isLeExtendedAdvertisingSupported()) {
                Log.e(LOG_TAG, "LE Extended Advertising not supported!");
                support = false;
            }
            return support;
        } else {
            return false;
        }
    }

    public void init1MPhy() {
        if (support1MPhy()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //默认情况下，Android 8.0 使用蓝牙 4.2 的蓝牙 LE 1M PHY
                advertiser = adapter.getBluetoothLeAdvertiser();
                AdvertisingSetParameters parameters = (new AdvertisingSetParameters.Builder())
                        .setLegacyMode(true) // True by default, but set here as a reminder.
                        .setConnectable(true)
                        .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
                        .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)
                        .build();
                AdvertiseData data = (new AdvertiseData.Builder()).setIncludeDeviceName(true).build();
                advertiser.startAdvertisingSet(parameters, data, null, null, null, this);
            }
        }
    }

    public void init2MPhy() {
        if (support1MPhy()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                advertiser = adapter.getBluetoothLeAdvertiser();
                AdvertisingSetParameters parameters = (new AdvertisingSetParameters.Builder())
                        .setLegacyMode(false) // True by default, but set here as a reminder.
                        .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
                        .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)
                        .setPrimaryPhy(BluetoothDevice.PHY_LE_2M)
                        .setSecondaryPhy(BluetoothDevice.PHY_LE_2M)
                        .build();
                AdvertiseData data = (new AdvertiseData.Builder()).setIncludeDeviceName(true).build();
                advertiser.startAdvertisingSet(parameters, data, null, null, null, this);
            }
        }
    }

    public void initCodedPhy() {
        if (support1MPhy()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                advertiser = adapter.getBluetoothLeAdvertiser();
                AdvertisingSetParameters parameters = (new AdvertisingSetParameters.Builder())
                        .setLegacyMode(false) // True by default, but set here as a reminder.
                        .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
                        .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)
                        .setPrimaryPhy(BluetoothDevice.PHY_LE_CODED)
                        .setSecondaryPhy(BluetoothDevice.PHY_LE_CODED)
                        .build();
                AdvertiseData data = (new AdvertiseData.Builder()).setIncludeDeviceName(true).build();
                advertiser.startAdvertisingSet(parameters, data, null, null, null, this);
            }
        }
    }

    public void stopAdvertisingSet() {
        if (advertiserStarted) {
            if (advertiser != null) {
                advertiser.stopAdvertisingSet(this);
            }
        }
    }

    @Override
    public void onAdvertisingSetStarted(AdvertisingSet advertisingSet, int txPower, int status) {
        super.onAdvertisingSetStarted(advertisingSet, txPower, status);
        Log.i(LOG_TAG, "onAdvertisingSetStarted(): txPower:" + txPower + " , status: "
                + status);
        if (listener != null) {
            listener.advStarted();
        }
        this.currentAdvertisingSet = advertisingSet;
        this.advertiserStarted = true;
        //TODO
        // After onAdvertisingSetStarted callback is called, you can modify the
        // advertising data and scan response data:
    }

    @Override
    public void onAdvertisingSetStopped(AdvertisingSet advertisingSet) {
        super.onAdvertisingSetStopped(advertisingSet);
        Log.i(LOG_TAG, "onAdvertisingSetStopped()");
        if (listener != null) {
            listener.advStopped();
        }
        this.advertiserStarted = false;
    }

    @Override
    public void onAdvertisingEnabled(AdvertisingSet advertisingSet, boolean enable, int status) {
        super.onAdvertisingEnabled(advertisingSet, enable, status);
    }

    @Override
    public void onAdvertisingDataSet(AdvertisingSet advertisingSet, int status) {
        super.onAdvertisingDataSet(advertisingSet, status);
        Log.i(LOG_TAG, "onAdvertisingDataSet() :status:" + status);
        if (listener != null) {
            listener.advDataSet();
        }
        //TODO
//        currentAdvertisingSet.setScanResponseData(new
//                AdvertiseData.Builder().addServiceUuid(new ParcelUuid(UUID.randomUUID())).build());
        currentAdvertisingSet.setScanResponseData(new
                AdvertiseData.Builder().addServiceUuid(new ParcelUuid(UUID.randomUUID())).build());
    }

    @Override
    public void onScanResponseDataSet(AdvertisingSet advertisingSet, int status) {
        super.onScanResponseDataSet(advertisingSet, status);
        Log.i(LOG_TAG, "onScanResponseDataSet() :status:" + status);
        if (listener != null) {
            listener.advScanResponseDataSet();
        }
    }

    @Override
    public void onAdvertisingParametersUpdated(AdvertisingSet advertisingSet, int txPower, int status) {
        super.onAdvertisingParametersUpdated(advertisingSet, txPower, status);
    }

    @Override
    public void onPeriodicAdvertisingParametersUpdated(AdvertisingSet advertisingSet, int status) {
        super.onPeriodicAdvertisingParametersUpdated(advertisingSet, status);
    }

    @Override
    public void onPeriodicAdvertisingDataSet(AdvertisingSet advertisingSet, int status) {
        super.onPeriodicAdvertisingDataSet(advertisingSet, status);
    }

    @Override
    public void onPeriodicAdvertisingEnabled(AdvertisingSet advertisingSet, boolean enable, int status) {
        super.onPeriodicAdvertisingEnabled(advertisingSet, enable, status);
    }
}

