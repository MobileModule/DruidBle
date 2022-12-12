package com.druid.bleact.model;

import java.io.Serializable;

/**
 * Created by druid on 2020/3/2.
 */

public class IdentityModel implements Serializable {
    public int MsgToken = 0; // 鉴权Token: 由DeviceID和16位随机数使用CRC16_CCITT生成, 计算初值是0xFFFF
    public int MsgIndex = 0; // 消息ID
    public String DeviceID = ""; // 设备ID: 设备发送时必填, 服务器发送时可以不填
    public int RspCode = 0 ; // 返回结果状态: 请求包不填, 回应包必填, 0表示执行成功
}
