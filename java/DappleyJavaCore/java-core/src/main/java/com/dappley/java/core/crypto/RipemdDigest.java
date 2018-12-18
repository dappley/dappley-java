package com.dappley.java.core.crypto;

import org.spongycastle.jcajce.provider.digest.RIPEMD128;
import org.spongycastle.jcajce.provider.digest.RIPEMD160;
import org.spongycastle.jcajce.provider.digest.RIPEMD256;
import org.spongycastle.jcajce.provider.digest.RIPEMD320;
import org.spongycastle.util.encoders.Hex;

/**
 * RIPEMD(RACE Integrity Primitives Evaluation Message Digest) algorithm tool
 * <p>This class may generate different bit length hash digest. They are 128/160/256/320.</p>
 * <p>Core library depends on <a href=http://www.bouncycastle.org/>Bouncy Castle</a>.</p>
 */
public class RipemdDigest {
    /**
     * Returns 128 bits RIPEMD digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] ripemd128(byte[] data) {
        RIPEMD128.Digest digest = new RIPEMD128.Digest();
        return digest.digest(data);
    }

    /**
     * Returns 128 bits RIPEMD digest in Hex String format.
     * @param data Original data
     * @return String Message digest in Hex String format.
     */
    public static String ripemd128Hex(byte[] data) {
        byte[] b = ripemd128(data);
        return Hex.toHexString(b);
    }

    /**
     * Returns 160 bits RIPEMD digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] ripemd160(byte[] data) {
        RIPEMD160.Digest digest = new RIPEMD160.Digest();
        return digest.digest(data);
    }

    /**
     * Returns 160 bits RIPEMD digest in Hex String format.
     * @param data Original data
     * @return String Message digest in Hex String format.
     */
    public static String ripemd160Hex(byte[] data) {
        byte[] b = ripemd160(data);
        return Hex.toHexString(b);
    }

    /**
     * Returns 256 bits RIPEMD digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] ripemd256(byte[] data) {
        RIPEMD256.Digest digest = new RIPEMD256.Digest();
        return digest.digest(data);
    }

    /**
     * Returns 256 bits RIPEMD digest in Hex String format.
     * @param data Original data
     * @return String Message digest in Hex String format.
     */
    public static String ripemd256Hex(byte[] data) {
        byte[] b = ripemd256(data);
        return Hex.toHexString(b);
    }

    /**
     * Returns 320 bits RIPEMD digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] ripemd320(byte[] data) {
        RIPEMD320.Digest digest = new RIPEMD320.Digest();
        return digest.digest(data);
    }

    /**
     * Returns 320 bits RIPEMD digest in Hex String format.
     * @param data Original data
     * @return String Message digest in Hex String format.
     */
    public static String ripemd320Hex(byte[] data) {
        byte[] b = ripemd320(data);
        return Hex.toHexString(b);
    }
}
