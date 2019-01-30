package com.dappley.java.test;

import com.dappley.java.core.crypto.Bip39;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import com.dappley.java.core.util.MnemonicLanguage;

import java.math.BigInteger;

@Slf4j
public class Bip39Test {
    private static final String TAG = "Bip39Test";

    @Test
    public void generate() {
        String mnemonic = Bip39.generateMnemonic();
        log.debug("generate: " + mnemonic);
        mnemonic = Bip39.generateMnemonic(MnemonicLanguage.ZH_CN);
        log.debug("generate: " + mnemonic);
        mnemonic = Bip39.generateMnemonic(MnemonicLanguage.ZH_TW);
        log.debug("generate: " + mnemonic);
        String[] array = mnemonic.split("\\s+");

        Assert.assertEquals(12, array.length);
    }

    @Test
    public void getPrivateKey() {
        String mnemonic = "infant crowd you noodle crazy fancy ankle beauty host fatigue behind extend";
        BigInteger privateKey = Bip39.getPrivateKey(mnemonic);
        log.debug("privateKey: " + privateKey.toString(16));

        Assert.assertEquals(32, privateKey.bitLength() / 8);
    }

    @Test
    public void validateMnemonic() {
        String mnemonic = "infant crowd you noodle crazy fancy ankle beauty host fatigue behind extend";
        boolean isValid = Bip39.validateMnemonic(mnemonic);
        log.debug("validateMnemonic: " + isValid);

        Assert.assertEquals(true, isValid);
    }
}
