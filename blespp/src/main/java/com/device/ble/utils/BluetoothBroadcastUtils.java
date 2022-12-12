package com.device.ble.utils;

import android.text.TextUtils;

import com.device.ble.broadcast.BCProtoDataEntity;
import com.device.ble.broadcast.BCProtoUtils;
import com.device.ble.broadcast.BCType;
import com.device.ble.broadcast.v2.BCV2DataEntity;
import com.device.ble.broadcast.v3_1.BCV3_1DataEntity;
import com.device.ble.broadcast.v5.BCV5DataEntity;
import com.device.ble.entity.HubBroadcastModel;

import java.io.Serializable;

/**
 * Created by druid on 2019/3/1.
 */

public class BluetoothBroadcastUtils {
    public static final String DEFAULT_BLE_NAME = "DEFAULT";
    public static final String DEVICE_2G = "SIM800C";//2g设备
    public static final String DEVICE_3G_FLEX_1 = "DRUID-01";//flex 3g设备
    public static final String DEVICE_3G_FLEX_2 = "DRUID-BM";//flex 3g设备
    public static final String DEVICE_MINI = "D";//mini设备 DtTaeM M代表:A代表激活状态，I代表失活状态，S代表库存状态

    public static final String DEVICE_BIRD_RELEASE = "b";//鸟设备
    public static final String DEVICE_CATTLE_RELEASE = "t";//cattle设备
    public static final String DEVICE_BASIC_RELEASE = "p";//基础数据平台设备
    public static final String DEVICE_LOTEK_RELEASE = "l";//lotek平台设备
    public static final String DEVICE_STACK_RELEASE = "s";//stack平台设备

    public static final String DEVICE_BIRD_DEBUG = "B";//鸟设备
    public static final String DEVICE_CATTLE_DEBUG = "T";//cattle设备
    public static final String DEVICE_BASIC_DEBUG = "P";//基础数据平台设备
    public static final String DEVICE_STACK_DEBUG = "S";//stack平台设备

    //
    public static final String HUB_G = "G";//桩
    public static final String HUB_H = "H";//桩
    public static final String HUB_QUEST_Q = "Q";//中继
    //
    public static final String ACTIVE_A = "A";//激活
    public static final String INACTIVE_I = "I";//失活
    public static final String INACTIVE_S = "S";//库存

    public static final String ACTIVE_A_BASIC = "a";//基础数据平台-激活
    public static final String INACTIVE_I_BASIC = "i";//基础数据平台-失活
    public static final String INACTIVE_S_BASIC = "s";//基础数据平台-库存

    public static final class Utils {
        public static boolean isHubQuest(String ble_name, BCProtoDataEntity bcData) {
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                DEVICE_TYPE.DeviceType type = DEVICE_TYPE.getDeviceType(bcData);
                if (type == DEVICE_TYPE.DeviceType.quest) {
                    return true;
                }
            } else {
                DEVICE_TYPE.DeviceType type = DEVICE_TYPE.getDeviceType(ble_name);
                if (type == DEVICE_TYPE.DeviceType.quest) {
                    return true;
                }
            }
            return false;
        }

