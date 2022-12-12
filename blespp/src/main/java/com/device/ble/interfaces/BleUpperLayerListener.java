package com.device.ble.interfaces;


import com.device.ble.core.BleConnParameter;
import com.device.ble.entity.CMDResp;
import com.device.ble.utils.DruidBleFunction;

/**
 * Created by LeaAnder on 2018/3/13.
 */

public interface BleUpperLayerListener {
    void initCmdFailed(int code, BleConnParameter connParameter, DruidBleFunction function);//CMD命令初始化失败

    /**
     * 0x01 蓝牙开始连接
     *
     * @param code
     */
    void initCmding(int code, DruidBleFunction function);

    /**
     * 蓝牙连接成功
     *
     * @param connParameter
     * @param function
     */
    void initCmdSuccess(BleConnParameter connParameter, DruidBleFunction function);

    /**
     * 设备重连接
     *
     * @param all
     * @param cur
     */
    void reInitCmd(int all, int cur, DruidBleFunction function);

    /**
     * 设备不支持某项功能
     *
     * @param function
     */
    void unsupportFunction(DruidBleFunction function);


    void respondCmd(CMDResp cmdResp);//设备回应CMD命令参数
}
