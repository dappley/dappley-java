package com.dappley.android.sdk.po;

import com.dappley.android.sdk.crypto.Bip39;
import com.dappley.android.sdk.crypto.KeyPairTool;
import com.dappley.android.sdk.util.AddressUtil;
import com.dappley.android.sdk.util.HashUtil;

import java.io.Serializable;
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
public class Wallet implements Serializable {
    private String name;
    private String address;
    private String mnemonic;
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger balance;

    public Wallet() {
    }

    public Wallet(String mnemonic) {
        if (!Bip39.validateMnemonic(mnemonic)) {
            return;
        }
        this.mnemonic = mnemonic;
        this.privateKey = Bip39.getPrivateKey(mnemonic);
        this.publicKey = KeyPairTool.getPublicKeyFromPrivate(privateKey);
        this.address = AddressUtil.createAddress(this.publicKey);
    }

    public Wallet(BigInteger privateKey) {
        this.privateKey = privateKey;
        this.publicKey = KeyPairTool.getPublicKeyFromPrivate(privateKey);
        this.address = AddressUtil.createAddress(this.publicKey);
    }
}