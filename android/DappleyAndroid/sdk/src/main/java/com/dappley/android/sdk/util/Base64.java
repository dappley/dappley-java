package com.dappley.android.sdk.util;

/**
 * Base64 encoding tool. Base on <code>commons-codec</code> jar.
 * <p>Encode byte datas into Base64 format string.</p>
 * <p>Decode Base64 format string into byte datas.</p>
 */
public class Base64 {

    /**
     * Encode given datas into Base64 format and return a String data.
     * @param data original data
     * @return String encoded data string
     */
    public static String encode(byte[] data) {
        byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(data);
        return new String(encoded);
    }

    /**
     * Decode String data into byte datas using Base64 decode tool.
     * @param str original data
     * @return byte[] decoded byte datas
     */
    public static byte[] decode(String str) {
        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes());
        return decoded;
    }
}
