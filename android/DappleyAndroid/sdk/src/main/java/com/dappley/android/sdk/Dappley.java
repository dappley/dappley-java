package com.dappley.android.sdk;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.chain.WalletManager;
import com.dappley.android.sdk.net.DataProvider;
import com.dappley.android.sdk.net.LocalDataProvider;
import com.dappley.android.sdk.net.RemoteDataProvider;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.sdk.util.Asserts;

import java.math.BigInteger;
import java.util.List;

public class Dappley {
    private static final String TAG = "Dappley";
    private static Context context;
    private static DataProvider dataProvider;

    public static void init(Context context, DataMode dataMode) {
        Dappley.context = context;
        try {
            if (dataMode == DataMode.LOCAL_STORAGE) {
                dataProvider = new LocalDataProvider(context);
            } else if (dataMode == DataMode.REMOTE_ONLINE) {
                dataProvider = new RemoteDataProvider(context, RemoteDataProvider.RemoteProtocalType.RPC);
            }
        } catch (Exception e) {
            Log.e(TAG, "init: ", e);
        }
    }

    public static Wallet createWallet() {
        Asserts.init(context);
        Wallet wallet = WalletManager.createWallet();
        return wallet;
    }

    public static Wallet importWalletFromMnemonic(String mnemonic) {
        Asserts.init(context);
        Wallet wallet = WalletManager.importWalletFromMnemonic(mnemonic);
        return wallet;
    }

    public static Wallet importWalletFromPrivateKey(String privateKey) {
        Asserts.init(context);
        Wallet wallet = WalletManager.importWalletFromPrivateKey(privateKey);
        return wallet;
    }

    public static BigInteger getWalletBalance(String address) {
        Asserts.init(context);
        return dataProvider.getBalance(address);
    }

    public static List<Wallet> getWalletBalances(List<Wallet> wallets) {
        Asserts.init(context);
        BigInteger balance;
        for (Wallet wallet : wallets) {
            balance = dataProvider.getBalance(wallet.getAddress());
            wallet.setBalance(balance);
        }
        return wallets;
    }

    public static byte[] encryptWallet(Wallet wallet, String password) {
        Asserts.init(context);
        return WalletManager.encryptWallet(wallet, password);
    }

    public static byte[] encryptWallets(List<Wallet> wallets, String password) {
        Asserts.init(context);
        return WalletManager.encryptWallets(wallets, password);
    }

    public static void addAddress(String address) {
        Asserts.init(context);
        if (dataProvider instanceof LocalDataProvider) {
            BlockChainManager.addWalletAddress(context, address);
        }
    }

    public static void removeAddress(String address) {
        Asserts.init(context);
        if (dataProvider instanceof LocalDataProvider) {
            BlockChainManager.removeWalletAddress(context, address);
        }
    }

    /**
     * Data storage mode
     */
    public enum DataMode {
        /**
         * All datas will be stored at local disk.
         */
        LOCAL_STORAGE,
        /**
         * All datas will be get from remove node in real time.
         */
        REMOTE_ONLINE
    }
}
