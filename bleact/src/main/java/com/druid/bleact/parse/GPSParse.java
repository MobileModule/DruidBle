package com.druid.bleact.parse;

import com.druid.bleact.model.GPSListModel;
import com.druid.bleact.model.GPSModel;
import com.druid.bleact.model.IdentityModel;
import com.druid.bleact.model.SatelliteModel;

import protocol_v1.V1GPS;
import protocol_v1.V1IdentityMsg;
import protocol_v1.V1SimpleRsp;

/**
 * Created by druid on 2018/11/5.
 */

public class GPSParse {

    public static GPSListModel getGPSListModel(V1GPS.GPSReq gpsReq) {
        GPSListModel gpsListModel = new GPSListModel();
        V1IdentityMsg.IdentityMsg identityMsg = gpsReq.getIden();
        //
        IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
        //
        gpsListModel.identityModel = identityModel;
        //
        if (gpsReq.hasSMS()) {
            gpsListModel.SMS = gpsReq.getSMS();
        }
        //
        for (int i = 0; i < gpsReq.getGPSInfoCount(); i++) {
            GPSModel gpsModel = new GPSModel();
            V1GPS.GPS gps = gpsReq.getGPSInfo(i);
            if (gps.hasTimestamp()) {
                gpsModel.Timestamp = gps.getTimestamp();
            }
            if (gps.hasLatitude()) {
                gpsModel.Latitude = gps.getLatitude();
            }
            if (gps.hasLongitude()) {
                gpsModel.Longitude = gps.getLongitude();
            }
            if (gps.hasAltitude()) {
                gpsModel.Altitude = gps.getAltitude();
            }
            if (gps.hasQuality()) {
                gpsModel.Quality = gps.getQuality();
            }
            if (gps.hasMode()) {
                gpsModel.Mode = gps.getMode();
            }
            if (gps.hasPDOP()) {
                gpsModel.PDOP = gps.getPDOP();
            }
            if (gps.hasHDOP()) {
                gpsModel.HDOP = gps.getHDOP();
            }
            if (gps.hasVDOP()) {
                gpsModel.VDOP = gps.getVDOP();
            }
            if (gps.hasSatellitesUsed()) {
                gpsModel.SatellitesUsed = gps.getSatellitesUsed();
            }
            if (gps.hasSatellitesInView()) {
                gpsModel.SatellitesInView = gps.getSatellitesInView();
            }
            if (gps.hasSpeed()) {
                gpsModel.Speed = gps.getSpeed();
            }
            if (gps.hasCourse()) {
                gpsModel.Course = gps.getCourse();
            }
            if (gps.hasHACC()) {
                gpsModel.HACC = gps.getHACC();
            }
            if (gps.hasVACC()) {
                gpsModel.VACC = gps.getVACC();
            }
            if (gps.hasFixTime()) {
                gpsModel.FixTime = gps.getFixTime();
            }
            for (int j = 0; j < gps.getSatellitesCount(); j++) {
                SatelliteModel satelliteModel = new SatelliteModel();
                V1GPS.Satellite satellite = gps.getSatellites(j);
                satelliteModel.Id = satellite.getId();
                if (satellite.hasElevation()) {
                    satelliteModel.Elevation = satellite.getElevation();
                }
                if (satellite.hasAzimuth()) {
                    satelliteModel.Azimuth = satellite.getAzimuth();
                }
                if (satellite.hasSNR()) {
                    satelliteModel.SNR = satellite.getSNR();
                }
                if (satellite.hasUsed()) {
                    satelliteModel.Used = satellite.getUsed();
                }
                if (satellite.hasType()) {
                    satelliteModel.Type = satellite.getType();
                }
                gpsModel.Satellites.add(satelliteModel);
            }

            gpsListModel.GPSInfo.add(gpsModel);
        }
        return gpsListModel;
    }

    public static byte[] getGPSRspModel(IdentityModel model) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }

    public static byte[] getGPSRspModelCustom(IdentityModel model, int rspCode) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        V1IdentityMsg.IdentityMsg.Builder identityBuilder = V1IdentityMsg.IdentityMsg.newBuilder();
        identityBuilder.setMsgToken(model.MsgToken);
        identityBuilder.setMsgIndex(model.MsgIndex);
        identityBuilder.setDeviceID(model.DeviceID);
        identityBuilder.setRspCode(rspCode);
        builder.setIden(identityBuilder.build());
        return builder.build().toByteArray();
    }
}
