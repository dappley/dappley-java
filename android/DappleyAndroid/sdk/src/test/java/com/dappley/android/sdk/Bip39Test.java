package com.dappley.android.sdk;

import com.dappley.java.core.crypto.Bip39;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class Bip39Test {

    @Test
    public void testPrivateKey() {
        String mnemonic = "figure light wasp oil buzz method comic jewel mammal vendor ceiling adapt";
        BigInteger privateKey = Bip39.getPrivateKey(mnemonic);
        Assert.assertEquals("privateKey not right", privateKey
                , new BigInteger("55824259074851176801993342453568773913117587252134704596750674165627066608413"));
    }
}
