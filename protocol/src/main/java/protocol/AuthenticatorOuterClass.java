// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AuthenticatorOuterClass.proto

package protocol;

public final class AuthenticatorOuterClass {
  private AuthenticatorOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AuthenticatorOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protocol.Authenticator)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required .protocol.IdentityMsg Iden = 1;</code>
     */
    boolean hasIden();
    /**
     * <code>required .protocol.IdentityMsg Iden = 1;</code>
     */
    protocol.IdentityMsgOuterClass.IdentityMsg getIden();
    /**
     * <code>required .protocol.IdentityMsg Iden = 1;</code>
     */
    protocol.IdentityMsgOuterClass.IdentityMsgOrBuilder getIdenOrBuilder();
  }
  /**
   * Protobuf type {@code protocol.Authenticator}
   */
  public  static final class Authenticator extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protocol.Authenticator)
      AuthenticatorOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Authenticator.newBuilder() to construct.
    private Authenticator(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Authenticator() {
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Authenticator(
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
              protocol.IdentityMsgOuterClass.IdentityMsg.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) != 0)) {
                subBuilder = iden_.toBuilder();
              }
              iden_ = input.readMessage(protocol.IdentityMsgOuterClass.IdentityMsg.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(iden_);
                iden_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
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
      return AuthenticatorOuterClass.internal_static_protocol_Authenticator_descriptor;
    }

    @Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AuthenticatorOuterClass.internal_static_protocol_Authenticator_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Authenticator.class, Builder.class);
    }

    private int bitField0_;
    public static final int IDEN_FIELD_NUMBER = 1;
    private protocol.IdentityMsgOuterClass.IdentityMsg iden_;
    /**
     * <code>required .protocol.IdentityMsg Iden = 1;</code>
     */
    public boolean hasIden() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>required .protocol.IdentityMsg Iden = 1;</code>
     */
    public protocol.IdentityMsgOuterClass.IdentityMsg getIden() {
      return iden_ == null ? protocol.IdentityMsgOuterClass.IdentityMsg.getDefaultInstance() : iden_;
    }
    /**
     * <code>required .protocol.IdentityMsg Iden = 1;</code>
     */
    public protocol.IdentityMsgOuterClass.IdentityMsgOrBuilder getIdenOrBuilder() {
      return iden_ == null ? protocol.IdentityMsgOuterClass.IdentityMsg.getDefaultInstance() : iden_;
    }

    private byte memoizedIsInitialized = -1;
    @Override
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

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeMessage(1, getIden());
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
          .computeMessageSize(1, getIden());
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
      if (!(obj instanceof Authenticator)) {
        return super.equals(obj);
      }
      Authenticator other = (Authenticator) obj;

      if (hasIden() != other.hasIden()) return false;
      if (hasIden()) {
        if (!getIden()
            .equals(other.getIden())) return false;
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
      if (hasIden()) {
        hash = (37 * hash) + IDEN_FIELD_NUMBER;
        hash = (53 * hash) + getIden().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Authenticator parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Authenticator parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Authenticator parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Authenticator parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Authenticator parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Authenticator parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Authenticator parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Authenticator parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Authenticator parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Authenticator parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Authenticator parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Authenticator parseFrom(
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
    public static Builder newBuilder(Authenticator prototype) {
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
     * Protobuf type {@code protocol.Authenticator}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protocol.Authenticator)
        AuthenticatorOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return AuthenticatorOuterClass.internal_static_protocol_Authenticator_descriptor;
      }

      @Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return AuthenticatorOuterClass.internal_static_protocol_Authenticator_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Authenticator.class, Builder.class);
      }

      // Construct using protocol.AuthenticatorOuterClass.Authenticator.newBuilder()
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
          getIdenFieldBuilder();
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        if (idenBuilder_ == null) {
          iden_ = null;
        } else {
          idenBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return AuthenticatorOuterClass.internal_static_protocol_Authenticator_descriptor;
      }

      @Override
      public Authenticator getDefaultInstanceForType() {
        return Authenticator.getDefaultInstance();
      }

      @Override
      public Authenticator build() {
        Authenticator result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public Authenticator buildPartial() {
        Authenticator result = new Authenticator(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          if (idenBuilder_ == null) {
            result.iden_ = iden_;
          } else {
            result.iden_ = idenBuilder_.build();
          }
          to_bitField0_ |= 0x00000001;
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
        if (other instanceof Authenticator) {
          return mergeFrom((Authenticator)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Authenticator other) {
        if (other == Authenticator.getDefaultInstance()) return this;
        if (other.hasIden()) {
          mergeIden(other.getIden());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        if (!hasIden()) {
          return false;
        }
        if (!getIden().isInitialized()) {
          return false;
        }
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Authenticator parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Authenticator) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private protocol.IdentityMsgOuterClass.IdentityMsg iden_;
      private com.google.protobuf.SingleFieldBuilderV3<
          protocol.IdentityMsgOuterClass.IdentityMsg, protocol.IdentityMsgOuterClass.IdentityMsg.Builder, protocol.IdentityMsgOuterClass.IdentityMsgOrBuilder> idenBuilder_;
      /**
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      public boolean hasIden() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      public protocol.IdentityMsgOuterClass.IdentityMsg getIden() {
        if (idenBuilder_ == null) {
          return iden_ == null ? protocol.IdentityMsgOuterClass.IdentityMsg.getDefaultInstance() : iden_;
        } else {
          return idenBuilder_.getMessage();
        }
      }
      /**
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      public Builder setIden(protocol.IdentityMsgOuterClass.IdentityMsg value) {
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
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      public Builder setIden(
          protocol.IdentityMsgOuterClass.IdentityMsg.Builder builderForValue) {
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
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      public Builder mergeIden(protocol.IdentityMsgOuterClass.IdentityMsg value) {
        if (idenBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0) &&
              iden_ != null &&
              iden_ != protocol.IdentityMsgOuterClass.IdentityMsg.getDefaultInstance()) {
            iden_ =
              protocol.IdentityMsgOuterClass.IdentityMsg.newBuilder(iden_).mergeFrom(value).buildPartial();
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
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
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
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      public protocol.IdentityMsgOuterClass.IdentityMsg.Builder getIdenBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getIdenFieldBuilder().getBuilder();
      }
      /**
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      public protocol.IdentityMsgOuterClass.IdentityMsgOrBuilder getIdenOrBuilder() {
        if (idenBuilder_ != null) {
          return idenBuilder_.getMessageOrBuilder();
        } else {
          return iden_ == null ?
              protocol.IdentityMsgOuterClass.IdentityMsg.getDefaultInstance() : iden_;
        }
      }
      /**
       * <code>required .protocol.IdentityMsg Iden = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          protocol.IdentityMsgOuterClass.IdentityMsg, protocol.IdentityMsgOuterClass.IdentityMsg.Builder, protocol.IdentityMsgOuterClass.IdentityMsgOrBuilder> 
          getIdenFieldBuilder() {
        if (idenBuilder_ == null) {
          idenBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              protocol.IdentityMsgOuterClass.IdentityMsg, protocol.IdentityMsgOuterClass.IdentityMsg.Builder, protocol.IdentityMsgOuterClass.IdentityMsgOrBuilder>(
                  getIden(),
                  getParentForChildren(),
                  isClean());
          iden_ = null;
        }
        return idenBuilder_;
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


      // @@protoc_insertion_point(builder_scope:protocol.Authenticator)
    }

    // @@protoc_insertion_point(class_scope:protocol.Authenticator)
    private static final Authenticator DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Authenticator();
    }

    public static Authenticator getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @Deprecated public static final com.google.protobuf.Parser<Authenticator>
        PARSER = new com.google.protobuf.AbstractParser<Authenticator>() {
      @Override
      public Authenticator parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Authenticator(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Authenticator> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Authenticator> getParserForType() {
      return PARSER;
    }

    @Override
    public Authenticator getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_Authenticator_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protocol_Authenticator_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\035AuthenticatorOuterClass.proto\022\010protoco" +
      "l\032\033IdentityMsgOuterClass.proto\"4\n\rAuthen" +
      "ticator\022#\n\004Iden\030\001 \002(\0132\025.protocol.Identit" +
      "yMsg"
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
          protocol.IdentityMsgOuterClass.getDescriptor(),
        }, assigner);
    internal_static_protocol_Authenticator_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protocol_Authenticator_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protocol_Authenticator_descriptor,
        new String[] { "Iden", });
    protocol.IdentityMsgOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
