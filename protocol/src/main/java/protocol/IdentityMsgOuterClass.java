// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: IdentityMsgOuterClass.proto

package protocol;

public final class IdentityMsgOuterClass {
  private IdentityMsgOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface IdentityMsgOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protocol.IdentityMsg)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required bytes UUID = 1;</code>
     */
    boolean hasUUID();
    /**
     * <code>required bytes UUID = 1;</code>
     */
    com.google.protobuf.ByteString getUUID();

    /**
     * <code>required uint32 Token = 2;</code>
     */
    boolean hasToken();
    /**
     * <code>required uint32 Token = 2;</code>
     */
    int getToken();
  }
  /**
   * Protobuf type {@code protocol.IdentityMsg}
   */
  public  static final class IdentityMsg extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protocol.IdentityMsg)
      IdentityMsgOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use IdentityMsg.newBuilder() to construct.
    private IdentityMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private IdentityMsg() {
      uUID_ = com.google.protobuf.ByteString.EMPTY;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private IdentityMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              bitField0_ |= 0x00000001;
              uUID_ = input.readBytes();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              token_ = input.readUInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return IdentityMsgOuterClass.internal_static_protocol_IdentityMsg_descriptor;
    }

    @Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return IdentityMsgOuterClass.internal_static_protocol_IdentityMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              IdentityMsg.class, Builder.class);
    }

    private int bitField0_;
    public static final int UUID_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString uUID_;
    /**
     * <code>required bytes UUID = 1;</code>
     */
    public boolean hasUUID() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>required bytes UUID = 1;</code>
     */
    public com.google.protobuf.ByteString getUUID() {
      return uUID_;
    }

    public static final int TOKEN_FIELD_NUMBER = 2;
    private int token_;
    /**
     * <code>required uint32 Token = 2;</code>
     */
    public boolean hasToken() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>required uint32 Token = 2;</code>
     */
    public int getToken() {
      return token_;
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasUUID()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasToken()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeBytes(1, uUID_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        output.writeUInt32(2, token_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, uUID_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(2, token_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof IdentityMsg)) {
        return super.equals(obj);
      }
      IdentityMsg other = (IdentityMsg) obj;

      if (hasUUID() != other.hasUUID()) return false;
      if (hasUUID()) {
        if (!getUUID()
            .equals(other.getUUID())) return false;
      }
      if (hasToken() != other.hasToken()) return false;
      if (hasToken()) {
        if (getToken()
            != other.getToken()) return false;
      }
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasUUID()) {
        hash = (37 * hash) + UUID_FIELD_NUMBER;
        hash = (53 * hash) + getUUID().hashCode();
      }
      if (hasToken()) {
        hash = (37 * hash) + TOKEN_FIELD_NUMBER;
        hash = (53 * hash) + getToken();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static IdentityMsg parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static IdentityMsg parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static IdentityMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static IdentityMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static IdentityMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static IdentityMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static IdentityMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static IdentityMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static IdentityMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static IdentityMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static IdentityMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static IdentityMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(IdentityMsg prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protocol.IdentityMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protocol.IdentityMsg)
        IdentityMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return IdentityMsgOuterClass.internal_static_protocol_IdentityMsg_descriptor;
      }

      @Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return IdentityMsgOuterClass.internal_static_protocol_IdentityMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                IdentityMsg.class, Builder.class);
      }

      // Construct using protocol.IdentityMsgOuterClass.IdentityMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        uUID_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        token_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return IdentityMsgOuterClass.internal_static_protocol_IdentityMsg_descriptor;
      }

      @Override
      public IdentityMsg getDefaultInstanceForType() {
        return IdentityMsg.getDefaultInstance();
      }

      @Override
      public IdentityMsg build() {
        IdentityMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public IdentityMsg buildPartial() {
        IdentityMsg result = new IdentityMsg(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          to_bitField0_ |= 0x00000001;
        }
        result.uUID_ = uUID_;
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.token_ = token_;
          to_bitField0_ |= 0x00000002;
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof IdentityMsg) {
          return mergeFrom((IdentityMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(IdentityMsg other) {
        if (other == IdentityMsg.getDefaultInstance()) return this;
        if (other.hasUUID()) {
          setUUID(other.getUUID());
        }
        if (other.hasToken()) {
          setToken(other.getToken());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        if (!hasUUID()) {
          return false;
        }
        if (!hasToken()) {
          return false;
        }
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        IdentityMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (IdentityMsg) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.ByteString uUID_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes UUID = 1;</code>
       */
      public boolean hasUUID() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>required bytes UUID = 1;</code>
       */
      public com.google.protobuf.ByteString getUUID() {
        return uUID_;
      }
      /**
       * <code>required bytes UUID = 1;</code>
       */
      public Builder setUUID(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        uUID_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes UUID = 1;</code>
       */
      public Builder clearUUID() {
        bitField0_ = (bitField0_ & ~0x00000001);
        uUID_ = getDefaultInstance().getUUID();
        onChanged();
        return this;
      }

      private int token_ ;
      /**
       * <code>required uint32 Token = 2;</code>
       */
      public boolean hasToken() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <code>required uint32 Token = 2;</code>
       */
      public int getToken() {
        return token_;
      }
      /**
       * <code>required uint32 Token = 2;</code>
       */
      public Builder setToken(int value) {
        bitField0_ |= 0x00000002;
        token_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required uint32 Token = 2;</code>
       */
      public Builder clearToken() {
        bitField0_ = (bitField0_ & ~0x00000002);
        token_ = 0;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:protocol.IdentityMsg)
    }

    // @@protoc_insertion_point(class_scope:protocol.IdentityMsg)
    private static final IdentityMsg DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new IdentityMsg();
    }

    public static IdentityMsg getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @Deprecated public static final com.google.protobuf.Parser<IdentityMsg>
        PARSER = new com.google.protobuf.AbstractParser<IdentityMsg>() {
      @Override
      public IdentityMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new IdentityMsg(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<IdentityMsg> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<IdentityMsg> getParserForType() {
      return PARSER;
    }

    @Override
    public IdentityMsg getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_IdentityMsg_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protocol_IdentityMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\033IdentityMsgOuterClass.proto\022\010protocol\"" +
      "*\n\013IdentityMsg\022\014\n\004UUID\030\001 \002(\014\022\r\n\005Token\030\002 " +
      "\002(\r"
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
    internal_static_protocol_IdentityMsg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protocol_IdentityMsg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protocol_IdentityMsg_descriptor,
        new String[] { "UUID", "Token", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
