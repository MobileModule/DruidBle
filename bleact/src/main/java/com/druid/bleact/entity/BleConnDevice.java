package com.druid.bleact.entity;

import android.content.Context;

import com.device.ble.DruidDeviceControl;
import com.device.ble.entity.CMDResp;
import com.device.ble.interfaces.BleUpperLayerListener;
import com.device.ble.core.BleConnParameter;
import com.device.ble.timeout.Timeout;
import com.device.ble.timeout.TimeoutRunnable;
import com.device.ble.utils.DruidBleFunction;
import com.druid.bleact.parse.HubBleScanParse;
import com.druid.bleact.parse.HubConnParse;

import java.io.Serializable;

import protocol_v1.V1Define;

public class BleConnDevice implements Serializable {
    private BleConnParameter connParameter;
    private DruidDeviceControl DDC;
    private DruidBleFunction function;
    private BleUpperLayerListener cmdStateListener;

    public BleConnDevice(Context context, BleConnParameter connParameter) {
        this.connParameter = connParameter;
        this.DDC = new DruidDeviceControl(context, null);
    }

    public DruidDeviceControl deviceBleConn(DruidBleFunction function,
                                            BleUpperLayerListener listener_) {
        this.function = function;
        this.cmdStateListener = listener_;
//        if (DDC != null) {
//            DDC.destoryCmd();
//        }
        DDC.setFunction(function);
        DDC.setUpperLayerListener(this.listener);
        DDC.conn(connParameter, true);
        return DDC;
    }

    public DruidDeviceControl getDDC() {
        return DDC;
    }

    public DruidDeviceControl deviceBleDisConn() {
        if (DDC != null) {
            //DDC.destoryCmd();
            DDC.forceDisConn(connParameter.mac);
        }
        return DDC;
    }

    public BleConnParameter getConnParameter() {
        return connParameter;
    }

    public void setConnParameter(BleConnParameter connParameter) {
        this.connParameter = connParameter;
    }

    private boolean connected = false;

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public String data = "";

    public int state = -1;//-1???????????????0???????????????1??????????????????2??????????????????3???????????????

    private BleUpperLayerListener listener = new BleUpperLayerListener() {

        @Override
        public void initCmdFailed(int code, BleConnParameter connParameter, DruidBleFunction function) {
            /**
             * 0x01 ???????????????
             * 0x02 ??????????????????
             * 0x03 ??????????????????
             * 0x04 ??????????????????
             * 0x05 ??????????????????
             * 0x06 ??????????????????
             * @param code
             */
            if (code == 0x06) {
                state = 3;
            } else {
                state = 2;
            }
            cmdStateListener.initCmdFailed(code, connParameter, function);
        }

        @Override
        public void initCmding(int code, DruidBleFunction function) {
            state = 0;
            cmdStateListener.initCmding(code, function);
        }

        @Override
        public void initCmdSuccess(BleConnParameter connParameter, DruidBleFunction function) {
            state = 1;
            cmdStateListener.initCmdSuccess(connParameter, function);
        }

        @Override
        public void reInitCmd(int all, int cur, DruidBleFunction function) {
            cmdStateListener.reInitCmd(all, cur, function);
        }

        @Override
        public void unsupportFunction(DruidBleFunction function) {
            cmdStateListener.unsupportFunction(function);
        }

        @Override
        public void respondCmd(CMDResp cmdResp) {
            cmdStateListener.respondCmd(cmdResp);
        }
    };

    /**
     * ?????????????????????
     */
    public void cmdBleScanStart(final int timeout) {
        Timeout timeoutTask = new Timeout();
        timeoutTask.schedule(new TimeoutRunnable(new TimeoutRunnable.TimeoutListener() {
            @Override
            public void timeout() {
                byte[] enply = HubBleScanParse.getBleScanReq(1, timeout);
                DDC.cmd(V1Define.HeaderType.TypeBleScanReq_VALUE, enply);
            }
        }), 1000);
    }

    /**
     * ?????????????????????
     */
    public void cmdBleScanStop() {
        byte[] enply = HubBleScanParse.getBleScanReq(0);
        DDC.cmd(V1Define.HeaderType.TypeBleScanReq_VALUE, enply);
    }

    /**
     * ?????????????????????
     */
    public void cmdBleConn(int linkType, String mac) {
        byte[] enply = HubConnParse.getHubConnReq(linkType, mac);
        DDC.cmd(V1Define.HeaderType.TypeBleConnectReq_VALUE, enply);
    }

    public void cmdBleConnMark(int linkType, String mac) {
        byte[] enply = HubConnParse.getHubConnReq(linkType, mac);
        DDC.cmd(V1Define.HeaderType.TypeBleConnectReq_VALUE, enply);
    }

    /**
     * ?????????????????????
     */
    public void cmdBleDisConn(String mac) {
        byte[] enply = HubConnParse.getHubDisconnReq(mac);
        DDC.cmd(V1Define.HeaderType.TypeBleConnectReq_VALUE, enply);
    }
}
