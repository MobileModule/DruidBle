package com.device.ble.core;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.android.log.log.BaseLog;
import com.android.log.log.MultiLog;
import com.device.ble.interfaces.DeviceStatusListener;
import com.device.ble.cmd.BleRequest;
import com.device.ble.interfaces.BleCommandListener;
import com.device.ble.interfaces.BleConnectionListener;
import com.device.ble.cmd.BleResponse;
import com.device.ble.cnf.BleConfig;
import com.device.ble.entity.BleConnectState;
import com.device.ble.timeout.Timeout;
import com.device.ble.timeout.TimeoutRunnable;
import com.device.ble.utils.BluetoothBroadcastUtils;
import com.device.ble.utils.BluetoothBroadcastUtils.DEVICE_RUN_STATUS;
import com.device.ble.cmd.ProtocolType;
import com.device.ble.utils.ByteUtil;
import com.device.ble.utils.DruidBleFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dale on 18/4/15.
 */
public class DruidBleManager implements BleManagerImpl2 {
    private Timeout timeout = new Timeout();
    protected int connState = BleConnectState.STATE_DISCONNECTED;

    @Override
    public boolean thisIsConnected() {
        return connState == BleConnectState.STATE_CONNECTED;
    }

    private String forceMac = "";

    @Override
    public void forceDisConnect(String forceMac) {
        this.forceMac = forceMac;
        if (!TextUtils.isEmpty(forceMac)) {
            disConnect();
            closeCache();
            clearGatt();
        }
    }

    private void checkoutForceClose() {
        if (!TextUtils.isEmpty(forceMac)) {
            if (mGatt != null) {
                if (forceMac.equalsIgnoreCase(mGatt.getDevice().getAddress())) {
                    disConnect();
                    closeCache();
                    clearGatt();
                }
            }
        }
    }

    protected TimeoutRunnable serviceDiscoveryTimeoutRunnable = null;

    private void getServiceDiscoveryTimeoutRunnable() {
        if (serviceDiscoveryTimeoutRunnable != null) {
            serviceDiscoveryTimeoutRunnable.cancel();
        }
        serviceDiscoveryTimeoutRunnable = new TimeoutRunnable(new TimeoutRunnable.TimeoutListener() {
            @Override
            public void timeout() {
                connectFailedEvent(BleConnectState.STATE_GATT_DISCOVERY_FAILED);
            }
        });
    }

    private BleConnectionListener bleConnectionListener = null;

    @SuppressLint("MissingPermission")
    private void connectedEvent() {
        if (bleConnectionListener != null) {
            bleConnectionListener.onConnected(connParameter, function);
        }
        //【兼容华为手机】
        if (segmentsToSend.size() > 0) {
            isSendingSegment = true;
            mCtrlCharacteristic_Write.setValue(segmentsToSend.get(0));
            mGatt.writeCharacteristic(mCtrlCharacteristic_Write);
            if (segmentsToSend.size() > 0) {
                byte[] datas_byte = segmentsToSend.get(0);
                if (datas_byte.length > 0) {

                }
            }
        }
    }

    private void connectFailedEvent(int code) {
        disConnect();
        closeCache();
        if (bleConnectionListener != null) {
            bleConnectionListener.onFailed(code, connParameter, function);
        }
    }

    private void disconnectedEvent() {
        closeCache();
        connState = BleConnectState.STATE_DISCONNECTED;
        if (bleConnectionListener != null) {
            bleConnectionListener.onDisconnected(connParameter, function);
        }
    }

    private void unsupportedEvent() {
        disConnect();
        closeCache();
        if (bleConnectionListener != null) {
            bleConnectionListener.onUnsupport(connParameter, function);
        }
    }

    private void statusErrorEvent(String mac, boolean active, boolean active_) {
        disConnect();
        closeCache();
        if (statusListener != null) {
            statusListener.deviceStatusError(mac, active, active_);
        }
    }

    private BleCommandListener bleCommandListener = null;

    private void doOnResponse(BleResponse resp) {
        if (bleCommandListener != null) {
            bleCommandListener.onResponse(connParameter, function, resp);
        }
    }

