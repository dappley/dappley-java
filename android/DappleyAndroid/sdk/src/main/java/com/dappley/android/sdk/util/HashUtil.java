package com.dappley.android.sdk.util;

import com.dappley.android.sdk.crypto.RipemdDigest;
import com.dappley.android.sdk.crypto.Secp256k1;
import com.dappley.android.sdk.crypto.Sha3Digest;
import com.dappley.android.sdk.crypto.ShaDigest;

import java.math.BigInteger;
import java.security.PublicKey;

/**
 * Provides several methods used in wallet address generate.
 */
public class HashUtil {

    /**
     * Generate a hash byte array from publicKey bytes
     * @param publicKeyBytes user's public secret key bytes
     * @return byte[] hash bytes
     */
    public static byte[] getPubKeyHash(byte[] publicKeyBytes) {
        byte[] shaBytes = Sha3Digest.sha3256(publicKeyBytes);
        byte[] ripeBytes = RipemdDigest.ripemd160(shaBytes);
        return ripeBytes;
    }

    /**
     * Generate a hash byte array from publicKey
     * <p>Rules depend on main chain</p>
     * @param publicKey user's public secret key
     * @return byte[] hash bytes
     */
    public static byte[] getPubKeyHash(PublicKey publicKey) {
        byte[] pubBytes = publicKey.getEncoded();
        return getPubKeyHash(pubBytes);
    }

    /**
     * Generate a hash byte array from publicKey
     * @param pubInteger publicKey in BigInteger format
     * @return byte[] hash bytes
     */
    public static byte[] getPubKeyHash(BigInteger pubInteger) {
        byte[] pubBytes = pubInteger.toByteArray();
        return getPubKeyHash(pubBytes);
    }

    /**
     * Recovery pubKeyHash value from address.
     * @param address wallet address in Base58 format
     * @return byte[] pubKeyHash
     */
    public static byte[] getPubKeyHash(String address) {
        // decode address in Base58 format
        byte[] addrs = Base58.decode(address);
        // pubKeyHash[1 : len(pubKeyHash)-4]
        byte[] pubKeyHash = ByteUtil.slice(addrs, 1, addrs.length - 4);
        return pubKeyHash;
    }

    public static byte[] fromECDSAPrivateKey(BigInteger privateKey){
        return privateKey.toByteArray();
    }

    public static byte[] secp256k1Sign(byte[] data, byte[] privKeyBytes){
        byte[] sign = Secp256k1.Sign(data, privKeyBytes);
        return sign;
    }

    /**
     * Get the checksum of versionedPayload
     * <p>Use sha256 algorithm compute twice to get a hash byte array, and then get the first N bytes as the checksum.</p>
     * @param versionedPayload orginal data
     * @param length the checksum's length
     * @return byte[] the checksum of versionedPayload
     */
    public static byte[] getAddressChecksum(byte[] versionedPayload, int length) {
        byte[] firstSHA = ShaDigest.sha256(versionedPayload);
        byte[] secondSHA = ShaDigest.sha256(firstSHA);
        return ByteUtil.slice(secondSHA, 0, length);
    }
}
