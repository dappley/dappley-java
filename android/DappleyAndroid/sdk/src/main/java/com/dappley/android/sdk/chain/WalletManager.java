package com.dappley.android.sdk.chain;

import com.dappley.android.sdk.crypto.AesCipher;
import com.dappley.android.sdk.crypto.Bip39;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.sdk.util.SerializeUtil;

import java.math.BigInteger;
import java.util.List;

/**
 * Utils of wallet.
 */
public class WalletManager {

    public static Wallet createWallet() {
        String mnemonic = Bip39.generateMnemonic();
        Wallet wallet = new Wallet(mnemonic);
        return wallet;
    }

    public static Wallet importWalletFromMnemonic(String mnemonic) {
        Wallet wallet = new Wallet(mnemonic);
        return wallet;
    }

    public static Wallet importWalletFromPrivateKey(String privateKey) {
        BigInteger privKey = new BigInteger(privateKey, 16);
        Wallet wallet = new Wallet(privKey);
        return wallet;
    }

    public static byte[] encryptWallet(Wallet wallet, String password) {
        byte[] bytes = SerializeUtil.encode(wallet);
        byte[] encryped = AesCipher.encrypt(bytes, password);
        return encryped;
    }

    public static byte[] encryptWallets(List<Wallet> wallets, String password) {
        byte[] bytes = SerializeUtil.encodeList(wallets, Wallet.class);
        byte[] encryped = AesCipher.encrypt(bytes, password);
        return encryped;
    }
}
