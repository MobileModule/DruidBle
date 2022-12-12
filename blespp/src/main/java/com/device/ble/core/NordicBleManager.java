package com.device.ble.core;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.log.log.BaseLog;
import com.android.log.log.MultiLog;
import com.device.ble.entity.BleConnectState;
import com.device.ble.interfaces.DeviceStatusListener;
import com.device.ble.interfaces.BleCommandListener;
import com.device.ble.interfaces.BleConnectionListener;
import com.device.ble.cmd.BleRequest;
import com.device.ble.cmd.BleResponse;
import com.device.ble.cmd.ProtocolType;
import com.device.ble.cnf.BleConfig;
import com.device.ble.timeout.Timeout;
import com.device.ble.timeout.TimeoutRunnable;
import com.device.ble.utils.BluetoothBroadcastUtils;
import com.device.ble.utils.ByteUtil;
import com.device.ble.utils.DruidBleFunction;

import java.util.ArrayList;
import java.util.UUID;

import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.ConnectRequest;
import no.nordicsemi.android.ble.ConnectionPriorityRequest;
import no.nordicsemi.android.ble.DisconnectRequest;
import no.nordicsemi.android.ble.PhyRequest;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.callback.DataSentCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.observer.ConnectionObserver;

public class NordicBleManager extends BleManager implements BleManagerImpl2 {
    public static final String TAG = NordicBleManager.class.getName();
    private Timeout timeout = new Timeout();

    protected int connState = BleConnectState.STATE_DISCONNECTED;

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

    private UUID mServiceUUID = null;
    private UUID mCtrlCharacteristicUUID_Write = null;
    private UUID mCtrlCharacteristicUUID_Notify = null;
    private UUID mCccUUID = null;

    private BluetoothGattCharacteristic mCtrlCharacteristic_Write = null;
    private BluetoothGattCharacteristic mCtrlCharacteristic_Notify = null;
    private BluetoothGattDescriptor mCtrlCharCccDescriptor = null;
    //
    private BluetoothDevice device;

    @Nullable
    private ConnectRequest connectRequest;

    private BleConnectionListener bleConnectionListener = null;

    private void connectedEvent() {
        if (serviceDiscoveryTimeoutRunnable != null) {
            serviceDiscoveryTimeoutRunnable.cancel();
        }
        if (bleConnectionListener != null) {
            bleConnectionListener.onConnected(connParameter, function);
        }
        //【兼容华为手机】
        if (segmentsToSend.size() > 0) {
            isSendingSegment = true;
            write(segmentsToSend.get(0));
            if (segmentsToSend.size() > 0) {
                byte[] datas_byte = segmentsToSend.get(0);
                if (datas_byte.length > 0) {

                }
            }
        }
        //判断是否退出
        checkoutForceClose();
    }

    private void connectFailedEvent(int code) {
        disConnect();
        if (bleConnectionListener != null) {
            bleConnectionListener.onFailed(code, connParameter, function);
        }
    }

    private void disconnectedEvent() {
        disConnect();
        connState = BleConnectState.STATE_DISCONNECTED;
        if (bleConnectionListener != null) {
            bleConnectionListener.onDisconnected(connParameter, function);
        }
    }

    private void unsupportedEvent() {
        disConnect();
        if (bleConnectionListener != null) {
            bleConnectionListener.onUnsupport(connParameter, function);
        }
    }

    private void statusErrorEvent(String mac, boolean active, boolean active_) {
        disConnect();
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

    private BleResponse ctrlResponse = new BleResponse();

    private DataReceivedCallback notifyCallback = new DataReceivedCallback() {
        @Override
        public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
            if (ctrlResponse.update(data.getValue())) {
                if (bleCommandListener != null) {
                    ctrlResponse.setMac(device.getAddress());
                    ctrlResponse.setOriginBleName(device.getName());
                    doOnResponse(ctrlResponse);
                }
                if (function != DruidBleFunction.DEVICE_OTA) {
                    boolean needSave = true;
                    if (ctrlResponse.getProtoCmd() == 3004) {
                        return;
                    }
                    if (ctrlResponse.getProtoCmd() == 3004 || ctrlResponse.getProtoCmd() == 3001) {
                        needSave = false;
                    }

                    MultiLog.instance().ble_w(BaseLog.BLE_DRIVE_READ, connParameter.mac, String.format("response: protoCmd=%d, pkg-data=", ctrlResponse.getProtoCmd()) +
                            ByteUtil.byteArrayToHexStringPretty(ctrlResponse.getFirstData()), needSave);
                }
            }
        }
    };

