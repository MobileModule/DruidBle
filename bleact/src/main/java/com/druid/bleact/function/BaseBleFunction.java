package com.druid.bleact.function;

import com.device.ble.cnf.BleConfig;
import com.device.ble.core.BleConnParameter;
import com.device.ble.timeout.Timeout;
import com.device.ble.timeout.TimeoutRunnable;
import com.device.ble.utils.BluetoothBroadcastUtils;
import com.druid.bleact.entity.BleConnDevice;
import com.druid.bleact.entity.DeviceTargetBean;
import com.druid.bleact.model.HubBroadcastListModel;
import com.device.ble.entity.HubBroadcastModel;
import com.druid.bleact.utils.BleActiveLog;

public abstract class BaseBleFunction {
    public static final String ble_tag = BaseBleFunction.class.getName();

    public abstract void startFunction(DeviceTargetBean targetDevice,
                                       BleConnParameter connParameter);

    public abstract void stopFunction();

    public abstract void nextBleConn();

    public int linkIndex = 0;
    protected BleFunctionListener listener;
    protected BleConnParameter connParameter;//马上连接的设备
    public DeviceTargetBean targetDevice;//要连接的设备，可能是网关，可能是终端
    protected BleConnDevice connDevice = null;
    public boolean gateway_searched_device = false;


    public void setListener(BleFunctionListener listener) {
        this.listener = listener;
    }

    protected void checkHubBroadcast(int linkType, HubBroadcastListModel hubBroadcastList) {
        synchronized (this) {
            if (hubBroadcastList != null) {
                if (gateway_searched_device == false) {
                    for (int i = 0; i < hubBroadcastList.broadcasts.size(); i++) {
                        HubBroadcastModel model = hubBroadcastList.broadcasts.get(i);
                        String name = model.device_name;
                        String mac = model.MAC;
                        int rssi = model.BleRSSI;
                        if (mac.equalsIgnoreCase(targetDevice.devices.get(linkIndex).mac)) {
//                            this.hubSearchDevice = model;
                            if (hubConnDevice(model)) {
                                this.gateway_searched_device = true;
                                if (listener != null) {
                                    listener.bleHubSearchedDevice(linkIndex, targetDevice);
                                }
                                connDevice.cmdBleScanStop();
                                deployQuestConnectTask(linkType);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    //0:停止 1 - 开关机, 2 - OTA升级, 3 - BLE升级, 4 -  Sensor调试
    //5 - 收数据, 6 - 打标, 9 - 远程控制
    protected void deployQuestConnectTask(int linkType) {
        if (deployRunnable != null) {
            deployRunnable.cancel();
        }
        deployRunnable = new TimeoutRunnable(new TimeoutRunnable.TimeoutListener() {
            @Override
            public void timeout() {
                if (listener != null) {
                    listener.bleHubConningDevice(linkIndex, targetDevice);
                }
                connDevice.cmdBleConn(linkType, targetDevice.devices.get(linkIndex).mac);
            }
        });
        handlerRsp.schedule(deployRunnable, 200);
    }


    public static final int SCAN_TIME_OUT = 5;
    private TimeoutRunnable questConnRspRunnable = null;

    protected void questConnectTerminalTimer() {
        connDevice.cmdBleScanStart(SCAN_TIME_OUT);
        if (questConnRspRunnable != null) {
            questConnRspRunnable.cancel();
        }
        questConnRspRunnable = new TimeoutRunnable(new TimeoutRunnable.TimeoutListener() {
            @Override
            public void timeout() {
                BleActiveLog.i(ble_tag, "gateway search timeout");
                questConnectTerminalTimer();
            }
        });
        handlerRsp.schedule(questConnRspRunnable, (SCAN_TIME_OUT + 1) * 1000L);
    }

    protected void stopQuestConnectTerminalTimer(){
        if (questConnRspRunnable != null) {
            questConnRspRunnable.cancel();
        }
    }

    protected void questDisconn() {
        connDevice.cmdBleDisConn(targetDevice.devices.get(linkIndex).mac);
    }

    public abstract boolean hubConnDevice(HubBroadcastModel model);

    public abstract void questConnDeviceTimeout();

    /**
     * 蓝牙响应超时
     */
    private Timeout handlerRsp = new Timeout();

    protected void rspTimeOut() {
        if (handlerRspRunnable != null) {
            handlerRspRunnable.cancel();
        }
        handlerRspRunnable = new TimeoutRunnable(new TimeoutRunnable.TimeoutListener() {
            @Override
            public void timeout() {
                BleActiveLog.i(ble_tag, "service rsp timeout");
//            connDevice.getDDC().destoryCmd();
            }
        });
        handlerRsp.schedule(handlerRspRunnable, BleConfig.SERVICE_RESPOND_TIMEOUT_MS);
    }

    private TimeoutRunnable handlerRspRunnable = null;

    private TimeoutRunnable deployRunnable = null;

    public static final int DEVICE_ON = 0x01;
    public static final int DEVICE_OFF = 0x02;

    /**
     * 检查设备是否开关机
     * result ：-1 设备不支持该操作，0 不合法，1 网关不合法，2 设备不合法，3 合法操作
     */
    protected int checkStatusLegal(BleConnParameter connParameter, int device_status) {
        if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(connParameter.getOriginBleName(),
                connParameter.bcData)) {
            //网关
            if (!connParameter.mac.equalsIgnoreCase(targetDevice.firstMac)) {
                boolean active_on = statusOnLegal(connParameter.getCreateBleFileName(false));
                if (active_on == false) {//网关已关机，不合法
                    return 1;
                } else {
                    return 3;
                }
            } else {
                if (device_status == DEVICE_ON) {//设备开机
                    boolean active_on = statusOnLegal(connParameter.getCreateBleFileName(false));
                    if (active_on) {//设备已开机，合法
                        return 3;
                    } else {
                        return 2;
                    }
                } else {//设备关机
                    boolean active_off = statusOffLegal(connParameter.getCreateBleFileName(false));
                    if (active_off) {//设备已关机，合法
                        return 3;
                    } else {
                        return 2;
                    }
                }
            }
        } else {
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceFlex3G(connParameter.getOriginBleName())) {
                if (device_status == DEVICE_OFF) {//设备关机
                    return 3;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

    /**
     * 是否开机
     */
    protected boolean statusOnLegal(String fileName) {
        if (BluetoothBroadcastUtils.DEVICE_TRANSFER.ISDruidFileName(fileName)) {
            BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus status =
                    BluetoothBroadcastUtils.DEVICE_RUN_STATUS.getDeviceStatusByFileName(fileName);
            if (status == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否关机
     */
    protected boolean statusOffLegal(String fileName) {
        if (BluetoothBroadcastUtils.DEVICE_TRANSFER.ISDruidFileName(fileName)) {
            BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus status =
                    BluetoothBroadcastUtils.DEVICE_RUN_STATUS.getDeviceStatusByFileName(fileName);
            if (status != BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                return true;
            }
        }
        return false;
    }
}
