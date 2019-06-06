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
public final class RpcServiceGrpc {

  private RpcServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcpb.RpcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetVersionRequest,
      com.dappley.java.core.protobuf.RpcProto.GetVersionResponse> getRpcGetVersionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetVersion",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetVersionRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetVersionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetVersionRequest,
      com.dappley.java.core.protobuf.RpcProto.GetVersionResponse> getRpcGetVersionMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetVersionRequest, com.dappley.java.core.protobuf.RpcProto.GetVersionResponse> getRpcGetVersionMethod;
    if ((getRpcGetVersionMethod = RpcServiceGrpc.getRpcGetVersionMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetVersionMethod = RpcServiceGrpc.getRpcGetVersionMethod) == null) {
          RpcServiceGrpc.getRpcGetVersionMethod = getRpcGetVersionMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetVersionRequest, com.dappley.java.core.protobuf.RpcProto.GetVersionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetVersion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetVersionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetVersionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetVersion"))
                  .build();
          }
        }
     }
     return getRpcGetVersionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse> getRpcGetBalanceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBalance",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse> getRpcGetBalanceMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest, com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse> getRpcGetBalanceMethod;
    if ((getRpcGetBalanceMethod = RpcServiceGrpc.getRpcGetBalanceMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBalanceMethod = RpcServiceGrpc.getRpcGetBalanceMethod) == null) {
          RpcServiceGrpc.getRpcGetBalanceMethod = getRpcGetBalanceMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest, com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetBalance"))
                  .build();
          }
        }
     }
     return getRpcGetBalanceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse> getRpcGetBlockchainInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlockchainInfo",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse> getRpcGetBlockchainInfoMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest, com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse> getRpcGetBlockchainInfoMethod;
    if ((getRpcGetBlockchainInfoMethod = RpcServiceGrpc.getRpcGetBlockchainInfoMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlockchainInfoMethod = RpcServiceGrpc.getRpcGetBlockchainInfoMethod) == null) {
          RpcServiceGrpc.getRpcGetBlockchainInfoMethod = getRpcGetBlockchainInfoMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest, com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlockchainInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetBlockchainInfo"))
                  .build();
          }
        }
     }
     return getRpcGetBlockchainInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetUTXORequest,
      com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse> getRpcGetUTXOMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetUTXO",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetUTXORequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetUTXORequest,
      com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse> getRpcGetUTXOMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetUTXORequest, com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse> getRpcGetUTXOMethod;
    if ((getRpcGetUTXOMethod = RpcServiceGrpc.getRpcGetUTXOMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetUTXOMethod = RpcServiceGrpc.getRpcGetUTXOMethod) == null) {
          RpcServiceGrpc.getRpcGetUTXOMethod = getRpcGetUTXOMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetUTXORequest, com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetUTXO"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetUTXORequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetUTXO"))
                  .build();
          }
        }
     }
     return getRpcGetUTXOMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse> getRpcGetBlocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlocks",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse> getRpcGetBlocksMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest, com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse> getRpcGetBlocksMethod;
    if ((getRpcGetBlocksMethod = RpcServiceGrpc.getRpcGetBlocksMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlocksMethod = RpcServiceGrpc.getRpcGetBlocksMethod) == null) {
          RpcServiceGrpc.getRpcGetBlocksMethod = getRpcGetBlocksMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest, com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetBlocks"))
                  .build();
          }
        }
     }
     return getRpcGetBlocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse> getRpcGetBlockByHashMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlockByHash",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse> getRpcGetBlockByHashMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest, com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse> getRpcGetBlockByHashMethod;
    if ((getRpcGetBlockByHashMethod = RpcServiceGrpc.getRpcGetBlockByHashMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlockByHashMethod = RpcServiceGrpc.getRpcGetBlockByHashMethod) == null) {
          RpcServiceGrpc.getRpcGetBlockByHashMethod = getRpcGetBlockByHashMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest, com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlockByHash"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetBlockByHash"))
                  .build();
          }
        }
     }
     return getRpcGetBlockByHashMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse> getRpcGetBlockByHeightMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetBlockByHeight",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest,
      com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse> getRpcGetBlockByHeightMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest, com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse> getRpcGetBlockByHeightMethod;
    if ((getRpcGetBlockByHeightMethod = RpcServiceGrpc.getRpcGetBlockByHeightMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetBlockByHeightMethod = RpcServiceGrpc.getRpcGetBlockByHeightMethod) == null) {
          RpcServiceGrpc.getRpcGetBlockByHeightMethod = getRpcGetBlockByHeightMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest, com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetBlockByHeight"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetBlockByHeight"))
                  .build();
          }
        }
     }
     return getRpcGetBlockByHeightMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest,
      com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse> getRpcSendTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSendTransaction",
      requestType = com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest,
      com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse> getRpcSendTransactionMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest, com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse> getRpcSendTransactionMethod;
    if ((getRpcSendTransactionMethod = RpcServiceGrpc.getRpcSendTransactionMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcSendTransactionMethod = RpcServiceGrpc.getRpcSendTransactionMethod) == null) {
          RpcServiceGrpc.getRpcSendTransactionMethod = getRpcSendTransactionMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest, com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcSendTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcSendTransaction"))
                  .build();
          }
        }
     }
     return getRpcSendTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest,
      com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse> getRpcSendBatchTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSendBatchTransaction",
      requestType = com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest,
      com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse> getRpcSendBatchTransactionMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest, com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse> getRpcSendBatchTransactionMethod;
    if ((getRpcSendBatchTransactionMethod = RpcServiceGrpc.getRpcSendBatchTransactionMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcSendBatchTransactionMethod = RpcServiceGrpc.getRpcSendBatchTransactionMethod) == null) {
          RpcServiceGrpc.getRpcSendBatchTransactionMethod = getRpcSendBatchTransactionMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest, com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcSendBatchTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcSendBatchTransaction"))
                  .build();
          }
        }
     }
     return getRpcSendBatchTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest,
      com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse> getRpcGetNewTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetNewTransaction",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest,
      com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse> getRpcGetNewTransactionMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest, com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse> getRpcGetNewTransactionMethod;
    if ((getRpcGetNewTransactionMethod = RpcServiceGrpc.getRpcGetNewTransactionMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetNewTransactionMethod = RpcServiceGrpc.getRpcGetNewTransactionMethod) == null) {
          RpcServiceGrpc.getRpcGetNewTransactionMethod = getRpcGetNewTransactionMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest, com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetNewTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetNewTransaction"))
                  .build();
          }
        }
     }
     return getRpcGetNewTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SubscribeRequest,
      com.dappley.java.core.protobuf.RpcProto.SubscribeResponse> getRpcSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcSubscribe",
      requestType = com.dappley.java.core.protobuf.RpcProto.SubscribeRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.SubscribeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SubscribeRequest,
      com.dappley.java.core.protobuf.RpcProto.SubscribeResponse> getRpcSubscribeMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.SubscribeRequest, com.dappley.java.core.protobuf.RpcProto.SubscribeResponse> getRpcSubscribeMethod;
    if ((getRpcSubscribeMethod = RpcServiceGrpc.getRpcSubscribeMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcSubscribeMethod = RpcServiceGrpc.getRpcSubscribeMethod) == null) {
          RpcServiceGrpc.getRpcSubscribeMethod = getRpcSubscribeMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.SubscribeRequest, com.dappley.java.core.protobuf.RpcProto.SubscribeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcSubscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SubscribeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.SubscribeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcSubscribe"))
                  .build();
          }
        }
     }
     return getRpcSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest,
      com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse> getRpcGetAllTransactionsFromTxPoolMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetAllTransactionsFromTxPool",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest,
      com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse> getRpcGetAllTransactionsFromTxPoolMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest, com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse> getRpcGetAllTransactionsFromTxPoolMethod;
    if ((getRpcGetAllTransactionsFromTxPoolMethod = RpcServiceGrpc.getRpcGetAllTransactionsFromTxPoolMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetAllTransactionsFromTxPoolMethod = RpcServiceGrpc.getRpcGetAllTransactionsFromTxPoolMethod) == null) {
          RpcServiceGrpc.getRpcGetAllTransactionsFromTxPoolMethod = getRpcGetAllTransactionsFromTxPoolMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest, com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetAllTransactionsFromTxPool"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetAllTransactionsFromTxPool"))
                  .build();
          }
        }
     }
     return getRpcGetAllTransactionsFromTxPoolMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest,
      com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse> getRpcGetLastIrreversibleBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RpcGetLastIrreversibleBlock",
      requestType = com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest.class,
      responseType = com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest,
      com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse> getRpcGetLastIrreversibleBlockMethod() {
    io.grpc.MethodDescriptor<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest, com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse> getRpcGetLastIrreversibleBlockMethod;
    if ((getRpcGetLastIrreversibleBlockMethod = RpcServiceGrpc.getRpcGetLastIrreversibleBlockMethod) == null) {
      synchronized (RpcServiceGrpc.class) {
        if ((getRpcGetLastIrreversibleBlockMethod = RpcServiceGrpc.getRpcGetLastIrreversibleBlockMethod) == null) {
          RpcServiceGrpc.getRpcGetLastIrreversibleBlockMethod = getRpcGetLastIrreversibleBlockMethod = 
              io.grpc.MethodDescriptor.<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest, com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "rpcpb.RpcService", "RpcGetLastIrreversibleBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RpcServiceMethodDescriptorSupplier("RpcGetLastIrreversibleBlock"))
                  .build();
          }
        }
     }
     return getRpcGetLastIrreversibleBlockMethod;
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
    public void rpcGetVersion(com.dappley.java.core.protobuf.RpcProto.GetVersionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetVersionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetVersionMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBalance(com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBalanceMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlockchainInfo(com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlockchainInfoMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetUTXO(com.dappley.java.core.protobuf.RpcProto.GetUTXORequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetUTXOMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlocks(com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlocksMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHash(com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlockByHashMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHeight(com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetBlockByHeightMethod(), responseObserver);
    }

    /**
     */
    public void rpcSendTransaction(com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSendTransactionMethod(), responseObserver);
    }

    /**
     */
    public void rpcSendBatchTransaction(com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSendBatchTransactionMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetNewTransaction(com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetNewTransactionMethod(), responseObserver);
    }

    /**
     */
    public void rpcSubscribe(com.dappley.java.core.protobuf.RpcProto.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SubscribeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcSubscribeMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetAllTransactionsFromTxPool(com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetAllTransactionsFromTxPoolMethod(), responseObserver);
    }

    /**
     */
    public void rpcGetLastIrreversibleBlock(com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRpcGetLastIrreversibleBlockMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRpcGetVersionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetVersionRequest,
                com.dappley.java.core.protobuf.RpcProto.GetVersionResponse>(
                  this, METHODID_RPC_GET_VERSION)))
          .addMethod(
            getRpcGetBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest,
                com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse>(
                  this, METHODID_RPC_GET_BALANCE)))
          .addMethod(
            getRpcGetBlockchainInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest,
                com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse>(
                  this, METHODID_RPC_GET_BLOCKCHAIN_INFO)))
          .addMethod(
            getRpcGetUTXOMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetUTXORequest,
                com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse>(
                  this, METHODID_RPC_GET_UTXO)))
          .addMethod(
            getRpcGetBlocksMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest,
                com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse>(
                  this, METHODID_RPC_GET_BLOCKS)))
          .addMethod(
            getRpcGetBlockByHashMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest,
                com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse>(
                  this, METHODID_RPC_GET_BLOCK_BY_HASH)))
          .addMethod(
            getRpcGetBlockByHeightMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest,
                com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse>(
                  this, METHODID_RPC_GET_BLOCK_BY_HEIGHT)))
          .addMethod(
            getRpcSendTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest,
                com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse>(
                  this, METHODID_RPC_SEND_TRANSACTION)))
          .addMethod(
            getRpcSendBatchTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest,
                com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse>(
                  this, METHODID_RPC_SEND_BATCH_TRANSACTION)))
          .addMethod(
            getRpcGetNewTransactionMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest,
                com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse>(
                  this, METHODID_RPC_GET_NEW_TRANSACTION)))
          .addMethod(
            getRpcSubscribeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.SubscribeRequest,
                com.dappley.java.core.protobuf.RpcProto.SubscribeResponse>(
                  this, METHODID_RPC_SUBSCRIBE)))
          .addMethod(
            getRpcGetAllTransactionsFromTxPoolMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest,
                com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse>(
                  this, METHODID_RPC_GET_ALL_TRANSACTIONS_FROM_TX_POOL)))
          .addMethod(
            getRpcGetLastIrreversibleBlockMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest,
                com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse>(
                  this, METHODID_RPC_GET_LAST_IRREVERSIBLE_BLOCK)))
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
    public void rpcGetVersion(com.dappley.java.core.protobuf.RpcProto.GetVersionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetVersionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetVersionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBalance(com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlockchainInfo(com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlockchainInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetUTXO(com.dappley.java.core.protobuf.RpcProto.GetUTXORequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetUTXOMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlocks(com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHash(com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlockByHashMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetBlockByHeight(com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetBlockByHeightMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSendTransaction(com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcSendTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSendBatchTransaction(com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcSendBatchTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetNewTransaction(com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getRpcGetNewTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcSubscribe(com.dappley.java.core.protobuf.RpcProto.SubscribeRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SubscribeResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getRpcSubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetAllTransactionsFromTxPool(com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetAllTransactionsFromTxPoolMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rpcGetLastIrreversibleBlock(com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest request,
        io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRpcGetLastIrreversibleBlockMethod(), getCallOptions()), request, responseObserver);
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
    public com.dappley.java.core.protobuf.RpcProto.GetVersionResponse rpcGetVersion(com.dappley.java.core.protobuf.RpcProto.GetVersionRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetVersionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse rpcGetBalance(com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse rpcGetBlockchainInfo(com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlockchainInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse rpcGetUTXO(com.dappley.java.core.protobuf.RpcProto.GetUTXORequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetUTXOMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse rpcGetBlocks(com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlocksMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse rpcGetBlockByHash(com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlockByHashMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse rpcGetBlockByHeight(com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetBlockByHeightMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse rpcSendTransaction(com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcSendTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse rpcSendBatchTransaction(com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcSendBatchTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse> rpcGetNewTransaction(
        com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getRpcGetNewTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.dappley.java.core.protobuf.RpcProto.SubscribeResponse> rpcSubscribe(
        com.dappley.java.core.protobuf.RpcProto.SubscribeRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getRpcSubscribeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse rpcGetAllTransactionsFromTxPool(com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetAllTransactionsFromTxPoolMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse rpcGetLastIrreversibleBlock(com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest request) {
      return blockingUnaryCall(
          getChannel(), getRpcGetLastIrreversibleBlockMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetVersionResponse> rpcGetVersion(
        com.dappley.java.core.protobuf.RpcProto.GetVersionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetVersionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse> rpcGetBalance(
        com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse> rpcGetBlockchainInfo(
        com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlockchainInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse> rpcGetUTXO(
        com.dappley.java.core.protobuf.RpcProto.GetUTXORequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetUTXOMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse> rpcGetBlocks(
        com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlocksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse> rpcGetBlockByHash(
        com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlockByHashMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse> rpcGetBlockByHeight(
        com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetBlockByHeightMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse> rpcSendTransaction(
        com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcSendTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse> rpcSendBatchTransaction(
        com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcSendBatchTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse> rpcGetAllTransactionsFromTxPool(
        com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetAllTransactionsFromTxPoolMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse> rpcGetLastIrreversibleBlock(
        com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRpcGetLastIrreversibleBlockMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RPC_GET_VERSION = 0;
  private static final int METHODID_RPC_GET_BALANCE = 1;
  private static final int METHODID_RPC_GET_BLOCKCHAIN_INFO = 2;
  private static final int METHODID_RPC_GET_UTXO = 3;
  private static final int METHODID_RPC_GET_BLOCKS = 4;
  private static final int METHODID_RPC_GET_BLOCK_BY_HASH = 5;
  private static final int METHODID_RPC_GET_BLOCK_BY_HEIGHT = 6;
  private static final int METHODID_RPC_SEND_TRANSACTION = 7;
  private static final int METHODID_RPC_SEND_BATCH_TRANSACTION = 8;
  private static final int METHODID_RPC_GET_NEW_TRANSACTION = 9;
  private static final int METHODID_RPC_SUBSCRIBE = 10;
  private static final int METHODID_RPC_GET_ALL_TRANSACTIONS_FROM_TX_POOL = 11;
  private static final int METHODID_RPC_GET_LAST_IRREVERSIBLE_BLOCK = 12;

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
          serviceImpl.rpcGetVersion((com.dappley.java.core.protobuf.RpcProto.GetVersionRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetVersionResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BALANCE:
          serviceImpl.rpcGetBalance((com.dappley.java.core.protobuf.RpcProto.GetBalanceRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBalanceResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCKCHAIN_INFO:
          serviceImpl.rpcGetBlockchainInfo((com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockchainInfoResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_UTXO:
          serviceImpl.rpcGetUTXO((com.dappley.java.core.protobuf.RpcProto.GetUTXORequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetUTXOResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCKS:
          serviceImpl.rpcGetBlocks((com.dappley.java.core.protobuf.RpcProto.GetBlocksRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlocksResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCK_BY_HASH:
          serviceImpl.rpcGetBlockByHash((com.dappley.java.core.protobuf.RpcProto.GetBlockByHashRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockByHashResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_BLOCK_BY_HEIGHT:
          serviceImpl.rpcGetBlockByHeight((com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetBlockByHeightResponse>) responseObserver);
          break;
        case METHODID_RPC_SEND_TRANSACTION:
          serviceImpl.rpcSendTransaction((com.dappley.java.core.protobuf.RpcProto.SendTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendTransactionResponse>) responseObserver);
          break;
        case METHODID_RPC_SEND_BATCH_TRANSACTION:
          serviceImpl.rpcSendBatchTransaction((com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SendBatchTransactionResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_NEW_TRANSACTION:
          serviceImpl.rpcGetNewTransaction((com.dappley.java.core.protobuf.RpcProto.GetNewTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetNewTransactionResponse>) responseObserver);
          break;
        case METHODID_RPC_SUBSCRIBE:
          serviceImpl.rpcSubscribe((com.dappley.java.core.protobuf.RpcProto.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.SubscribeResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_ALL_TRANSACTIONS_FROM_TX_POOL:
          serviceImpl.rpcGetAllTransactionsFromTxPool((com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetAllTransactionsResponse>) responseObserver);
          break;
        case METHODID_RPC_GET_LAST_IRREVERSIBLE_BLOCK:
          serviceImpl.rpcGetLastIrreversibleBlock((com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockRequest) request,
              (io.grpc.stub.StreamObserver<com.dappley.java.core.protobuf.RpcProto.GetLastIrreversibleBlockResponse>) responseObserver);
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

  private static abstract class RpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RpcServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.dappley.java.core.protobuf.RpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RpcService");
    }
  }

  private static final class RpcServiceFileDescriptorSupplier
      extends RpcServiceBaseDescriptorSupplier {
    RpcServiceFileDescriptorSupplier() {}
  }

  private static final class RpcServiceMethodDescriptorSupplier
      extends RpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RpcServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (RpcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RpcServiceFileDescriptorSupplier())
              .addMethod(getRpcGetVersionMethod())
              .addMethod(getRpcGetBalanceMethod())
              .addMethod(getRpcGetBlockchainInfoMethod())
              .addMethod(getRpcGetUTXOMethod())
              .addMethod(getRpcGetBlocksMethod())
              .addMethod(getRpcGetBlockByHashMethod())
              .addMethod(getRpcGetBlockByHeightMethod())
              .addMethod(getRpcSendTransactionMethod())
              .addMethod(getRpcSendBatchTransactionMethod())
              .addMethod(getRpcGetNewTransactionMethod())
              .addMethod(getRpcSubscribeMethod())
              .addMethod(getRpcGetAllTransactionsFromTxPoolMethod())
              .addMethod(getRpcGetLastIrreversibleBlockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
