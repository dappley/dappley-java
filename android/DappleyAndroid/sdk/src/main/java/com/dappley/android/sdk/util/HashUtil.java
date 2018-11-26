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
    private static final byte[] VERSION_USER = new byte[]{0x5A};
    private static final byte[] VERSION_CONTRACT = new byte[]{0x58};

    /**
     * Generate a hash byte array from publicKey bytes
     * @param publicKeyBytes user's public secret key bytes
     * @return byte[] hash bytes
     */
    public static byte[] getPubKeyHash(byte[] publicKeyBytes) {
        byte[] shaBytes = Sha3Digest.sha3256(publicKeyBytes);
        byte[] ripeBytes = RipemdDigest.ripemd160(shaBytes);
        // add version head to pubKeyHash
        byte[] pubKeyHash = ByteUtil.concat(VERSION_USER, ripeBytes);
        return pubKeyHash;
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
     * @param pubKey publicKey in BigInteger format
     * @return byte[] hash bytes
     */
    public static byte[] getPubKeyHash(BigInteger pubKey) {
        byte[] pubBytes = getPubKeyBytes(pubKey);
        return getPubKeyHash(pubBytes);
    }

    /**
     * Transform pubKey into byte array.
     * @param pubKey public key
     * @return byte[] byte array
     */
    public static byte[] getPubKeyBytes(BigInteger pubKey) {
        byte[] pubKeyBytes = pubKey.toByteArray();
        int byteLength = pubKey.bitLength() / 8;
        int mod = pubKey.bitLength() % 8;
        byteLength += mod > 0 ? 1 : 0;
        if (pubKeyBytes.length > byteLength) {
            pubKeyBytes = ByteUtil.slice(pubKeyBytes, pubKeyBytes.length - byteLength, byteLength);
        }
        return pubKeyBytes;
    }

    /**
     * Recovery pubKeyHash value from address.
     * @param address wallet address in Base58 format
     * @return byte[] pubKeyHash
     */
    public static byte[] getPubKeyHash(String address) {
        // decode address in Base58 format
        byte[] addrs = Base58.decode(address);
        // pubKeyHash[0 : len(pubKeyHash)-4]
        byte[] pubKeyHash = ByteUtil.slice(addrs, 0, addrs.length - Constant.ADDRESS_CHECKSUM_LENGTH);
        return pubKeyHash;
    }

    public static byte[] fromECDSAPrivateKey(BigInteger privateKey) {
        return privateKey.toByteArray();
    }

    public static byte[] secp256k1Sign(byte[] data, byte[] privKeyBytes) {
        byte[] sign = Secp256k1.Sign(data, privKeyBytes);
        return sign;
    }

    /**
     * Get the checksum of pubKeyHash
     * <p>Use sha256 algorithm compute twice to get a hash byte array, and then get the first N bytes as the checksum.</p>
     * @param pubKeyHash orginal data
     * @param length the checksum's length
     * @return byte[] the checksum of versionedPayload
     */
    public static byte[] getPubKeyHashChecksum(byte[] pubKeyHash, int length) {
        byte[] firstSHA = ShaDigest.sha256(pubKeyHash);
        byte[] secondSHA = ShaDigest.sha256(firstSHA);
        return ByteUtil.slice(secondSHA, 0, length);
    }
}
