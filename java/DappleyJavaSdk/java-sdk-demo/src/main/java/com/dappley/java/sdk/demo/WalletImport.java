package com.dappley.java.sdk.demo;

import com.dappley.java.core.po.Wallet;
import com.dappley.java.core.util.MnemonicLanguage;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

/**
 * Wallet import demo.
 * <p>Both mnemonics and privateKey are supported</p>
 */
@Slf4j
public class WalletImport {

    public static void main(String[] args) {
        String mnemonic = "figure light wasp oil buzz method comic jewel mammal vendor ceiling adapt";
        Wallet wallet = Dappley.importWalletFromMnemonic(mnemonic);
        log.info("wallet: " + wallet.toString());

        mnemonic = "剂 束 彼 硝 酵 内 埃 胎 闭 简 称 互";
        wallet = Dappley.importWalletFromMnemonic(mnemonic, MnemonicLanguage.ZH_CN);
        log.info("wallet: " + wallet.toString());

        mnemonic = "擊 積 碼 檢 特 拒 陶 角 爾 床 繼 那";
        wallet = Dappley.importWalletFromMnemonic(mnemonic, MnemonicLanguage.ZH_TW);
        log.info("wallet: " + wallet.toString());

        String privateKey = "7b6b69332e1877c00004121d9dfeab55fb12bf1b3f74b1a5ef7635c0c48d6b1d";
        Wallet walletPrivate = Dappley.importWalletFromPrivateKey(privateKey);
        log.info("walletPrivate: " + walletPrivate.toString());
    }
}
