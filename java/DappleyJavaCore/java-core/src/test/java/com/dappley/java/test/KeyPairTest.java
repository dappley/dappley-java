package com.dappley.java.test;

import com.dappley.java.core.chain.WalletManager;
import com.dappley.java.core.crypto.KeyPairTool;
import com.dappley.java.core.po.Wallet;
import com.dappley.java.core.util.AddressUtil;
import com.dappley.java.core.util.HashUtil;
import com.dappley.java.core.util.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

@Slf4j
public class KeyPairTest {
    private static final String TAG = "KeyPairTest";

    @Test
    public void toPublicKey() {
        String privateKey = "f00fbae3a1ecd3de06be15cc455443fcf275008a04c1f69689a2f670f6f49c5b";
        BigInteger publickKey = KeyPairTool.getPublicKeyFromPrivate(new BigInteger(privateKey, 16));
        Wallet wallet = WalletManager.importWalletFromPrivateKey(privateKey);

        log.debug("testPubKey: publicKey: " + publickKey.toString(16));

        Assert.assertEquals(publickKey, wallet.getPublicKey());
    }

    @Test
    public void getAddress() {
        String privateKey = "4ecff43e39d3d65bfaa3eca0999fddecf12785228269c772b603dd93532c748f";
        BigInteger publickKey = KeyPairTool.getPublicKeyFromPrivate(new BigInteger(privateKey, 16));
        byte[] pubKeyHash = HashUtil.getUserPubKeyHash(publickKey);
        String userAddress = AddressUtil.getAddressFromPubKeyHash(pubKeyHash);

        log.debug("testPubKey: pubKeyHash: " + HexUtil.toHex(pubKeyHash));
        log.debug("testPubKey: userAddress: " + userAddress);

        Assert.assertEquals("dU5ErX1uP5QYq5ENZUgJGMjDkR4VbS6LDC", userAddress);
    }
}