    private ConnectionObserver connectionObserver = new ConnectionObserver() {
        @Override
        public void onDeviceConnecting(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onDeviceConnected(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onDeviceFailedToConnect(@NonNull BluetoothDevice device, int reason) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "onDeviceFailedToConnect：" + reason, true);
            connectFailedEvent(BleConnectState.STATE_CONNECT_FAILED);
        }

        @Override
        public void onDeviceReady(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onDeviceDisconnecting(@NonNull BluetoothDevice device) {
            Log.i(TAG, "onDeviceDisconnecting");
        }

        @Override
        public void onDeviceDisconnected(@NonNull BluetoothDevice device, int reason) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "onDeviceDisconnected：" + reason, true);
            disconnectedEvent();
        }
    };

    public int getMaximumPacketSize() {
        return super.getMtu() - 3;
    }

    private void setMtu(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            requestMtu(BleConfig.MAX_MTU).done(new SuccessCallback() {
                @Override
                public void onRequestCompleted(@NonNull BluetoothDevice device) {
                    discoveryService();
                }
            }).fail(new FailCallback() {
                @Override
                public void onRequestFailed(@NonNull BluetoothDevice device, int status) {
                    connectFailedEvent(BleConnectState.STATE_CONNECT_FAILED);
                }
            }).enqueue();
        }else {
            discoveryService();
        }
    }

    private void discoveryService(){
        setConnectionObserver(connectionObserver);
        setNotificationCallback(mCtrlCharacteristic_Notify).with(notifyCallback);
        readCharacteristic(mCtrlCharacteristic_Notify).with(notifyCallback).enqueue();
        enableNotifications(mCtrlCharacteristic_Notify).enqueue();
    }

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new MyGattCallbackImpl();
    }

    private class MyGattCallbackImpl extends BleManagerGattCallback {

        @Override
        protected void initialize() {
            setMtu();
        }

        @Override
        protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac,
                    "gatt connected, starting service discovery.", true);
            boolean support = setCharacteristicUUID(gatt);
            if (support) {
                support = bleServiceEnable(gatt);
//                gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
                requestConnectionPriority(ConnectionPriorityRequest.CONNECTION_PRIORITY_HIGH);
            }
            if (support) {
                support = functionSupport(gatt);
            }
            return support;
        }

        @Override
        protected void onServicesInvalidated() {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac,
                    "onServicesInvalidated", true);
            overrideMtu(BleConfig.MIN_MTU);
            mCtrlCharacteristic_Write = null;
            mCtrlCharacteristic_Notify = null;
            mCtrlCharCccDescriptor = null;
