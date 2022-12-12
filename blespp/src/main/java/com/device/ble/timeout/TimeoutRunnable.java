package com.device.ble.timeout;

import java.util.TimerTask;

public class TimeoutRunnable extends TimerTask {
    public interface TimeoutListener {
        void timeout();
    }

    private TimeoutListener listener;

    public TimeoutRunnable(TimeoutListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        if(listener!=null){
            listener.timeout();
        }
    }
}
