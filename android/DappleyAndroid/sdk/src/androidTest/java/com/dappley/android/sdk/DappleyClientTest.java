package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.po.Wallet;
import com.dappley.java.core.util.AddressUtil;
import com.dappley.java.core.util.HashUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class DappleyClientTest {
    public static final String TAG = "DappleyTest";

    @Test
    public void createWallet() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);

        Wallet wallet = Dappley.createWallet();
        Log.d(TAG, "createWallet: " + wallet.toString());

        Assert.assertNotNull(wallet);
    }

    @Test
    public void importWalletFromMnemonic() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);

        String mneonic = "border hub runway parade toast eye make swim alarm pink autumn bless";
        Wallet wallet = Dappley.importWalletFromMnemonic(mneonic);
        Log.d(TAG, "importWalletFromMnemonic: " + wallet.toString());

        Assert.assertEquals("dUUJ826xi12tCq4D465xyAFn9kKs7VVsgp", wallet.getAddress());
    }

    @Test
    public void importWalletFromPrivateKey() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);

        String privateKey = "f00fbae3a1ecd3de06be15cc455443fcf275008a04c1f69689a2f670f6f49c5b";
        Wallet wallet = Dappley.importWalletFromPrivateKey(privateKey);
        Log.d(TAG, "importWalletFromPrivateKey: " + wallet.toString());

        Assert.assertEquals("dUUJ826xi12tCq4D465xyAFn9kKs7VVsgp", wallet.getAddress());
    }

    @Test
    public void getWalletBalance() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);

        String address = "dUUJ826xi12tCq4D465xyAFn9kKs7VVsgp";
        BigInteger balance = Dappley.getWalletBalance(address);
        Log.d(TAG, "getWalletBalance: " + balance);

        Assert.assertTrue(balance.compareTo(BigInteger.ZERO) >= 0);
    }

    @Test
    public void getWalletBalances() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);

        String address = "dUUJ826xi12tCq4D465xyAFn9kKs7VVsgp";
        List<Wallet> wallets = new ArrayList<>();
        Wallet wallet = new Wallet();
        wallet.setAddress(address);
        wallets.add(wallet);

        address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        wallet = new Wallet();
        wallet.setAddress(address);
        wallets.add(wallet);
        wallets = Dappley.getWalletBalances(wallets);
        Log.d(TAG, "getWalletBalances: " + wallets.get(1).toString());

        Assert.assertTrue(wallets.get(1).getBalance().compareTo(BigInteger.ZERO) >= 0);
    }

    @Test
    public void encryptWallet() {
        Wallet wallet = new Wallet();
        wallet.setAddress("dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa");
        wallet = Dappley.encryptWallet(wallet, "123456");
        Log.d(TAG, "encryptWallet: " + wallet.toString());

        Assert.assertTrue(wallet.getEncryptedPrivateKey().length() > 0);
    }

    @Test
    public void decryptWallet() {
        Wallet wallet = new Wallet();
        String encypted = "5bcbcba5d9f6f0ddff4ace46a2b001011a68bacec2231cfc3c07d570bf2dd048c694309cf1359a37f584d724258acb61";
        wallet.setEncryptedPrivateKey(encypted);
        wallet = Dappley.decryptWallet(wallet, "123456");
        Log.d(TAG, "decryptWallet: " + wallet.toString());

        Assert.assertNotNull(wallet);
    }

    @Test
    public void addAddress() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.LOCAL_STORAGE);

        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        Dappley.addAddress(address);

        BlockChainDb blockChainDb = new BlockChainDb(context);
        Set<String> walletAddressSet = blockChainDb.getWalletAddressSet();

        Assert.assertTrue(walletAddressSet.contains(address));
    }

    @Test
    public void removeAddress() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.LOCAL_STORAGE);

        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        Dappley.removeAddress(address);

        BlockChainDb blockChainDb = new BlockChainDb(context);
        Set<String> walletAddressSet = blockChainDb.getWalletAddressSet();
        if (walletAddressSet == null) {
            walletAddressSet = new HashSet<>();
        }
        Assert.assertTrue(!walletAddressSet.contains(address));
    }

    @Test
    public void publicKeyToAddress() {
        String privateKey = "f00fbae3a1ecd3de06be15cc455443fcf275008a04c1f69689a2f670f6f49c5b";
        Wallet wallet = Dappley.importWalletFromPrivateKey(privateKey);

        BigInteger publicKey = wallet.getPublicKey();
        byte[] pubKeyHash = HashUtil.getUserPubKeyHash(publicKey);
        String address = Dappley.publicKeyToAddress(pubKeyHash);
        Log.d(TAG, "publicKeyToAddress: " + address);

        Assert.assertEquals("dUUJ826xi12tCq4D465xyAFn9kKs7VVsgp", address);
    }

    @Test
    public void validateAddress() {
        String address = "dUUJ826xi12tCq4D465xyAFn9kKs7VVsgp";
        boolean isTrue = Dappley.validateAddress(address);

        Assert.assertEquals(true, isTrue);
    }

    @Test
    public void getUtxos() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);
        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        List<Utxo> utxos = Dappley.getUtxos(address, 1, 10);

        Assert.assertTrue(utxos.size() > 0);
    }

    @Test
    public void sendTransaction() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);

        String fromAddress = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        String toAddress = "dUUJ826xi12tCq4D465xyAFn9kKs7VVsgp";
        BigInteger amount = new BigInteger("1");
        String privateKey = "300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a";

        boolean isSuccess = Dappley.sendTransaction(fromAddress, toAddress, amount, new BigInteger(privateKey, 16));

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void senTransactionWithContract() {
        Context context = InstrumentationRegistry.getTargetContext();
        Dappley.init(context, Dappley.DataMode.REMOTE_ONLINE);

        String fromAddress = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        BigInteger amount = new BigInteger("1");
        String privateKey = "300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a";

        String contractAddress = Dappley.createContractAddress();
        String contract = "{\"function\":\"record\",\"args\":[\"%s\",\"4\"]}";
        contract = String.format(contract, contractAddress);
        boolean isSuccess = Dappley.sendTransactionWithContract(fromAddress, contractAddress, amount, new BigInteger(privateKey, 16), contract);

        Assert.assertTrue(isSuccess);
    }
}
