package com.dappley.android.sdk.po;

import com.dappley.android.sdk.crypto.Bip39;
import com.dappley.android.sdk.util.HashUtil;

import java.math.BigInteger;

import lombok.Data;

/**
 * Block chain wallet.
 * <p>We can use mnemonic words to prove memoriable identification.
 *    A mnemonic string can be convert to private key. However, a private key cannot be convert mnemonic words again.
 *    So remember to notice user be care of saving his/her mnemonic words.
 * </p>
 */
@Data
public class Wallet {
    private String mnemonic;
    private BigInteger privateKey;
    private BigInteger publicKey;

    public Wallet() {
    }

    public Wallet(String mnemonic) {
        this.mnemonic = mnemonic;
        this.privateKey = Bip39.getPrivateKey(mnemonic);
    }

    public Wallet(BigInteger privateKey){
        this.privateKey = privateKey;
    }
}
