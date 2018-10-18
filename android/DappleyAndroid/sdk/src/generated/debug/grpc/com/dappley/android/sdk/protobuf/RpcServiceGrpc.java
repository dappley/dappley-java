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
public final class RpcServiceGrpc {

  private RpcServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcpb.RpcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse> getRpcGetVersionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetVersion",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse> getRpcGetVersionMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest, com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse> getRpcGetVersionMethod;
    if ((getRpcGetVersionMethod = RpcServiceGrpc.getRpcGetVersionMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetVersionMethod = RpcServiceGrpc.getRpcGetVersionMethod) == null) {
          RpcServiceGrpc.getRpcGetVersionMethod = getRpcGetVersionMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest, com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetVersion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetVersionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest,
      com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse> getRpcCreateWalletMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcCreateWallet",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest,
      com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse> getRpcCreateWalletMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest, com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse> getRpcCreateWalletMethod;
    if ((getRpcCreateWalletMethod = RpcServiceGrpc.getRpcCreateWalletMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcCreateWalletMethod = RpcServiceGrpc.getRpcCreateWalletMethod) == null) {
          RpcServiceGrpc.getRpcCreateWalletMethod = getRpcCreateWalletMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest, com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcCreateWallet"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcCreateWalletMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest,
      com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse> getRpcAddProducerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcAddProducer",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest,
      com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse> getRpcAddProducerMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest, com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse> getRpcAddProducerMethod;
    if ((getRpcAddProducerMethod = RpcServiceGrpc.getRpcAddProducerMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcAddProducerMethod = RpcServiceGrpc.getRpcAddProducerMethod) == null) {
          RpcServiceGrpc.getRpcAddProducerMethod = getRpcAddProducerMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest, com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcAddProducer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcAddProducerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse> getRpcGetBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBalance",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse> getRpcGetBalanceMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse> getRpcGetBalanceMethod;
    if ((getRpcGetBalanceMethod = RpcServiceGrpc.getRpcGetBalanceMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBalanceMethod = RpcServiceGrpc.getRpcGetBalanceMethod) == null) {
          RpcServiceGrpc.getRpcGetBalanceMethod = getRpcGetBalanceMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetBalanceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest,
      com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse> getRpcAddBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcAddBalance",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest,
      com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse> getRpcAddBalanceMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest, com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse> getRpcAddBalanceMethod;
    if ((getRpcAddBalanceMethod = RpcServiceGrpc.getRpcAddBalanceMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcAddBalanceMethod = RpcServiceGrpc.getRpcAddBalanceMethod) == null) {
          RpcServiceGrpc.getRpcAddBalanceMethod = getRpcAddBalanceMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest, com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcAddBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcAddBalanceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse> getRpcGetWalletAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetWalletAddress",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse> getRpcGetWalletAddressMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest, com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse> getRpcGetWalletAddressMethod;
    if ((getRpcGetWalletAddressMethod = RpcServiceGrpc.getRpcGetWalletAddressMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetWalletAddressMethod = RpcServiceGrpc.getRpcGetWalletAddressMethod) == null) {
          RpcServiceGrpc.getRpcGetWalletAddressMethod = getRpcGetWalletAddressMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest, com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetWalletAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetWalletAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.SendRequest,
      com.dappley.android.sdk.protobuf.RpcProto.SendResponse> getRpcSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSend",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.SendRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.SendResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.SendRequest,
      com.dappley.android.sdk.protobuf.RpcProto.SendResponse> getRpcSendMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.SendRequest, com.dappley.android.sdk.protobuf.RpcProto.SendResponse> getRpcSendMethod;
    if ((getRpcSendMethod = RpcServiceGrpc.getRpcSendMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcSendMethod = RpcServiceGrpc.getRpcSendMethod) == null) {
          RpcServiceGrpc.getRpcSendMethod = getRpcSendMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.SendRequest, com.dappley.android.sdk.protobuf.RpcProto.SendResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcSend"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.SendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.SendResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse> getRpcGetPeerInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetPeerInfo",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse> getRpcGetPeerInfoMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest, com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse> getRpcGetPeerInfoMethod;
    if ((getRpcGetPeerInfoMethod = RpcServiceGrpc.getRpcGetPeerInfoMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetPeerInfoMethod = RpcServiceGrpc.getRpcGetPeerInfoMethod) == null) {
          RpcServiceGrpc.getRpcGetPeerInfoMethod = getRpcGetPeerInfoMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest, com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetPeerInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetPeerInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse> getRpcGetBlockchainInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlockchainInfo",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse> getRpcGetBlockchainInfoMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse> getRpcGetBlockchainInfoMethod;
    if ((getRpcGetBlockchainInfoMethod = RpcServiceGrpc.getRpcGetBlockchainInfoMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlockchainInfoMethod = RpcServiceGrpc.getRpcGetBlockchainInfoMethod) == null) {
          RpcServiceGrpc.getRpcGetBlockchainInfoMethod = getRpcGetBlockchainInfoMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlockchainInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetBlockchainInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse> getRpcGetUTXOMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetUTXO",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse> getRpcGetUTXOMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest, com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse> getRpcGetUTXOMethod;
    if ((getRpcGetUTXOMethod = RpcServiceGrpc.getRpcGetUTXOMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetUTXOMethod = RpcServiceGrpc.getRpcGetUTXOMethod) == null) {
          RpcServiceGrpc.getRpcGetUTXOMethod = getRpcGetUTXOMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest, com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetUTXO"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetUTXOMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse> getRpcGetBlocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlocks",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse> getRpcGetBlocksMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse> getRpcGetBlocksMethod;
    if ((getRpcGetBlocksMethod = RpcServiceGrpc.getRpcGetBlocksMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlocksMethod = RpcServiceGrpc.getRpcGetBlocksMethod) == null) {
          RpcServiceGrpc.getRpcGetBlocksMethod = getRpcGetBlocksMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetBlocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse> getRpcGetBlockByHashMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlockByHash",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse> getRpcGetBlockByHashMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse> getRpcGetBlockByHashMethod;
    if ((getRpcGetBlockByHashMethod = RpcServiceGrpc.getRpcGetBlockByHashMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlockByHashMethod = RpcServiceGrpc.getRpcGetBlockByHashMethod) == null) {
          RpcServiceGrpc.getRpcGetBlockByHashMethod = getRpcGetBlockByHashMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlockByHash"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetBlockByHashMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse> getRpcGetBlockByHeightMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlockByHeight",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest,
      com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse> getRpcGetBlockByHeightMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse> getRpcGetBlockByHeightMethod;
    if ((getRpcGetBlockByHeightMethod = RpcServiceGrpc.getRpcGetBlockByHeightMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlockByHeightMethod = RpcServiceGrpc.getRpcGetBlockByHeightMethod) == null) {
          RpcServiceGrpc.getRpcGetBlockByHeightMethod = getRpcGetBlockByHeightMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest, com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlockByHeight"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcGetBlockByHeightMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest,
      com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse> getRpcSendTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSendTransaction",
      requestType = com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest.class,
      responseType = com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest,
      com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse> getRpcSendTransactionMethod() {
    io.grpc.MethodDescriptor<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest, com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse> getRpcSendTransactionMethod;
    if ((getRpcSendTransactionMethod = RpcServiceGrpc.getRpcSendTransactionMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcSendTransactionMethod = RpcServiceGrpc.getRpcSendTransactionMethod) == null) {
          RpcServiceGrpc.getRpcSendTransactionMethod = getRpcSendTransactionMethod = 
              io.grpc.MethodDescriptor.<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest, com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcSendTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getRpcSendTransactionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RpcServiceStub newStub(io.grpc.Channel channel) {
    return new RpcServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RpcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RpcServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RpcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RpcServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class RpcServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void rpcGetVersion(com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetVersionMethod(), responseObserver);
    }

    /**
     */
    public void rpcCreateWallet(com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcCreateWalletMethod(), responseObserver);
    }

    /**
     */
    public void rpcAddProducer(com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcAddProducerMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBalance(com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBalanceMethod(), responseObserver);
    }

    /**
     */
    public void rpcAddBalance(com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcAddBalanceMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetWalletAddress(com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetWalletAddressMethod(), responseObserver);
    }

    /**
     */
    public void rpcSend(com.dappley.android.sdk.protobuf.RpcProto.SendRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.SendResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSendMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetPeerInfo(com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetPeerInfoMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlockchainInfo(com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlockchainInfoMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetUTXO(com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetUTXOMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlocks(com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlocksMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHash(com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlockByHashMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHeight(com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlockByHeightMethod(), responseObserver);
    }

    /**
     */
    public void rpcSendTransaction(com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSendTransactionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRpcGetVersionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse>(
                  this, METHODID_RPC_GET_VERSION)))
          .addMethod(
            getRpcCreateWalletMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest,
                com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse>(
                  this, METHODID_RPC_CREATE_WALLET)))
          .addMethod(
            getRpcAddProducerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest,
                com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse>(
                  this, METHODID_RPC_ADD_PRODUCER)))
          .addMethod(
            getRpcGetBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse>(
                  this, METHODID_RPC_GET_BALANCE)))
          .addMethod(
            getRpcAddBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest,
                com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse>(
                  this, METHODID_RPC_ADD_BALANCE)))
          .addMethod(
            getRpcGetWalletAddressMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse>(
                  this, METHODID_RPC_GET_WALLET_ADDRESS)))
          .addMethod(
            getRpcSendMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.SendRequest,
                com.dappley.android.sdk.protobuf.RpcProto.SendResponse>(
                  this, METHODID_RPC_SEND)))
          .addMethod(
            getRpcGetPeerInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse>(
                  this, METHODID_RPC_GET_PEER_INFO)))
          .addMethod(
            getRpcGetBlockchainInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse>(
                  this, METHODID_RPC_GET_BLOCKCHAIN_INFO)))
          .addMethod(
            getRpcGetUTXOMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse>(
                  this, METHODID_RPC_GET_UTXO)))
          .addMethod(
            getRpcGetBlocksMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse>(
                  this, METHODID_RPC_GET_BLOCKS)))
          .addMethod(
            getRpcGetBlockByHashMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse>(
                  this, METHODID_RPC_GET_BLOCK_BY_HASH)))
          .addMethod(
            getRpcGetBlockByHeightMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest,
                com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse>(
                  this, METHODID_RPC_GET_BLOCK_BY_HEIGHT)))
          .addMethod(
            getRpcSendTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest,
                com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse>(
                  this, METHODID_RPC_SEND_TRANSACTION)))
          .build();
    }
  }

  /**
   */
  public static final class RpcServiceStub extends io.grpc.stub.AbstractStub<RpcServiceStub> {
    private RpcServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServiceStub(channel, callOptions);
    }

    /**
     */
    public void rpcGetVersion(com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetVersionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcCreateWallet(com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcCreateWalletMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcAddProducer(com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcAddProducerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBalance(com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcAddBalance(com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcAddBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetWalletAddress(com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetWalletAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSend(com.dappley.android.sdk.protobuf.RpcProto.SendRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.SendResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetPeerInfo(com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetPeerInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlockchainInfo(com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlockchainInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetUTXO(com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetUTXOMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlocks(com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHash(com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlockByHashMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHeight(com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlockByHeightMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSendTransaction(com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcSendTransactionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RpcServiceBlockingStub extends io.grpc.stub.AbstractStub<RpcServiceBlockingStub> {
    private RpcServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse rpcGetVersion(com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetVersionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse rpcCreateWallet(com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcCreateWalletMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse rpcAddProducer(com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcAddProducerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse rpcGetBalance(com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse rpcAddBalance(com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcAddBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse rpcGetWalletAddress(com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetWalletAddressMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.SendResponse rpcSend(com.dappley.android.sdk.protobuf.RpcProto.SendRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcSendMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse rpcGetPeerInfo(com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetPeerInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse rpcGetBlockchainInfo(com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlockchainInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse rpcGetUTXO(com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetUTXOMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse rpcGetBlocks(com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlocksMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse rpcGetBlockByHash(com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlockByHashMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse rpcGetBlockByHeight(com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlockByHeightMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse rpcSendTransaction(com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcSendTransactionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RpcServiceFutureStub extends io.grpc.stub.AbstractStub<RpcServiceFutureStub> {
    private RpcServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse> rpcGetVersion(
        com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetVersionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse> rpcCreateWallet(
        com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcCreateWalletMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse> rpcAddProducer(
        com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcAddProducerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse> rpcGetBalance(
        com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse> rpcAddBalance(
        com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcAddBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse> rpcGetWalletAddress(
        com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetWalletAddressMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.SendResponse> rpcSend(
        com.dappley.android.sdk.protobuf.RpcProto.SendRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcSendMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse> rpcGetPeerInfo(
        com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetPeerInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse> rpcGetBlockchainInfo(
        com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlockchainInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse> rpcGetUTXO(
        com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetUTXOMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse> rpcGetBlocks(
        com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlocksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse> rpcGetBlockByHash(
        com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlockByHashMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse> rpcGetBlockByHeight(
        com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlockByHeightMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse> rpcSendTransaction(
        com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcSendTransactionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RPC_GET_VERSION = 0;
  private static final int METHODID_RPC_CREATE_WALLET = 1;
  private static final int METHODID_RPC_ADD_PRODUCER = 2;
  private static final int METHODID_RPC_GET_BALANCE = 3;
  private static final int METHODID_RPC_ADD_BALANCE = 4;
  private static final int METHODID_RPC_GET_WALLET_ADDRESS = 5;
  private static final int METHODID_RPC_SEND = 6;
  private static final int METHODID_RPC_GET_PEER_INFO = 7;
  private static final int METHODID_RPC_GET_BLOCKCHAIN_INFO = 8;
  private static final int METHODID_RPC_GET_UTXO = 9;
  private static final int METHODID_RPC_GET_BLOCKS = 10;
  private static final int METHODID_RPC_GET_BLOCK_BY_HASH = 11;
  private static final int METHODID_RPC_GET_BLOCK_BY_HEIGHT = 12;
  private static final int METHODID_RPC_SEND_TRANSACTION = 13;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RpcServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RpcServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RPC_GET_VERSION:
          serviceImpl.rpcGetVersion((com.dappley.android.sdk.protobuf.RpcProto.GetVersionRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetVersionResponse>) responseObserver);
          break;
        case METHODID_RPC_CREATE_WALLET:
          serviceImpl.rpcCreateWallet((com.dappley.android.sdk.protobuf.RpcProto.CreateWalletRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.CreateWalletResponse>) responseObserver);
          break;
        case METHODID_RPC_ADD_PRODUCER:
          serviceImpl.rpcAddProducer((com.dappley.android.sdk.protobuf.RpcProto.AddProducerRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddProducerResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BALANCE:
          serviceImpl.rpcGetBalance((com.dappley.android.sdk.protobuf.RpcProto.GetBalanceRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBalanceResponse>) responseObserver);
          break;
        case METHODID_RPC_ADD_BALANCE:
          serviceImpl.rpcAddBalance((com.dappley.android.sdk.protobuf.RpcProto.AddBalanceRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.AddBalanceResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_WALLET_ADDRESS:
          serviceImpl.rpcGetWalletAddress((com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetWalletAddressResponse>) responseObserver);
          break;
        case METHODID_RPC_SEND:
          serviceImpl.rpcSend((com.dappley.android.sdk.protobuf.RpcProto.SendRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.SendResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_PEER_INFO:
          serviceImpl.rpcGetPeerInfo((com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetPeerInfoResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCKCHAIN_INFO:
          serviceImpl.rpcGetBlockchainInfo((com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockchainInfoResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_UTXO:
          serviceImpl.rpcGetUTXO((com.dappley.android.sdk.protobuf.RpcProto.GetUTXORequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetUTXOResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCKS:
          serviceImpl.rpcGetBlocks((com.dappley.android.sdk.protobuf.RpcProto.GetBlocksRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlocksResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCK_BY_HASH:
          serviceImpl.rpcGetBlockByHash((com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHashResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCK_BY_HEIGHT:
          serviceImpl.rpcGetBlockByHeight((com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.GetBlockByHeightResponse>) responseObserver);
          break;
        case METHODID_RPC_SEND_TRANSACTION:
          serviceImpl.rpcSendTransaction((com.dappley.android.sdk.protobuf.RpcProto.SendTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.android.sdk.protobuf.RpcProto.SendTransactionResponse>) responseObserver);
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
      synchronized (RpcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getRpcGetVersionMethod())
              .addMethod(getRpcCreateWalletMethod())
              .addMethod(getRpcAddProducerMethod())
              .addMethod(getRpcGetBalanceMethod())
              .addMethod(getRpcAddBalanceMethod())
              .addMethod(getRpcGetWalletAddressMethod())
              .addMethod(getRpcSendMethod())
              .addMethod(getRpcGetPeerInfoMethod())
              .addMethod(getRpcGetBlockchainInfoMethod())
              .addMethod(getRpcGetUTXOMethod())
              .addMethod(getRpcGetBlocksMethod())
              .addMethod(getRpcGetBlockByHashMethod())
              .addMethod(getRpcGetBlockByHeightMethod())
              .addMethod(getRpcSendTransactionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
