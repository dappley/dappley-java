package com.dappley.java.core.po;

import com.dappley.java.core.crypto.Bip39;
import com.dappley.java.core.crypto.KeyPairTool;
import com.dappley.java.core.util.AddressUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Block chain wallet.
 * <p>We can use mnemonic words to prove memoriable identification.
 * A mnemonic string can be convert to private key. However, a private key cannot be convert mnemonic words again.
 * So remember to notice user be care of saving his/her mnemonic words.
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
    private String encryptedMnemonic;
    private String encryptedPrivateKey;

    public Wallet() {
    }

    public Wallet(String mnemonic) {
        if (!Bip39.validateMnemonic(mnemonic)) {
            return;
        }
        this.mnemonic = mnemonic;
        this.privateKey = Bip39.getPrivateKey(mnemonic);
        this.publicKey = KeyPairTool.getPublicKeyFromPrivate(privateKey);
        this.address = AddressUtil.getUserAddress(this.publicKey);
    }

    public Wallet(BigInteger privateKey) {
        this.privateKey = privateKey;
        this.publicKey = KeyPairTool.getPublicKeyFromPrivate(privateKey);
        this.address = AddressUtil.getUserAddress(this.publicKey);
    }
}
