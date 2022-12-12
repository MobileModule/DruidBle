// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: v1.Debug.proto

package protocol_v1;

public final class V1Debug {
  private V1Debug() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface DebugReqOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protocol_v1.DebugReq)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
     */
    boolean hasIden();
    /**
     * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
     */
    protocol_v1.V1IdentityMsg.IdentityMsg getIden();
    /**
     * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
     */
    protocol_v1.V1IdentityMsg.IdentityMsgOrBuilder getIdenOrBuilder();

    /**
     * <code>optional string Message = 2;</code>
     */
    boolean hasMessage();
    /**
     * <code>optional string Message = 2;</code>
     */
    java.lang.String getMessage();
    /**
     * <code>optional string Message = 2;</code>
     */
    com.google.protobuf.ByteString
        getMessageBytes();

    /**
     * <code>optional bytes Data = 3;</code>
     */
    boolean hasData();
    /**
     * <code>optional bytes Data = 3;</code>
     */
    com.google.protobuf.ByteString getData();
  }
  /**
   * Protobuf type {@code protocol_v1.DebugReq}
   */
  public  static final class DebugReq extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:protocol_v1.DebugReq)
      DebugReqOrBuilder {
    // Use DebugReq.newBuilder() to construct.
    private DebugReq(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private DebugReq() {
      message_ = "";
      data_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private DebugReq(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
      this();
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
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              protocol_v1.V1IdentityMsg.IdentityMsg.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = iden_.toBuilder();
              }
              iden_ = input.readMessage(protocol_v1.V1IdentityMsg.IdentityMsg.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(iden_);
                iden_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              message_ = bs;
              break;
            }
            case 26: {
              bitField0_ |= 0x00000004;
              data_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw new RuntimeException(e.setUnfinishedMessage(this));
      } catch (java.io.IOException e) {
        throw new RuntimeException(
            new com.google.protobuf.InvalidProtocolBufferException(
                e.getMessage()).setUnfinishedMessage(this));
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return protocol_v1.V1Debug.internal_static_protocol_v1_DebugReq_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protocol_v1.V1Debug.internal_static_protocol_v1_DebugReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              protocol_v1.V1Debug.DebugReq.class, protocol_v1.V1Debug.DebugReq.Builder.class);
    }

    private int bitField0_;
    public static final int IDEN_FIELD_NUMBER = 1;
    private protocol_v1.V1IdentityMsg.IdentityMsg iden_;
    /**
     * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
     */
    public boolean hasIden() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
     */
    public protocol_v1.V1IdentityMsg.IdentityMsg getIden() {
      return iden_ == null ? protocol_v1.V1IdentityMsg.IdentityMsg.getDefaultInstance() : iden_;
    }
    /**
     * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
     */
    public protocol_v1.V1IdentityMsg.IdentityMsgOrBuilder getIdenOrBuilder() {
      return iden_ == null ? protocol_v1.V1IdentityMsg.IdentityMsg.getDefaultInstance() : iden_;
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    private volatile java.lang.Object message_;
    /**
     * <code>optional string Message = 2;</code>
     */
    public boolean hasMessage() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional string Message = 2;</code>
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          message_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string Message = 2;</code>
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DATA_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString data_;
    /**
     * <code>optional bytes Data = 3;</code>
     */
    public boolean hasData() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional bytes Data = 3;</code>
     */
    public com.google.protobuf.ByteString getData() {
      return data_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasIden()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!getIden().isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(1, getIden());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 2, message_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, data_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getIden());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(2, message_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, data_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    public static protocol_v1.V1Debug.DebugReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protocol_v1.V1Debug.DebugReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protocol_v1.V1Debug.DebugReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protocol_v1.V1Debug.DebugReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protocol_v1.V1Debug.DebugReq parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protocol_v1.V1Debug.DebugReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static protocol_v1.V1Debug.DebugReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static protocol_v1.V1Debug.DebugReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static protocol_v1.V1Debug.DebugReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protocol_v1.V1Debug.DebugReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(protocol_v1.V1Debug.DebugReq prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protocol_v1.DebugReq}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protocol_v1.DebugReq)
        protocol_v1.V1Debug.DebugReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return protocol_v1.V1Debug.internal_static_protocol_v1_DebugReq_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return protocol_v1.V1Debug.internal_static_protocol_v1_DebugReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                protocol_v1.V1Debug.DebugReq.class, protocol_v1.V1Debug.DebugReq.Builder.class);
      }

      // Construct using protocol_v1.V1Debug.DebugReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getIdenFieldBuilder();
        }
      }
      public Builder clear() {
        super.clear();
        if (idenBuilder_ == null) {
          iden_ = null;
        } else {
          idenBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        message_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        data_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return protocol_v1.V1Debug.internal_static_protocol_v1_DebugReq_descriptor;
      }

      public protocol_v1.V1Debug.DebugReq getDefaultInstanceForType() {
        return protocol_v1.V1Debug.DebugReq.getDefaultInstance();
      }

      public protocol_v1.V1Debug.DebugReq build() {
        protocol_v1.V1Debug.DebugReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public protocol_v1.V1Debug.DebugReq buildPartial() {
        protocol_v1.V1Debug.DebugReq result = new protocol_v1.V1Debug.DebugReq(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        if (idenBuilder_ == null) {
          result.iden_ = iden_;
        } else {
          result.iden_ = idenBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.message_ = message_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.data_ = data_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof protocol_v1.V1Debug.DebugReq) {
          return mergeFrom((protocol_v1.V1Debug.DebugReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(protocol_v1.V1Debug.DebugReq other) {
        if (other == protocol_v1.V1Debug.DebugReq.getDefaultInstance()) return this;
        if (other.hasIden()) {
          mergeIden(other.getIden());
        }
        if (other.hasMessage()) {
          bitField0_ |= 0x00000002;
          message_ = other.message_;
          onChanged();
        }
        if (other.hasData()) {
          setData(other.getData());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        if (!hasIden()) {
          return false;
        }
        if (!getIden().isInitialized()) {
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        protocol_v1.V1Debug.DebugReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (protocol_v1.V1Debug.DebugReq) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private protocol_v1.V1IdentityMsg.IdentityMsg iden_ = null;
      private com.google.protobuf.SingleFieldBuilder<
          protocol_v1.V1IdentityMsg.IdentityMsg, protocol_v1.V1IdentityMsg.IdentityMsg.Builder, protocol_v1.V1IdentityMsg.IdentityMsgOrBuilder> idenBuilder_;
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public boolean hasIden() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public protocol_v1.V1IdentityMsg.IdentityMsg getIden() {
        if (idenBuilder_ == null) {
          return iden_ == null ? protocol_v1.V1IdentityMsg.IdentityMsg.getDefaultInstance() : iden_;
        } else {
          return idenBuilder_.getMessage();
        }
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public Builder setIden(protocol_v1.V1IdentityMsg.IdentityMsg value) {
        if (idenBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          iden_ = value;
          onChanged();
        } else {
          idenBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public Builder setIden(
          protocol_v1.V1IdentityMsg.IdentityMsg.Builder builderForValue) {
        if (idenBuilder_ == null) {
          iden_ = builderForValue.build();
          onChanged();
        } else {
          idenBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public Builder mergeIden(protocol_v1.V1IdentityMsg.IdentityMsg value) {
        if (idenBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001) &&
              iden_ != null &&
              iden_ != protocol_v1.V1IdentityMsg.IdentityMsg.getDefaultInstance()) {
            iden_ =
              protocol_v1.V1IdentityMsg.IdentityMsg.newBuilder(iden_).mergeFrom(value).buildPartial();
          } else {
            iden_ = value;
          }
          onChanged();
        } else {
          idenBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public Builder clearIden() {
        if (idenBuilder_ == null) {
          iden_ = null;
          onChanged();
        } else {
          idenBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public protocol_v1.V1IdentityMsg.IdentityMsg.Builder getIdenBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getIdenFieldBuilder().getBuilder();
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      public protocol_v1.V1IdentityMsg.IdentityMsgOrBuilder getIdenOrBuilder() {
        if (idenBuilder_ != null) {
          return idenBuilder_.getMessageOrBuilder();
        } else {
          return iden_ == null ?
              protocol_v1.V1IdentityMsg.IdentityMsg.getDefaultInstance() : iden_;
        }
      }
      /**
       * <code>required .protocol_v1.IdentityMsg Iden = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          protocol_v1.V1IdentityMsg.IdentityMsg, protocol_v1.V1IdentityMsg.IdentityMsg.Builder, protocol_v1.V1IdentityMsg.IdentityMsgOrBuilder> 
          getIdenFieldBuilder() {
        if (idenBuilder_ == null) {
          idenBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              protocol_v1.V1IdentityMsg.IdentityMsg, protocol_v1.V1IdentityMsg.IdentityMsg.Builder, protocol_v1.V1IdentityMsg.IdentityMsgOrBuilder>(
                  getIden(),
                  getParentForChildren(),
                  isClean());
          iden_ = null;
        }
        return idenBuilder_;
      }

      private java.lang.Object message_ = "";
      /**
       * <code>optional string Message = 2;</code>
       */
      public boolean hasMessage() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional string Message = 2;</code>
       */
      public java.lang.String getMessage() {
        java.lang.Object ref = message_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            message_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string Message = 2;</code>
       */
      public com.google.protobuf.ByteString
          getMessageBytes() {
        java.lang.Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string Message = 2;</code>
       */
      public Builder setMessage(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string Message = 2;</code>
       */
      public Builder clearMessage() {
        bitField0_ = (bitField0_ & ~0x00000002);
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }
      /**
       * <code>optional string Message = 2;</code>
       */
      public Builder setMessageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        message_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes Data = 3;</code>
       */
      public boolean hasData() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional bytes Data = 3;</code>
       */
      public com.google.protobuf.ByteString getData() {
        return data_;
      }
      /**
       * <code>optional bytes Data = 3;</code>
       */
      public Builder setData(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes Data = 3;</code>
       */
      public Builder clearData() {
        bitField0_ = (bitField0_ & ~0x00000004);
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:protocol_v1.DebugReq)
    }

    // @@protoc_insertion_point(class_scope:protocol_v1.DebugReq)
    private static final protocol_v1.V1Debug.DebugReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new protocol_v1.V1Debug.DebugReq();
    }

    public static protocol_v1.V1Debug.DebugReq getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<DebugReq>
        PARSER = new com.google.protobuf.AbstractParser<DebugReq>() {
      public DebugReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        try {
          return new DebugReq(input, extensionRegistry);
        } catch (RuntimeException e) {
          if (e.getCause() instanceof
              com.google.protobuf.InvalidProtocolBufferException) {
            throw (com.google.protobuf.InvalidProtocolBufferException)
                e.getCause();
          }
          throw e;
        }
      }
    };

    public static com.google.protobuf.Parser<DebugReq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DebugReq> getParserForType() {
      return PARSER;
    }

    public protocol_v1.V1Debug.DebugReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_v1_DebugReq_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protocol_v1_DebugReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016v1.Debug.proto\022\013protocol_v1\032\024v1.Identi" +
      "tyMsg.proto\"Q\n\010DebugReq\022&\n\004Iden\030\001 \002(\0132\030." +
      "protocol_v1.IdentityMsg\022\017\n\007Message\030\002 \001(\t" +
      "\022\014\n\004Data\030\003 \001(\014B\005\242\002\002V1"
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
          protocol_v1.V1IdentityMsg.getDescriptor(),
        }, assigner);
    internal_static_protocol_v1_DebugReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protocol_v1_DebugReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_protocol_v1_DebugReq_descriptor,
        new java.lang.String[] { "Iden", "Message", "Data", });
    protocol_v1.V1IdentityMsg.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
