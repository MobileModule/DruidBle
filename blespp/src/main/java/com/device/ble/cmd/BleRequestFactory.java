package com.device.ble.cmd;


public class BleRequestFactory {
    private static int seq = 0;

    public static void setSeq(int seq) {
        BleRequestFactory.seq = seq;
    }

    public static int getSeq() {
        seq++;
        if (seq == 0x80) {
            seq = 1;
        }
        return seq;
    }

    public static BleRequest generalCommand_PkgCmd(int seq, int protoCmd, byte[] data) {
        return new BleRequest(seq, BleRequest.CMD_1, protoCmd, data);
    }

    public static BleRequest generalCommand_Seq(int pkgCmd, int protoCmd, byte[] data) {
        return new BleRequest(getSeq(), pkgCmd, protoCmd, data);
    }

    public static BleRequest generalCommand_ALL(int seq, int pkgCmd, int protoCmd, byte[] data) {
        return new BleRequest(seq, pkgCmd, protoCmd, data);
    }

}
