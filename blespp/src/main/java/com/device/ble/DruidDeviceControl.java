package com.device.ble;

import android.content.Context;

import com.device.ble.core.CmdBleManager;
import com.device.ble.entity.CMDResp;
import com.device.ble.entity.FailedReasonState;
import com.device.ble.interfaces.BleUpperLayerListener;
import com.device.ble.interfaces.BleRspChangeListener;
import com.device.ble.cmd.BleRequest;
import com.device.ble.cmd.BleResponse;
import com.device.ble.cmd.ProtocolType;
import com.device.ble.core.BleConnParameter;
import com.device.ble.utils.DruidBleFunction;

/**
 * Created by druid on 2020/3/2.
 */

public class DruidDeviceControl implements BleRspChangeListener {
    private BleUpperLayerListener upperLayerListener;
    private CmdBleManager cmdBleManager;
    private ProtocolType.Version version = ProtocolType.Version.unkown;

    public ProtocolType.Version getVersion() {
        return version;
    }

    public void setVersion(ProtocolType.Version version) {
        this.version = version;
    }

    public void setUpperLayerListener(BleUpperLayerListener upperLayerListener) {
        this.upperLayerListener = upperLayerListener;
    }


    private DruidBleFunction function;

    public DruidDeviceControl(Context context, DruidBleFunction function) {
        this.function = function;
        if (cmdBleManager == null) {
            cmdBleManager = new CmdBleManager(context);
            cmdBleManager.setConnStateListener(this);
        }
    }

    public void setFunction(DruidBleFunction function) {
        this.function = function;
    }

    private boolean forceAgainConn = true;

    public void setForceAgainConn(boolean forceAgainConn) {//蓝牙是否强制重连
        this.forceAgainConn = forceAgainConn;
    }

    /**
     * init Cmd
     */
    public void conn(BleConnParameter connParameter, boolean permissionUI) {
        curReconn = 0;
        this.connParameter = connParameter;
        this.permissionUI = permissionUI;
        cmdBleManager.connDevice(connParameter, function);
    }

    void reInitCmd(BleConnParameter connParameter, boolean permissionUI) {
        this.connParameter = connParameter;
        this.permissionUI = permissionUI;
        cmdBleManager.connDevice(connParameter, function);
    }

    public void destoryCmd() {
        if (cmdBleManager != null) {
            cmdBleManager.exitCmdBLE();
        }
    }

    public void forceDisConn(String forceMac){
        if(cmdBleManager!=null){
            cmdBleManager.forceDisconn(forceMac);
        }
    }

    public void cmd(int type, byte[] datas) {
        cmdBleManager.writeData(type, datas);
    }

    public void cmd(int type, int seq, byte[] datas) {
        cmdBleManager.writeData(type, seq, datas);
    }

    public void cmd(BleRequest request) {
        cmdBleManager.writeCommand(request);
    }

    @Override
    public void unAvailable(BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            upperLayerListener.initCmdFailed(FailedReasonState.BLE_NOT_USED, connParameter, function);
        }
    }

    @Override
    public void validBle(final BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
    }

    @Override
    public void invalideMac(BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            upperLayerListener.initCmdFailed(FailedReasonState.BLE_ADDRESS_INVALID, connParameter, function);
        }
    }

    @Override
    public void bleNofound(BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            upperLayerListener.initCmdFailed(FailedReasonState.BLE_SEARCH_FAILED, connParameter, function);
        }
    }

    @Override
    public void startConn(BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            if (isReconn) {
            } else {
                upperLayerListener.initCmding(0X01, function);
            }
        }
    }

    @Override
    public void connected(final BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            curReconn = 0;
            isReconn = false;
            upperLayerListener.initCmdSuccess(connParameter, function);
        }
    }

    /**
     * 0x01 蓝牙不可用
     * 0x02 蓝牙地址无效
     * 0x03 蓝牙未搜索到
     * 0x04 蓝牙连接失败
     * 0x05 蓝牙重连失败
     * 0x06 蓝牙断开连接
     */

    @Override
    public void connFailed(BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            connFailedTips(connParameter);
        }
    }

    @Override
    public void disconn(BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            curReconn = 0;
            destoryCmd();
            upperLayerListener.initCmdFailed(FailedReasonState.BLE_DISCONNECTED, connParameter, function);
            isReconn = false;
        }
    }

    void connFailedTips(BleConnParameter connParameter) {
        if (upperLayerListener != null) {
            if (reconn == curReconn) {
                curReconn = 0;
                destoryCmd();
                if (isReconn) {
                    upperLayerListener.initCmdFailed(FailedReasonState.BLE_CONN_AGAIN_FAILED, connParameter, function);
                    isReconn = false;
                } else {
                    upperLayerListener.initCmdFailed(FailedReasonState.BLE_CONN_FAILED, connParameter, function);
                }
            } else {
                if (this.forceAgainConn) {
                    isReconn = true;
                    reInitCmd(connParameter, permissionUI);
                    upperLayerListener.reInitCmd(reconn, curReconn, function);
                    curReconn += 1;
                }
            }
        }
    }

    @Override
    public void onUnsupport(BleConnParameter connParameter, DruidBleFunction function) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        if (upperLayerListener != null) {
            curReconn = 0;
            destoryCmd();
            upperLayerListener.unsupportFunction(function);
            isReconn = false;
        }
    }

    @Override
    public void receiveData(BleConnParameter connParameter, DruidBleFunction function,
                            final BleResponse response) {
        if (!connParameter.mac.equalsIgnoreCase(this.connParameter.mac)) {
            return;
        }
        curReconn = reconn;
        isReconn = false;
        if (upperLayerListener != null) {
            CMDResp cmdResp = new CMDResp();
            cmdResp.setPkgRespType(response.getPkgCmd());
            cmdResp.setProtoRespType(response.getProtoCmd());
            cmdResp.setBytesDatas(response.getData());
            cmdResp.setMac(response.getMac());
            cmdResp.setResponse(response);
            upperLayerListener.respondCmd(cmdResp);
        }
    }

    private BleConnParameter connParameter = null;
    private boolean permissionUI = false;

    private int curReconn = 0;//当前重连次数
    private int reconn = 3;//重连总次数
    boolean isReconn = false;

    public void setReconn(int reconn) {
        this.reconn = reconn;
    }

    public int getReconn() {
        return this.reconn;
    }
}