        public static boolean isHub(String ble_name, BCProtoDataEntity bcData) {
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                DEVICE_TYPE.DeviceType type = DEVICE_TYPE.getDeviceType(bcData);
                if (type == DEVICE_TYPE.DeviceType.hub) {
                    return true;
                }
            } else {
                DEVICE_TYPE.DeviceType type = DEVICE_TYPE.getDeviceType(ble_name);
                if (type == DEVICE_TYPE.DeviceType.hub) {
                    return true;
                }
            }
            return false;
        }

        public static double getVoltage(BCProtoDataEntity bcData) {
            double voltage = 0d;// 无效值
            if (bcData != null) {
                if (bcData.type == BCType.v0) {
                    if (bcData.v0Data != null) {
                        voltage = bcData.v0Data.voltage / 1000d;
                    }
                }
                if (bcData.type == BCType.v1) {
                    if (bcData.v1Data != null) {
                        voltage = bcData.v1Data.voltage / 1000d;
                    }
                }
                if (bcData.type == BCType.v2) {
                    if (bcData.v2Datas.size() > 0) {
                        BCV2DataEntity v2Data = bcData.v2Datas.get(0);
                        if (v2Data.power_type == 1) {//0 vlaue 剩余电量,1 value 剩余电压 单位 mv
                            voltage = v2Data.power_value / 1000d;
                        }
                    }
                }
                if (bcData.type == BCType.v3) {
                    if (bcData.v3Data != null) {
                        if (bcData.v3Data.power_type == 1) {//0 vlaue 剩余电量,1 value 剩余电压 单位 mv
                            voltage = bcData.v3Data.power_value / 1000d;
                        }
                    }
                }
                if (bcData.type == BCType.v5) {
                    if (bcData.v5Data != null) {
                        voltage = bcData.v5Data.power_value / 1000d;
                    }
                }
            }
            return voltage;
        }

        public static String getInCompleteUniqueUUID(BCProtoDataEntity bcData) {
            String inCompleteUUID = "";// 无效值
            if (bcData != null) {
                if (bcData.type == BCType.v0) {
                    if (bcData.v0Data != null) {
                        byte[] mac_b = DataType.data16ToBytes(bcData.v0Data.mac);
                        inCompleteUUID = ByteUtil.byteArrayToHexString(mac_b);
                    }
                }
                if (bcData.type == BCType.v1) {
                    if (bcData.v1Data != null) {
                        byte[] mac_b = DataType.data16ToBytes(bcData.v1Data.mac);
                        inCompleteUUID = ByteUtil.byteArrayToHexString(mac_b);
                    }
                }
                if (bcData.type == BCType.v2) {
                    if (bcData.v2Datas.size() > 0) {
                        BCV2DataEntity v2Data = bcData.v2Datas.get(0);
                        inCompleteUUID = ByteUtil.byteArrayToHexString(v2Data.sn);
                    }
                }
                if (bcData.type == BCType.v3) {
                    if (bcData.v3Data != null) {
                        inCompleteUUID = ByteUtil.byteArrayToHexString(bcData.v3Data.sn);
                    }
                }
                if (bcData.type == BCType.v5) {
                    if (bcData.v5Data != null) {
                        inCompleteUUID = ByteUtil.byteArrayToHexString(bcData.v5Data.sn);
                    }
                }
            }
            return inCompleteUUID;
        }

        public static String getInCompleteShowUUID(BCProtoDataEntity bcData, String mac) {
            String inCompleteUUID = "";// 无效值
            if (bcData != null) {
                if (bcData.type == BCType.v0) {
                    if (bcData.v0Data != null) {
                        byte[] mac_b = DataType.data16ToBytes(bcData.v0Data.mac);
                        inCompleteUUID = ByteUtil.byteArrayToHexString(mac_b);
                        inCompleteUUID = mac.toLowerCase();
                        if (inCompleteUUID.contains(":")) {
                            inCompleteUUID = inCompleteUUID.replace(":", "");
                        }
                    }
                }
                if (bcData.type == BCType.v1) {
                    if (bcData.v1Data != null) {
                        byte[] mac_b = DataType.data16ToBytes(bcData.v1Data.mac);
                        inCompleteUUID = ByteUtil.byteArrayToHexString(mac_b);
                        inCompleteUUID = mac.toLowerCase();
                        if (inCompleteUUID.contains(":")) {
                            inCompleteUUID = inCompleteUUID.replace(":", "");
                        }
                    }
                }
                if (bcData.type == BCType.v2) {
                    if (bcData.v2Datas.size() > 0) {
                        BCV2DataEntity v2Data = bcData.v2Datas.get(0);
                        inCompleteUUID = ByteUtil.byteArrayToHexString(v2Data.sn);
                    }
                }
                if (bcData.type == BCType.v3) {
                    if (bcData.v3Data != null) {
                        inCompleteUUID = ByteUtil.byteArrayToHexString(bcData.v3Data.sn);
                    }
                }
                if (bcData.type == BCType.v5) {
                    if (bcData.v5Data != null) {
                        inCompleteUUID = ByteUtil.byteArrayToHexString(bcData.v5Data.sn);
                    }
                }
            }
            return inCompleteUUID;
        }

        public static int getHardVersion(BCProtoDataEntity bcData){
            int version = 0;// 无效值
            if (bcData != null) {
                if (bcData.type == BCType.v0) {

                }
                if (bcData.type == BCType.v1) {

                }
                if (bcData.type == BCType.v2) {
                    if (bcData.v2Datas.size() > 0) {
                        BCV2DataEntity v2Data = bcData.v2Datas.get(0);
//                        if (v2Data.power_type == 1) {
                        if (v2Data.header.has_basic_info == 1) {
                            version = v2Data.hw_version;
                        }
//                        }
                    }
                }
                if (bcData.type == BCType.v3) {
                    if (bcData.v3Data != null) {
//                        if (bcData.v3Data.power_type == 1) {
                        version = bcData.v3Data.hw_version;
//                        }
                    }
                }
                if (bcData.type == BCType.v5) {
                    if (bcData.v5Data != null) {
//                        if (bcData.v3Data.power_type == 1) {
                        version = bcData.v5Data.hw_version ;
//                        }
                    }
                }
            }
            return version;
        }

        public static int getFirmWareVersion(BCProtoDataEntity bcData) {
            int version = 0;// 无效值
            if (bcData != null) {
                if (bcData.type == BCType.v0) {
                    if (bcData.v0Data != null) {
                        version = bcData.v0Data.version;
                    }
                }
                if (bcData.type == BCType.v1) {
                    if (bcData.v1Data != null) {
                        version = bcData.v1Data.fw_version;
                    }
                }
                if (bcData.type == BCType.v2) {
                    if (bcData.v2Datas.size() > 0) {
                        BCV2DataEntity v2Data = bcData.v2Datas.get(0);
//                        if (v2Data.power_type == 1) {
                        if (v2Data.header.has_basic_info == 1) {
                            version = v2Data.fw_version;
                        }
//                        }
                    }
                }
                if (bcData.type == BCType.v3) {
                    if (bcData.v3Data != null) {
//                        if (bcData.v3Data.power_type == 1) {
                        version = bcData.v3Data.fw_version + 1000;
//                        }
                    }
                }
                if (bcData.type == BCType.v5) {
                    if (bcData.v5Data != null) {
//                        if (bcData.v3Data.power_type == 1) {
                        version = bcData.v5Data.fw_version + 1000;
//                        }
                    }
                }
            }
            return version;
        }

        //获取设备类型device_type
        public static int getServerDeviceType(BCProtoDataEntity bcData) {
            int device_type = -1;// 无效值
            if (bcData != null) {
                if (bcData.type == BCType.v0) {//无广播设备类型
                }
                if (bcData.type == BCType.v1) {
                    if (bcData.v1Data != null) {
                        if (bcData.v1Data.header.has_dev_type == 1) {
                            device_type = bcData.v1Data.dev_type;
                        }
                    }
                }
                if (bcData.type == BCType.v2) {
                    if (bcData.v2Datas.size() > 0) {
                        BCV2DataEntity v2Data = bcData.v2Datas.get(0);
                        byte[] sn = v2Data.sn;
                        if (sn != null) {
                            if (sn.length == 5) {
                                byte[] dt = new byte[]{sn[1], sn[0]};
                                device_type = DataType.byteArrayTo16(dt, 0);
                            }
                        }
                    }
                }
                if (bcData.type == BCType.v3) {
                    if (bcData.v3Data != null) {
                        byte[] sn = bcData.v3Data.sn;
                        if (sn != null) {
                            if (sn.length == 5) {
                                byte[] dt = new byte[]{sn[1], sn[0]};
                                device_type = DataType.byteArrayTo16(dt, 0);
                            }
                        }
                    }
                }
                if (bcData.type == BCType.v5) {
                    if (bcData.v5Data != null) {
                        byte[] sn = bcData.v5Data.sn;
                        if (sn != null) {
                            if (sn.length == 5) {
                                byte[] dt = new byte[]{sn[1], sn[0]};
                                device_type = DataType.byteArrayTo16(dt, 0);
                            }
                        }
                    }
                }
            }
            return device_type;
        }
    }

    public static final class DEVICE_VALID {
        /**
         * 是否是德鲁伊设备
         */
        public static boolean ISDruidDevices(String ble_name, ScanRecordUtil scanRecordUtil) {
            if (validDruidDeviceFromBroadCast(scanRecordUtil)) {
                return true;
            } else {
                return ISDruidDevices(ble_name);
            }
        }

        /**
         * 是否是德鲁伊设备 从名字和广播内容判断
         */
        public static boolean ISDruidDevices(String ble_name, BCProtoDataEntity bcData) {
            if (validDruidDeviceFromBroadCast(bcData)) {
                return true;
            } else {
                return ISDruidDevices(ble_name);
            }
        }

        /**
         * 是否是德鲁伊设备 从名字判断
         */
        public static boolean ISDruidDevices(String ble_name) {
            boolean device_self = false;
            if (!TextUtils.isEmpty(ble_name)) {
                if (ble_name.contains(BluetoothBroadcastUtils.DEVICE_2G)) {
                    device_self = true;
                } else {
                    if (ble_name.contains(BluetoothBroadcastUtils.DEVICE_3G_FLEX_1)) {
                        device_self = true;
                    } else {
                        if (ble_name.contains(BluetoothBroadcastUtils.DEVICE_3G_FLEX_2)) {
                            device_self = true;
                        } else {
                            if (ble_name.length() == 6) {
                                String ble_name_type = ble_name.substring(0, 1);
                                if (ble_name_type.equals(BluetoothBroadcastUtils.DEVICE_MINI)) {//D
                                    if (ble_name.length() == 6) {
                                        String ble_name_status = ble_name.substring(5, 6);
                                        if (ble_name_status.equalsIgnoreCase(ACTIVE_A) ||
                                                ble_name_status.equalsIgnoreCase(INACTIVE_I) ||
                                                ble_name_status.equalsIgnoreCase(INACTIVE_S)) {
                                            device_self = true;
                                        } else {
                                            device_self = false;
                                        }
                                    } else {
                                        device_self = false;
                                    }
                                } else {
                                    device_self = false;
                                }
                            }
                        }
                    }
                }
            }
            return device_self;
        }

        /**
         * 是否是2G经典蓝牙设备
         */
        public static boolean ISDevice2G(String ble_name) {
            boolean device_self = ISDruidDevices(ble_name);
            if (device_self) {
                if (ble_name.contains(BluetoothBroadcastUtils.DEVICE_2G)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        /**
         * 是否是Flex3G低功耗蓝牙设备
         */
        public static boolean ISDeviceFlex3G(String ble_name) {
            boolean device_self = ISDruidDevices(ble_name);
            if (device_self) {
                if (ble_name.contains(BluetoothBroadcastUtils.DEVICE_3G_FLEX_1) ||
                        ble_name.contains(BluetoothBroadcastUtils.DEVICE_3G_FLEX_2)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        /**
         * 是否是Mini或者其他低功耗蓝牙设备 从名字和广播内容判断
         */
        public static boolean ISDeviceMini_Other(String ble_name, BCProtoDataEntity bcData) {
            if (validDruidDeviceFromBroadCast(bcData)) {
                return true;
            } else {
                return ISDeviceMini_Other(ble_name);
            }
        }

        /**
         * 是否是Mini或者其他低功耗蓝牙设备 从名字判断
         */
        public static boolean ISDeviceMini_Other(String ble_name) {
            boolean device_self = ISDruidDevices(ble_name);
            if (device_self) {
                if (ble_name.length() == 6) {
                    String ble_name_type = ble_name.substring(0, 1);
                    if (ble_name_type.equals(BluetoothBroadcastUtils.DEVICE_MINI)) {//D
                        if (ble_name.length() == 6) {
                            String ble_name_status = ble_name.substring(5, 6);
                            if (ble_name_status.equalsIgnoreCase(ACTIVE_A) ||
                                    ble_name_status.equalsIgnoreCase(INACTIVE_I) ||
                                    ble_name_status.equalsIgnoreCase(INACTIVE_S)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        /**
         * 判断是否为新的设备，没有蓝牙名称什么的
         */
        public static boolean validDruidDeviceFromBroadCast(ScanRecordUtil scanRecordUtil) {
            BCProtoDataEntity bcData = BCProtoUtils.getBCEntity(scanRecordUtil);
            return validDruidDeviceFromBroadCast(bcData);
        }

        public static boolean validDruidDeviceFromBroadCast(BCProtoDataEntity bcData) {
            if (validV2DruidDevice(bcData)) {
                return true;
            }
            if (validV3DruidDevice(bcData)) {
                return true;
            }
            if (validV5DruidDevice(bcData)) {
                return true;
            }
            return false;
        }

        static boolean validV2DruidDevice(BCProtoDataEntity bcData) {
            if (bcData != null) {
                if (bcData.type == BCType.v2) {
                    if (bcData.v2Datas.size() > 0) {
                        BCV2DataEntity v2Data = bcData.v2Datas.get(0);
                        if (v2Data.sn != null) {
                            //添加查表判断类型
                            if (v2Data.sn.length == 5) {
                                //1 - 整测完成，等待升级  ,2 - 库存,3 - 激活,4 - 失活
                                if (1 < v2Data.sys_state && v2Data.sys_state < 5) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

        static boolean validV3DruidDevice(BCProtoDataEntity bcData) {
            if (bcData != null) {
                if (bcData.type == BCType.v3) {
                    if (bcData.v3Data != null) {
                        if (bcData.v3Data.sn != null) {
                            //TODO 添加查表判断类型
                            if (bcData.v3Data.sn.length == 5) {
                                //1 - 整测完成，等待升级  ,2 - 库存,3 - 激活,4 - 失活
                                if (1 < bcData.v3Data.sys_state && bcData.v3Data.sys_state < 5) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

        static boolean validV5DruidDevice(BCProtoDataEntity bcData) {
            if (bcData != null) {
                if (bcData.type == BCType.v5) {
                    if (bcData.v5Data != null) {
                        if (bcData.v5Data.sn != null) {
                            //TODO 添加查表判断类型
                            if (bcData.v5Data.sn.length == 5) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

    public static final class DEVICE_TYPE {
        public enum DeviceType {
            unknown,
            device,
            hub,
            quest
        }

        public static DeviceType getDeviceType(String ble_name, BCProtoDataEntity bcData) {
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                return getDeviceType(bcData);
            } else {
                return getDeviceType(ble_name);
            }
        }

        public static DeviceType getDeviceType(BCProtoDataEntity bcData) {
            DeviceType type = DeviceType.unknown;
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                byte[] sn = null;
                if (bcData.v2Datas.size() > 0) {
                    if (bcData.v2Datas.get(0) != null) {
                        sn = bcData.v2Datas.get(0).sn;
                    }
                }
                if (bcData.v3Data != null) {
                    sn = bcData.v3Data.sn;
                }
                if (bcData.v5Data != null) {
                    sn = bcData.v5Data.sn;
                }
                if (sn != null) {
                    if (sn.length == 5) {
                        type = DeviceType.device;
                        int sn_index_0 = DataType.byteArrayTo16ByBigEndian(sn, 0);
                        if (sn_index_0 == 0X2A20) {
                            type = DeviceType.hub;
                        }
                        if (sn_index_0 == 0X1700) {
                            type = DeviceType.quest;
                        }
//                        //特殊处理
//                        // BADGE
//                        if (sn_index_0 == 0X2800) {
//                            type = DeviceType.hub;
//                        }
//                        //TAG III
//                        if (sn_index_0 == 0X2600) {
//                            type = DeviceType.hub;
//                        }
//                        //TAG G
//                        if (sn_index_0 == 0X2d00) {
//                            type = DeviceType.hub;
//                        }
                    }
                }
            }
            return type;
        }

        public static DeviceType getDeviceType(String ble_name) {
            DeviceType type = DeviceType.unknown;
            if (!TextUtils.isEmpty(ble_name)) {
                if (DEVICE_VALID.ISDeviceMini_Other(ble_name)) {
                    String status_tips = ble_name.substring(2, 3);
                    if (status_tips.contains(HUB_H) || status_tips.contains(HUB_G)) {
                        type = DeviceType.hub;
                    } else {
                        if (status_tips.contains(HUB_QUEST_Q)) {
                            type = DeviceType.quest;
                        } else {
                            type = DeviceType.device;
                        }
                    }
                }
            }
            return type;
        }
    }

    public static final class DEVICE_RUN_STATUS {
        //M代表:A代表失活状态，I代表激活状态，S代表库存状态
        public enum DeviceStatus {
            unkown,//未知
            active_A,//激活
            inactive_I,//失活
            inactive_S//库存
        }

        public interface DeviceStatusServerType {
            // 0:未知，1:保留, 2:关机（原库存）, 3:开机, 4:休眠（原关机, 废弃）
            int unknown = 0;
            int off = 2;
            int on = 3;
        }

        /**
         * 设备开关机状态 从名字和广播内容获取
         */
        public static DeviceStatus getDeviceStatus(String ble_name, BCProtoDataEntity bcData) {
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                return getDeviceStatus(bcData);
            } else {
                return getDeviceStatusByBleName(ble_name);
            }
        }

        public static String parseDeviceStatus(String ble_name, BCProtoDataEntity bcData, boolean isOn) {
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                //1 - 整测完成，等待升级  ,2 - 库存,3 - 激活,4 - 失活
                int sys_state = 4;
                if (isOn) {
                    sys_state = 2;
                }
                if (DEVICE_VALID.validV2DruidDevice(bcData)) {
                    BCV2DataEntity v2Data = bcData.v2Datas.get(0);
                    v2Data.sys_state = sys_state;
                }
                if (DEVICE_VALID.validV3DruidDevice(bcData)) {
                    BCV3_1DataEntity v3Data = bcData.v3Data;
                    v3Data.sys_state = sys_state;
                }
//                if (DEVICE_VALID.validV5DruidDevice(bcData)) {
//                    BCV5DataEntity v5Data = bcData.v5Data;
//                    v3Data.sys_state = sys_state;
//                }
            } else {
                if (!TextUtils.isEmpty(ble_name)) {
                    if (DEVICE_VALID.ISDeviceMini_Other(ble_name)) {
                        String status_tips = INACTIVE_I;
                        if (isOn) {
                            status_tips = ACTIVE_A;
                        }
                        ble_name = ble_name.substring(0, 5) + status_tips;
                    }
                }
            }
            return ble_name;
        }

        public static DeviceStatus getDeviceStatus(BCProtoDataEntity bcData) {
            DeviceStatus status = DeviceStatus.unkown;
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                //1 - 整测完成，等待升级  ,2 - 库存,3 - 激活,4 - 失活
                int sys_state = -1;
                if (DEVICE_VALID.validV2DruidDevice(bcData)) {
                    BCV2DataEntity v2Data = bcData.v2Datas.get(0);
                    sys_state = v2Data.sys_state;
                }
                if (DEVICE_VALID.validV3DruidDevice(bcData)) {
                    BCV3_1DataEntity v3Data = bcData.v3Data;
                    sys_state = v3Data.sys_state;
                }
                if (DEVICE_VALID.validV5DruidDevice(bcData)) {
                    BCV5DataEntity v5Data = bcData.v5Data;
                    if (v5Data.dev_status_active == 0) {
                        sys_state = 4;
                    } else {
                        sys_state = 3;
                    }
                }

                if (sys_state == 2) {
                    status = DeviceStatus.inactive_S;
                }
                if (sys_state == 3) {
                    status = DeviceStatus.active_A;
                }
                if (sys_state == 4) {
                    status = DeviceStatus.inactive_I;
                }
                return status;
            }
            return status;
        }

        /**
         * 设备开关机状态 从蓝牙广播名字获取
         */
        public static DeviceStatus getDeviceStatusByBleName(String ble_name) {
            DeviceStatus status = DeviceStatus.unkown;
            if (!TextUtils.isEmpty(ble_name)) {
                if (DEVICE_VALID.ISDeviceMini_Other(ble_name)) {
                    String status_tips = ble_name.substring(5, ble_name.length());
                    //M代表:A代表激活状态，I代表失活状态,S代表库存状态
                    if (status_tips.equalsIgnoreCase(INACTIVE_I)) {
                        status = DeviceStatus.inactive_I;
                    } else {
                        if (status_tips.equalsIgnoreCase(ACTIVE_A)) {
                            status = DeviceStatus.active_A;
                        } else {
                            if (status_tips.equalsIgnoreCase(INACTIVE_S)) {
                                status = DeviceStatus.inactive_S;
                            } else {
                                status = DeviceStatus.unkown;
                            }
                        }
                    }
                }
            }
            return status;
        }

        /**
         * 设备开关机状态 从收取数据文件名获取
         */
        public static DeviceStatus getDeviceStatusByFileName(String ble_name) {
            DeviceStatus status = DeviceStatus.unkown;
            if (!TextUtils.isEmpty(ble_name)) {
                if (DEVICE_TRANSFER.ISDruidFileName(ble_name)) {
                    String status_tips = ble_name.substring(ble_name.length() - 1, ble_name.length());
                    //M代表:A代表激活状态，I代表失活状态,S代表库存状态
                    if (status_tips.equalsIgnoreCase(INACTIVE_I)) {
                        status = DeviceStatus.inactive_I;
                    } else {
                        if (status_tips.equalsIgnoreCase(ACTIVE_A)) {
                            status = DeviceStatus.active_A;
                        } else {
                            if (status_tips.equalsIgnoreCase(INACTIVE_S)) {
                                status = DeviceStatus.inactive_S;
                            } else {
                                status = DeviceStatus.unkown;
                            }
                        }
                    }
                }
            }
            return status;
        }

        /**
         * 设备开关机状态 从Quest内容获取
         */
        public static DeviceStatus getDeviceStatusFromQuest(int device_status) {
            // quest 广播 设备状态，0：失活，1：激活，2：库存
            DeviceStatus status = DeviceStatus.unkown;
            if (device_status == 0) {
                status = DeviceStatus.inactive_I;
            }
            if (device_status == 1) {
                status = DeviceStatus.active_A;
            }
            if (device_status == 2) {
                status = DeviceStatus.inactive_S;
            }
            return status;
        }
    }

    public static final class DEVICE_TRANSFER {
        //蓝牙转换名称: 6位MAC_平台类别id_客户id_开关机状态 .例如:AC8C7F_1_1_I
        //是否是德鲁伊设备 数据收取存储
        public static boolean ISDruidFileName(String file_name) {
            boolean file_name_v1 = ISDruidFileNameV1(file_name);
            if (file_name_v1) {
                return true;
            }
            boolean file_name_v2 = ISDruidFileNameV2(file_name);
            if (file_name_v2) {
                return true;
            }
            boolean file_name_v3 = ISDruidFileNameV3(file_name);
            if (file_name_v3) {
                return true;
            }
            return false;
        }

        //收取数据v1.0[DbL1aS]
        public static boolean ISDruidFileNameV1(String file_name) {
            if (!TextUtils.isEmpty(file_name)) {
                if (file_name.length() == 6) {
                    String ble_name_type = file_name.substring(0, 1);
                    if (ble_name_type.equals(BluetoothBroadcastUtils.DEVICE_MINI)) {//D
                        if (file_name.length() == 6) {
                            String ble_name_status = file_name.substring(5, 6);
                            if (ble_name_status.equalsIgnoreCase(ACTIVE_A) ||
                                    ble_name_status.equalsIgnoreCase(INACTIVE_I) ||
                                    ble_name_status.equalsIgnoreCase(INACTIVE_S)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        //收取数据v2.0[ac8a7f_1_i]
        public static boolean ISDruidFileNameV2(String file_name) {
            if (!TextUtils.isEmpty(file_name)) {
                if (file_name.contains("_")) {
                    String[] ble_name_splits = file_name.split("_");
                    if (ble_name_splits.length == 3) {
                        if (ble_name_splits[0].length() == 6) {
                            try {
                                int platform = Integer.parseInt(ble_name_splits[1]);
                                if (ble_name_splits[2].length() == 1) {
                                    String status = ble_name_splits[2];
                                    if (status.equalsIgnoreCase(ACTIVE_A) ||
                                            status.equalsIgnoreCase(INACTIVE_I) ||
                                            status.equalsIgnoreCase(INACTIVE_S)) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } catch (Exception ex) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        //收取数据v3.0[ac8a7f_1_1_i]
        public static boolean ISDruidFileNameV3(String file_name) {
            if (!TextUtils.isEmpty(file_name)) {
                if (file_name.contains("_")) {
                    String[] ble_name_splits = file_name.split("_");
                    if (ble_name_splits.length == 4) {
                        if (ble_name_splits[0].length() == 6) {
                            try {
                                int platTypeId = Integer.parseInt(ble_name_splits[1]);
                                int customId = Integer.parseInt(ble_name_splits[2]);
                                if (ble_name_splits[3].length() == 1) {
                                    String status = ble_name_splits[3];
                                    if (status.equalsIgnoreCase(ACTIVE_A) ||
                                            status.equalsIgnoreCase(INACTIVE_I) ||
                                            status.equalsIgnoreCase(INACTIVE_S)) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } catch (Exception ex) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        //是否为基础数据平台
        public static boolean isBasicPlatformByFileName(String file_name) {
            if (ISDruidFileName(file_name)) {
                PLATFORM.Platform platform = PLATFORM.getDevicePlatformFromFileName(file_name);
                if (platform != null) {
                    //3,1
                    if (platform.PlatTypeID == 3 && platform.CustomID == 1) {
                        return true;
                    }
                }
            }
            return false;
        }

        //生成收取数据文件名
        public static String getFileName(String ble_name, String mac,
                                         BCProtoDataEntity bcData) {
            if (!TextUtils.isEmpty(ble_name)) {
                if (DEVICE_VALID.ISDruidDevices(ble_name)) {
                    return getFileNameByBleName(ble_name, mac);
                }
            }
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                return getFileNameByBroadCast(mac, bcData);
            }
            if (!TextUtils.isEmpty(ble_name)) {
                if (DEVICE_VALID.ISDevice2G(ble_name) || DEVICE_VALID.ISDeviceFlex3G(ble_name)) {
                    return ble_name;
                }
            }
            return DEFAULT_BLE_NAME;
        }

        public static String getFileNameByBleName(String ble_name, String mac) {
            try {
                if (!TextUtils.isEmpty(mac)) {
                    if (!TextUtils.isEmpty(ble_name)) {
                        if (DEVICE_VALID.ISDruidDevices(ble_name)) {
                            DEVICE_RUN_STATUS.DeviceStatus status = DEVICE_RUN_STATUS.getDeviceStatusByBleName(ble_name);
                            if (mac.contains(":")) {
                                mac = mac.replace(":", "");
                            }
                            if (mac.length() == 12) {
                                String mac_index = mac.toUpperCase().substring(mac.length() - 6, mac.length());
                                PLATFORM.Platform platform = PLATFORM.getDevicePlatformFromBleName(ble_name);
                                String status_index = "";
                                if (status != DEVICE_RUN_STATUS.DeviceStatus.unkown) {
                                    if (status == DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                                        status_index = ACTIVE_A;
                                    }
                                    if (status == DEVICE_RUN_STATUS.DeviceStatus.inactive_I) {
                                        status_index = INACTIVE_I;
                                    }
                                    if (status == DEVICE_RUN_STATUS.DeviceStatus.inactive_S) {
                                        status_index = INACTIVE_S;
                                    }
                                }

                                if ((!TextUtils.isEmpty(mac_index)) && platform != null) {
                                    return mac_index + "_" + platform.PlatTypeID + "_" + platform.CustomID + "_" + status_index;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {

            }
            return DEFAULT_BLE_NAME;
        }

        public static String getFileNameByBroadCast(String mac, BCProtoDataEntity bcData) {
            try {
                if (!TextUtils.isEmpty(mac)) {
                    if (bcData != null) {
                        if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                            DEVICE_RUN_STATUS.DeviceStatus status = DEVICE_RUN_STATUS.getDeviceStatus(bcData);
                            if (mac.contains(":")) {
                                mac = mac.replace(":", "");
                            }
                            if (mac.length() == 12) {
                                String mac_index = mac.toUpperCase().substring(mac.length() - 6, mac.length());
                                PLATFORM.Platform platform = PLATFORM.getDevicePlatformFromBroadCast(bcData);
                                String status_index = "";
                                if (status != DEVICE_RUN_STATUS.DeviceStatus.unkown) {
                                    if (status == DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                                        status_index = ACTIVE_A;
                                    }
                                    if (status == DEVICE_RUN_STATUS.DeviceStatus.inactive_I) {
                                        status_index = INACTIVE_I;
                                    }
                                    if (status == DEVICE_RUN_STATUS.DeviceStatus.inactive_S) {
                                        status_index = INACTIVE_S;
                                    }
                                }

                                if ((!TextUtils.isEmpty(mac_index)) && platform != null) {
                                    return mac_index + "_" + platform.PlatTypeID + "_" + platform.CustomID + "_" + status_index;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {

            }
            return DEFAULT_BLE_NAME;
        }

        public static String getFileNameByQuest(HubBroadcastModel model) {
            String mac = model.MAC;
            try {
                if (!TextUtils.isEmpty(mac)) {
                    DEVICE_RUN_STATUS.DeviceStatus status =
                            DEVICE_RUN_STATUS.getDeviceStatusFromQuest(model.DeviceStatus);
                    if (mac.contains(":")) {
                        mac = mac.replace(":", "");
                    }
                    if (mac.length() == 12) {
                        String mac_index = mac.toUpperCase().substring(mac.length() - 6, mac.length());
                        PLATFORM.Platform platform = model.platform;
                        String status_index = "";
                        if (status != DEVICE_RUN_STATUS.DeviceStatus.unkown) {
                            if (status == DEVICE_RUN_STATUS.DeviceStatus.active_A) {
                                status_index = ACTIVE_A;
                            }
                            if (status == DEVICE_RUN_STATUS.DeviceStatus.inactive_I) {
                                status_index = INACTIVE_I;
                            }
                            if (status == DEVICE_RUN_STATUS.DeviceStatus.inactive_S) {
                                status_index = INACTIVE_S;
                            }
                        }

                        if ((!TextUtils.isEmpty(mac_index)) && platform != null) {
                            return mac_index + "_" + platform.PlatTypeID + "_" + platform.CustomID + "_" + status_index;
                        }
                    }
                }
            } catch (Exception ex) {

            }
            return DEFAULT_BLE_NAME;
        }

        public static String forceBasicFileNameByFileName(String file_name) {
            String basic_file_name = "";
            if (ISDruidFileName(file_name)) {
                String[] ble_name_splits = file_name.split("_");
                if (ble_name_splits.length >= 3) {
                    basic_file_name = ble_name_splits[0] + "_" + "3" + "_" + "1" + "_" + ble_name_splits[2];
                }
            }
            return basic_file_name;
        }
    }

    public static final class PLATFORM {
        //平台对象
        public static class Platform implements Serializable {
            public int PlatTypeID = 1;//平台类别id
            public int CustomID = 0;//客户id

            public Platform(int typeId, int customId) {
                this.PlatTypeID = typeId;
                this.CustomID = customId;
            }

            @Override
            public String toString() {
                return "[" + "PlatTypeID:" + PlatTypeID + "," + "CustomID:" + CustomID + "]";
            }
        }

        // 999：super，0.Unknow, 1.druid_bird, 2.druid_cattle, 3.druid_basic,
        // 4.factory, 10.cas_bird, 11.koeco_bird

        public static Platform getDevicePlatformFromFileName(String file_name, BCProtoDataEntity bcData) {
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                return getDevicePlatformFromBroadCast(bcData);
            } else {
                if (DEVICE_TRANSFER.ISDruidFileName(file_name)) {
                    return getDevicePlatformFromFileName(file_name);
                }
            }
            return null;
        }

        public static Platform getDevicePlatformFromBroadCast(String ble_name, BCProtoDataEntity bcData) {
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                return getDevicePlatformFromBroadCast(bcData);
            } else {
                if (DEVICE_VALID.ISDeviceMini_Other(ble_name)) {
                    return getDevicePlatformFromBleName(ble_name);
                }
            }
            return null;
        }

        public static Platform getDevicePlatformFromBroadCast(BCProtoDataEntity bcData) {
            int sn_index_0 = -1;
            if (DEVICE_VALID.validDruidDeviceFromBroadCast(bcData)) {
                if (DEVICE_VALID.validV5DruidDevice(bcData)) {
                    return getDevicePlatformFromBroadCastV5(bcData);
                }
                byte[] sn = null;
                if (bcData.v2Datas.size() > 0) {
                    if (bcData.v2Datas.get(0) != null) {
                        sn = bcData.v2Datas.get(0).sn;
                    }
                }
                if (bcData.v3Data != null) {
                    sn = bcData.v3Data.sn;
                }
                if (sn != null) {
                    if (sn.length > 0) {
                        sn_index_0 = sn[0] >> 4;//高4位 代表平台
                        if (bcData.v3Data != null) {
                            if (bcData.v3Data.header.has_platform == 1) {
                                sn_index_0 = bcData.v3Data.platform;
                            }
                        }
                    }
                }
            }
            if (sn_index_0 != -1) {
                if (sn_index_0 == 1) {//ecotopia
                    return new Platform(1, 1);
                }
                if (sn_index_0 == 2) {//cattle
                    return new Platform(2, 1);
                }
                if (sn_index_0 == 3) {//basic
                    return new Platform(3, 1);
                }
                if (sn_index_0 == 4) {//Stack-factory
                    return new Platform(0, 1);
                }
                if (sn_index_0 == 11) {//koeco
                    return new Platform(1, 2);
                }
            }
            return null;
        }

        //V5平台获取
        private static Platform getDevicePlatformFromBroadCastV5(BCProtoDataEntity bcData) {
            int platTypeId = getDevicePlatTypeIDFromBroadCastV5(bcData);
            int customId = getDeviceCustomIDFromBroadCastV5(bcData);
            return new Platform(platTypeId, customId);
        }

        private static int getDevicePlatTypeIDFromBroadCastV5(BCProtoDataEntity bcData) {
            int platTypeId = -1;
            if (DEVICE_VALID.validV5DruidDevice(bcData)) {
                if (bcData.v5Data != null) {
                    platTypeId = bcData.v5Data.platform;
                }
            }
            return platTypeId;
        }

        private static int getDeviceCustomIDFromBroadCastV5(BCProtoDataEntity bcData) {
            int customId = -1;
            if (DEVICE_VALID.validV5DruidDevice(bcData)) {
                if (bcData.v5Data != null) {
                    customId = bcData.v5Data.customer_id;
                }
            }
            return customId;
        }

        public static Platform getDevicePlatformFromBleName(String ble_name) {
            if (!TextUtils.isEmpty(ble_name)) {
                if (DEVICE_VALID.ISDeviceMini_Other(ble_name)) {//DbL1aS
                    String platformStr = ble_name.substring(1, 2);
                    if (platformStr.equals(DEVICE_BIRD_RELEASE)) {
                        return new Platform(1, 1);
                    }
                    if (platformStr.equals(DEVICE_BIRD_DEBUG)) {
                        return new Platform(1, 4);
                    }
                    if (platformStr.equals(DEVICE_CATTLE_RELEASE)) {
                        return new Platform(2, 1);
                    }
                    if (platformStr.equals(DEVICE_CATTLE_DEBUG)) {
                        return new Platform(2, 4);
                    }
                    if (platformStr.equals(DEVICE_BASIC_RELEASE)) {
                        return new Platform(3, 1);
                    }
                    if (platformStr.equals(DEVICE_BASIC_DEBUG)) {
                        return new Platform(3, 4);
                    }
                    if (platformStr.equals(DEVICE_LOTEK_RELEASE)) {
                        return new Platform(1, 3);
                    }
                    if (platformStr.equals(DEVICE_STACK_RELEASE)) {
                        return new Platform(0, 1);
                    }
                    if (platformStr.equals(DEVICE_STACK_DEBUG)) {
                        return new Platform(0, 4);
                    }
                }
            }
            return null;
        }

        //
        public static Platform getDevicePlatformFromFileName(String file_name) {
            if (DEVICE_TRANSFER.ISDruidFileNameV1(file_name)) {
                return getDevicePlatformFromFileNameV1(file_name);
            }
            if (DEVICE_TRANSFER.ISDruidFileNameV2(file_name)) {
                return getDevicePlatformFromFileNameV2(file_name);
            }
            if (DEVICE_TRANSFER.ISDruidFileNameV3(file_name)) {
                return getDevicePlatformFromFileNameV3(file_name);
            }
            if (file_name.equals(DEFAULT_BLE_NAME)) {
                //兼容
            }
            return null;
        }

        //收取数据v1.0[DbL1aS]
        private static Platform getDevicePlatformFromFileNameV1(String file_name) {
            return getDevicePlatformFromBleName(file_name);
        }

        //收取数据v2.0[ac8a7f_1_i]
        private static Platform getDevicePlatformFromFileNameV2(String file_name) {
            if (!TextUtils.isEmpty(file_name)) {
                if (DEVICE_TRANSFER.ISDruidFileNameV2(file_name)) {
                    String[] ble_name_device = file_name.split("_");
                    int platform = Integer.parseInt(ble_name_device[1]);
                    if (platform == 1) {//ecotopia
                        return new Platform(1, 1);
                    }
                    if (platform == 2) {//cattle
                        return new Platform(2, 1);
                    }
                    if (platform == 3) {//basic
                        return new Platform(3, 1);
                    }
                    if (platform == 4) {//Stack-factory
                        return new Platform(0, 1);
                    }
                    if (platform == 11) {//koeco
                        return new Platform(1, 2);
                    }
                }
            }
            return null;
        }

        //新版本-收取数据v3.0[ac8a7f_1_1_i]
        private static Platform getDevicePlatformFromFileNameV3(String file_name) {
            if (!TextUtils.isEmpty(file_name)) {
                if (DEVICE_TRANSFER.ISDruidFileNameV3(file_name)) {
                    String[] ble_name_device = file_name.split("_");
                    int platTypeId = Integer.parseInt(ble_name_device[1]);
                    int customId = Integer.parseInt(ble_name_device[2]);
                    return new Platform(platTypeId, customId);
                }
            }
            return null;
        }

        //老版本的 platform
        public static Platform getDevicePlatformFromQuest(int platform) {
            if (platform == 1) {//ecotopia
                return new Platform(1, 1);
            }
            if (platform == 2) {//cattle
                return new Platform(2, 1);
            }
            if (platform == 3) {//basic
                return new Platform(3, 1);
            }
            if (platform == 4) {//Stack-factory
                return new Platform(0, 1);
            }
            if (platform == 11) {//koeco
                return new Platform(1, 2);
            }
            return null;
        }

    }

    public static final class UUID {
        public static final class FLEX_3G {
            public static final java.util.UUID mServiceUUID =
                    java.util.UUID.fromString("0000ffcc-0000-1000-8000-00805f9b34fb");
            public static final java.util.UUID mCtrlCharacteristicUUID_Write =
                    java.util.UUID.fromString("0000ffcd-0000-1000-8000-00805f9b34fb");
            public static final java.util.UUID mCtrlCharacteristicUUID_Notify =
                    java.util.UUID.fromString("0000ffcd-0000-1000-8000-00805f9b34fb");
            public static final java.util.UUID mCccUUID =
                    java.util.UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        }

        public static final class MINI {
            public static final java.util.UUID mServiceUUID =
                    java.util.UUID.fromString("01040001-b5a3-f393-e0a9-e50e24dcca9e");
            public static final java.util.UUID
                    mCtrlCharacteristicUUID_Write =
                    java.util.UUID.fromString("01040002-b5a3-f393-e0a9-e50e24dcca9e");
            public static final java.util.UUID
                    mCtrlCharacteristicUUID_Notify =
                    java.util.UUID.fromString("01040003-b5a3-f393-e0a9-e50e24dcca9e");
            public static final java.util.UUID mCccUUID =
                    java.util.UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        }
    }
}