    private void doOnCommandFailed(BleRequest cmd) {
        if (bleCommandListener != null) {
            bleCommandListener.onResponseFailed(connParameter, function, cmd);
        }
    }

    @Override
    public void setCommandCallback(BleCommandListener bleCommandListener) {
        this.bleCommandListener = bleCommandListener;
    }

    BluetoothDevice device;

    @SuppressLint("MissingPermission")
    @Override
    public void connect(BleConnectionListener callback) {
        closeCache();
        checkoutForceClose();
        this.bleConnectionListener = callback;
        if (mBluetoothAdapter == null || connParameter.mac == null) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "BluetoothAdapter not initialized or unspecified address.", true);
            connectFailedEvent(BleConnectState.STATE_NOT_FOUND);
            return;
        }

        if (mBluetoothDeviceAddress != null && connParameter.mac.equals(mBluetoothDeviceAddress)
                && mGatt != null) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "Trying to use an existing mBluetoothGatt for connection.", true);
            if (mGatt.connect()) {
                connState = BleConnectState.STATE_CONNECTING;
                return;
            } else {
                connectFailedEvent(BleConnectState.STATE_CONNECT_FAILED);
                return;
            }
        }

        device = mBluetoothAdapter.getRemoteDevice(connParameter.mac);
        if (device == null) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "Device not found.  Unable to connect.", true);
            connectFailedEvent(BleConnectState.STATE_NOT_FOUND);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mGatt = device.connectGatt(mContext, false, gattCallback,
                    BluetoothDevice.TRANSPORT_LE);
        } else {
            mGatt = device.connectGatt(mContext, false, gattCallback);
        }
        MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "Trying to create a new connection.", true);
        getServiceDiscoveryTimeoutRunnable();
        timeout.schedule(serviceDiscoveryTimeoutRunnable, BleConfig.SERVICE_DISCOVERY_TIMEOUT_MS);
        mBluetoothDeviceAddress = connParameter.mac;
        connState = BleConnectState.STATE_CONNECTING;
        return;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void disConnect() {
        if (connState == BleConnectState.STATE_DISCONNECTED) {
            return;
        }
        if (serviceDiscoveryTimeoutRunnable != null) {
            serviceDiscoveryTimeoutRunnable.cancel();
        }
        connState = BleConnectState.STATE_DISCONNECTED;
        if (mGatt != null) {
            mGatt.disconnect();
        }
    }

    @Override
    @SuppressLint("MissingPermission")
    public void closeCache() {
        if (serviceDiscoveryTimeoutRunnable != null) {
            serviceDiscoveryTimeoutRunnable.cancel();
        }
        connState = BleConnectState.STATE_DISCONNECTED;
        try {
            //清空缓存发送数据
            segmentsToSend.clear();
            if (mGatt != null) {
                refreshDeviceCache(mGatt);
                mGatt.close();
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void clearGatt() {
        this.mGatt = null;
    }

    /**
     * 清理本地的BluetoothGatt 的缓存，以保证在蓝牙连接设备的时候，设备的服务、特征是最新的
     */
    public boolean refreshDeviceCache(BluetoothGatt gatt) {
        if (null != gatt) {
            try {
                BluetoothGatt localBluetoothGatt = gatt;
                Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
                if (localMethod != null) {
                    boolean bool = ((Boolean) localMethod.invoke(
                            localBluetoothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------
    private ArrayList<byte[]> segmentsToSend = new ArrayList<>();
    private boolean isSendingSegment = false;
    public int curMTU = 20;

    @Override
    @SuppressLint("MissingPermission")
    public void sendCommand(BleRequest cmd) {
        if (segmentsToSend.isEmpty()) {
            isSendingSegment = false;
        }
        segmentsToSend.addAll(cmd.asSegments(version, curMTU));
        if (!isSendingSegment) {
            if (segmentsToSend.size() > 0) {
                isSendingSegment = true;
                mCtrlCharacteristic_Write.setValue(segmentsToSend.get(0));
                mGatt.writeCharacteristic(mCtrlCharacteristic_Write);
                if (segmentsToSend.size() > 0) {
                    byte[] datas_byte = segmentsToSend.get(0);
                    if (datas_byte.length > 0) {

                    }
                }
            }
        }
    }

    //region 回调
    @SuppressLint("MissingPermission")
    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, final int status, final int newState) {
            mGatt = gatt;
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    setBleMTU(gatt);
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    if (connState == BleConnectState.STATE_CONNECTING) {
                        MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "gatt disconnected", true);
                        connectFailedEvent(BleConnectState.STATE_CONNECT_FAILED);
                    } else if (connState >= BleConnectState.STATE_CONNECTING) {
                        gatt.close();
                        disconnectedEvent();
                    }
                }
            } else {
                if (connState == BleConnectState.STATE_CONNECTING) {
                    connectFailedEvent(BleConnectState.STATE_CONNECT_FAILED);
                } else if (connState > BleConnectState.STATE_CONNECTING) {
                    gatt.close();
                    closeCache();
                    disconnectedEvent();
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (gatt != mGatt) {
                return;
            }
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (serviceDiscoveryTimeoutRunnable != null) {
                    serviceDiscoveryTimeoutRunnable.cancel();
                }
                setNotifyService(gatt);
            } else {
                connectFailedEvent(BleConnectState.STATE_GATT_SERVICE_ENABLE_FAILED);
            }
        }

        public void setBleMTU(BluetoothGatt gatt) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                mGatt.requestMtu(BleConfig.MAX_MTU);
            } else {
                discoverServices(gatt);
            }
        }

        public void discoverServices(BluetoothGatt gatt) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "gatt connected, starting service discovery.", true);
            connState = BleConnectState.STATE_GATT_DISCOVERY;
            gatt.discoverServices();
        }

        public void setNotifyService(BluetoothGatt gatt) {
            boolean device_legal = false;
            //是否是公司设备
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDruidDevices(getName(gatt), connParameter.bcData)) {
                if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(getName(gatt),
                        connParameter.bcData)) {

                    if (function == DruidBleFunction.DEVICE_ON || function == DruidBleFunction.HUB_ON) {//开机
                        //是否支持开关机设备
                        DEVICE_RUN_STATUS.DeviceStatus deviceStatus =
                                DEVICE_RUN_STATUS.getDeviceStatus(getName(gatt), connParameter.bcData);
                        if (deviceStatus == DEVICE_RUN_STATUS.DeviceStatus.unkown) {//TODO 开机状态不知道
                            //继续开机
                            device_legal = true;
                        } else {
                            if (deviceStatus == DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                                //设备已经开机,不能再开机
                                statusErrorEvent(getName(gatt), true, true);
                                return;
                            } else {//设备未开机
                                //开机
                                device_legal = true;
                            }
                        }
                    }

                    if (function == DruidBleFunction.DEVICE_OFF || function == DruidBleFunction.HUB_OFF) {//关机
                        //是否支持开关机设备
                        DEVICE_RUN_STATUS.DeviceStatus deviceStatus =
                                DEVICE_RUN_STATUS.getDeviceStatus(getName(gatt), connParameter.bcData);
                        if (deviceStatus == DEVICE_RUN_STATUS.DeviceStatus.unkown) {//TODO 开机状态不知道
                            //继续关机
                            device_legal = true;
                        } else {
                            if (deviceStatus == DEVICE_RUN_STATUS.DeviceStatus.active_A) {//设备已经开机
                                //关机
                                device_legal = true;
                            } else {
                                //设备已经关机了,不能再关机
                                statusErrorEvent(getName(gatt), false, false);
                                return;
                            }
                        }
                    }

                    if (function == DruidBleFunction.DEVICE_MARK) {//打标
                        //支持打标
                        device_legal = true;
                    }

                    if (function == DruidBleFunction.DEVICE_COLLECT_DATAS) {//收取数据
                        //支持收取数据
                        device_legal = true;
                    }

                    if (function == DruidBleFunction.DEVICE_SETTING ||
                            function == DruidBleFunction.HUB_SETTING) {//同步配置
                        //支持收取数据
                        device_legal = true;
                        //不支持收取数据
//                            doOnUnsupport();
                    }

                    if (function == DruidBleFunction.DEVICE_OTA) {
                        //支持OTA
                        device_legal = true;
                    }

                    if (function == DruidBleFunction.DEVICE_RESET) {
                        //支持设备重置
                        device_legal = true;
                    }

                    if (function == DruidBleFunction.HUB_QUEST) {//中继功能
                        DEVICE_RUN_STATUS.DeviceStatus deviceStatus =
                                DEVICE_RUN_STATUS.getDeviceStatus(getName(gatt), connParameter.bcData);
                        if (deviceStatus == DEVICE_RUN_STATUS.DeviceStatus.unkown) {
                            device_legal = true;
                        } else {
                            if (deviceStatus == DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                                device_legal = true;
                            } else {//设备未开机
                                statusErrorEvent(getName(gatt), true, true);
                                return;
                            }
                        }
                    }
                }
            }
            //TODO
            if (!enableNotification()) {
                connectFailedEvent(BleConnectState.STATE_GATT_SERVICE_ENABLE_FAILED);
            }

        }

        @Override
        public void onDescriptorWrite(final BluetoothGatt gatt, BluetoothGattDescriptor descriptor, final int status) {
            Log.i("onDescriptorWrite", "onDescriptorWrite:" + status);
            if (gatt != mGatt) {
                return;
            }

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (descriptor == mCtrlCharCccDescriptor) {
                    try {
                        String name = gatt.getDevice().getName();
                        if (!TextUtils.isEmpty(name)) {
                            /**
                             * 连接成功
                             */
                            connectedEvent();
                            Log.i("Bluetooth connected -->", connParameter.getOriginBleName());
                        } else {
                            connectedEvent();
                        }
                    } catch (Exception e) {
                        connectedEvent();
                    }
                }
            } else {
                disconnectedEvent();
            }
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.i("onDescriptorRead", "onDescriptorRead");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            curMTU = mtu - 3;
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "onMtuChanged-->" + curMTU, true);
            discoverServices(gatt);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (gatt != mGatt || characteristic != mCtrlCharacteristic_Write) {
                return;
            }
            if (status == BluetoothGatt.GATT_SUCCESS) {
                final byte[] request = characteristic.getValue();
                if (function != DruidBleFunction.DEVICE_OTA) {
                    if (ctrlResponse.getProtoCmd() != 3004 && ctrlResponse.getProtoCmd() != 3001&&
                            ctrlResponse.getProtoCmd() != 1026) {
                        MultiLog.instance().ble_w(BaseLog.BLE_DRIVE_WRITE, connParameter.mac, "request:" + ByteUtil.byteArrayToHexStringPretty(request), true);
                    }
                }
                if (!segmentsToSend.isEmpty()) {
                    segmentsToSend.remove(0);
                }
                if (!segmentsToSend.isEmpty()) {
                    mCtrlCharacteristic_Write.setValue(segmentsToSend.get(0));
                    mGatt.writeCharacteristic(mCtrlCharacteristic_Write);
                } else {
                    isSendingSegment = false;
                }
            } else {
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (gatt != mGatt) {
                return;
            }
            if (characteristic == mCtrlCharacteristic_Notify) {
                if (ctrlResponse.update(characteristic.getValue())) {
                    if (bleCommandListener != null) {
                        ctrlResponse.setMac(gatt.getDevice().getAddress());
                        ctrlResponse.setOriginBleName(gatt.getDevice().getName());
                        doOnResponse(ctrlResponse);
                    }
                    if (function != DruidBleFunction.DEVICE_OTA ) {
                        if (ctrlResponse.getProtoCmd() != 3004 && ctrlResponse.getProtoCmd() != 3001&&
                                ctrlResponse.getProtoCmd() != 1026) {
                            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE_READ, connParameter.mac, String.format("response: protoCmd=%d, pkg-data=", ctrlResponse.getProtoCmd()) +
                                    ByteUtil.byteArrayToHexStringPretty(ctrlResponse.getFirstData()), true);
                        }
                    }
                }
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }
    };
    //endregion

    private BleResponse ctrlResponse = new BleResponse();

    private UUID mServiceUUID = null;
    private UUID mCtrlCharacteristicUUID_Write = null;
    private UUID mCtrlCharacteristicUUID_Notify = null;
    private UUID mCccUUID = null;

    private BluetoothGattCharacteristic mCtrlCharacteristic_Write = null;
    private BluetoothGattCharacteristic mCtrlCharacteristic_Notify = null;
    private BluetoothGattDescriptor mCtrlCharCccDescriptor = null;

    private boolean enableNotification() {
        return enableNotificationV1();
    }

    @SuppressLint("MissingPermission")
    private boolean enableNotificationV1() {
        setCharacteristicUUID();
        mCtrlCharacteristic_Write = null;
        mCtrlCharacteristic_Notify = null;
        mCtrlCharCccDescriptor = null;
        //
        BluetoothGattService service = mGatt.getService(mServiceUUID);
        if (null == service) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Service not found!UUID："
                    + mServiceUUID.toString(), true);
            return false;
        }
        //
        mCtrlCharacteristic_Write = service.getCharacteristic(mCtrlCharacteristicUUID_Write);
        if (null == mCtrlCharacteristic_Write) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Control Characteristic not found!UUID："
                    + mCtrlCharacteristicUUID_Write.toString(), true);
            return false;
        }
        //透传
        mCtrlCharacteristic_Write.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        //
        mCtrlCharacteristic_Notify = service.getCharacteristic(mCtrlCharacteristicUUID_Notify);
        if (null == mCtrlCharacteristic_Notify) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Control Characteristic not found!UUID："
                    + mCtrlCharacteristicUUID_Notify.toString(), true);
            return false;
        }
        mCtrlCharacteristic_Notify.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        //
        mCtrlCharCccDescriptor = mCtrlCharacteristic_Notify.getDescriptor(mCccUUID);
        if (null == mCtrlCharCccDescriptor) {
            mCtrlCharacteristic_Notify = null;
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: CCC for Control Characteristic not found!UUID："
                    + mCccUUID.toString(), true);
            return false;
        }
        //
        boolean enableNotify = mGatt.setCharacteristicNotification(mCtrlCharacteristic_Notify, true);
        if (enableNotify) {
            mCtrlCharCccDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            boolean enableDescriptor = mGatt.writeDescriptor(mCtrlCharCccDescriptor);
            if (enableDescriptor) {
                MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "Enabling notification", true);
                connState = BleConnectState.STATE_CONNECTED;
            } else {
                MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "writeDescriptor set CCC false", true);
                return false;
            }
        }
        return enableNotify;
    }

    @SuppressLint("MissingPermission")
    private boolean enableNotificationV2() {
        setCharacteristicUUID();
        mCtrlCharacteristic_Write = null;
        mCtrlCharacteristic_Notify = null;
        mCtrlCharCccDescriptor = null;
        //
        BluetoothGattService service = null;
        List<BluetoothGattService> services = mGatt.getServices();
        for (BluetoothGattService gattService : services) {
            if (mServiceUUID.equals(gattService.getUuid())) {
                service = gattService;
                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    if (mCtrlCharacteristicUUID_Write.equals(gattCharacteristic.getUuid())) {
                        mCtrlCharacteristic_Write = gattCharacteristic;
                        mCtrlCharacteristic_Write.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                    }
                    if (mCtrlCharacteristicUUID_Notify.equals(gattCharacteristic.getUuid())) {
                        mCtrlCharacteristic_Notify = gattCharacteristic;
                        mCtrlCharacteristic_Notify.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                        //
                        List<BluetoothGattDescriptor> gattDescriptors = mCtrlCharacteristic_Notify.getDescriptors();
                        for (BluetoothGattDescriptor gattDescriptor : gattDescriptors) {
                            if (mCccUUID.equals(gattDescriptor.getUuid())) {
                                mCtrlCharCccDescriptor = gattDescriptor;
                                boolean enableNotify = mGatt.setCharacteristicNotification(mCtrlCharacteristic_Notify, true);
                                if (enableNotify) {
                                    mCtrlCharCccDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    boolean enableDescriptor = mGatt.writeDescriptor(mCtrlCharCccDescriptor);
                                    if (enableDescriptor) {
                                        MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "Enabling notification", true);
                                        connState = BleConnectState.STATE_CONNECTED;
                                        return true;
                                    } else {
                                        MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "writeDescriptor set CCC false", true);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        //
        if (null == service) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Service not found!UUID："
                    + mServiceUUID.toString(), true);
            return false;
        }
        if (null == mCtrlCharacteristic_Write) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Control Characteristic not found!UUID："
                    + mCtrlCharacteristicUUID_Write.toString(), true);
            return false;
        }
        if (null == mCtrlCharacteristic_Notify) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Control Characteristic not found!UUID："
                    + mCtrlCharacteristicUUID_Notify.toString(), true);
            return false;
        }
        if (null == mCtrlCharCccDescriptor) {
            mCtrlCharacteristic_Notify = null;
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: CCC for Control Characteristic not found!UUID："
                    + mCccUUID.toString(), true);
            return false;
        }
        return false;
    }

    private ProtocolType.Version version = null;

    private boolean setCharacteristicUUID() {
        if (function == DruidBleFunction.DEVICE_ON || function == DruidBleFunction.DEVICE_OFF
                || function == DruidBleFunction.DEVICE_OTA) {
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceFlex3G(getName(mGatt))) {
                mServiceUUID = BluetoothBroadcastUtils.UUID.FLEX_3G.mServiceUUID;
                mCtrlCharacteristicUUID_Write = BluetoothBroadcastUtils.UUID.FLEX_3G.mCtrlCharacteristicUUID_Write;
                mCtrlCharacteristicUUID_Notify = BluetoothBroadcastUtils.UUID.FLEX_3G.mCtrlCharacteristicUUID_Notify;
                mCccUUID = BluetoothBroadcastUtils.UUID.FLEX_3G.mCccUUID;
                version = ProtocolType.Version.v1_3g_flex;
                return true;
            } else {
                if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(getName(mGatt), connParameter.bcData)) {
                    mServiceUUID = BluetoothBroadcastUtils.UUID.MINI.mServiceUUID;
                    mCtrlCharacteristicUUID_Write = BluetoothBroadcastUtils.UUID.MINI.mCtrlCharacteristicUUID_Write;
                    mCtrlCharacteristicUUID_Notify = BluetoothBroadcastUtils.UUID.MINI.mCtrlCharacteristicUUID_Notify;
                    mCccUUID = BluetoothBroadcastUtils.UUID.MINI.mCccUUID;
                    version = ProtocolType.Version.v1_mini_other;
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(getName(mGatt), connParameter.bcData)) {
                mServiceUUID = BluetoothBroadcastUtils.UUID.MINI.mServiceUUID;
                mCtrlCharacteristicUUID_Write = BluetoothBroadcastUtils.UUID.MINI.mCtrlCharacteristicUUID_Write;
                mCtrlCharacteristicUUID_Notify = BluetoothBroadcastUtils.UUID.MINI.mCtrlCharacteristicUUID_Notify;
                mCccUUID = BluetoothBroadcastUtils.UUID.MINI.mCccUUID;
                version = ProtocolType.Version.v1_mini_other;
                return true;
            } else {
                return false;
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    protected Context mContext;
    protected BluetoothAdapter mBluetoothAdapter;
    protected BluetoothManager mBluetoothManager;
    protected BluetoothGatt mGatt;
    protected BleConnParameter connParameter;
    private String mBluetoothDeviceAddress;
    private DruidBleFunction function = DruidBleFunction.DEVICE_ON;//0 开机，1 关机

    private DeviceStatusListener statusListener = null;

    @Override
    public void setDeviceStatusListener(DeviceStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    @Override
    public void setDruidBleFunction(DruidBleFunction function) {
        this.function = function;
    }

    @Override
    public void setBleConnParameter(BleConnParameter connParameter) {
        this.connParameter = connParameter;
    }

    public String getAddress() {
        if (mGatt != null) {
            return mGatt.getDevice().getAddress();
        }
        return "";
    }

    public String getName(BluetoothGatt gatt) {
        return connParameter.getOriginBleName();
    }

    public DruidBleManager(Context context, BluetoothManager mBluetoothManager, BluetoothAdapter mBluetoothAdapter) {
        mContext = context.getApplicationContext();
        this.mBluetoothManager = mBluetoothManager;
        this.mBluetoothAdapter = mBluetoothAdapter;
    }

    public DruidBleManager(Context context) {
        mContext = context.getApplicationContext();
        this.mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        this.mBluetoothAdapter = mBluetoothManager.getAdapter();
    }
}
