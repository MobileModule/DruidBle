package com.druid.bleact.parse;

import android.text.TextUtils;
import android.util.Log;

import com.device.ble.utils.BluetoothBroadcastUtils;
import com.druid.bleact.model.HubBroadcastListModel;
import com.device.ble.entity.HubBroadcastModel;
import com.druid.bleact.model.IdentityModel;

import java.io.Serializable;

import protocol_v1.V1Broadcast;
import protocol_v1.V1IdentityMsg;
import protocol_v1.V1Summary;

/**
 * Created by druid on 2019/9/23.
 */

public class HubBroadcastParse implements Serializable {
    public static HubBroadcastListModel getHubBroadcastReq(V1Broadcast.BroadcastReq
                                                                   broadcastReq, String mac) {
        Log.i("HUB_DEVICE",mac+"-->"+broadcastReq.toString());
        HubBroadcastListModel broadcastListModel = new HubBroadcastListModel();
        //
        V1IdentityMsg.IdentityMsg identityMsg = broadcastReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        broadcastListModel.identityModel = identityModel;
        //
        for (int i = 0; i < broadcastReq.getBroadcastInfoCount(); i++) {
            HubBroadcastModel hubBroadcastModel = new HubBroadcastModel();
            V1Broadcast.Broadcast broadcast = broadcastReq.getBroadcastInfo(i);
            hubBroadcastModel.QUEST_MAC = mac;
            hubBroadcastModel.timestamp = broadcastReq.getTimestamp();
            hubBroadcastModel.MAC = broadcast.getMAC();
            if (!TextUtils.isEmpty(hubBroadcastModel.MAC)) {
                hubBroadcastModel.MAC = MacParse.getRealMac(hubBroadcastModel.MAC).toUpperCase();
            }
            if (broadcast.hasUUID()) {
                hubBroadcastModel.device_name = broadcast.getUUID();
            }
            // 固件版本
            if (broadcast.hasFirmwareVersion()) {
                hubBroadcastModel.FirmwareVersion = broadcast.getFirmwareVersion();
            }
            // 电池电压: 毫伏, 精度到0.001V
            if (broadcast.hasBatteryVoltage()) {
                hubBroadcastModel.BatteryVoltage = broadcast.getBatteryVoltage();
            }
            // 接收信号强度(dBm)
            if (broadcast.hasBleRSSI()) {
                hubBroadcastModel.BleRSSI = broadcast.getBleRSSI();
            }
            if (BluetoothBroadcastUtils.DEVICE_VALID.ISDeviceMini_Other(broadcast.getUUID())) {
                hubBroadcastModel.platform =
                        BluetoothBroadcastUtils.PLATFORM.getDevicePlatformFromBleName(broadcast.getUUID());
                BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus deviceStatus =
                        BluetoothBroadcastUtils.DEVICE_RUN_STATUS.getDeviceStatusByBleName(broadcast.getUUID());
                // 设备状态，0：失活  1：激活   2：库存
                if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                    hubBroadcastModel.DeviceStatus = 1;
                }
                if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.inactive_I) {
                    hubBroadcastModel.DeviceStatus = 0;
                }
                if (deviceStatus == BluetoothBroadcastUtils.DEVICE_RUN_STATUS.DeviceStatus.inactive_S) {
                    hubBroadcastModel.DeviceStatus = 2;
                }
            } else {
                // 设备状态，0：失活  1：激活   2：库存
                if (broadcast.hasDeviceStatus()) {
                    hubBroadcastModel.DeviceStatus = broadcast.getDeviceStatus();
                }
            }
            broadcastListModel.broadcasts.add(hubBroadcastModel);
        }
        return broadcastListModel;
    }

    public static HubBroadcastListModel getHubBroadcastSummaryReq(V1Summary.SummaryReq
                                                                          summaryReq, String mac) {
        Log.i("HUB_DEVICE",mac+"-->"+summaryReq.toString());
        HubBroadcastListModel broadcastListModel = new HubBroadcastListModel();
        //
        V1IdentityMsg.IdentityMsg identityMsg = summaryReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        broadcastListModel.identityModel = identityModel;
        //
        for (int i = 0; i < summaryReq.getSummaryInfoCount(); i++) {
            HubBroadcastModel hubBroadcastModel = new HubBroadcastModel();
            hubBroadcastModel.QUEST_MAC = mac;
            V1Summary.Summary summary = summaryReq.getSummaryInfo(i);
            if (summary.hasTimestamp()) {
                hubBroadcastModel.timestamp = summary.getTimestamp();
            }
            if (summary.hasUUID()) {
                hubBroadcastModel.uuid = summary.getUUID();
            }
            if (!TextUtils.isEmpty(hubBroadcastModel.uuid)) {
                if (hubBroadcastModel.uuid.length() == 12) {
                    hubBroadcastModel.MAC = hubBroadcastModel.uuid.toUpperCase();
                }
                if (hubBroadcastModel.uuid.length() == 10) {
                    hubBroadcastModel.MAC = "C0" + hubBroadcastModel.uuid.toUpperCase();
                }
                hubBroadcastModel.MAC = MacParse.getRealMac(hubBroadcastModel.MAC).toUpperCase();
            }
            // 固件版本
            if (summary.hasFirmwareVersion()) {
                hubBroadcastModel.FirmwareVersion = summary.getFirmwareVersion();
            }
            if (summary.hasHardwareVersion()) {
                hubBroadcastModel.HardwareVersion = summary.getHardwareVersion();
            }
            // 电池电压: 毫伏, 精度到0.001V
            if (summary.hasBatteryVoltage()) {
                hubBroadcastModel.BatteryVoltage = summary.getBatteryVoltage();
            }
            // 接收信号强度(dBm)
            if (summary.hasBleRSSI()) {
                hubBroadcastModel.BleRSSI = summary.getBleRSSI();
            }
            // 设备状态，0：失活  1：激活   2：库存
            if (summary.hasWorkMode()) {
                //设备运行状态: 0.未知, 1.保留, 2.关机, 3.开机, 4.休眠
                int workMode = summary.getWorkMode();
                if (workMode == 2 || workMode == 3 || workMode == 4) {
                    if (workMode == 2) {
                        workMode = 0;
                    }
                    if (workMode == 4) {
                        workMode = 2;
                    }
                    if (workMode == 3) {
                        workMode = 1;
                    }
                } else {
                    workMode = -1;
                }
                hubBroadcastModel.DeviceStatus = workMode;
            }
            if (summary.hasCustomerId()) {
                int platTypeId= summary.getPlatform();
                int customId=summary.getCustomerId();
                hubBroadcastModel.platform=new BluetoothBroadcastUtils.PLATFORM.Platform(platTypeId,customId);
            }else {
                hubBroadcastModel.platform = BluetoothBroadcastUtils.PLATFORM.
                        getDevicePlatformFromQuest(summary.getPlatform());
            }

            broadcastListModel.broadcasts.add(hubBroadcastModel);
        }
        return broadcastListModel;
    }
}
