package com.device.ble.broadcast.v1;
import com.device.ble.utils.DataType;

public class BCV1Utils {
    /**
     * v1 包头
     */
    public static BCV1HeaderEntity getV1Header(byte[] manufacturerDatas) {
        BCV1HeaderEntity v1Header = null;
        try {
            // 小端字节
            if (manufacturerDatas.length > 2) {
                int HEADER = DataType.byteArrayTo16(manufacturerDatas, 0);
                v1Header = new BCV1HeaderEntity();
                // header - version
                v1Header.version = (HEADER & 0X0003); // 2
                // header- reserved
                v1Header.reserved = (HEADER & 0X0004) >> 2;// 3
                // header- has_adv_hash
                v1Header.has_adv_hash = (HEADER & 0X0008) >> 3;// 4
                // header- has_mac
                v1Header.has_mac = (HEADER & 0X0010) >> 4;// 5
                // header- has_fw_version
                v1Header.has_fw_version = (HEADER & 0X0020) >> 5;// 6
                // header- has_cfg_hash
                v1Header.has_cfg_hash = (HEADER & 0X0040) >> 6;// 7
                // header- has_dev_type
                v1Header.has_dev_type = (HEADER & 0X0080) >> 7;// 8
                // header- has_temperature
                v1Header.has_temperature = (HEADER & 0X0100) >> 8;// 9
                // header- has_pressure
                v1Header.has_pressure = (HEADER & 0X0200) >> 9;// 10
                // header- has_voltage
                v1Header.has_voltage = (HEADER & 0X0400) >> 10;// 11
                // header- has_power
                v1Header.has_power = (HEADER & 0X0800) >> 11;// 12
                // header- has_index
                v1Header.has_index = (HEADER & 0X1000) >> 12;// 13
                // header- has_status
                v1Header.has_status = (HEADER & 0X2000) >> 13;// 14
                // header- has_behavior
                v1Header.has_behavior = (HEADER & 0X4000) >> 14;// 16
                // header- has_old_behavior
                v1Header.has_old_behavior = (HEADER & 0X8000) >> 15;// 13
                v1Header.version = (HEADER & 0X0003);
                // header- reserved
                v1Header.reserved = (HEADER & 0X0004) >> 2;
                // header- has_adv_hash
                v1Header.has_adv_hash = (HEADER & 0X0008) >> 3;
                // header- has_mac
                v1Header.has_mac = (HEADER & 0X0010) >> 4;
                // header- has_fw_version
                v1Header.has_fw_version = (HEADER & 0X0020) >> 5;
                // header- has_cfg_hash
                v1Header.has_cfg_hash = (HEADER & 0X0040) >> 6;
                // header- has_dev_type
                v1Header.has_dev_type = (HEADER & 0X0080) >> 7;
                // header- has_temperature
                v1Header.has_temperature = (HEADER & 0X0100) >> 8;
                // header- has_pressure
                v1Header.has_pressure = (HEADER & 0X0200) >> 9;
                // header- has_voltage
                v1Header.has_voltage = (HEADER & 0X0400) >> 10;
                // header- has_power
                v1Header.has_power = (HEADER & 0X0800) >> 11;
                // header- has_index
                v1Header.has_index = (HEADER & 0X1000) >> 12;
                // header- has_status
                v1Header.has_status = (HEADER & 0X2000) >> 13;
                // header- has_behavior
                v1Header.has_behavior = (HEADER & 0X4000) >> 14;
                // header- has_old_behavior
                v1Header.has_old_behavior = (HEADER & 0X8000) >> 15;
            }
        } catch (Exception e) {

        }
        return v1Header;
    }

    /**
     * v1 包长度 单位 byte 数据总长度28
     */
    public static int getV1PackageLength(byte[] manufacturerDatas) {
        int length = 0;
        BCV1HeaderEntity v1Header = getV1Header(manufacturerDatas);
        if (v1Header != null) {
            if (v1Header.has_cfg_hash == 0 && v1Header.has_adv_hash == 0) {
                length += 0;
            }else {
                if (v1Header.has_cfg_hash == 1) {
                    length += 2;
                } else {
                    length += 1;
                }
            }
            if (v1Header.has_mac == 1) {
                length += 2;
            }
            if (v1Header.has_fw_version == 1) {
                length += 2;
            }
            if (v1Header.has_dev_type == 1) {
                length += 2;
            }
            if (v1Header.has_temperature == 1) {
                length += 1;
            }
            if (v1Header.has_pressure == 1) {
                length += 2;
            }
            if (v1Header.has_voltage == 1) {
                length += 2;
            }
            if (v1Header.has_power == 1) {
                length += 1;
            }
            if (v1Header.has_index == 1) {
                length += 1;
            }
            if (v1Header.has_status == 1) {
                length += 1;
            }
            if (v1Header.has_behavior == 1) {
                length += 6;
            }
            if (v1Header.has_old_behavior == 1) {
                length += 6;
            }
        }
        return length;
    }

