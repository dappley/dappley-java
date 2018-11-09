package com.dappley.android.sdk;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.chain.TransactionManager;
import com.dappley.android.sdk.chain.UtxoManager;
import com.dappley.android.sdk.chain.WalletManager;
import com.dappley.android.sdk.net.DataProvider;
import com.dappley.android.sdk.net.LocalDataProvider;
import com.dappley.android.sdk.net.RemoteDataProvider;
import com.dappley.android.sdk.net.TransactionSender;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.sdk.util.AddressUtil;
import com.dappley.android.sdk.util.Asserts;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * Dapple sdk enter.
 * <p>
 *     Provides different method to synchronize block infomation from neighbor chain nodes.
 * </p>
 * <p>
 *    DataMode.LOCAL_STORAGE: use local storage with frame MMKV.
 *    DataMode.REMOTE_ONLINE: use real data from neighbor chain node. This may cause info delay while the node's data is not accurate.
 * </p>
 */
public class Dappley {
    private static final String TAG = "Dappley";
    private static Context context;
    private static DataProvider dataProvider;
    private static TransactionSender transactionSender;

    /**
     * Initialize client
     * @param context
     * @param dataMode choose a data reader mode
     */
    public static void init(Context context, DataMode dataMode) {
        Dappley.context = context;
        try {
            if (dataMode == DataMode.LOCAL_STORAGE) {
                dataProvider = new LocalDataProvider(context);
            } else if (dataMode == DataMode.REMOTE_ONLINE) {
                dataProvider = new RemoteDataProvider(context, RemoteDataProvider.RemoteProtocalType.RPC);
            }
            transactionSender = new TransactionSender(context);
        } catch (Exception e) {
            Log.e(TAG, "init: ", e);
        }
    }

    /**
     * Create a new wallet address.
     * <p>Contains mnemonic and private key in wallet.</p>
     * @return Wallet
     */
    public static Wallet createWallet() {
        Wallet wallet = WalletManager.createWallet();
        return wallet;
    }

    /**
     * Import wallet info from mnemonic words.
     * <p>Mnemonic are made up of 12 english words within blank as seperator.</p>
     * @param mnemonic words
     * @return Wallet
     */
    public static Wallet importWalletFromMnemonic(String mnemonic) {
        Wallet wallet = WalletManager.importWalletFromMnemonic(mnemonic);
        return wallet;
    }

    /**
     * Import wallet info from privateKey.
     * @param privateKey
     * @return Wallet
     */
    public static Wallet importWalletFromPrivateKey(String privateKey) {
        Wallet wallet = WalletManager.importWalletFromPrivateKey(privateKey);
        return wallet;
    }

    /**
     * Returns balance under current address.
     * @param address wallet address
     * @return BigInteger balance amount
     */
    public static BigInteger getWalletBalance(String address) {
        Asserts.init(context);
        return dataProvider.getBalance(address);
    }

    /**
     * Returns all balances in wallet list.
     * @param wallets wallet list
     * @return List<Wallet> wallet list with balance inside
     */
    public static List<Wallet> getWalletBalances(List<Wallet> wallets) {
        Asserts.init(context);
        BigInteger balance;
        for (Wallet wallet : wallets) {
            balance = dataProvider.getBalance(wallet.getAddress());
            wallet.setBalance(balance);
        }
        return wallets;
    }

    /**
     * Encrypt wallet data with AES.
     * @param wallet data
     * @param password
     * @return String encrypted data in Hex format
     */
    public static String encryptWallet(Wallet wallet, String password) {
        return WalletManager.encryptWallet(wallet, password);
    }

    /**
     * Decrypt wallet data.
     * @param walletString encrypted data in Hex format
     * @param password
     * @return Wallet wallet data
     */
    public static Wallet decryptWallet(String walletString, String password) {
        return WalletManager.decryptWallet(walletString, password);
    }

    /**
     * Add a new wallet address into local storage.
     * @param address wallet address
     */
    public static void addAddress(String address) {
        Asserts.init(context);
        if (dataProvider instanceof LocalDataProvider) {
            BlockChainManager.addWalletAddress(context, address);
        }
    }

    /**
     * Remove a wallet address in local storage.
     * @param address wallet address
     */
    public static void removeAddress(String address) {
        Asserts.init(context);
        if (dataProvider instanceof LocalDataProvider) {
            BlockChainManager.removeWalletAddress(context, address);
        }
    }

    /**
     * Convert publicKey hash to wallet address
     * @param pubKeyHash publicKey hash
     * @return String wallet address
     */
    public static String publicKeyToAddress(byte[] pubKeyHash) {
        return AddressUtil.createAddress(pubKeyHash);
    }

    /**
     * Valid the address is legal
     * @param address wallet address
     * @return boolean is legal
     */
    public static boolean validateAddress(String address) {
        return AddressUtil.validateAddress(address);
    }

    /**
     * Returns paged utxo list.
     * @param address wallet address
     * @param pageIndex current page index
     * @param pageSize
     * @return List<Utxo>
     */
    public static List<Utxo> getUtxos(String address, int pageIndex, int pageSize) {
        if (pageIndex <= 0 || pageSize <= 0) {
            return null;
        }
        List<Utxo> utxos = dataProvider.getUtxos(address);
        if (CollectionUtils.isEmpty(utxos)) {
            return null;
        }
        int pageNo = (pageIndex - 1) * pageSize;
        if (utxos.size() <= pageNo) {
            return null;
        }
        int toIndex = pageNo + pageSize;
        if (toIndex > utxos.size()) {
            toIndex = utxos.size();
        }
        List<Utxo> subList = utxos.subList(pageNo, toIndex);
        return subList;
    }

    /**
     * Send a new transaction to blockchain online.
     * @param fromAddress from user's address
     * @param toAddress to user's address
     * @param amount transferred amount
     * @param privateKey from user's privateKey
     * @return boolean is transaction committed successful
     */
    public static boolean sendTransaction(String fromAddress, String toAddress, BigInteger amount, BigInteger privateKey) {
        List<Utxo> allUtxo = dataProvider.getUtxos(fromAddress);
        if (CollectionUtils.isEmpty(allUtxo)) {
            return false;
        }
        List<Utxo> utxos = UtxoManager.getSpendableUtxos(allUtxo, amount);
        if (CollectionUtils.isEmpty(utxos)) {
            return false;
        }
        Transaction transaction = TransactionManager.newTransaction(utxos, toAddress, amount, privateKey);
        int errorCode = transactionSender.sendTransaction(transaction);
        if (errorCode == 0) {
            // success
            return true;
        }
        return false;
    }

    /**
     * Data storage mode
     * <p>We provides two type of data synchronize mode.
     *  <code>LOCAL_STORAGE</code> will use local disk space to save blockchain datas.
     *  <code>REMOTE_ONLINE</code> will use in-time data from neighbor nodes which is not always be creditable.
     * </p>
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
