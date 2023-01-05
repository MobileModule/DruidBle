package com.druid.bleact.parse;

import com.druid.bleact.model.IdentityModel;
import com.druid.bleact.model.Origin22ListModel;
import com.druid.bleact.model.Origin22Model;
import com.druid.bleact.model.Origin2ListModel;
import com.druid.bleact.model.Origin2Model;
import com.druid.ota.cmd.DataType;

import protocol_v1.V1IdentityMsg;
import protocol_v1.V1Origin2;
import protocol_v1.V1SimpleRsp;

public final class Origin2Parse {

    public static final class Origin2 {
        public static Origin2ListModel getOrigin2ReqModel(V1Origin2.Origin2Req origin2Req) {
            Origin2ListModel origin2ListModel = new Origin2ListModel();
            V1IdentityMsg.IdentityMsg identityMsg = origin2Req.getIden();
            //
            IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
            //
            origin2ListModel.identityModel = identityModel;
            //
            //
            for (int i = 0; i < origin2Req.getOrigin2InfoCount(); i++) {
                Origin2Model origin2Model = new Origin2Model();
                V1Origin2.Origin2 origin2 = origin2Req.getOrigin2Info(i);

                origin2Model.Timestamp = origin2.getTimestamp();
                origin2Model.StartTick = origin2.getStartTick();
                origin2Model.EndTick = origin2.getEndTick();
                origin2Model.SamplingFreq = origin2.getSamplingFreq();
                origin2Model.X = DataType.byteArrayTo8(origin2.getX().toByteArray(), 0);
                origin2Model.Y = DataType.byteArrayTo8(origin2.getZ().toByteArray(), 0);
                origin2Model.Z = DataType.byteArrayTo8(origin2.getZ().toByteArray(), 0);

                origin2ListModel.Origin2Info.add(origin2Model);
            }
            return origin2ListModel;
        }
    }

    public static final class Origin22 {
        public static Origin22ListModel getOrigin22ReqModel(V1Origin2.Origin2Req origin2Req) {
            Origin22ListModel origin22ListModel = new Origin22ListModel();
            V1IdentityMsg.IdentityMsg identityMsg = origin2Req.getIden();
            //
            IdentityModel identityModel = IdentifyModelParse.getIdentifyModel(identityMsg);
            //
            origin22ListModel.identityModel = identityModel;
            //
            for (int i = 0; i < origin2Req.getOrigin22InfoCount(); i++) {
                Origin22Model origin22Model = new Origin22Model();
                V1Origin2.Origin2 origin2 = origin2Req.getOrigin2Info(i);

                origin22Model.Timestamp = origin2.getTimestamp();
//                origin22Model.StartTick = origin2.getStartTick();
//                origin22Model.EndTick = origin2.getEndTick();
                origin22Model.SamplingFreq = origin2.getSamplingFreq();

                origin22Model.X = DataType.byteArrayTo8(origin2.getX().toByteArray(), 0);
                origin22Model.Y = DataType.byteArrayTo8(origin2.getZ().toByteArray(), 0);
                origin22Model.Z = DataType.byteArrayTo8(origin2.getZ().toByteArray(), 0);

                origin22ListModel.Origin22Info.add(origin22Model);
            }
            return origin22ListModel;
        }
    }

    public static byte[] getOrigin2Simple(V1Origin2.Origin2Req origin2Req) {
        V1SimpleRsp.SimpleRsp.Builder builder = V1SimpleRsp.SimpleRsp.newBuilder();
        builder.setIden(origin2Req.getIden());
        return builder.build().toByteArray();
    }

}
