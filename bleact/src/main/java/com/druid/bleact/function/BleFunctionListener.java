package com.druid.bleact.function;

import com.druid.bleact.entity.DeviceTargetBean;
import com.device.ble.entity.HubBroadcastModel;
import com.druid.ota.bean.OtaInfo;

import java.util.ArrayList;

public interface BleFunctionListener {
    void bleConnFailed(int processIndex, DeviceTargetBean device, String error, boolean isHub);

    void bleConnSuccess(int processIndex, DeviceTargetBean device, String tips, boolean isHub);

    void bleLinkedDeviceName(int processIndex, DeviceTargetBean device, String ble_name, boolean isHub);

    void bleHubSearchingDevice(int processIndex, DeviceTargetBean device, ArrayList<HubBroadcastModel> devices);

    void bleHubSearchedDevice(int processIndex, DeviceTargetBean device);

    void bleHubConningDevice(int processIndex, DeviceTargetBean device);

    void bleHubConnFailedDevice(int processIndex, DeviceTargetBean device, String tips);

    void bleHubConnSuccessDevice(int processIndex, DeviceTargetBean device, String tips);

    void bleFunctionLog(int processIndex, DeviceTargetBean device, String tips);

    void bleFunctionProgress(int processIndex, DeviceTargetBean device, String tips);

    void bleFunctionTransfer(int processIndex, DeviceTargetBean device, String tips, int cmd_type);

    void bleOperaSuccess(boolean forceStop,int processIndex, DeviceTargetBean device, String tips);

    void bleOperaFailed(boolean forceStop,int processIndex, DeviceTargetBean device, String tips);

    void bleOperaNext(int processIndex, DeviceTargetBean device);

    void bleOperaUnSupport(boolean forceStop,int processIndex, DeviceTargetBean device, String tips);

    void bleOperaMarkData(int processIndex, DeviceTargetBean device, byte[] datas);

    void bleOtaInfo(int processIndex, DeviceTargetBean device, OtaInfo otaInfo);

    void bleOtaStart(int processIndex, DeviceTargetBean device);

    void bleOtaProgress(int processIndex, DeviceTargetBean device, int progress, int all);
}
