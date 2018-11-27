package com.dappley.android.sdk.util;

import com.dappley.android.sdk.crypto.RipemdDigest;
import com.dappley.android.sdk.crypto.Secp256k1;
import com.dappley.android.sdk.crypto.Sha3Digest;
import com.dappley.android.sdk.crypto.ShaDigest;

import java.math.BigInteger;

/**
 * Provides several methods used in wallet address generate.
 */
public class HashUtil {
    public static final byte[] VERSION_USER = new byte[]{0x5A};
    public static final byte[] VERSION_CONTRACT = new byte[]{0x58};

    /**
     * Returns public key hash value
     * @param publicKeyBytes
     * @return byte[] hash bytes
     */
    private static byte[] getPublicKeyHash(byte[] publicKeyBytes) {
        byte[] shaBytes = Sha3Digest.sha3256(publicKeyBytes);
        byte[] pubKeyHash = RipemdDigest.ripemd160(shaBytes);
        return pubKeyHash;
    }

    /**
     * Recovery pubKeyHash value from address.
     * @param address wallet address in Base58 format
     * @return byte[] pubKeyHash
     */
    public static byte[] getPublicKeyHash(String address) {
        // decode address in Base58 format
        byte[] addrs = Base58.decode(address);
        // pubKeyHash[0 : len(pubKeyHash)-4]
        byte[] pubKeyHash = ByteUtil.slice(addrs, 0, addrs.length - Constant.ADDRESS_CHECKSUM_LENGTH);
        return pubKeyHash;
    }

    /**
     * Generate a hash byte array from publicKey bytes
     * @param publicKeyBytes user's public secret key bytes
     * @return byte[] hash bytes
     */
    public static byte[] getUserPubKeyHash(byte[] publicKeyBytes) {
        byte[] pubKeyHash = getPublicKeyHash(publicKeyBytes);
        // add version head to pubKeyHash
        pubKeyHash = ByteUtil.concat(VERSION_USER, pubKeyHash);
        return pubKeyHash;
    }

    /**
     * Generate a hash byte array from publicKey
     * @param pubKey publicKey in BigInteger format
     * @return byte[] hash bytes
     */
    public static byte[] getUserPubKeyHash(BigInteger pubKey) {
        byte[] pubBytes = getPubKeyBytes(pubKey);
        return getUserPubKeyHash(pubBytes);
    }

    /**
     * Generate a contract hash byte array from publicKey
     * @param pubKey publicKey in BigInteger format
     * @return byte[] hash bytes
     */
    public static byte[] getContractPubKeyHash(BigInteger pubKey) {
        byte[] pubBytes = getPubKeyBytes(pubKey);
        return getContractPubKeyHash(pubBytes);
    }

    /**
     * Generate a contract hash byte array from publicKey bytes
     * @param publicKeyBytes contract's public secret key bytes
     * @return byte[] hash bytes
     */
    public static byte[] getContractPubKeyHash(byte[] publicKeyBytes) {
        byte[] pubKeyHash = getPublicKeyHash(publicKeyBytes);
        // add version head to pubKeyHash
        pubKeyHash = ByteUtil.concat(VERSION_CONTRACT, pubKeyHash);
        return pubKeyHash;
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

    /**
     * convert privateKey to bytes
     * @param privateKey
     * @return byte[] byte array
     */
    public static byte[] fromECDSAPrivateKey(BigInteger privateKey) {
        return privateKey.toByteArray();
    }

    /**
     * Returns sign data of message
     * @param data message
     * @param privKeyBytes
     * @return byte[] sign data
     */
    public static byte[] secp256k1Sign(byte[] data, byte[] privKeyBytes) {
        byte[] sign = Secp256k1.Sign(data, privKeyBytes);
        return sign;
    }
}
