package com.dappley.android.sdk.crypto;

import org.spongycastle.jcajce.provider.digest.SHA3;
import org.spongycastle.util.encoders.Hex;

/**
 * SHA-3(Secure Hash Algorithm 3) algorithm tool
 * <p>This class may generate different bit length hash digest. They are 224/256/384/512.</p>
 * <p>Core library depends on <a href=http://www.bouncycastle.org/>Bouncy Castle</a>.</p>
 */
public class Sha3Digest {

    /**
     * Returns 224 bits SHA-3 digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] sha3224(byte[] data) {
        SHA3.Digest224 digest = new SHA3.Digest224();
        return digest.digest(data);
    }

    /**
     * Returns 256 bits SHA-3 digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] sha3256(byte[] data) {
        SHA3.Digest256 digest = new SHA3.Digest256();
        return digest.digest(data);
    }

    /**
     * Returns 384 bits SHA-3 digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] sha3384(byte[] data) {
        SHA3.Digest384 digest = new SHA3.Digest384();
        return digest.digest(data);
    }

    /**
     * Returns 512 bits SHA-3 digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] sha3512(byte[] data) {
        SHA3.Digest512 digest = new SHA3.Digest512();
        return digest.digest(data);
    }

    /**
     * Convert byte datas into Hex format.
     * @param data Original data
     * @return String data in Hex format
     */
    public static String digestToString(byte[] data) {
        return Hex.toHexString(data);
    }
}
