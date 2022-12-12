package com.druid.ota.g2;

import java.io.Serializable;

/**
 * 项目名称：pom
 * 类描述：
 * 创建人：LeaAnder
 * 创建时间：2016/10/14 11:47
 * 修改人：LeaAnder
 * 修改时间：2016/10/14 11:47
 * 修改备注：
 */
public class OtaBean implements Serializable{
    public int MsgType;
    public int MsgLen;
    public byte[] datas;
}
