package com.dappley.android.sdk;

import android.content.Context;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.config.Configuration;
import com.dappley.android.sdk.crypto.AesCipher;
import com.dappley.android.sdk.crypto.Bip39;
import com.dappley.android.sdk.crypto.KeyPairTool;
import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.BlockIndexDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.db.UtxoIndexDb;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.po.UtxoIndex;
import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.protobuf.RpcServiceGrpc;
import com.dappley.android.sdk.task.LocalBlockSchedule;
import com.dappley.android.sdk.util.AddressUtil;
import com.dappley.android.sdk.util.HashUtil;
import com.dappley.android.sdk.util.HexUtil;
import com.google.protobuf.ByteString;
import com.tencent.mmkv.MMKV;

import org.web3j.crypto.ECKeyPair;

import java.security.KeyPair;
import java.util.HashSet;
import java.util.Set;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DappleyTest {
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = KeyPairTool.newKeyPair();

        ECKeyPair ecKeyPair = KeyPairTool.castToEcKeyPair(keyPair);
        System.out.println(ecKeyPair.getPrivateKey().toString(16));
        String address = AddressUtil.createAddress(ecKeyPair);
        System.out.println(address);

//        testRpcConnect(null);

        String addr = "1FZqATrZWdXWi9tsGHZzHzgwJRnpwQoCGi";
        System.out.println(HexUtil.toHex(HashUtil.getPubKeyHash(addr)));
    }

    public static void testAes() {
        String encoded = AesCipher.encryptToHex("I am the one.", "aesKey");
        System.out.println(encoded);
        System.out.println(new String(AesCipher.decrypt(AesCipher.encrypt("I am the one.".getBytes(), "aesKey"), "aesKey")));
    }

    public static void testWord() {
        Bip39.generateMnemonic();
    }

    public static void testDB(Context context) {
        MMKV.initialize(context);
        BlockDb blockDB = new BlockDb(context);
        BlockProto.BlockHeader blockHeader = BlockProto.BlockHeader.newBuilder().setHash(ByteString.copyFromUtf8("testblock")).build();
        BlockProto.Block block = BlockProto.Block.newBuilder().setHeader(blockHeader).build();
//        boolean isSuccess = blockDB.saveBytes(block);
//        boolean isSuccess = blockDB.save(block);
        System.out.println(block);

    }

    public static void testRpcConnect(Context context) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(Configuration.getInstance(context).getServerIp(), Configuration.getInstance(context).getServerPort())
                .usePlaintext()
                .build();
        RpcServiceGrpc.RpcServiceBlockingStub stub = RpcServiceGrpc.newBlockingStub(channel);
        RpcProto.GetBalanceRequest request = RpcProto.GetBalanceRequest.newBuilder()
                .setAddress("1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG")
                .build();
        RpcProto.GetBalanceResponse response = stub.rpcGetBalance(request);
        String message = response.getMessage();
        System.out.println(message);
    }

    public static void testSchedule(Context context) {
        MMKV.initialize(context);

        BlockChainManager.initGenesisBlock(context);

        String address = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
        BlockChainManager.addWalletAddress(context, address);
        // receiver
        address = "1FZqATrZWdXWi9tsGHZzHzgwJRnpwQoCGi";
        BlockChainManager.addWalletAddress(context, address);

        LocalBlockSchedule.start(context);
    }

    public static void testLocal(Context context) {
        String address = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
//        String address = "1FZqATrZWdXWi9tsGHZzHzgwJRnpwQoCGi";
        MMKV.initialize(context);

//        BlockChainManager.initGenesisBlock(context);
        UtxoIndexDb utxoIndexDb = new UtxoIndexDb(context);
        UtxoDb utxoDb = new UtxoDb(context);
        Set<UtxoIndex> utxoIndexSet = utxoIndexDb.get(address);

        System.out.println("utxoIndexSet: " + (utxoIndexSet == null ? 0 : utxoIndexSet.size()));

        if (utxoIndexSet != null) {
            int i = 0;
            for (UtxoIndex utxoIndex : utxoIndexSet) {
                if (i > 10) {
                    break;
                }
                Utxo utxo = utxoDb.get(utxoIndex.toString());
                System.out.println(utxo.toString());
                i++;
            }
        }
    }

    public static void clearAddress(Context context) {
        MMKV.initialize(context);
        BlockChainDb blockChainDb = new BlockChainDb(context);
        blockChainDb.saveWalletAddressSet(new HashSet<String>());
    }

    public static void clearAll(Context context) {
        MMKV.initialize(context);
        UtxoIndexDb utxoIndexDb = new UtxoIndexDb(context);
        BlockDb blockDb = new BlockDb(context);
        BlockIndexDb blockIndexDb = new BlockIndexDb(context);
        TransactionDb transactionDb = new TransactionDb(context);
        UtxoDb utxoDb = new UtxoDb(context);

        utxoIndexDb.clearAll();
        utxoDb.clearAll();
        transactionDb.clearAll();
        blockIndexDb.clearAll();
        blockDb.clearAll();

        // add genesis block
        BlockChainManager.initGenesisBlock(context);
    }
}
