package com.druid.ota;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.druid.ota.bean.BinInfo;
import com.druid.ota.bean.OtaInfo;
import com.druid.ota.cmd.Command;
import com.druid.ota.g2.DealMsg;
import com.druid.ota.g2.OtaBean;
import com.druid.ota.utils.OtaLog;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import protocol.AppOuterClass;
import protocol.Define;
import protocol.Download;
import protocol.IdentityMsgOuterClass;
import protocol.Register;
import protocol.Sample;
import protocol.Setting;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * 2G固件升级
 * Created by LeaAnder on 2017/6/1.
 */
public class Ota2G {
    private Activity mContext;
    private String mac;
    private BluetoothSPP bt;
    private OtaListener otaListener;
    private BinInfo binInfo;
    private OtaInfo otaInfo = null;

    public void setBinInfo(BinInfo binInfo) {
        this.binInfo = binInfo;
    }

    public Ota2G(Activity mContext) {
        this.mContext = mContext;
    }

    public void initOta(final OtaListener otaListener) {
        bt = new BluetoothSPP(mContext);
        this.otaListener = otaListener;
        if (!bt.isBluetoothAvailable()) {
//            Toast.makeText(mContext.getApplicationContext()
//                    , "Bluetooth is not available"
//                    , Toast.LENGTH_SHORT).show();
            otaListener.unAvailable();
            return;
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                parseCmd2(data);
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
//                Toast.makeText(mContext.getApplicationContext()
//                        , "Connected to " + name + "\n" + address
//                        , Toast.LENGTH_SHORT).show();
                otaListener.connect();
            }

            public void onDeviceDisconnected() {
//                Toast.makeText(mContext.getApplicationContext()
//                        , "Connection lost", Toast.LENGTH_SHORT).show();
                otaListener.disconnect();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(mContext.getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
                otaListener.connFailed();
            }
        });

