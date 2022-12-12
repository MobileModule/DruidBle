package com.android.log.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.io.Serializable;

@Entity
public class LocalSaveLogEntity implements Serializable {
    private static final long serialVersionUID = 1131316232774213217L;
    @Id(autoincrement = true)
    public Long id;
    public String mac = "";
    public String content = "";
    public long timestamp=0;
    @Keep
    public LocalSaveLogEntity(Long id, String mac, String content, long timestamp) {
        this.id = id;
        this.mac = mac;
        this.content = content;
        this.timestamp = timestamp;
    }

    @Generated(hash = 131393136)
    public LocalSaveLogEntity() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
