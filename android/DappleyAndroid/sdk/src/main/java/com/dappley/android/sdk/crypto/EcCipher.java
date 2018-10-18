package com.dappley.android.sdk.crypto;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EcCipher {
    /**
     * user ec algorithm
     */
    private static final String ALGORITHM = "ECDSA";
    /**
     * standard name secp256k1
     */
    private static final String STD_NAME = "secp256k1";

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public static byte[] encrypt(byte[] original, PublicKey publicKey) throws NoSuchPaddingException
            , NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(original);
        return encrypted;
    }

    public static byte[] decrypt(byte[] encrypted, PrivateKey privateKey) throws NoSuchPaddingException
            , NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryped = cipher.doFinal(encrypted);
        return decryped;
    }
}
