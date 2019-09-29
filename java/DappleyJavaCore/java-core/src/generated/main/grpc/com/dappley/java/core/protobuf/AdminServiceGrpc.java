package com.dappley.java.core.protobuf;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.1)",
    comments = "Source: rpc.proto")
public final class AdminServiceGrpc {

  private AdminServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcpb.AdminService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.AddPeerRequest,
      com.dappley.java.core.protobuf.RpcProto.AddPeerResponse> getRpcAddPeerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcAddPeer",
      requestType = com.dappley.java.core.protobuf.RpcProto.AddPeerRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.AddPeerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.AddPeerRequest,
      com.dappley.java.core.protobuf.RpcProto.AddPeerResponse> getRpcAddPeerMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.AddPeerRequest, com.dappley.java.core.protobuf.RpcProto.AddPeerResponse> getRpcAddPeerMethod;
    if ((getRpcAddPeerMethod = AdminServiceGrpc.getRpcAddPeerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getRpcAddPeerMethod = AdminServiceGrpc.getRpcAddPeerMethod) == null) {
          AdminServiceGrpc.getRpcAddPeerMethod = getRpcAddPeerMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.AddPeerRequest, com.dappley.java.core.protobuf.RpcProto.AddPeerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.AdminService", "RpcAddPeer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.AddPeerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.AddPeerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("RpcAddPeer"))
                  .build();
          }
        }
     }
     return getRpcAddPeerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendRequest,
      com.dappley.java.core.protobuf.RpcProto.SendResponse> getRpcSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSend",
      requestType = com.dappley.java.core.protobuf.RpcProto.SendRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.SendResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendRequest,
      com.dappley.java.core.protobuf.RpcProto.SendResponse> getRpcSendMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendRequest, com.dappley.java.core.protobuf.RpcProto.SendResponse> getRpcSendMethod;
    if ((getRpcSendMethod = AdminServiceGrpc.getRpcSendMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getRpcSendMethod = AdminServiceGrpc.getRpcSendMethod) == null) {
          AdminServiceGrpc.getRpcSendMethod = getRpcSendMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.SendRequest, com.dappley.java.core.protobuf.RpcProto.SendResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.AdminService", "RpcSend"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("RpcSend"))
                  .build();
          }
        }
     }
     return getRpcSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest,
      com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse> getRpcGetPeerInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetPeerInfo",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest,
      com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse> getRpcGetPeerInfoMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest, com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse> getRpcGetPeerInfoMethod;
    if ((getRpcGetPeerInfoMethod = AdminServiceGrpc.getRpcGetPeerInfoMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getRpcGetPeerInfoMethod = AdminServiceGrpc.getRpcGetPeerInfoMethod) == null) {
          AdminServiceGrpc.getRpcGetPeerInfoMethod = getRpcGetPeerInfoMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest, com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.AdminService", "RpcGetPeerInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("RpcGetPeerInfo"))
                  .build();
          }
        }
     }
     return getRpcGetPeerInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest,
      com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse> getRpcSendFromMinerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSendFromMiner",
      requestType = com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest,
      com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse> getRpcSendFromMinerMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest, com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse> getRpcSendFromMinerMethod;
    if ((getRpcSendFromMinerMethod = AdminServiceGrpc.getRpcSendFromMinerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getRpcSendFromMinerMethod = AdminServiceGrpc.getRpcSendFromMinerMethod) == null) {
          AdminServiceGrpc.getRpcSendFromMinerMethod = getRpcSendFromMinerMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest, com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.AdminService", "RpcSendFromMiner"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("RpcSendFromMiner"))
                  .build();
          }
        }
     }
     return getRpcSendFromMinerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.AddProducerRequest,
      com.dappley.java.core.protobuf.RpcProto.AddProducerResponse> getRpcAddProducerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcAddProducer",
      requestType = com.dappley.java.core.protobuf.RpcProto.AddProducerRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.AddProducerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.AddProducerRequest,
      com.dappley.java.core.protobuf.RpcProto.AddProducerResponse> getRpcAddProducerMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.AddProducerRequest, com.dappley.java.core.protobuf.RpcProto.AddProducerResponse> getRpcAddProducerMethod;
    if ((getRpcAddProducerMethod = AdminServiceGrpc.getRpcAddProducerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getRpcAddProducerMethod = AdminServiceGrpc.getRpcAddProducerMethod) == null) {
          AdminServiceGrpc.getRpcAddProducerMethod = getRpcAddProducerMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.AddProducerRequest, com.dappley.java.core.protobuf.RpcProto.AddProducerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.AdminService", "RpcAddProducer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.AddProducerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.AddProducerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("RpcAddProducer"))
                  .build();
          }
        }
     }
     return getRpcAddProducerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest,
      com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse> getRpcUnlockAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcUnlockAccount",
      requestType = com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest,
      com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse> getRpcUnlockAccountMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest, com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse> getRpcUnlockAccountMethod;
    if ((getRpcUnlockAccountMethod = AdminServiceGrpc.getRpcUnlockAccountMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getRpcUnlockAccountMethod = AdminServiceGrpc.getRpcUnlockAccountMethod) == null) {
          AdminServiceGrpc.getRpcUnlockAccountMethod = getRpcUnlockAccountMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest, com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.AdminService", "RpcUnlockAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("RpcUnlockAccount"))
                  .build();
          }
        }
     }
     return getRpcUnlockAccountMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdminServiceStub newStub(io.grpc.Channel channel) {
    return new AdminServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdminServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AdminServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdminServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AdminServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class AdminServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void rpcAddPeer(com.dappley.java.core.protobuf.RpcProto.AddPeerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.AddPeerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcAddPeerMethod(), responseObserver);
    }

    /**
     */
    public void rpcSend(com.dappley.java.core.protobuf.RpcProto.SendRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSendMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetPeerInfo(com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetPeerInfoMethod(), responseObserver);
    }

    /**
     */
    public void rpcSendFromMiner(com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSendFromMinerMethod(), responseObserver);
    }

    /**
     */
    public void rpcAddProducer(com.dappley.java.core.protobuf.RpcProto.AddProducerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.AddProducerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcAddProducerMethod(), responseObserver);
    }

    /**
     */
    public void rpcUnlockAccount(com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcUnlockAccountMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRpcAddPeerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.AddPeerRequest,
                com.dappley.java.core.protobuf.RpcProto.AddPeerResponse>(
                  this, METHODID_RPC_ADD_PEER)))
          .addMethod(
            getRpcSendMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.SendRequest,
                com.dappley.java.core.protobuf.RpcProto.SendResponse>(
                  this, METHODID_RPC_SEND)))
          .addMethod(
            getRpcGetPeerInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest,
                com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse>(
                  this, METHODID_RPC_GET_PEER_INFO)))
          .addMethod(
            getRpcSendFromMinerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest,
                com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse>(
                  this, METHODID_RPC_SEND_FROM_MINER)))
          .addMethod(
            getRpcAddProducerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.AddProducerRequest,
                com.dappley.java.core.protobuf.RpcProto.AddProducerResponse>(
                  this, METHODID_RPC_ADD_PRODUCER)))
          .addMethod(
            getRpcUnlockAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest,
                com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse>(
                  this, METHODID_RPC_UNLOCK_ACCOUNT)))
          .build();
    }
  }

  /**
   */
  public static final class AdminServiceStub extends io.grpc.stub.AbstractStub<AdminServiceStub> {
    private AdminServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdminServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdminServiceStub(channel, callOptions);
    }

    /**
     */
    public void rpcAddPeer(com.dappley.java.core.protobuf.RpcProto.AddPeerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.AddPeerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcAddPeerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSend(com.dappley.java.core.protobuf.RpcProto.SendRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetPeerInfo(com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetPeerInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSendFromMiner(com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcSendFromMinerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcAddProducer(com.dappley.java.core.protobuf.RpcProto.AddProducerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.AddProducerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcAddProducerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcUnlockAccount(com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcUnlockAccountMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AdminServiceBlockingStub extends io.grpc.stub.AbstractStub<AdminServiceBlockingStub> {
    private AdminServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdminServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdminServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.AddPeerResponse rpcAddPeer(com.dappley.java.core.protobuf.RpcProto.AddPeerRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcAddPeerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.SendResponse rpcSend(com.dappley.java.core.protobuf.RpcProto.SendRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcSendMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse rpcGetPeerInfo(com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetPeerInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse rpcSendFromMiner(com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcSendFromMinerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.AddProducerResponse rpcAddProducer(com.dappley.java.core.protobuf.RpcProto.AddProducerRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcAddProducerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse rpcUnlockAccount(com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcUnlockAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AdminServiceFutureStub extends io.grpc.stub.AbstractStub<AdminServiceFutureStub> {
    private AdminServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdminServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdminServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.AddPeerResponse> rpcAddPeer(
        com.dappley.java.core.protobuf.RpcProto.AddPeerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcAddPeerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.SendResponse> rpcSend(
        com.dappley.java.core.protobuf.RpcProto.SendRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcSendMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse> rpcGetPeerInfo(
        com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetPeerInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse> rpcSendFromMiner(
        com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcSendFromMinerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.AddProducerResponse> rpcAddProducer(
        com.dappley.java.core.protobuf.RpcProto.AddProducerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcAddProducerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse> rpcUnlockAccount(
        com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcUnlockAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RPC_ADD_PEER = 0;
  private static final int METHODID_RPC_SEND = 1;
  private static final int METHODID_RPC_GET_PEER_INFO = 2;
  private static final int METHODID_RPC_SEND_FROM_MINER = 3;
  private static final int METHODID_RPC_ADD_PRODUCER = 4;
  private static final int METHODID_RPC_UNLOCK_ACCOUNT = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AdminServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AdminServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RPC_ADD_PEER:
          serviceImpl.rpcAddPeer((com.dappley.java.core.protobuf.RpcProto.AddPeerRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.AddPeerResponse>) responseObserver);
          break;
        case METHODID_RPC_SEND:
          serviceImpl.rpcSend((com.dappley.java.core.protobuf.RpcProto.SendRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_PEER_INFO:
          serviceImpl.rpcGetPeerInfo((com.dappley.java.core.protobuf.RpcProto.GetPeerInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetPeerInfoResponse>) responseObserver);
          break;
        case METHODID_RPC_SEND_FROM_MINER:
          serviceImpl.rpcSendFromMiner((com.dappley.java.core.protobuf.RpcProto.SendFromMinerRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendFromMinerResponse>) responseObserver);
          break;
        case METHODID_RPC_ADD_PRODUCER:
          serviceImpl.rpcAddProducer((com.dappley.java.core.protobuf.RpcProto.AddProducerRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.AddProducerResponse>) responseObserver);
          break;
        case METHODID_RPC_UNLOCK_ACCOUNT:
          serviceImpl.rpcUnlockAccount((com.dappley.java.core.protobuf.RpcProto.UnlockAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.UnlockAccountResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AdminServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdminServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.dappley.java.core.protobuf.RpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdminService");
    }
  }

  private static final class AdminServiceFileDescriptorSupplier
      extends AdminServiceBaseDescriptorSupplier {
    AdminServiceFileDescriptorSupplier() {}
  }

  private static final class AdminServiceMethodDescriptorSupplier
      extends AdminServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AdminServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AdminServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdminServiceFileDescriptorSupplier())
              .addMethod(getRpcAddPeerMethod())
              .addMethod(getRpcSendMethod())
              .addMethod(getRpcGetPeerInfoMethod())
              .addMethod(getRpcSendFromMinerMethod())
              .addMethod(getRpcAddProducerMethod())
              .addMethod(getRpcUnlockAccountMethod())
              .build();
        }
      }
    }
    return result;
  }
}
