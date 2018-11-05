package com.dappley.android.sdk.crypto;

import com.dappley.android.sdk.util.AddressUtil;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.MnemonicUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Bip39 {
    public static final String MNEMONIC = "settle better cherry loud junk actual result brother absent chief energy moon";
    private static final SecureRandom secureRandom = SecureRandomUtils.secureRandom();

    public static String generateMnemonic() {
        byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);

        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        System.out.println(mnemonic);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        ECKeyPair ecKeyPair = ECKeyPair.create(ShaDigest.sha256(seed));
        System.out.println(new BigInteger(1, ShaDigest.sha256(seed)));
        System.out.println(ecKeyPair.getPrivateKey());
        System.out.println(ecKeyPair.getPrivateKey().toString(16));

        return mnemonic;
    }

    public static BigInteger getPrivateKey(String mnemonic) {
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        ECKeyPair ecKeyPair = ECKeyPair.create(ShaDigest.sha256(seed));
        Credentials credentials = Credentials.create(ecKeyPair);
        String addr2 = AddressUtil.createAddress(ecKeyPair);
        System.out.println("addr1: " + credentials.getAddress());
        System.out.println("addr2: " + addr2);
        return ecKeyPair.getPrivateKey();
    }

    public static boolean validateMnemonic(String mnemonic){
        return MnemonicUtils.validateMnemonic(mnemonic);
    }
}
