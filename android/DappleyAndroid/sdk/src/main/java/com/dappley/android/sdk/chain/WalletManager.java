package com.dappley.android.sdk.chain;

import com.dappley.android.sdk.crypto.AesCipher;
import com.dappley.android.sdk.crypto.Bip39;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.sdk.util.SerializeUtil;

import java.math.BigInteger;

/**
 * Utils of wallet.
 */
public class WalletManager {

    /**
     * Create a new wallet address.
     * @return Wallet
     */
    public static Wallet createWallet() {
        String mnemonic = Bip39.generateMnemonic();
        Wallet wallet = new Wallet(mnemonic);
        return wallet;
    }

    /**
     * Import wallet info by mnemonic words
     * @param mnemonic
     * @return Wallet
     */
    public static Wallet importWalletFromMnemonic(String mnemonic) {
        Wallet wallet = new Wallet(mnemonic);
        return wallet;
    }

    /**
     * Import wallet info by privateKey
     * @param privateKey
     * @return Wallet
     */
    public static Wallet importWalletFromPrivateKey(String privateKey) {
        BigInteger privKey = new BigInteger(privateKey, 16);
        Wallet wallet = new Wallet(privKey);
        return wallet;
    }

    /**
     * Encrypt wallet data with AES.
     * @param wallet
     * @param password
     * @return String encrypted data in Hex format
     */
    public static String encryptWallet(Wallet wallet, String password) {
        byte[] bytes = SerializeUtil.encode(wallet);
        String encryped = AesCipher.encryptToHex(bytes, password);
        return encryped;
    }

    /**
     * Decrypt wallet data.
     * @param walletString encrypted data in Hex format
     * @param password
     * @return Wallet wallet data
     */
    public static Wallet decryptWallet(String walletString, String password) {
        byte[] decrypted = AesCipher.decryptBytesFromHex(walletString, password);
        Wallet wallet = SerializeUtil.decode(decrypted, Wallet.class);
        return wallet;
    }
}
