package com.druid.ota;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.device.ble.DruidDeviceControl;
import com.device.ble.entity.CMDResp;
import com.device.ble.interfaces.BleUpperLayerListener;
import com.device.ble.cmd.BleRequest;
import com.device.ble.cmd.BleRequestFactory;
import com.device.ble.cmd.BleResponse;
import com.device.ble.cmd.ProtocolType;
import com.device.ble.core.BleConnParameter;
import com.device.ble.utils.DruidBleFunction;
import com.druid.ota.bean.BinInfo;
import com.druid.ota.bean.OtaInfo;
import com.druid.ota.g2.DealMsg;
import com.druid.ota.g2.OtaBean;
import com.druid.ota.utils.V1IdentityMsgUtils;
import com.druid.ota.utils.OtaLog;
import com.druid.ota.utils.V1ParameterUtils;
import com.druid.ota.utils.V1SettingUtils;
import com.druid.ota.utils.V1SimpleUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import protocol.AppOuterClass;
import protocol.Define;
import protocol.Download;
import protocol.IdentityMsgOuterClass;
import protocol.Register;
import protocol.Sample;
import protocol.Setting;

/**
 * Created by LeaAnder on 2017/6/1.
 */
public class Ota3G implements BleUpperLayerListener {
    private Activity activity;
    private DruidDeviceControl DDC;
    private OtaListener otaListener;
    private BinInfo binInfo;
    private OtaInfo otaInfo = null;

    public void setBinInfo(BinInfo binInfo) {
        this.binInfo = binInfo;
    }

    public Ota3G(Activity activity) {
        this.activity = activity;
    }

    public void initOta(final OtaListener otaListener) {
        DDC = new DruidDeviceControl(activity, DruidBleFunction.DEVICE_OTA);
        DDC.setUpperLayerListener(this);
        this.otaListener = otaListener;
    }

