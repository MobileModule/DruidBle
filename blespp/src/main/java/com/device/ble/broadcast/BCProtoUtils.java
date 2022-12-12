package com.device.ble.broadcast;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.device.ble.broadcast.v0.BCV0DataEntity;
import com.device.ble.broadcast.v0.BCV0Utils;
import com.device.ble.broadcast.v1.BCV1DataEntity;
import com.device.ble.broadcast.v1.BCV1Utils;
import com.device.ble.broadcast.v2.BCV2DataEntity;
import com.device.ble.broadcast.v2.BCV2Utils;
import com.device.ble.broadcast.v3_1.BCV3_1DataEntity;
import com.device.ble.broadcast.v3_1.BCV3_1Utils;
import com.device.ble.broadcast.v5.BCV5DataEntity;
import com.device.ble.broadcast.v5.BCV5Utils;
import com.device.ble.utils.DataType;
import com.device.ble.utils.ScanRecordUtil;

public class BCProtoUtils {
    public static BCProtoDataEntity getBCEntityFromScanData(BluetoothDevice device, ScanRecordUtil scanRecordUtil) {
        return getBCEntity(scanRecordUtil);
    }

    public static BCProtoDataEntity getBCEntity(ScanRecordUtil scanRecordUtil) {
        BCProtoDataEntity bcData = new BCProtoDataEntity();
        if (scanRecordUtil.getManufacturerSpecificData() == null) {
            return bcData;
        }
        if (scanRecordUtil.getManufacturerSpecificData().size() == 0) {
            return bcData;
        }
        int scanResultSize = scanRecordUtil.getManufacturerSpecificData().size();
        for (int i = 0; i < scanResultSize; i++) {
            byte[] manufacturerDatas = scanRecordUtil.getManufacturerSpecificData(i);
            BCType bcType = getVersionType(manufacturerDatas);
            if (bcType == BCType.v0) {
                BCV0DataEntity v0Data = BCV0Utils.getV0Data(manufacturerDatas);
                if (v0Data != null) {
                    bcData = new BCProtoDataEntity();
                    bcData.type = bcType;
                    bcData.v0Data = v0Data;
                    break;
                }
            }
            if (bcType == BCType.v1) {
                BCV1DataEntity v1Data = BCV1Utils.getV1Data(manufacturerDatas);
                if (v1Data != null) {
                    bcData = new BCProtoDataEntity();
                    bcData.type = bcType;
                    bcData.v1Data = v1Data;
                    break;
                }
            }
            if (bcType == BCType.v2) {
                BCV2DataEntity v2Data = BCV2Utils.getV2Data(manufacturerDatas);
                if (v2Data != null) {
                    bcData.type = bcType;
                    bcData.v2Datas.add(v2Data);
                }
            }
            if (bcType == BCType.v3) {
                BCV3_1DataEntity v3Data = BCV3_1Utils.getV3Data(manufacturerDatas);
                if (v3Data != null) {
                    bcData.type = bcType;
                    bcData.v3Data = v3Data;
                    break;
                }
            }
            if (bcType == BCType.v5) {
                BCV5DataEntity v5Data = BCV5Utils.getV5Data(manufacturerDatas);
                if (v5Data != null) {
                    bcData.type = bcType;
                    bcData.v5Data = v5Data;
                    break;
                }
            }

        }
        return bcData;
    }

    /**
     * 协议版本
     */
    public static BCType getVersionType(byte[] manufacturerDatas) {
        BCType bcType = BCType.unkown;
        if (manufacturerDatas != null) {
            if (manufacturerDatas.length > 2) {
                int version = DataType.byteArrayTo16(manufacturerDatas, 0);
                version = version & 0x03;
                if (version == 0) {
                    bcType = BCType.v1;
                }
                if (version == 3) {
                    version= DataType.byteArrayTo16(manufacturerDatas, 0);
                    version=version&0x07;
                    if(version==0x07) {
                        bcType = BCType.v0;
                    }
                }
                if (version == 1) {
                    bcType = BCType.v2;
                }
                if (version == 2) {
                    bcType = BCType.v3;
                }
                if (bcType == BCType.unkown) {
                    version = version & 0x1F;
                    if (version == 0x03) {
                        bcType = BCType.v5;
                    }
                }
            }
        }
        return bcType;
    }

    public static void testBcData(ScanRecordUtil scanRecordUtil) {
        BCProtoDataEntity bcData = new BCProtoDataEntity();
        if (scanRecordUtil.getManufacturerSpecificData() == null) {
            return;
        }
        if (scanRecordUtil.getManufacturerSpecificData().size() == 0) {
            return;
        }
        int scanResultSize = scanRecordUtil.getManufacturerSpecificData().size();
        for (int i = 0; i < scanResultSize; i++) {
            byte[] manufacturerDatas = scanRecordUtil.getManufacturerSpecificData(i);
            BCType bcType = BCType.unkown;
            if (manufacturerDatas != null) {
                if (manufacturerDatas.length > 2) {
                    int version = DataType.byteArrayTo16(manufacturerDatas, 0);
                    version = version & 0x03;
                    if (version == 0) {
                        bcType = BCType.v1;
                    }
                    if (version == 3) {
                        version= DataType.byteArrayTo16(manufacturerDatas, 0);
                        version=version&0x07;
                        if(version==0x07) {
                            bcType = BCType.v0;
                        }
                    }
                    if (version == 1) {
                        bcType = BCType.v2;
                    }
                    if (version == 2) {
                        bcType = BCType.v3;
                    }
                    if (bcType == BCType.unkown) {
                        version = version & 0x1F;
                        if (version == 0x03) {
                            bcType = BCType.v5;
                        }
                    }
                }
            }
            if (bcType == BCType.v0) {
                BCV0DataEntity v0Data = BCV0Utils.getV0Data(manufacturerDatas);
                if (v0Data != null) {
                    bcData = new BCProtoDataEntity();
                    bcData.type = bcType;
                    bcData.v0Data = v0Data;
                    break;
                }
            }
            if (bcType == BCType.v1) {
                BCV1DataEntity v1Data = BCV1Utils.getV1Data(manufacturerDatas);
                if (v1Data != null) {
                    bcData = new BCProtoDataEntity();
                    bcData.type = bcType;
                    bcData.v1Data = v1Data;
                    break;
                }
            }
            if (bcType == BCType.v2) {
                BCV2DataEntity v2Data = BCV2Utils.getV2Data(manufacturerDatas);
                if (v2Data != null) {
                    bcData.type = bcType;
                    bcData.v2Datas.add(v2Data);
                }
            }
            if (bcType == BCType.v3) {
                BCV3_1DataEntity v3Data = BCV3_1Utils.getV3Data(manufacturerDatas);
                if (v3Data != null) {
                    bcData.type = bcType;
                    bcData.v3Data = v3Data;
                    break;
                }
            }
            if (bcType == BCType.v5) {
                BCV5DataEntity v5Data = BCV5Utils.getV5Data(manufacturerDatas);
                if (v5Data != null) {
                    bcData.type = bcType;
                    bcData.v5Data = v5Data;
                    Log.i("V5_BC",v5Data.printLog());
                    break;
                }
            }

        }
        return;
    }
}
