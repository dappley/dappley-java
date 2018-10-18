package com.dappley.android.sdk.protobuf;

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
  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest,
      com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse> getRpcAddPeerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcAddPeer",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest,
      com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse> getRpcAddPeerMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest, com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse> getRpcAddPeerMethod;
    if ((getRpcAddPeerMethod = AdminServiceGrpc.getRpcAddPeerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getRpcAddPeerMethod = AdminServiceGrpc.getRpcAddPeerMethod) == null) {
          AdminServiceGrpc.getRpcAddPeerMethod = getRpcAddPeerMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest, com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.AdminService", "RpcAddPeer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcAddPeerMethod;
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
    public void rpcAddPeer(com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcAddPeerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRpcAddPeerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest,
                com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse>(
                  this, METHODID_RPC_ADD_PEER)))
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
    public void rpcAddPeer(com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcAddPeerMethod(), getCallOptions()), request, responseObserver);
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
    public com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse rpcAddPeer(com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcAddPeerMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse> rpcAddPeer(
        com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcAddPeerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RPC_ADD_PEER = 0;

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
          serviceImpl.rpcAddPeer((com.dappley.android.sdk.protobuf.RpcProto.AddPeerRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddPeerResponse>) responseObserver);
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

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AdminServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getRpcAddPeerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