    @Override
    public void initCmdFailed(final int code, BleConnParameter connParameter, DruidBleFunction function) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 0x01 蓝牙不可用
                 * 0x02 蓝牙地址无效
                 * 0x03 蓝牙未搜索到
                 * 0x04 蓝牙连接失败
                 * 0x05 蓝牙重连失败
                 * 0x06 蓝牙断开连接
                 */
                if (code == 0x06) {
//                    Toast.makeText(activity.getApplicationContext()
//                            , "Connection lost", Toast.LENGTH_SHORT).show();
                    otaListener.disconnect();
                } else {
                    if (code == 0x04) {
//                        Toast.makeText(activity.getApplicationContext()
//                                , "Unable to connect", Toast.LENGTH_SHORT).show();
                        otaListener.connFailed();
                    } else {
//                        Toast.makeText(activity.getApplicationContext()
//                                , "Illegal BleRequest", Toast.LENGTH_SHORT).show();
                        otaListener.unAvailable();
                    }
                }
            }
        });
    }

    @Override
    public void initCmding(int code, DruidBleFunction function) {

    }

    @Override
    public void initCmdSuccess(final BleConnParameter connParameter, DruidBleFunction function) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(activity.getApplicationContext()
//                        , "Connected to " + connParameter.mac + "\n", Toast.LENGTH_SHORT).show();
                otaListener.connect();
            }
        });
    }

    @Override
    public void reInitCmd(int all, int cur, DruidBleFunction function) {

    }

    @Override
    public void unsupportFunction(DruidBleFunction function) {

    }

    @Override
    public void respondCmd(final CMDResp cmdResp) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseCmd2(cmdResp.getResponse());
            }
        });
    }

    public void close() {
        DDC.destoryCmd();
    }

    public void conn(BinInfo binInfo, BleConnParameter connParameter) {
        this.binInfo = binInfo;
        try {
            DDC.conn(connParameter, true);
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(activity.getApplicationContext()
//                    , "Bluetooth mac illegal"
//                    , Toast.LENGTH_SHORT).show();
            otaListener.connFailed();
        }
    }

    private void parseCmd2(BleResponse rsp) {
        InfoCmd2(rsp);
    }

    private void InfoCmd2(BleResponse res) {
        if (res.getVersion() == ProtocolType.Version.v1_3g_flex) {
            parseV0(res);
        } else {
            parseV1(res.getProtoCmd(), res);
        }
    }

    private void parseV0(BleResponse rsp) {
        byte[] data = rsp.getData();
        OtaBean otaBean = DealMsg.decodeData(data);
        switch (otaBean.MsgType) {
            //mode
            case Define.HeaderType.TypeAppReq_VALUE:
                parseMode2V0(rsp);
                break;
            //info
            case Define.HeaderType.TypeRegisterReq_VALUE:
                parseInfo2V0(rsp);
                break;
            //setting
            case Define.HeaderType.TypeSettingReq_VALUE:
                parseSetting2V0(rsp);
                break;
            //down
            case Define.HeaderType.TypeDownloadReq_VALUE:
                parseDown2V0(rsp);
                break;
            default:
                break;
        }
    }

    private void parseV1(int MsgType, BleResponse res) {
        switch (MsgType) {
            //mode
            case protocol_v1.V1Define.HeaderType.TypeAppReq_VALUE: {
                parseMode2V1(res);
                break;
            }
            //info
            case protocol_v1.V1Define.HeaderType.TypeRegisterReq_VALUE: {
                parseInfo2V1(res);
                break;
            }
            //setting
            case protocol_v1.V1Define.HeaderType.TypeSettingReq_VALUE: {
                parseSetting2V1(res);
                break;
            }
            //setting_hub
            case protocol_v1.V1Define.HeaderType.TypeHubSettingReq_VALUE: {
                parseSettingHub2V1(res);
                break;
            }
            //parameter
            case protocol_v1.V1Define.HeaderType.TypeParameterReq_VALUE: {
                parseParameter2V1(res);
                break;
            }
            //argos sop
            case protocol_v1.V1Define.HeaderType.TypeArgosAopReq_VALUE: {
                parseArgosAop2V1(res);
                break;
            }
            //down
            case protocol_v1.V1Define.HeaderType.TypeDownloadReq_VALUE: {
                parseDown2V1(res);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void parseMode2V0(BleResponse rsp) {
        try {
            byte[] data = rsp.getData();
            OtaBean otaBean = DealMsg.decodeData(data);
            AppOuterClass.AppReq appReq = AppOuterClass.AppReq.parseFrom(otaBean.datas);

            //send
            AppOuterClass.AppRsp.Builder appRsp = AppOuterClass.AppRsp.newBuilder();
            appRsp.setCode(0);
            appRsp.setLinkType(2);

            byte[] enReply = appRsp.build().toByteArray();
            int msg_type = Define.HeaderType.TypeAppRsp_VALUE;
            byte[] reply = DealMsg.encodeData(enReply, msg_type);//16
            int seq = rsp.getSeq();
            mHandler.obtainMessage(Ota3G.MESSAGE_MODE, msg_type, seq, reply).sendToTarget();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void parseMode2V1(BleResponse res) {
        try {
            protocol_v1.V1App.AppReq appReq =
                    protocol_v1.V1App.AppReq.parseFrom(res.getData());
            //
            protocol_v1.V1App.AppRsp.Builder appRsp =
                    protocol_v1.V1App.AppRsp.newBuilder();
            appRsp.setLinkType(2);
            appRsp.setIden(V1IdentityMsgUtils.getIdentityMsg(appReq.getIden()));
            //
            byte[] reply = appRsp.build().toByteArray();
            int msg_type = protocol_v1.V1Define.HeaderType.TypeAppRsp_VALUE;
            mHandler.obtainMessage(Ota3G.MESSAGE_MODE, msg_type, res.getSeq(), reply).sendToTarget();
        } catch (Exception ex) {
            Log.i("error", ex.getMessage());
        }
    }

    private void parseInfo2V0(BleResponse rsp) {
        try {
            //parse
            byte[] data = rsp.getData();
            OtaBean otaBean = DealMsg.decodeData(data);
            Register.RegisterReq registerReq = Register.RegisterReq.parseFrom(otaBean.datas);
            IdentityMsgOuterClass.IdentityMsg identityMsg = registerReq.getIden();
            int token = identityMsg.getToken();
            ByteString bs = identityMsg.getUUID();
            byte[] uid = bs.toByteArray();

            otaInfo = new OtaInfo();
            otaInfo.dt = registerReq.getDeviceType();
            otaInfo.hwv = registerReq.getHardwareVersion();
            otaInfo.fwv = registerReq.getFirmwareVersion();
            otaInfo.uid = com.druid.ota.cmd.Command.bytesToHexString(uid);
            otaInfo.mac = registerReq.getMac();
            otaInfo.imsi = registerReq.getIMSI();
            otaInfo.vol = registerReq.getVoltage();
            otaListener.deviceInfo(otaInfo);
            //set reply
            Sample.SampleRsp.Builder simpleRsp = Sample.SampleRsp.newBuilder();
            simpleRsp.setCode(0);
            byte[] enReply = simpleRsp.build().toByteArray();
            int msg_type = Define.HeaderType.TypeRegisterRsp_VALUE;
            byte[] reply = DealMsg.encodeData(enReply, msg_type);//2);
            int seq = rsp.getSeq();
            mHandler.obtainMessage(Ota3G.MESSAGE_INFO, msg_type, seq, reply).sendToTarget();
        } catch (InvalidProtocolBufferException e) {
        }
    }

    private void parseInfo2V1(BleResponse res) {
        try {
            protocol_v1.V1Register.RegisterReq registerReq =
                    protocol_v1.V1Register.RegisterReq.parseFrom(res.getData());
            //
            otaInfo = new OtaInfo();
            otaInfo.dt = registerReq.getDeviceType();
            otaInfo.hwv = registerReq.getHardwareVersion();
            otaInfo.fwv = registerReq.getFirmwareVersion();
            otaInfo.uid = registerReq.getIden().getDeviceID();
            otaInfo.mac = registerReq.getMAC();
            otaInfo.imsi = registerReq.getIMSI();
            otaInfo.vol = registerReq.getBatteryVoltage();
            otaListener.deviceInfo(otaInfo);
            //
            //set reply
            protocol_v1.V1Register.RegisterRsp.Builder registerRsp =
                    protocol_v1.V1Register.RegisterRsp.newBuilder();
            registerRsp.setIden(V1IdentityMsgUtils.getIdentityMsg(registerReq.getIden()));
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            registerRsp.setTimestamp(timestamp);
            //
            byte[] reply = registerRsp.build().toByteArray();
            int msg_type = protocol_v1.V1Define.HeaderType.TypeRegisterRsp_VALUE;
            mHandler.obtainMessage(Ota3G.MESSAGE_INFO, msg_type, res.getSeq(), reply).sendToTarget();
        } catch (Exception ex) {

        }
    }

    private void parseSetting2V0(BleResponse rsp) {
        try {
            //parse msg
            byte[] data = rsp.getData();
            OtaBean otaBean = DealMsg.decodeData(data);
            Setting.SettingReq settingReq = Setting.SettingReq.parseFrom(otaBean.datas);
            //token=settingReq.getIden().getToken();
            //set msg
            //TODO
            Setting.SettingRsp.Builder settingRsp = Setting.SettingRsp.newBuilder();
            //hwv&dt must to strictly equals
            if (binInfo != null) {
                if (otaInfo.hwv == binInfo.hwv_bin && otaInfo.dt == binInfo.dt_bin) {
                    if (otaInfo.fwv < binInfo.fwv_bin || binInfo.fwv_bin == 0
                            || binInfo.fwv_bin == 100) {
                        settingRsp.setFirmwareID(com.druid.ota.cmd.Command.getFirmwareID(binInfo.datas) + "");
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
            settingRsp.setGprsForzen(0);
            settingRsp.setCode(0);
            if (binInfo != null) {
                settingRsp.setFirmwareVersion(binInfo.fwv_bin);
            }
            byte[] enReply = settingRsp.build().toByteArray();
            int msg_type = Define.HeaderType.TypeSettingRsp_VALUE;
            byte[] reply = DealMsg.encodeData(enReply, msg_type);//12
            int seq = rsp.getSeq();
            mHandler.obtainMessage(Ota3G.MESSAGE_SETTING, msg_type, seq, reply).sendToTarget();
        } catch (InvalidProtocolBufferException e) {
        }
    }

    private void parseSetting2V1(BleResponse res) {
        try {
            protocol_v1.V1Setting.SettingReq settingReq =
                    protocol_v1.V1Setting.SettingReq.parseFrom(res.getData());
            //
            protocol_v1.V1Setting.SettingRsp.Builder settingRspBuilder =
                    protocol_v1.V1Setting.SettingRsp.newBuilder();
            settingRspBuilder.setIden(settingReq.getIden());
            protocol_v1.V1Setting.Setting.Builder settingBuilder = V1SettingUtils.getSettingOTA();
            //hwv&dt must to strictly equals
            if (binInfo != null) {
                if (otaInfo.hwv == binInfo.hwv_bin && otaInfo.dt == binInfo.dt_bin) {
                    if (otaInfo.fwv < binInfo.fwv_bin || binInfo.fwv_bin == 200|| binInfo.fwv_bin == 1000) {
                        settingBuilder.setOTAFirmwareID(com.druid.ota.cmd.Command.getFirmwareID(binInfo.datas) + "");
                        otaListener.availableBin();
                    } else {
                        settingBuilder.setOTAFirmwareID("");
                        otaListener.binTips(0);
                    }
                } else {
                    settingBuilder.setOTAFirmwareID("");
                    otaListener.binTips(1);
                }
            } else {
                settingBuilder.setOTAFirmwareID("");
                otaListener.binTips(1);
            }

            if (binInfo != null) {
                settingBuilder.setOTAFirmwareVersion(binInfo.fwv_bin);
            }
            settingRspBuilder.setSettingInfo(settingBuilder);
            byte[] reply = settingRspBuilder.build().toByteArray();
            int msg_type = protocol_v1.V1Define.HeaderType.TypeSettingRsp_VALUE;
            mHandler.obtainMessage(Ota3G.MESSAGE_SETTING, msg_type, res.getSeq(), reply).sendToTarget();
        } catch (Exception ex) {

        }
    }

    private void parseSettingHub2V1(BleResponse res) {
        try {
            protocol_v1.V1HubSetting.HubSettingReq hubSettingReq =
                    protocol_v1.V1HubSetting.HubSettingReq.parseFrom(res.getData());
            byte[] reply = V1SimpleUtils.getSimpleRsp(hubSettingReq.getIden());
            int msg_type = protocol_v1.V1Define.HeaderType.TypeHubSettingRsp_VALUE;
            mHandler.obtainMessage(Ota3G.MESSAGE_SETTING_HUB, msg_type, res.getSeq(), reply).sendToTarget();
        } catch (Exception ex) {

        }
    }

    private void parseArgosAop2V1(BleResponse res) {
        try {
            protocol_v1.V1ArgosAop.ArgosAopReq argosAopReq =
                    protocol_v1.V1ArgosAop.ArgosAopReq.parseFrom(res.getData());
            byte[] reply = V1SimpleUtils.getSimpleRsp(argosAopReq.getIden());
            int msg_type = protocol_v1.V1Define.HeaderType.TypeArgosAopRsp_VALUE;
            mHandler.obtainMessage(Ota3G.MESSAGE_ARGOS_AOP, msg_type, res.getSeq(), reply).sendToTarget();
        } catch (Exception ex) {

        }
    }

    private void parseParameter2V1(BleResponse res) {
        try {
            protocol_v1.V1Parameter.ParameterReq parameterReq =
                    protocol_v1.V1Parameter.ParameterReq.parseFrom(res.getData());
            protocol_v1.V1Parameter.ParameterRsp.Builder parameterRspBuilder
                    = protocol_v1.V1Parameter.ParameterRsp.newBuilder();
            parameterRspBuilder.setIden(parameterReq.getIden());

            protocol_v1.V1Parameter.Parameter.Builder parameterBuilder =
                    V1ParameterUtils.getParameterOTA();
            parameterRspBuilder.setParameterInfo(parameterBuilder);

            byte[] reply = parameterRspBuilder.build().toByteArray();
            int msg_type = protocol_v1.V1Define.HeaderType.TypeParameterRsp_VALUE;
            mHandler.obtainMessage(Ota3G.MESSAGE_PARAMETER, msg_type, res.getSeq(), reply).sendToTarget();
        } catch (Exception ex) {

        }
    }

    private void parseDown2V0(BleResponse rsp) {
        try {
            //parse
            byte[] data = rsp.getData();
            OtaBean otaBean = DealMsg.decodeData(data);
            Download.DownloadReq downloadReq = Download.DownloadReq.parseFrom(otaBean.datas);
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
            int msg_type = Define.HeaderType.TypeDownloadRsp_VALUE;
            byte[] reply = DealMsg.encodeData(enReply, msg_type);//8
            int seq = rsp.getSeq();
            mHandler.obtainMessage(Ota3G.MESSAGE_OTA, msg_type, seq, reply).sendToTarget();
        } catch (Exception e) {
        }
    }

    private void parseDown2V1(BleResponse rsp) {
        try {
            //parse
            protocol_v1.V1Download.DownloadReq downloadReq =
                    protocol_v1.V1Download.DownloadReq.parseFrom(rsp.getData());
            String fileId = downloadReq.getFileId();
            int begin = downloadReq.getBegin();
            int length = downloadReq.getLength();
            //set
            protocol_v1.V1Download.DownloadRsp.Builder downloadRsp =
                    protocol_v1.V1Download.DownloadRsp.newBuilder();
            downloadRsp.setIden(downloadReq.getIden());
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
            byte[] reply = downloadRsp.build().toByteArray();
            int msg_type = protocol_v1.V1Define.HeaderType.TypeDownloadRsp_VALUE;
            mHandler.obtainMessage(Ota3G.MESSAGE_OTA, msg_type, rsp.getSeq(), reply).sendToTarget();
        } catch (Exception e) {
        }
    }

    public static final int MESSAGE_MODE = 0;
    public static final int MESSAGE_INFO = 1;
    public static final int MESSAGE_SETTING = 2;
    public static final int MESSAGE_SETTING_HUB = 3;
    public static final int MESSAGE_PARAMETER = 4;
    public static final int MESSAGE_OTA = 5;
    public static final int MESSAGE_ARGOS_AOP = 6;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Ota3G.MESSAGE_MODE: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_APP length " + data.length);
                    int proto_cmd = msg.arg1;
                    int seq = msg.arg2;
                    writeCommand(new BleRequestFactory().generalCommand_PkgCmd(seq, proto_cmd, data));
                    break;
                }
                case Ota3G.MESSAGE_INFO: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_REG length " + data.length);
                    int proto_cmd = msg.arg1;
                    int seq = msg.arg2;
                    writeCommand(new BleRequestFactory().generalCommand_PkgCmd(seq, proto_cmd, data));
                    break;
                }
                case Ota3G.MESSAGE_SETTING: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_SETTING length " + data.length);
                    int proto_cmd = msg.arg1;
                    int seq = msg.arg2;
                    writeCommand(new BleRequestFactory().generalCommand_PkgCmd(seq, proto_cmd, data));
                    break;
                }
                case Ota3G.MESSAGE_SETTING_HUB: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_SETTING_HUB length " + data.length);
                    int proto_cmd = msg.arg1;
                    int seq = msg.arg2;
                    writeCommand(new BleRequestFactory().generalCommand_PkgCmd(seq, proto_cmd, data));
                    break;
                }
                case Ota3G.MESSAGE_PARAMETER: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_PARAMETER length " + data.length);
                    int proto_cmd = msg.arg1;
                    int seq = msg.arg2;
                    writeCommand(new BleRequestFactory().generalCommand_PkgCmd(seq, proto_cmd, data));
                    break;
                }
                case Ota3G.MESSAGE_ARGOS_AOP: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_ARGOS_AOP length " + data.length);
                    int proto_cmd = msg.arg1;
                    int seq = msg.arg2;
                    writeCommand(new BleRequestFactory().generalCommand_PkgCmd(seq, proto_cmd, data));
                    break;
                }
                case Ota3G.MESSAGE_OTA: {
                    byte[] data = (byte[]) msg.obj;
                    OtaLog.v("MESSAGE_DOWNLOAD length " + data.length);
                    int proto_cmd = msg.arg1;
                    int seq = msg.arg2;
                    writeCommand(new BleRequestFactory().generalCommand_PkgCmd(seq, proto_cmd, data));
                    break;
                }
                default: {
                    DDC.destoryCmd();
                    break;
                }
            }
        }
    };

    private void writeCommand(final BleRequest req) {
        if (DDC != null) {
            DDC.cmd(req);
        }
    }
}
