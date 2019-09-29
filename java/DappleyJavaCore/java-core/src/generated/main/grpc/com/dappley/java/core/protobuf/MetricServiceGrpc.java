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
public final class MetricServiceGrpc {

  private MetricServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcpb.MetricService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest,
      com.dappley.java.core.protobuf.RpcProto.GetStatsResponse> getRpcGetStatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetStats",
      requestType = com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetStatsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest,
      com.dappley.java.core.protobuf.RpcProto.GetStatsResponse> getRpcGetStatsMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest, com.dappley.java.core.protobuf.RpcProto.GetStatsResponse> getRpcGetStatsMethod;
    if ((getRpcGetStatsMethod = MetricServiceGrpc.getRpcGetStatsMethod) == null) {
      synchronized (MetricServiceGrpc.class) {
        if ((getRpcGetStatsMethod = MetricServiceGrpc.getRpcGetStatsMethod) == null) {
          MetricServiceGrpc.getRpcGetStatsMethod = getRpcGetStatsMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest, com.dappley.java.core.protobuf.RpcProto.GetStatsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.MetricService", "RpcGetStats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetStatsResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new MetricServiceMethodDescriptorSupplier("RpcGetStats"))
                  .build();
          }
        }
     }
     return getRpcGetStatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest,
      com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> getRpcGetNodeConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetNodeConfig",
      requestType = com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest,
      com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> getRpcGetNodeConfigMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest, com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> getRpcGetNodeConfigMethod;
    if ((getRpcGetNodeConfigMethod = MetricServiceGrpc.getRpcGetNodeConfigMethod) == null) {
      synchronized (MetricServiceGrpc.class) {
        if ((getRpcGetNodeConfigMethod = MetricServiceGrpc.getRpcGetNodeConfigMethod) == null) {
          MetricServiceGrpc.getRpcGetNodeConfigMethod = getRpcGetNodeConfigMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest, com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.MetricService", "RpcGetNodeConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new MetricServiceMethodDescriptorSupplier("RpcGetNodeConfig"))
                  .build();
          }
        }
     }
     return getRpcGetNodeConfigMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest,
      com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> getRpcSetNodeConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSetNodeConfig",
      requestType = com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest,
      com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> getRpcSetNodeConfigMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest, com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> getRpcSetNodeConfigMethod;
    if ((getRpcSetNodeConfigMethod = MetricServiceGrpc.getRpcSetNodeConfigMethod) == null) {
      synchronized (MetricServiceGrpc.class) {
        if ((getRpcSetNodeConfigMethod = MetricServiceGrpc.getRpcSetNodeConfigMethod) == null) {
          MetricServiceGrpc.getRpcSetNodeConfigMethod = getRpcSetNodeConfigMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest, com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.MetricService", "RpcSetNodeConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new MetricServiceMethodDescriptorSupplier("RpcSetNodeConfig"))
                  .build();
          }
        }
     }
     return getRpcSetNodeConfigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MetricServiceStub newStub(io.grpc.Channel channel) {
    return new MetricServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MetricServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MetricServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MetricServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MetricServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MetricServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void rpcGetStats(com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetStatsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetStatsMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetNodeConfig(com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetNodeConfigMethod(), responseObserver);
    }

    /**
     */
    public void rpcSetNodeConfig(com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSetNodeConfigMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRpcGetStatsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest,
                com.dappley.java.core.protobuf.RpcProto.GetStatsResponse>(
                  this, METHODID_RPC_GET_STATS)))
          .addMethod(
            getRpcGetNodeConfigMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest,
                com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse>(
                  this, METHODID_RPC_GET_NODE_CONFIG)))
          .addMethod(
            getRpcSetNodeConfigMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest,
                com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse>(
                  this, METHODID_RPC_SET_NODE_CONFIG)))
          .build();
    }
  }

  /**
   */
  public static final class MetricServiceStub extends io.grpc.stub.AbstractStub<MetricServiceStub> {
    private MetricServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetricServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetricServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetricServiceStub(channel, callOptions);
    }

    /**
     */
    public void rpcGetStats(com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetStatsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetStatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetNodeConfig(com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetNodeConfigMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSetNodeConfig(com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcSetNodeConfigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MetricServiceBlockingStub extends io.grpc.stub.AbstractStub<MetricServiceBlockingStub> {
    private MetricServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetricServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetricServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetricServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetStatsResponse rpcGetStats(com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetStatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse rpcGetNodeConfig(com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetNodeConfigMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse rpcSetNodeConfig(com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcSetNodeConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MetricServiceFutureStub extends io.grpc.stub.AbstractStub<MetricServiceFutureStub> {
    private MetricServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetricServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetricServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetricServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetStatsResponse> rpcGetStats(
        com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetStatsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> rpcGetNodeConfig(
        com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetNodeConfigMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse> rpcSetNodeConfig(
        com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcSetNodeConfigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RPC_GET_STATS = 0;
  private static final int METHODID_RPC_GET_NODE_CONFIG = 1;
  private static final int METHODID_RPC_SET_NODE_CONFIG = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MetricServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MetricServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RPC_GET_STATS:
          serviceImpl.rpcGetStats((com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetStatsResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_NODE_CONFIG:
          serviceImpl.rpcGetNodeConfig((com.dappley.java.core.protobuf.RpcProto.MetricsServiceRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse>) responseObserver);
          break;
        case METHODID_RPC_SET_NODE_CONFIG:
          serviceImpl.rpcSetNodeConfig((com.dappley.java.core.protobuf.RpcProto.SetNodeConfigRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNodeConfigResponse>) responseObserver);
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

  private static abstract class MetricServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MetricServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.dappley.java.core.protobuf.RpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MetricService");
    }
  }

  private static final class MetricServiceFileDescriptorSupplier
      extends MetricServiceBaseDescriptorSupplier {
    MetricServiceFileDescriptorSupplier() {}
  }

  private static final class MetricServiceMethodDescriptorSupplier
      extends MetricServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MetricServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (MetricServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MetricServiceFileDescriptorSupplier())
              .addMethod(getRpcGetStatsMethod())
              .addMethod(getRpcGetNodeConfigMethod())
              .addMethod(getRpcSetNodeConfigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
