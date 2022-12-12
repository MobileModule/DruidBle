package com.device.ble.broadcast.v5;

import com.device.ble.utils.DataType;

public class BCV5Utils {
    /**
     * v5包头
     */
    public static BCV5HeaderEntity getV5Header(byte[] manufacturerDatas) {
        BCV5HeaderEntity v5Header = null;
        try {
            // 小端字节
            if (manufacturerDatas.length > 2) {
                int HEADER = DataType.byteArrayTo16(manufacturerDatas, 0);
                v5Header = new BCV5HeaderEntity();
                v5Header.version = HEADER & 0X001F;
                v5Header.option_data_type = (HEADER & 0X0038) >> 3;
            }
        } catch (Exception e) {

        }
        return v5Header;
    }

    /**
     * v5 包长度 单位 byte
     */
    public static int getV5PackageLength(byte[] manufacturerDatas) {
        int length = 0;
        BCV5HeaderEntity v5Header = getV5Header(manufacturerDatas);
        if (v5Header != null) {
            // SN
            length += 5;
            // platform
            length += 1;
            // customer_id
            length += 1;
            // firmware version
            length += 2;
            // hardware version
            length += 2;
            // voltage / power
            length += 2;
            // hash
            length += 2;
            // device status
            length += 1;
            if (v5Header.option_data_type == 1) {
                length += 7;
            }
        }
        return length;
    }

    /**
     * v5 数据域
     */
    public static BCV5DataEntity getV5Data(byte[] manufacturerDatas) {
        BCV5DataEntity v5Data = null;
        try {
            int length = getV5PackageLength(manufacturerDatas);
            if (length != 0) {
                int allLength = manufacturerDatas.length;
                BCV5HeaderEntity v5Header = getV5Header(manufacturerDatas);
                int header_length = 1;
                // if ((length + header_length) == allLength) {
                int checksum = 0x0A;
                for (int i = 0; i < manufacturerDatas.length - 1; i++) {
                    checksum += manufacturerDatas[i];
                }
                int lastSize = (manufacturerDatas[manufacturerDatas.length - 1] & 0x00FF);
                if ((checksum & 0x00FF) == lastSize) {
                    if (v5Header != null) {
                        v5Data = new BCV5DataEntity();
                        v5Data.header = v5Header;
                        // 有效数据
                        byte[] datas_value = new byte[manufacturerDatas.length - header_length];
                        System.arraycopy(manufacturerDatas, header_length, datas_value, 0,
                                manufacturerDatas.length - header_length);
                        int position = 0;
                        // sn
                        v5Data.sn = new byte[5];
                        System.arraycopy(datas_value, position, v5Data.sn, 0, v5Data.sn.length);
                        position += 5;// 5 byte
                        //platform
                        v5Data.platform = datas_value[position];
                        position += 1;// 1 byte
                        //customer_id
                        v5Data.customer_id = datas_value[position];
                        position += 1;// 1 byte
                        // fw_version
                        v5Data.fw_version =  datas_value[position];
                        position += 1;// 1 byte +1000;
                        //
                        v5Data.hw_version = DataType.byteArrayTo16(datas_value, position);
                        position += 2;// 2 byte
                        // power
                        int value_power = DataType.byteArrayTo16(datas_value, position);
                        v5Data.power_value = (value_power & 0x7FFF);
                        v5Data.power_type = (value_power & 0x8000) >> 15;
                        position += 2;// 2 byte //0 vlaue 剩余电量,1 value 剩余电压 单位 mv
                        // hash
                        int hash = DataType.byteArrayTo16(datas_value, position);
                        v5Data.hash_cfg = hash & 0x0FFF;
                        v5Data.hash_adv = (hash & 0xF000) >> 12;
                        position += 2;// 2 byte
                        // device status
                        int dev_status = datas_value[position];
                        v5Data.dev_status_time = dev_status & 0x0F;
                        v5Data.dev_status_data = (dev_status & 0xB0) >> 4;
                        v5Data.dev_status_active = (dev_status & 0x80) >> 7;
                        position += 1;// 1 byte
                        if (v5Data.header != null) {
                            if (v5Data.header.option_data_type == 1) {
                                // temperature
                                int temperature = DataType.byteArrayTo16(datas_value, position);
                                int value = (temperature & 0x07FF);
                                int type = (temperature & 0x0800) >> 15;
                                v5Data.temperature_type = type;
                                v5Data.temperature_value = value;
                                position += 2;// 1 byte
                                //odba
                                v5Data.odba = DataType.byteArrayTo16(datas_value, position);
                                position += 2;// 2 byte
                            }
                        }
                    }

                } else {
//				System.out.println("ble broadcast v3-data length error");
                }
            }
        } catch (Exception ex) {

        }
        return v5Data;
    }
}
