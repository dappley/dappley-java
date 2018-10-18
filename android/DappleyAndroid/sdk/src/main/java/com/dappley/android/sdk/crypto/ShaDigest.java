package com.dappley.android.sdk.crypto;

import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.jcajce.provider.digest.SHA512;

/**
 * SHA(Secure Hash Algorithm) algorithm tool
 * <p>This class may generate different bit length hash digest. They are 256/512.</p>
 * <p>Core library depends on <a href=http://www.bouncycastle.org/>Bouncy Castle</a>.</p>
 */
public class ShaDigest {

    /**
     * Returns 256 bits SHA digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] sha256(byte[] data) {
        SHA256.Digest digest = new SHA256.Digest();
        return digest.digest(data);
    }

    /**
     * Returns 512 bits SHA digest.
     * @param data Original data
     * @return byte[] Message digest
     */
    public static byte[] sha512(byte[] data) {
        SHA512.Digest digest = new SHA512.Digest();
        return digest.digest(data);
    }
}
