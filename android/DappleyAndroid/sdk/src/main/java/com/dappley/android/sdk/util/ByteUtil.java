package com.dappley.android.sdk.util;

import com.google.protobuf.ByteString;

import java.math.BigInteger;

/**
 * Provides several methods about byte calculate.
 */
public class ByteUtil {
    public static final ByteString EMPTY_BYTE_STRING = ByteString.copyFrom(new byte[]{});

    /**
     * Combine two byte arrays into one.
     * @param bt1 byte array
     * @param bt2 byte array
     * @return byte[] new array
     */
    public static byte[] concat(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /**
     * Slice some data from byte array.
     * @param data source byte array
     * @param begin slice start position
     * @param count sliced data length
     * @return byte[] sliced byte array
     */
    public static byte[] slice(byte[] data, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(data, begin, bs, 0, count);
        return bs;
    }

    public static byte[] fromLong(long value) {
        return new BigInteger(String.valueOf(value)).toByteArray();
    }

    public static byte[] fromInt(int value) {
        return new BigInteger(String.valueOf(value)).toByteArray();
    }
}
