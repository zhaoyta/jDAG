// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/org/jdryad/com/messages/up-and-alive-messages.proto

package org.jdryad.com.messages;

public final class UpAndLiveProtos {
  private UpAndLiveProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class WiredUpAndAliveMessage extends
      com.google.protobuf.GeneratedMessage {
    // Use WiredUpAndAliveMessage.newBuilder() to construct.
    private WiredUpAndAliveMessage() {
      initFields();
    }
    private WiredUpAndAliveMessage(boolean noInit) {}
    
    private static final WiredUpAndAliveMessage defaultInstance;
    public static WiredUpAndAliveMessage getDefaultInstance() {
      return defaultInstance;
    }
    
    public WiredUpAndAliveMessage getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.jdryad.com.messages.UpAndLiveProtos.internal_static_org_jdryad_com_messages_WiredUpAndAliveMessage_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.jdryad.com.messages.UpAndLiveProtos.internal_static_org_jdryad_com_messages_WiredUpAndAliveMessage_fieldAccessorTable;
    }
    
    // required int32 messageType = 1;
    public static final int MESSAGETYPE_FIELD_NUMBER = 1;
    private boolean hasMessageType;
    private int messageType_ = 0;
    public boolean hasMessageType() { return hasMessageType; }
    public int getMessageType() { return messageType_; }
    
    // required string hostID = 2;
    public static final int HOSTID_FIELD_NUMBER = 2;
    private boolean hasHostID;
    private java.lang.String hostID_ = "";
    public boolean hasHostID() { return hasHostID; }
    public java.lang.String getHostID() { return hostID_; }
    
    // required uint64 aliveMillis = 3;
    public static final int ALIVEMILLIS_FIELD_NUMBER = 3;
    private boolean hasAliveMillis;
    private long aliveMillis_ = 0L;
    public boolean hasAliveMillis() { return hasAliveMillis; }
    public long getAliveMillis() { return aliveMillis_; }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      if (!hasMessageType) return false;
      if (!hasHostID) return false;
      if (!hasAliveMillis) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasMessageType()) {
        output.writeInt32(1, getMessageType());
      }
      if (hasHostID()) {
        output.writeString(2, getHostID());
      }
      if (hasAliveMillis()) {
        output.writeUInt64(3, getAliveMillis());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasMessageType()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, getMessageType());
      }
      if (hasHostID()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getHostID());
      }
      if (hasAliveMillis()) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(3, getAliveMillis());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage result;
      
      // Construct using org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage();
        return builder;
      }
      
      protected org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage.getDescriptor();
      }
      
      public org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage getDefaultInstanceForType() {
        return org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage) {
          return mergeFrom((org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage other) {
        if (other == org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage.getDefaultInstance()) return this;
        if (other.hasMessageType()) {
          setMessageType(other.getMessageType());
        }
        if (other.hasHostID()) {
          setHostID(other.getHostID());
        }
        if (other.hasAliveMillis()) {
          setAliveMillis(other.getAliveMillis());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setMessageType(input.readInt32());
              break;
            }
            case 18: {
              setHostID(input.readString());
              break;
            }
            case 24: {
              setAliveMillis(input.readUInt64());
              break;
            }
          }
        }
      }
      
      
      // required int32 messageType = 1;
      public boolean hasMessageType() {
        return result.hasMessageType();
      }
      public int getMessageType() {
        return result.getMessageType();
      }
      public Builder setMessageType(int value) {
        result.hasMessageType = true;
        result.messageType_ = value;
        return this;
      }
      public Builder clearMessageType() {
        result.hasMessageType = false;
        result.messageType_ = 0;
        return this;
      }
      
      // required string hostID = 2;
      public boolean hasHostID() {
        return result.hasHostID();
      }
      public java.lang.String getHostID() {
        return result.getHostID();
      }
      public Builder setHostID(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasHostID = true;
        result.hostID_ = value;
        return this;
      }
      public Builder clearHostID() {
        result.hasHostID = false;
        result.hostID_ = getDefaultInstance().getHostID();
        return this;
      }
      
      // required uint64 aliveMillis = 3;
      public boolean hasAliveMillis() {
        return result.hasAliveMillis();
      }
      public long getAliveMillis() {
        return result.getAliveMillis();
      }
      public Builder setAliveMillis(long value) {
        result.hasAliveMillis = true;
        result.aliveMillis_ = value;
        return this;
      }
      public Builder clearAliveMillis() {
        result.hasAliveMillis = false;
        result.aliveMillis_ = 0L;
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:org.jdryad.com.messages.WiredUpAndAliveMessage)
    }
    
    static {
      defaultInstance = new WiredUpAndAliveMessage(true);
      org.jdryad.com.messages.UpAndLiveProtos.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:org.jdryad.com.messages.WiredUpAndAliveMessage)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_org_jdryad_com_messages_WiredUpAndAliveMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_org_jdryad_com_messages_WiredUpAndAliveMessage_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n7src/org/jdryad/com/messages/up-and-ali" +
      "ve-messages.proto\022\027org.jdryad.com.messag" +
      "es\"R\n\026WiredUpAndAliveMessage\022\023\n\013messageT" +
      "ype\030\001 \002(\005\022\016\n\006hostID\030\002 \002(\t\022\023\n\013aliveMillis" +
      "\030\003 \002(\004B\021B\017UpAndLiveProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_org_jdryad_com_messages_WiredUpAndAliveMessage_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_org_jdryad_com_messages_WiredUpAndAliveMessage_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_org_jdryad_com_messages_WiredUpAndAliveMessage_descriptor,
              new java.lang.String[] { "MessageType", "HostID", "AliveMillis", },
              org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage.class,
              org.jdryad.com.messages.UpAndLiveProtos.WiredUpAndAliveMessage.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}