//            if (!isConnected()) {
//                connectFailedEvent(BleConnectState.STATE_CONNECT_FAILED);
//            } else {
//                //TODO
//            }
        }

    }

    private ProtocolType.Version version = null;

    private boolean setCharacteristicUUID(BluetoothGatt gatt) {
        if (function == DruidBleFunction.DEVICE_ON || function == DruidBleFunction.DEVICE_OFF
                || function == DruidBleFunction.DEVICE_OTA) {
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceFlex3G(getName(gatt))) {
                mServiceUUID = BluetoothBroadcastUtils.UUID.FLEX_3G.mServiceUUID;
                mCtrlCharacteristicUUID_Write = BluetoothBroadcastUtils.UUID.FLEX_3G.mCtrlCharacteristicUUID_Write;
                mCtrlCharacteristicUUID_Notify = BluetoothBroadcastUtils.UUID.FLEX_3G.mCtrlCharacteristicUUID_Notify;
                mCccUUID = BluetoothBroadcastUtils.UUID.FLEX_3G.mCccUUID;
                version = ProtocolType.Version.v1_3g_flex;
                return true;
            } else {
                if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(getName(gatt),
                        connParameter.bcData)) {
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
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(getName(gatt), connParameter.bcData)) {
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

    private boolean bleServiceEnable(BluetoothGatt gatt) {
        final BluetoothGattService service = gatt.getService(mServiceUUID);
        if (service == null) {
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Service not found!UUID："
                    + mServiceUUID.toString(), true);
            return false;
        } else {
            mCtrlCharacteristic_Write = service.getCharacteristic(mCtrlCharacteristicUUID_Write);
            if (null == mCtrlCharacteristic_Write) {
                MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Control Characteristic not found!UUID："
                        + mCtrlCharacteristicUUID_Write.toString(), true);
                return false;
            }
            //
            mCtrlCharacteristic_Notify = service.getCharacteristic(mCtrlCharacteristicUUID_Notify);
            if (null == mCtrlCharacteristic_Notify) {
                MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: Control Characteristic not found!UUID："
                        + mCtrlCharacteristicUUID_Notify.toString(), true);
                return false;
            }
            //
            mCtrlCharCccDescriptor = mCtrlCharacteristic_Notify.getDescriptor(mCccUUID);
            if (null == mCtrlCharCccDescriptor) {
                mCtrlCharacteristic_Notify = null;
                MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "enableNotification: CCC for Control Characteristic not found!UUID："
                        + mCccUUID.toString(), true);
                return false;
            }
        }

        boolean writeRequest = false;
        if (mCtrlCharacteristic_Write != null) {
            final int writeProperties = mCtrlCharacteristic_Write.getProperties();
            writeRequest = (writeProperties & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0;
        }

        boolean supported = mCtrlCharacteristic_Write != null
                && mCtrlCharacteristic_Notify != null && writeRequest;
        return supported;
    }

    private boolean functionSupport(BluetoothGatt gatt) {
        boolean device_legal = false;
        //是否是公司设备
        if (BluetoothBroadcastUtils.DEVICE_VALID.ISDruidDevices(getName(gatt), connParameter.bcData)) {
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(getName(gatt),
                    connParameter.bcData)) {

                if (function == DruidBleFunction.DEVICE_ON || function == DruidBleFunction.HUB_ON) {//开机
                    //是否支持开关机设备
                    BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus deviceStatus =
                            BluetoothBroadcastUtils.DEVICE_RUN_STATUS.getDeviceStatus(getName(gatt), connParameter.bcData);
                    if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.unkown) {//TODO 开机状态不知道
                        //继续开机
                        device_legal = true;
                    } else {
                        if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                            //设备已经开机,不能再开机
                            statusErrorEvent(getName(gatt), true, true);
                            return false;
                        } else {//设备未开机
                            //开机
                            device_legal = true;
                        }
                    }
                }

                if (function == DruidBleFunction.DEVICE_OFF || function == DruidBleFunction.HUB_OFF) {//关机
                    //是否支持开关机设备
                    BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus deviceStatus =
                            BluetoothBroadcastUtils.DEVICE_RUN_STATUS.getDeviceStatus(getName(gatt), connParameter.bcData);
                    if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.unkown) {//TODO 开机状态不知道
                        //继续关机
                        device_legal = true;
                    } else {
                        if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.active_A) {//设备已经开机
                            //关机
                            device_legal = true;
                        } else {
                            //设备已经关机了,不能再关机
                            statusErrorEvent(getName(gatt), false, false);
                            return false;
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
                if (function == DruidBleFunction.QUEST_SEARCH) {
                    //中继搜索
                    device_legal = true;
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
                    BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus deviceStatus =
                            BluetoothBroadcastUtils.DEVICE_RUN_STATUS.getDeviceStatus(getName(gatt), connParameter.bcData);
                    if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.unkown) {
                        device_legal = true;
                    } else {
                        if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                            device_legal = true;
                        } else {//设备未开机
                            statusErrorEvent(getName(gatt), true, true);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void connect(BleConnectionListener callback) {
        //   this.forceMac = "";
        this.bleConnectionListener = callback;
        device = mAdapter.getRemoteDevice(connParameter.mac);
        if (device != null) {
            connectRequest = connect(device)
                    .retry(0, 100)
                    .timeout(BleConfig.SERVICE_DISCOVERY_TIMEOUT_MS)
                    .useAutoConnect(false)
                    .usePreferredPhy(PhyRequest.PHY_LE_1M_MASK) /*| PhyRequest.PHY_LE_2M_MASK
                            | PhyRequest.PHY_LE_CODED_MASK)*/
                    .before(new BeforeCallback() {
                        @Override
                        public void onRequestStarted(@NonNull BluetoothDevice device) {

                        }
                    })
                    .done(new SuccessCallback() {
                        @Override
                        public void onRequestCompleted(@NonNull BluetoothDevice device) {
                            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac,
                                    "onMtuChanged-->" + getMaximumPacketSize(), true);
                            connectedEvent();
                        }
                    })

                    .fail(new FailCallback() {
                        @Override
                        public void onRequestFailed(@NonNull BluetoothDevice device, int status) {
                            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "onRequestFailed：" + status, false);
                            connectFailedEvent(BleConnectState.STATE_CONNECT_FAILED);
                        }
                    })
                    .invalid(new InvalidRequestCallback() {
                        @Override
                        public void onInvalidRequest() {

                        }
                    })
                    .then(new AfterCallback() {
                        @Override
                        public void onRequestFinished(@NonNull BluetoothDevice device) {

                        }
                    });
            connectRequest.enqueue();
            getServiceDiscoveryTimeoutRunnable();
            timeout.schedule(serviceDiscoveryTimeoutRunnable, BleConfig.SERVICE_DISCOVERY_TIMEOUT_MS + 1000L);
        }
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
            if (getBluetoothDevice() != null) {
                if (forceMac.equalsIgnoreCase(getBluetoothDevice().getAddress())) {
                    disConnect();
                    closeCache();
                    clearGatt();
                }
            }
        }
    }

    @Override
    public void disConnect() {
        device = null;
        connState = BleConnectState.STATE_DISCONNECTED;
        if (serviceDiscoveryTimeoutRunnable != null) {
            serviceDiscoveryTimeoutRunnable.cancel();
        }
        if (connectRequest != null) {
            connectRequest.cancelPendingConnection();
        }
        if (isConnected()) {
            disconnect()
                    .then(new AfterCallback() {
                        @Override
                        public void onRequestFinished(@NonNull BluetoothDevice device) {
                            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE, connParameter.mac, "onRequestFinished", false);
                            close();
                            forceMac = "";
                        }
                    }).enqueue();
        }
    }

    @Override
    public void closeCache() {
        if (serviceDiscoveryTimeoutRunnable != null) {
            serviceDiscoveryTimeoutRunnable.cancel();
        }
        // close();
    }

    @Override
    public void clearGatt() {

    }

    // ---------------------------------------------------------------------------------------------
    private ArrayList<byte[]> segmentsToSend = new ArrayList<>();
    private boolean isSendingSegment = false;

    @Override
    public void sendCommand(BleRequest cmd) {
        if (segmentsToSend.isEmpty()) {
            isSendingSegment = false;
        }
        segmentsToSend.addAll(cmd.asSegments(version, getMaximumPacketSize()));
        if (!isSendingSegment) {
            if (segmentsToSend.size() > 0) {
                isSendingSegment = true;
                write(segmentsToSend.get(0));
                if (segmentsToSend.size() > 0) {
                    byte[] datas_byte = segmentsToSend.get(0);
                    if (datas_byte.length > 0) {

                    }
                }
            }
        }
    }

    DataSentCallback writeCallBack = new DataSentCallback() {
        @Override
        public void onDataSent(@NonNull BluetoothDevice device, @NonNull Data data) {
            final byte[] request = data.getValue();
            boolean needSave = true;
            if (function == DruidBleFunction.DEVICE_OTA ||
                    function == DruidBleFunction.QUEST_SEARCH) {
                needSave = false;
            }
            MultiLog.instance().ble_w(BaseLog.BLE_DRIVE_WRITE, connParameter.mac,
                    "request:" + ByteUtil.byteArrayToHexStringPretty(request), needSave);
            if (!segmentsToSend.isEmpty()) {
                segmentsToSend.remove(0);
            }
            if (!segmentsToSend.isEmpty()) {
                write(segmentsToSend.get(0));
            } else {
                isSendingSegment = false;
            }
        }
    };

    private void write(byte[] datas) {
        writeCharacteristic(mCtrlCharacteristic_Write, datas,
                BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE)
                .before(new BeforeCallback() {
                    @Override
                    public void onRequestStarted(@NonNull BluetoothDevice device) {

                    }
                })
                .with(writeCallBack)
                .done(new SuccessCallback() {
                    @Override
                    public void onRequestCompleted(@NonNull BluetoothDevice device) {

                    }
                })
                .fail(new FailCallback() {
                    @Override
                    public void onRequestFailed(@NonNull BluetoothDevice device, int status) {

                    }
                })
                .invalid(new InvalidRequestCallback() {
                    @Override
                    public void onInvalidRequest() {

                    }
                })
                .then(new AfterCallback() {
                    @Override
                    public void onRequestFinished(@NonNull BluetoothDevice device) {

                    }
                })
                .enqueue();
    }

    // ---------------------------------------------------------------------------------------------
    protected Context mContext;
    protected BluetoothAdapter mAdapter;
    protected BluetoothManager mManager;
    protected BleConnParameter connParameter;
    private String mBluetoothDeviceAddress;
    private DruidBleFunction function = DruidBleFunction.DEVICE_ON;//0 开机，1 关机

    private DeviceStatusListener statusListener = null;

    @Override
    public void setDeviceStatusListener(DeviceStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    @Override
    public boolean thisIsConnected() {
        return isConnected();
    }

    public void setDruidBleFunction(DruidBleFunction function) {
        this.function = function;
    }

    public void setBleConnParameter(BleConnParameter connParameter) {
        this.connParameter = connParameter;
    }

    public String getAddress(BluetoothGatt gatt) {
        if (gatt != null) {
            return gatt.getDevice().getAddress();
        }
        return "";
    }

    public String getName(BluetoothGatt gatt) {
        return connParameter.getOriginBleName();
    }

    public NordicBleManager(Context context) {
        super(context);
        mContext = context.getApplicationContext();
        mManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mAdapter = mManager.getAdapter();
    }
}
