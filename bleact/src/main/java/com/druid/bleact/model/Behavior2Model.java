package com.druid.bleact.model;

import java.io.Serializable;

/**
 * Created by LeaAnder on 2018/4/11.
 */

public class Behavior2Model implements Serializable {
    public int Timestamp = 0;
    public int ODBAX = 0;    // odbax value in 0.0001g
    public int ODBAY = 0;    // odbay value in 0.0001g
    public int ODBAZ = 0;        // odbaz value in 0.0001g
    public int MeandlX = 0;        // mean(|X(i) - X(i-1)|) in 0.0001g
    public int MeandlY = 0;       // mean(|Y(i) - Y(i-1)|) in 0.0001g
    public int MeandlZ = 0;         // mean(|Z(i) - Z(i-1)|) in 0.0001g
    public int ODBA = 0;           // odba value in 0.0001g
    public int Steps = 0;            // number of steps
}
