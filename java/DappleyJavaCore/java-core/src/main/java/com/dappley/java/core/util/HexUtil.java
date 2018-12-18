package com.dappley.java.core.util;

import com.google.protobuf.ByteString;

import org.spongycastle.util.encoders.Hex;

/**
 * Utils of Hex string format.
 */
public class HexUtil {

    /**
     * Convert byte[] into String in Hex format.
     * @param bytes original data
     * @return String hex format string
     */
    public static String toHex(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

    /**
     * Convert byteString into String in Hex format.
     * @param byteString original data
     * @return String hex format string
     */
    public static String toHex(ByteString byteString) {
        return toHex(byteString.toByteArray());
    }

    /**
     * Convert hex string into byte[] .
     * @param hex original hex format data
     * @return byte[] decoded byte[]
     */
    public static byte[] toBytes(String hex) {
        return Hex.decode(hex);
    }

    /**
     * Convert hex string into ByteString .
     * @param hex riginal hex format data
     * @return ByteString decoded ByteString
     */
    public static ByteString toByteString(String hex) {
        return ByteString.copyFrom(toBytes(hex));
    }
}
