package com.druid.bleact.parse;

public class MacParse {
    public static String getRealMac(String mac) {
        mac = mac.toUpperCase();
        String real_mac = "";
        try {
            if (mac.contains("\r")) {
                if (mac.contains(":")) {
                    if (mac.length() >= 17) {
                        mac = mac.substring(0, 17);
                    }
                } else {
                    if (mac.length() >= 12) {
                        mac = mac.substring(0, 12);
                    }
                }
            }
            if (mac.length() == 12) {
                mac = mac.substring(0, 2) + ":" + mac.substring(2, 4) + ":" +
                        mac.substring(4, 6) + ":" + mac.substring(6, 8) + ":"
                        + mac.substring(8, 10) + ":" + mac.substring(10, 12);
            }
            if (mac.length() == 17) {
                real_mac = mac.toLowerCase();
            }
        } catch (Exception ex) {
            real_mac = "";
        }
        return real_mac;
    }
}
