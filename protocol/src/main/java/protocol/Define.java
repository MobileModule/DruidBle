// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Define.proto

package protocol;

public final class Define {
  private Define() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code protocol.HeaderType}
   */
  public enum HeaderType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>TypeRegisterReq = 1;</code>
     */
    TypeRegisterReq(1),
    /**
     * <code>TypeRegisterRsp = 2;</code>
     */
    TypeRegisterRsp(2),
    /**
     * <code>TypeGPSReq = 3;</code>
     */
    TypeGPSReq(3),
    /**
     * <code>TypeGPSRsp = 4;</code>
     */
    TypeGPSRsp(4),
    /**
     * <code>TypeBehaviorReq = 5;</code>
     */
    TypeBehaviorReq(5),
    /**
     * <code>TypeBehaviorRsp = 6;</code>
     */
    TypeBehaviorRsp(6),
    /**
     * <code>TypeDownloadReq = 7;</code>
     */
    TypeDownloadReq(7),
    /**
     * <code>TypeDownloadRsp = 8;</code>
     */
    TypeDownloadRsp(8),
    /**
     * <code>TypeStatusReq = 9;</code>
     */
    TypeStatusReq(9),
    /**
     * <code>TypeStatusRsp = 10;</code>
     */
    TypeStatusRsp(10),
    /**
     * <code>TypeSettingReq = 11;</code>
     */
    TypeSettingReq(11),
    /**
     * <code>TypeSettingRsp = 12;</code>
     */
    TypeSettingRsp(12),
    /**
     * <code>TypePingReq = 13;</code>
     */
    TypePingReq(13),
    /**
     * <code>TypePingRsp = 14;</code>
     */
    TypePingRsp(14),
    /**
     * <code>TypeAppReq = 15;</code>
     */
    TypeAppReq(15),
    /**
     * <code>TypeAppRsp = 16;</code>
     */
    TypeAppRsp(16),
    /**
     * <code>TypeTransferSettingReq = 17;</code>
     */
    TypeTransferSettingReq(17),
    /**
     * <code>TypeTransferSettingRsp = 18;</code>
     */
    TypeTransferSettingRsp(18),
    /**
     * <code>TypePigSettingReq = 19;</code>
     */
    TypePigSettingReq(19),
    /**
     * <code>TypePigSettingRsp = 20;</code>
     */
    TypePigSettingRsp(20),
    /**
     * <code>TypeEnvSettingReq = 21;</code>
     */
    TypeEnvSettingReq(21),
    /**
     * <code>TypeEnvSettingRsp = 22;</code>
     */
    TypeEnvSettingRsp(22),
    /**
     * <code>TypePigEarReq = 23;</code>
     */
    TypePigEarReq(23),
    /**
     * <code>TypePigEarRsp = 24;</code>
     */
    TypePigEarRsp(24),
    /**
     * <code>TypeEnvironmentReq = 25;</code>
     */
    TypeEnvironmentReq(25),
    /**
     * <code>TypeEnvironmentRsp = 26;</code>
     */
    TypeEnvironmentRsp(26),
    /**
     * <code>TypeTransferReq = 27;</code>
     */
    TypeTransferReq(27),
    /**
     * <code>TypeTransferRsp = 28;</code>
     */
    TypeTransferRsp(28),
    /**
     * <pre>
     * gps v2 request with sint32, it had many value &lt; 0
     * </pre>
     *
     * <code>TypeGPS2Req = 29;</code>
     */
    TypeGPS2Req(29),
    /**
     * <code>TypeSensorReq = 31;</code>
     */
    TypeSensorReq(31),
    /**
     * <code>TypeSensorRsp = 32;</code>
     */
    TypeSensorRsp(32),
    /**
     * <code>TypeCellularReq = 33;</code>
     */
    TypeCellularReq(33),
    /**
     * <code>TypeCellularRsp = 34;</code>
     */
    TypeCellularRsp(34),
    /**
     * <code>TypeDebugReq = 10001;</code>
     */
    TypeDebugReq(10001),
    /**
     * <code>TypeDebugRsp = 10002;</code>
     */
    TypeDebugRsp(10002),
    /**
     * <code>TypeBehavior2Req = 35;</code>
     */
    TypeBehavior2Req(35),
    /**
     * <code>TypeBehavior2Rsp = 36;</code>
     */
    TypeBehavior2Rsp(36),
    ;

    /**
     * <code>TypeRegisterReq = 1;</code>
     */
    public static final int TypeRegisterReq_VALUE = 1;
    /**
     * <code>TypeRegisterRsp = 2;</code>
     */
    public static final int TypeRegisterRsp_VALUE = 2;
    /**
     * <code>TypeGPSReq = 3;</code>
     */
    public static final int TypeGPSReq_VALUE = 3;
    /**
     * <code>TypeGPSRsp = 4;</code>
     */
    public static final int TypeGPSRsp_VALUE = 4;
    /**
     * <code>TypeBehaviorReq = 5;</code>
     */
    public static final int TypeBehaviorReq_VALUE = 5;
    /**
     * <code>TypeBehaviorRsp = 6;</code>
     */
    public static final int TypeBehaviorRsp_VALUE = 6;
    /**
     * <code>TypeDownloadReq = 7;</code>
     */
    public static final int TypeDownloadReq_VALUE = 7;
    /**
     * <code>TypeDownloadRsp = 8;</code>
     */
    public static final int TypeDownloadRsp_VALUE = 8;
    /**
     * <code>TypeStatusReq = 9;</code>
     */
    public static final int TypeStatusReq_VALUE = 9;
    /**
     * <code>TypeStatusRsp = 10;</code>
     */
    public static final int TypeStatusRsp_VALUE = 10;
    /**
     * <code>TypeSettingReq = 11;</code>
     */
    public static final int TypeSettingReq_VALUE = 11;
    /**
     * <code>TypeSettingRsp = 12;</code>
     */
    public static final int TypeSettingRsp_VALUE = 12;
    /**
     * <code>TypePingReq = 13;</code>
     */
    public static final int TypePingReq_VALUE = 13;
    /**
     * <code>TypePingRsp = 14;</code>
     */
    public static final int TypePingRsp_VALUE = 14;
    /**
     * <code>TypeAppReq = 15;</code>
     */
    public static final int TypeAppReq_VALUE = 15;
    /**
     * <code>TypeAppRsp = 16;</code>
     */
    public static final int TypeAppRsp_VALUE = 16;
    /**
     * <code>TypeTransferSettingReq = 17;</code>
     */
    public static final int TypeTransferSettingReq_VALUE = 17;
    /**
     * <code>TypeTransferSettingRsp = 18;</code>
     */
    public static final int TypeTransferSettingRsp_VALUE = 18;
    /**
     * <code>TypePigSettingReq = 19;</code>
     */
    public static final int TypePigSettingReq_VALUE = 19;
    /**
     * <code>TypePigSettingRsp = 20;</code>
     */
    public static final int TypePigSettingRsp_VALUE = 20;
    /**
     * <code>TypeEnvSettingReq = 21;</code>
     */
    public static final int TypeEnvSettingReq_VALUE = 21;
    /**
     * <code>TypeEnvSettingRsp = 22;</code>
     */
    public static final int TypeEnvSettingRsp_VALUE = 22;
    /**
     * <code>TypePigEarReq = 23;</code>
     */
    public static final int TypePigEarReq_VALUE = 23;
    /**
     * <code>TypePigEarRsp = 24;</code>
     */
    public static final int TypePigEarRsp_VALUE = 24;
    /**
     * <code>TypeEnvironmentReq = 25;</code>
     */
    public static final int TypeEnvironmentReq_VALUE = 25;
    /**
     * <code>TypeEnvironmentRsp = 26;</code>
     */
    public static final int TypeEnvironmentRsp_VALUE = 26;
    /**
     * <code>TypeTransferReq = 27;</code>
     */
    public static final int TypeTransferReq_VALUE = 27;
    /**
     * <code>TypeTransferRsp = 28;</code>
     */
    public static final int TypeTransferRsp_VALUE = 28;
    /**
     * <pre>
     * gps v2 request with sint32, it had many value &lt; 0
     * </pre>
     *
     * <code>TypeGPS2Req = 29;</code>
     */
    public static final int TypeGPS2Req_VALUE = 29;
    /**
     * <code>TypeSensorReq = 31;</code>
     */
    public static final int TypeSensorReq_VALUE = 31;
    /**
     * <code>TypeSensorRsp = 32;</code>
     */
    public static final int TypeSensorRsp_VALUE = 32;
    /**
     * <code>TypeCellularReq = 33;</code>
     */
    public static final int TypeCellularReq_VALUE = 33;
    /**
     * <code>TypeCellularRsp = 34;</code>
     */
    public static final int TypeCellularRsp_VALUE = 34;
    /**
     * <code>TypeDebugReq = 10001;</code>
     */
    public static final int TypeDebugReq_VALUE = 10001;
    /**
     * <code>TypeDebugRsp = 10002;</code>
     */
    public static final int TypeDebugRsp_VALUE = 10002;
    /**
     * <code>TypeBehavior2Req = 35;</code>
     */
    public static final int TypeBehavior2Req_VALUE = 35;
    /**
     * <code>TypeBehavior2Rsp = 36;</code>
     */
    public static final int TypeBehavior2Rsp_VALUE = 36;


    public final int getNumber() {
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @Deprecated
    public static HeaderType valueOf(int value) {
      return forNumber(value);
    }

    public static HeaderType forNumber(int value) {
      switch (value) {
        case 1: return TypeRegisterReq;
        case 2: return TypeRegisterRsp;
        case 3: return TypeGPSReq;
        case 4: return TypeGPSRsp;
        case 5: return TypeBehaviorReq;
        case 6: return TypeBehaviorRsp;
        case 7: return TypeDownloadReq;
        case 8: return TypeDownloadRsp;
        case 9: return TypeStatusReq;
        case 10: return TypeStatusRsp;
        case 11: return TypeSettingReq;
        case 12: return TypeSettingRsp;
        case 13: return TypePingReq;
        case 14: return TypePingRsp;
        case 15: return TypeAppReq;
        case 16: return TypeAppRsp;
        case 17: return TypeTransferSettingReq;
        case 18: return TypeTransferSettingRsp;
        case 19: return TypePigSettingReq;
        case 20: return TypePigSettingRsp;
        case 21: return TypeEnvSettingReq;
        case 22: return TypeEnvSettingRsp;
        case 23: return TypePigEarReq;
        case 24: return TypePigEarRsp;
        case 25: return TypeEnvironmentReq;
        case 26: return TypeEnvironmentRsp;
        case 27: return TypeTransferReq;
        case 28: return TypeTransferRsp;
        case 29: return TypeGPS2Req;
        case 31: return TypeSensorReq;
        case 32: return TypeSensorRsp;
        case 33: return TypeCellularReq;
        case 34: return TypeCellularRsp;
        case 10001: return TypeDebugReq;
        case 10002: return TypeDebugRsp;
        case 35: return TypeBehavior2Req;
        case 36: return TypeBehavior2Rsp;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<HeaderType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        HeaderType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<HeaderType>() {
            public HeaderType findValueByNumber(int number) {
              return HeaderType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return Define.getDescriptor().getEnumTypes().get(0);
    }

    private static final HeaderType[] VALUES = values();

    public static HeaderType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private HeaderType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:protocol.HeaderType)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\014Define.proto\022\010protocol*\201\006\n\nHeaderType\022" +
      "\023\n\017TypeRegisterReq\020\001\022\023\n\017TypeRegisterRsp\020" +
      "\002\022\016\n\nTypeGPSReq\020\003\022\016\n\nTypeGPSRsp\020\004\022\023\n\017Typ" +
      "eBehaviorReq\020\005\022\023\n\017TypeBehaviorRsp\020\006\022\023\n\017T" +
      "ypeDownloadReq\020\007\022\023\n\017TypeDownloadRsp\020\010\022\021\n" +
      "\rTypeStatusReq\020\t\022\021\n\rTypeStatusRsp\020\n\022\022\n\016T" +
      "ypeSettingReq\020\013\022\022\n\016TypeSettingRsp\020\014\022\017\n\013T" +
      "ypePingReq\020\r\022\017\n\013TypePingRsp\020\016\022\016\n\nTypeApp" +
      "Req\020\017\022\016\n\nTypeAppRsp\020\020\022\032\n\026TypeTransferSet" +
      "tingReq\020\021\022\032\n\026TypeTransferSettingRsp\020\022\022\025\n" +
      "\021TypePigSettingReq\020\023\022\025\n\021TypePigSettingRs" +
      "p\020\024\022\025\n\021TypeEnvSettingReq\020\025\022\025\n\021TypeEnvSet" +
      "tingRsp\020\026\022\021\n\rTypePigEarReq\020\027\022\021\n\rTypePigE" +
      "arRsp\020\030\022\026\n\022TypeEnvironmentReq\020\031\022\026\n\022TypeE" +
      "nvironmentRsp\020\032\022\023\n\017TypeTransferReq\020\033\022\023\n\017" +
      "TypeTransferRsp\020\034\022\017\n\013TypeGPS2Req\020\035\022\021\n\rTy" +
      "peSensorReq\020\037\022\021\n\rTypeSensorRsp\020 \022\023\n\017Type" +
      "CellularReq\020!\022\023\n\017TypeCellularRsp\020\"\022\021\n\014Ty" +
      "peDebugReq\020\221N\022\021\n\014TypeDebugRsp\020\222N\022\024\n\020Type" +
      "Behavior2Req\020#\022\024\n\020TypeBehavior2Rsp\020$"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
