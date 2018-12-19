package com.dappley.java.core.crypto;

import org.spongycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utils of AES crypto encrypt/decrypt.
 * <p>Call encryptToHex to convert the result into Hex format in order to save on the disk.
 * Call decryptFromHex to decrypt a secret string in Hex formt.</p>
 */
public class AesCipher {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGOTIRHM = "AES";
    private static final byte[] IV_DEFAULT = {0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38,
            0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31};

    /**
     * Returns the encypted data in hex format by 'AES' algorithm.
     * @param message the orginal data
     * @param key     secret key
     * @return String hex format encypted data
     */
    public static String encryptToHex(String message, String key) {
        byte[] enc = encrypt(key.getBytes(), key);
        return Hex.toHexString(enc);
    }

    public static String encryptToHex(byte[] messageBytes, String key) {
        byte[] enc = encrypt(messageBytes, key);
        return Hex.toHexString(enc);
    }

    /**
     * Returns the encypted bytes by 'AES' algorithm.
     * @param messageBytes the orginal data
     * @param key          secret key
     * @return byte[] encypted data
     */
    public static byte[] encrypt(byte[] messageBytes, String key) {
        try {
            byte[] keyBytes = RipemdDigest.ripemd128(key.getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGOTIRHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(IV_DEFAULT));
            byte[] enc = cipher.doFinal(messageBytes);
            return enc;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the decrypted data by 'AES' algorithm.
     * @param message the encrypted data in hex format
     * @param key     secret key
     * @return String decrypted data
     */
    public static byte[] decryptBytesFromHex(String message, String key) {
        byte[] dec = decrypt(Hex.decode(message), key);
        return dec;
    }

    /**
     * Returns the decrypted data by 'AES' algorithm.
     * @param messageBytes the encrypted data
     * @param key          secret key
     * @return byte[] decrypted data
     */
    public static byte[] decrypt(byte[] messageBytes, String key) {
        try {
            byte[] keyBytes = RipemdDigest.ripemd128(key.getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGOTIRHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV_DEFAULT));
            byte[] dec = cipher.doFinal(messageBytes);
            return dec;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
