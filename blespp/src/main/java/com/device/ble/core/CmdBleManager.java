package com.device.ble.core;

import android.content.Context;

import com.device.ble.interfaces.BleRspChangeListener;
import com.device.ble.interfaces.DeviceStatusListener;
import com.device.ble.interfaces.BleCommandListener;
import com.device.ble.interfaces.BleConnectionListener;
import com.device.ble.cmd.BleRequest;
import com.device.ble.cmd.BleRequestFactory;
import com.device.ble.cmd.BleResponse;
import com.device.ble.entity.BleConnectState;
import com.device.ble.utils.DruidBleFunction;
import com.device.ble.utils.StrUtils;

/**
 * Created by druid on 2020/3/2.
 */

public class CmdBleManager implements BleConnectionListener, BleCommandListener {
    private BleManagerImpl2 bleManager;
    private Context context;
    private BleRspChangeListener connStateListener = null;
    private String mac;

    public void setAddress(String address) {
        this.mac = address;
    }

    public String getAddress() {
        return this.mac;
    }

    private DeviceStatusListener statusListener = null;

    public void setDeviceStatusListener(DeviceStatusListener statusListener) {
        this.statusListener = statusListener;
        this.bleManager.setDeviceStatusListener(statusListener);
    }

    public void setConnStateListener(BleRspChangeListener connStateListener) {
        this.connStateListener = connStateListener;
    }

    public CmdBleManager(Context context) {
        this.context = context;
        if (bleManager == null) {
            bleManager = new DruidBleManager(context);//new NordicBleManager(context);
            bleManager.setCommandCallback(this);
        }
    }

    public CmdBleManager(Context context, BleRspChangeListener connStateListener) {
        this.context = context;
        this.connStateListener = connStateListener;
        if (bleManager == null) {
            bleManager = new DruidBleManager(context);//new NordicBleManager(context);
            bleManager.setCommandCallback(this);
        }
    }

    public void connDevice(BleConnParameter connParameter, DruidBleFunction function) {
        if (bleManager != null) {
            if (!StrUtils.isEmpty(connParameter.mac)) {
                try {
                    setAddress(mac);
                    bleManager.setDruidBleFunction(function);
                    bleManager.setBleConnParameter(connParameter);
                    bleManager.connect(this);
                    if (connStateListener != null) {
                        connStateListener.startConn(connParameter, function);
                    }
                } catch (Exception ex) {
                    if (connStateListener != null) {
                        connStateListener.invalideMac(connParameter, function);
                    }
                }
            } else {
                if (connStateListener != null) {
                    connStateListener.invalideMac(connParameter, function);
                }
            }
        }
    }

    /**
     * 数据写入
     */
    public void writeData(int protoCmd, byte[] datas) {
        BleRequest cmd = BleRequestFactory.generalCommand_Seq(BleRequest.CMD_1, protoCmd, datas);
        writeCommand(cmd);
    }

    public void writeData(int protoCmd, int seq, byte[] datas) {
        BleRequest cmd = BleRequestFactory.generalCommand_PkgCmd(seq, protoCmd, datas);
        writeCommand(cmd);
    }

    public void writeCommand(BleRequest cmd) {
        if (bleManager.thisIsConnected()) {
            bleManager.sendCommand(cmd);
        }
    }

    @Override
    public void onConnected(BleConnParameter connParameter, DruidBleFunction function) {
        if (connStateListener != null) {
            connStateListener.connected(connParameter, function);
        }
    }

    @Override
    public void onDisconnected(BleConnParameter connParameter, DruidBleFunction function) {
        exitCmdBLE();
        if (connStateListener != null) {
            connStateListener.disconn(connParameter, function);
        }
    }

    @Override
    public void onUnsupport(BleConnParameter connParameter, DruidBleFunction function) {
        exitCmdBLE();
        if (connStateListener != null) {
            connStateListener.onUnsupport(connParameter, function);
        }
    }

    @Override
    public void onFailed(int code, BleConnParameter connParameter, DruidBleFunction function) {
        exitCmdBLE();
        switch (code) {
            case BleConnectState.STATE_NOT_FOUND:
                if (connStateListener != null) {
                    connStateListener.bleNofound(connParameter, function);
                }
                break;
            default:
                if (connStateListener != null) {
                    connStateListener.connFailed(connParameter, function);
                }
                break;
        }
    }

    @Override
    public void onResponse(BleConnParameter connParameter, DruidBleFunction function, BleResponse resp) {
        try {
            parseCmd2(connParameter, function, resp);
        } catch (Exception ex) {

        }
    }

    private void parseCmd2(BleConnParameter connParameter, DruidBleFunction function, final BleResponse resp) {
        if (connStateListener != null) {
            connStateListener.receiveData(connParameter, function, resp);
        }
    }

    @Override
    public void onResponseFailed(BleConnParameter connParameter, DruidBleFunction function, BleRequest cmd) {
        exitCmdBLE();
    }


    public void exitCmdBLE() {
        if (bleManager != null) {
            bleManager.disConnect();
            bleManager.closeCache();
            bleManager.clearGatt();
        }
    }

    public void forceDisconn(String forceMac) {
        if (bleManager != null) {
            bleManager.forceDisConnect(forceMac);
        }
    }
}

