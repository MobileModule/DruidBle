package com.druid.bleact.parse;

import com.device.ble.utils.StrUtils;
import com.druid.bleact.model.IdentityModel;
import com.druid.bleact.model.RegistorReqModel;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class RegistorParse {
    /**
     * 解析硬件数据
     *
     * @param registerReq
     * @return
     */
    public static RegistorReqModel getRegistorReqModel(protocol_v1.V1Register.RegisterReq registerReq) {
        RegistorReqModel registorReqModel = new RegistorReqModel();

        //
        protocol_v1.V1IdentityMsg.IdentityMsg identityMsg = registerReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        registorReqModel.identityModel = identityModel;

        //
        String IMSI = registerReq.getIMSI();
        registorReqModel.IMSI = IMSI;
        String IMEI = registerReq.getIMSI();
        registorReqModel.IMEI = IMEI;

        String MAC = ""; //蓝牙MAC地址
        if (registerReq.hasMAC()) {
            MAC = registerReq.getMAC();
        }
        registorReqModel.MAC = MAC;

        int DeviceType = 0; //设备类型
        if (registerReq.hasDeviceType()) {
            DeviceType = registerReq.getDeviceType();
        }
        registorReqModel.DeviceType = DeviceType;

        int HardwareVersion = 0; // 硬件版本
        if (registerReq.hasHardwareVersion()) {
            HardwareVersion = registerReq.getHardwareVersion();
        }
        registorReqModel.HardwareVersion = HardwareVersion;

        int FirmwareVersion = 0; // 固件版本
        if (registerReq.hasFirmwareVersion()) {
            FirmwareVersion = registerReq.getFirmwareVersion();
        }
        registorReqModel.FirmwareVersion = FirmwareVersion;

        int Status = 0;  // 自检状态: 0 - 无异常
        if (registerReq.hasStatus()) {
            Status = registerReq.getStatus();
        }
        registorReqModel.Status = Status;

        int BatteryVoltage = 0; // 电池电压: 毫伏
        if (registerReq.hasBatteryVoltage()) {
            BatteryVoltage = registerReq.getBatteryVoltage();
        }
        registorReqModel.BatteryVoltage = BatteryVoltage;

        int BatteryPower = 0; // 电池电量: 百分比
        if (registerReq.hasBatteryPower()) {
            BatteryPower = registerReq.getBatteryPower();
        }
        registorReqModel.BatteryPower = BatteryPower;

        int SignalStrength = 0; // 信号强度
        if (registerReq.hasSignalStrength()) {
            SignalStrength = registerReq.getSignalStrength();
        }
        registorReqModel.SignalStrength = SignalStrength;

        int BitErrorRate = 0; // 误码率
        if (registerReq.hasBitErrorRate()) {
            BitErrorRate = registerReq.getBitErrorRate();
        }
        registorReqModel.BitErrorRate = BitErrorRate;

        int RadioAccessTechnology = 0; // 网络类型
        if (registerReq.hasRadioAccessTechnology()) {
            RadioAccessTechnology = registerReq.getRadioAccessTechnology();
        }
        registorReqModel.RadioAccessTechnology = RadioAccessTechnology;

        int NetworkOperator = 0; // 运营商代码
        if (registerReq.hasNetworkOperator()) {
            NetworkOperator = registerReq.getNetworkOperator();
        }
        registorReqModel.NetworkOperator = NetworkOperator;
        return registorReqModel;
    }

    public static byte[] getRegistorRspModel(IdentityModel model) {
        protocol_v1.V1Register.RegisterRsp.Builder builder =
                protocol_v1.V1Register.RegisterRsp.newBuilder();
        builder.setTimestamp(StrUtils.getTimeStampSecond());
        protocol_v1.V1IdentityMsg.IdentityMsg.Builder identityBuilder =
                protocol_v1.V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }
}
