package com.dappley.android.sdk;

import android.content.Context;

import com.dappley.android.sdk.crypto.KeyPairTool;
import com.dappley.android.sdk.net.ProtocalProvider;
import com.dappley.android.sdk.net.RpcProvider;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.dappley.android.sdk.util.AddressUtil;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

/**
 * Client of Dappley Chain
 * <p>user interfaces</p>
 */
public class DappleyClient {
    private static ProtocalProvider protocalProvider;

    /**
     * Init variables of DappleyClient. It should be called first before you call any method of DappleyClient.
     * @param context Android context
     */
    public static void init(Context context, ProtocalProvider.ProviderType providerType) {
        if (providerType == ProtocalProvider.ProviderType.RPC) {
            protocalProvider = new RpcProvider();
            protocalProvider.init(context);
        } else {
            throw new IllegalArgumentException("only rpc protocal is supported now.");
        }
    }

    /**
     * Close protocal connection and free memories.
     */
    public static void close() {
        if (protocalProvider == null) {
            return;
        }
        protocalProvider.close();
    }

    public static String createWalletAddress() {
        String address = AddressUtil.createAddress();
        return address;
    }

    public static String getVersion() throws IllegalAccessException {
        return protocalProvider.getVersion();
    }

    public static String getBalance(String address) throws IllegalAccessException {
        return protocalProvider.getBalance(address);
    }

    public static String addBalance(String address) throws IllegalAccessException {
        return protocalProvider.addBalance(address);
    }

    public static void getBlockchainInfo() throws IllegalAccessException {
        protocalProvider.getBlockchainInfo();
    }

    public static List<RpcProto.UTXO> getUtxo(String address) throws IllegalAccessException {
        return protocalProvider.getUtxo(address);
    }

    public static void getBlocks() throws IllegalAccessException {
        protocalProvider.getBlocks();
    }

    public static int sendTransaction(TransactionProto.Transaction transaction) throws IllegalAccessException {
        return protocalProvider.sendTransaction(transaction);
    }

}