        startService();
    }

    private void startService() {
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            }
        }
    }

    public void conn(BinInfo binInfo, String mac) {
        this.mac = mac;
        this.binInfo = binInfo;
        try {
            bt.connect(mac);
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(mContext.getApplicationContext()
//                    , "Bluetooth mac illegal"
//                    , Toast.LENGTH_SHORT).show();
            otaListener.connFailed();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Command.MESSAGE_MODE: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_APP length " + data.length);
                    bt.send(data, false);
                    break;
                }
                case Command.MESSAGE_INFO: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_REG length " + data.length);
                    bt.send(data, false);
                    break;
                }
                case Command.MESSAGE_SETTING: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_SETTING length " + data.length);
                    bt.send(data, false);
                    break;
                }
                case Command.MESSAGE_OTA: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_DOWNLOAD length " + data.length);
                    bt.send(data, false);
                    break;
                }
                default: {
                    bt.disconnect();
                    break;
                }
            }
        }
    };

    private void parseCmd2(byte[] origindata) {
        otaListener.receiveData(origindata);
        InfoCmd2(origindata);
    }

    private void InfoCmd2(byte[] data) {
        OtaBean otaBean = DealMsg.decodeData(data);
        if (otaBean != null) {
            switch (otaBean.MsgType) {
                //mode
                case Define.HeaderType.TypeAppReq_VALUE:
                    parseMode2(otaBean.datas);
                    break;
                //info
                case Define.HeaderType.TypeRegisterReq_VALUE:
                    parseInfo2(otaBean.datas);
                    break;
                //setting
                case Define.HeaderType.TypeSettingReq_VALUE:
                    parseSetting2(otaBean.datas);
                    break;
                //down
                case Define.HeaderType.TypeDownloadReq_VALUE:
                    parseDown2(otaBean.datas);
                    break;
                default:
                    break;
            }
        }
    }

    private void parseMode2(byte[] data) {
        try {
            AppOuterClass.AppReq appReq = AppOuterClass.AppReq.parseFrom(data);

            //send
            AppOuterClass.AppRsp.Builder appRsp = AppOuterClass.AppRsp.newBuilder();
            appRsp.setCode(0);
            appRsp.setLinkType(2);
            // appRsp.setWorkMode(2);
            byte[] enReply = appRsp.build().toByteArray();
            byte[] reply = DealMsg.encodeData(enReply, Define.HeaderType.TypeAppRsp_VALUE);//16
            mHandler.obtainMessage(Command.MESSAGE_MODE, reply.length, -1, reply).sendToTarget();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void parseInfo2(byte[] data) {
        try {
            //parse
            Register.RegisterReq registerReq = Register.RegisterReq.parseFrom(data);
            IdentityMsgOuterClass.IdentityMsg identityMsg = registerReq.getIden();
            int token = identityMsg.getToken();
//            if (!BleRequest.checkToken(DataType.data32ToBytes(token))) {
//                OtaLog.v("check token fail");
//                return;
//            }
            ByteString bs = identityMsg.getUUID();
            byte[] uid = bs.toByteArray();

            otaInfo = new OtaInfo();
            otaInfo.dt = registerReq.getDeviceType();
            otaInfo.hwv = registerReq.getHardwareVersion();
            otaInfo.fwv = registerReq.getFirmwareVersion();
            otaInfo.uid = Command.bytesToHexString(uid);
            otaInfo.mac = registerReq.getMac();
            otaInfo.imsi = registerReq.getIMSI();
            otaInfo.vol = registerReq.getVoltage();
            otaListener.deviceInfo(otaInfo);

            //set reply
            Sample.SampleRsp.Builder simpleRsp = Sample.SampleRsp.newBuilder();
            simpleRsp.setCode(0);
            byte[] enReply = simpleRsp.build().toByteArray();
            byte[] reply = DealMsg.encodeData(enReply, Define.HeaderType.TypeRegisterRsp_VALUE);//2);
            mHandler.obtainMessage(Command.MESSAGE_INFO, reply.length, -1, reply).sendToTarget();
        } catch (InvalidProtocolBufferException e) {
        }
    }

    private void parseSetting2(byte[] data) {
        try {
            //parse msg
            Setting.SettingReq settingReq = Setting.SettingReq.parseFrom(data);
            //token=settingReq.getIden().getToken();
            //set msg
            //TODO
            Setting.SettingRsp.Builder settingRsp = Setting.SettingRsp.newBuilder();
            //hwv&dt must to strictly equals
            if (binInfo != null) {
                if (otaInfo.hwv == binInfo.hwv_bin && otaInfo.dt == binInfo.dt_bin) {
                    if (otaInfo.fwv < binInfo.fwv_bin || binInfo.fwv_bin == 0|| binInfo.fwv_bin == 100) {
                        settingRsp.setFirmwareID(Command.getFirmwareID(binInfo.datas) + "");
                        otaListener.availableBin();
                    } else {
                        settingRsp.setFirmwareID("");
                        otaListener.binTips(0);
                    }
                } else {
                    settingRsp.setFirmwareID("");
                    otaListener.binTips(1);
                }
            } else {
                settingRsp.setFirmwareID("");
                otaListener.binTips(1);
            }
            settingRsp.setEnvSamplingMode(0);
            settingRsp.setEnvSamplingFreq(0);
            settingRsp.setBehaviorSamplingMode(0);
            settingRsp.setBehaviorSamplingFreq(0);
            settingRsp.setGprsMode(0);
            settingRsp.setGprsFreq(0);
            settingRsp.setEnvVoltageThreshold(0);
            settingRsp.setBehaviorVoltageThreshold(0);
            settingRsp.setGprsVoltageThreshold(0);
            settingRsp.setOtaVoltageThreshold(0);
            settingRsp.setSMSNumber("");
            settingRsp.setCode(0);
            if (binInfo != null) {
                settingRsp.setFirmwareVersion(binInfo.fwv_bin);
            }
            byte[] enReply = settingRsp.build().toByteArray();
            byte[] reply = DealMsg.encodeData(enReply, Define.HeaderType.TypeSettingRsp_VALUE);//12
            mHandler.obtainMessage(Command.MESSAGE_SETTING, reply.length, -1, reply).sendToTarget();
        } catch (InvalidProtocolBufferException e) {
        }
    }

    private void parseDown2(byte[] data) {
        try {
            //parse
            Download.DownloadReq downloadReq = Download.DownloadReq.parseFrom(data);
            IdentityMsgOuterClass.IdentityMsg identityMsg = downloadReq.getIden();
            //.getIden();
            String fileId = downloadReq.getFileId();
            int begin = downloadReq.getBegin();
            int length = downloadReq.getLength();
            //set
            Download.DownloadRsp.Builder downloadRsp = Download.DownloadRsp.newBuilder();
            downloadRsp.setCode(0);
            downloadRsp.setTotal(binInfo.datas.length);//(img_A.length-28);
            downloadRsp.setLength(length);
            downloadRsp.setBegin(begin);
            byte[] dsd = new byte[length];
            System.arraycopy(binInfo.datas, begin, dsd, 0, length);//(img_A,28+begin,dsd,0,length);
            ByteString bs = ByteString.copyFrom(dsd);
            downloadRsp.setData(bs);

            int upSize = begin;
            upSize += length;
            //
            otaListener.upload(upSize);
            if (upSize >= binInfo.datas.length) {
                otaListener.completeDownload();
                upSize = 0;
            }
            // send
            byte[] enReply = downloadRsp.build().toByteArray();
            byte[] reply = DealMsg.encodeData(enReply, Define.HeaderType.TypeDownloadRsp_VALUE);//8
            mHandler.obtainMessage(Command.MESSAGE_OTA, reply.length, -1, reply).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void close() {
//        if (bt.isServiceAvailable())
        {
            bt.disconnect();
            bt.stopService();
        }
    }
}
