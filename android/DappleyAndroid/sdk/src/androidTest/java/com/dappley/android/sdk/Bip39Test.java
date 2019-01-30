package com.dappley.android.sdk;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dappley.java.core.crypto.Bip39;
import com.dappley.java.core.util.HexUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.web3j.crypto.MnemonicUtils;

import java.math.BigInteger;

@RunWith(AndroidJUnit4.class)
public class Bip39Test {
    private static final String TAG = "Bip39Test";

    @Test
    public void generate() {
        String mnemonic = Bip39.generateMnemonic();
        Log.d(TAG, "generate: " + mnemonic);
        String[] array = mnemonic.split("\\s+");

        Assert.assertEquals(12, array.length);
    }

    @Test
    public void getPrivateKey() {
        String mnemonic = "infant crowd you noodle crazy fancy ankle beauty host fatigue behind extend";
        BigInteger privateKey = Bip39.getPrivateKey(mnemonic);
        Log.d(TAG, "privateKey: " + privateKey.toString(16));

        Assert.assertEquals(32, privateKey.bitLength() / 8);
    }

    @Test
    public void validateMnemonic() {
        String mnemonic = "infant crowd you noodle crazy fancy ankle beauty host fatigue behind extend";
        boolean isValid = Bip39.validateMnemonic(mnemonic);
        Log.d(TAG, "validateMnemonic: " + isValid);

        Assert.assertEquals(true, isValid);
    }

    @Test
    public void testMnemonicPrivateKey() {
        String mnemonic = Bip39.generateMnemonic();
        Log.d(TAG, "mnemonic: " + mnemonic);
        BigInteger privateKey = Bip39.getPrivateKey(mnemonic);
        Log.d(TAG, "privateKey: " + privateKey.toString(16));
    }

    @Test
    public void testMnemonicSeed() {
        String mnemonic = "fox danger popular slender tobacco portion hour use neither then canal income";
//        String mnemonic = "edit shop copy ecology grain knife anchor reopen floor detail essay buffalo";
//        String mnemonic = "view brave hazard solution believe oxygen click few abuse mail pretty west";
//        String mnemonic = "maze action together neither reduce apple over this square glide write symptom";
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        Log.d(TAG, "seed: " + HexUtil.toHex(seed));
        BigInteger privateKey = Bip39.getPrivateKey(mnemonic);
        Log.d(TAG, "privateKey: " + privateKey.toString(16));
    }
}
