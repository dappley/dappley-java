package com.dappley.android.sdk;

import com.dappley.java.core.crypto.Bip39;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class Bip39Test {

    @Test
    public void testPrivateKey() {
        String mnemonic = "";
        BigInteger privateKey = Bip39.getPrivateKey(mnemonic);
        Assert.assertEquals("privateKey not right", privateKey
                , new BigInteger("6424137665956762791404338473136100785971241617189692246493279763101725491440"));
    }
}
