package com.dappley.android.sdk;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dappley.android.sdk.chain.WalletManager;
import com.dappley.android.sdk.crypto.KeyPairTool;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.sdk.util.AddressUtil;
import com.dappley.android.sdk.util.HashUtil;
import com.dappley.android.sdk.util.HexUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;

@RunWith(AndroidJUnit4.class)
public class KeyPairTest {
    private static final String TAG = "KeyPairTest";

    @Test
    public void toPublicKey() {
        String privateKey = "f00fbae3a1ecd3de06be15cc455443fcf275008a04c1f69689a2f670f6f49c5b";
        BigInteger publickKey = KeyPairTool.getPublicKeyFromPrivate(new BigInteger(privateKey, 16));
        Wallet wallet = WalletManager.importWalletFromPrivateKey(privateKey);

        Log.d(TAG, "testPubKey: publicKey: " + publickKey.toString(16));

        Assert.assertEquals(publickKey, wallet.getPublicKey());
    }

    @Test
    public void getAddress() {
        String privateKey = "4ecff43e39d3d65bfaa3eca0999fddecf12785228269c772b603dd93532c748f";
        BigInteger publickKey = KeyPairTool.getPublicKeyFromPrivate(new BigInteger(privateKey, 16));
        byte[] pubKeyHash = HashUtil.getUserPubKeyHash(publickKey);
        String userAddress = AddressUtil.getAddressFromPubKeyHash(pubKeyHash);

        Log.d(TAG, "testPubKey: pubKeyHash: " + HexUtil.toHex(pubKeyHash));
        Log.d(TAG, "testPubKey: userAddress: " + userAddress);

        Assert.assertEquals("dU5ErX1uP5QYq5ENZUgJGMjDkR4VbS6LDC", userAddress);
    }
}