    /**
     * v1 数据域
     */
    public static BCV1DataEntity getV1Data(byte[] manufacturerDatas) {
        BCV1DataEntity v1Data = null;
        try {
            int length = getV1PackageLength(manufacturerDatas);
            if (length != 0) {
                int allLength = manufacturerDatas.length;
                if ((length + 2) == allLength) {
                    BCV1HeaderEntity v1Header = getV1Header(manufacturerDatas);
                    if (v1Header != null) {
                        v1Data = new BCV1DataEntity();
                        v1Data.header = v1Header;
                        // 有效数据
                        byte[] datas_value = new byte[manufacturerDatas.length - 2];
                        System.arraycopy(manufacturerDatas, 2, datas_value, 0, manufacturerDatas.length - 2);
                        //
                        int position = 0;
                        // mac
                        if (v1Header.has_mac == 1) {
                            v1Data.mac = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                        }
                        // fw_version
                        if (v1Header.has_fw_version == 1) {
                            v1Data.fw_version = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                        }

                        // hash
                        if ((v1Header.has_cfg_hash != 0) || (v1Header.has_adv_hash != 0)) {
                            if (v1Header.has_cfg_hash == 1) {//2 byte
                                int hash = DataType.byteArrayTo16(datas_value, position);
                                if (v1Header.has_adv_hash == 1) {// 两个都有
                                    v1Data.hash_cfg = hash & 0x0FFF;
                                    v1Data.hash_adv = (hash & 0xF000) >> 12;
                                } else {
                                    v1Data.hash_cfg = hash;
                                }
                                position += 2;// 1 byte
                            } else {
                                int hash = DataType.byteArrayTo8(datas_value, position);
                                v1Data.hash_adv = hash;
                                position += 1;// 1 byte
                            }
                        }

                        // device type
                        if (v1Header.has_dev_type == 1) {
                            v1Data.dev_type = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                        }
                        // temperature
                        if (v1Header.has_temperature == 1) {
                            // TODO
                            int temperature = datas_value[position];
                            int type = (temperature & 0x80) >> 7;
                            if (type == 1) {// 高精度
                                temperature = (temperature & 0x7F);
                                v1Data.temperature_type = 1;
                                v1Data.temperature_value = 306 + temperature;
                                v1Data.temperature = v1Data.temperature_value * 10.0f;
                            } else {
                                v1Data.temperature_type = 0;
                                v1Data.temperature_value = (temperature - 41) * 10;
                                v1Data.temperature = v1Data.temperature_value / 10.0f;
                            }
                            position += 1;// 1 byte
                        }
                        // pressure
                        if (v1Header.has_pressure == 1) {
                            v1Data.pressure = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                        }
                        // voltage
                        if (v1Header.has_voltage == 1) {
                            v1Data.voltage = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
//                        Log.i("v0battery-->"+v1Data.voltage);
                        }
                        // power
                        if (v1Header.has_power == 1) {
                            v1Data.power = DataType.byteArrayTo8(datas_value, position);
                            position += 1;// 1 byte
                        }
                        // index
                        if (v1Header.has_index == 1) {
                            // TODO
                            int index = DataType.byteArrayTo8(datas_value, position);
                            v1Data.index_time = index & 0x0F;
                            v1Data.index_data = (index & 0xF0) >> 4;
                            position += 1;// 1 byte
                        }
                        // status
                        if (v1Header.has_status == 1) {
                            // TODO
                            int status = DataType.byteArrayTo8(datas_value, position);
                            v1Data.status_hw_error = status & 0x01;
                            v1Data.status_sw_error = (status & 0x02) >> 1;
                            v1Data.status_reserved = (status & 0x3C) >> 2;
                            v1Data.status_estrus_flag = (status & 0x40) >> 6;
                            v1Data.status_static_flag = (status & 0x80) >> 7;// 0000,0000,1000,000
                            position += 1;// 1 byte
                        }
                        // behavior
                        if (v1Header.has_behavior == 1) {
                            // TODO
                            int behavior = DataType.byteArrayTo16(datas_value, position);
                            v1Data.behavior_version = behavior & 0x0003;
                            v1Data.behavior_eating = (behavior & 0x01FC) >> 2;
                            v1Data.behavior_ruminate = (behavior & 0xFE00) >> 9;
                            position += 2;// 2 byte
                            //
                            v1Data.behavior_odba = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                            //
                            v1Data.behavior_steps = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                        }
                        // old_behavior
                        if (v1Header.has_old_behavior == 1) {
                            // TODO
                            int old_behavior = DataType.byteArrayTo16(datas_value, position);
                            v1Data.old_behavior_version = old_behavior & 0x0003;
                            v1Data.old_behavior_eating = (old_behavior & 0x01FC) >> 2;
                            v1Data.old_behavior_ruminate = (old_behavior & 0xFE00) >> 9;
                            position += 2;// 2 byte
                            //
                            v1Data.old_behavior_odba = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                            //
                            v1Data.old_behavior_steps = DataType.byteArrayTo16(datas_value, position);
                            position += 2;// 2 byte
                        }
                    }

                } else {
//                System.out.println("ble broadcast v1-data length error");
                }
            }
        }catch (Exception ex){

        }
        return v1Data;
    }
}
