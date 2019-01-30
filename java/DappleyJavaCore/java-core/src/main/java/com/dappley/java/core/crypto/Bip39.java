package com.dappley.java.core.crypto;

import org.web3j.crypto.ECKeyPair;
import com.dappley.java.core.util.MnemonicLanguage;
import org.web3j.crypto.MnemonicUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Utils of Bip39 protocol.
 */
public class Bip39 {
    private static final SecureRandom secureRandom = SecureRandomUtils.secureRandom();

    /**
     * Generate a string of mnemonic words randomly.
     * @return String mnemonic words
     */
    public static String generateMnemonic() {
        return generateMnemonic(MnemonicLanguage.EN);
    }

    /**
     * Generate a string of mnemonic words randomly.
     * @param mnemonicLanguage language type
     * @return String mnemonic words
     */
    public static String generateMnemonic(MnemonicLanguage mnemonicLanguage) {
        byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);

        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy, mnemonicLanguage);
        return mnemonic;
    }

    /**
     * Convert mnemonic words to PrivateKey
     * @param mnemonic words
     * @return BigInteger PrivateKey
     */
    public static BigInteger getPrivateKey(String mnemonic) {
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        ECKeyPair ecKeyPair = ECKeyPair.create(ShaDigest.sha256(seed));
        return ecKeyPair.getPrivateKey();
    }

    /**
     * Valid a mnemonic string is legal
     * @param mnemonic words
     * @return boolean is legal
     */
    public static boolean validateMnemonic(String mnemonic) {
        return MnemonicUtils.validateMnemonic(mnemonic, MnemonicLanguage.EN);
    }

    /**
     * Valid a mnemonic string is legal
     * @param mnemonic         words
     * @param mnemonicLanguage language type
     * @return boolean is legal
     */
    public static boolean validateMnemonic(String mnemonic, MnemonicLanguage mnemonicLanguage) {
        return MnemonicUtils.validateMnemonic(mnemonic, mnemonicLanguage);
    }
}
