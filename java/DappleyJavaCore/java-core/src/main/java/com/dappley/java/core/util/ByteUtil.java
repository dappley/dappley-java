package com.dappley.java.core.util;

import com.google.protobuf.ByteString;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Collection;

/**
 * Provides several methods about byte calculate.
 */
public class ByteUtil {
    public static final ByteString EMPTY_BYTE_STRING = ByteString.copyFrom(new byte[]{});
    public static final byte[] EMPTY_BYTE = new byte[]{};

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

    /**
     * Convert int to bytes in big-endian order
     * @param value int to convert
     * @return byte[]
     */
    public static byte[] int2Bytes(int value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(value);
        return byteBuffer.array();
    }

    /**
     * Convert long to bytes in big-endian order
     * @param value long to convert
     * @return byte[]
     */
    public static byte[] long2Bytes(long value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(value);
        return byteBuffer.array();
    }

    /**
     * Convert BigInteger to byte and trim leading zeros
     * @param value original data
     * @return byte[]
     */
    public static byte[] bigInteger2Bytes(BigInteger value) {
        byte[] rawDatas = value.toByteArray();

        int index = 0;
        for (; index < rawDatas.length; index++) {
            if (rawDatas[index] != 0) {
                break;
            }
        }

        int leftSize = rawDatas.length - index;
        byte[] result = new byte[leftSize];

        System.arraycopy(rawDatas, index, result, 0, leftSize);
        return result;
    }

    /**
     * Omitting sign indication byte.
     * <br><br>
     * Instead of {@link org.spongycastle.util.BigIntegers#asUnsignedByteArray(BigInteger)}
     * <br>we use this custom method to avoid an empty array in case of BigInteger.ZERO
     *
     * @param value - any big integer number. A <code>null</code>-value will return <code>null</code>
     * @return A byte array without a leading zero byte if present in the signed encoding.
     *      BigInteger.ZERO will return an array with length 1 and byte-value 0.
     */
    public static byte[] bigInteger2BytesOmitSign(BigInteger value) {
        if (value == null) {
            return null;
        }
        byte[] data = value.toByteArray();

        if (data.length != 1 && data[0] == 0) {
            byte[] tmp = new byte[data.length - 1];
            System.arraycopy(data, 1, tmp, 0, tmp.length);
            data = tmp;
        }
        return data;
    }

    /**
     * The regular {@link BigInteger#toByteArray()} method isn't quite what we often need:
     * it appends a leading zero to indicate that the number is positive and may need padding.
     *
     * @param b the integer to format into a byte array
     * @param numBytes the desired size of the resulting byte array
     * @return numBytes byte long array.
     */
    public static byte[] bigInteger2Bytes(BigInteger b, int numBytes) {
        if (b == null) {
            return null;
        }
        byte[] bytes = new byte[numBytes];
        byte[] biBytes = b.toByteArray();
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    /**
     * Join bytes in list into one
     * @param list src bytes
     * @return byte[] new array
     */
    public static byte[] joinBytes(byte[]... list) {
        int length = 0;
        for (byte[] bytes : list) {
            if (bytes != null && bytes.length > 0) {
                length += bytes.length;
            }
        }

        byte[] result = new byte[length];
        int startPos = 0;
        for (byte[] bytes : list) {
            if (bytes != null && bytes.length > 0) {
                System.arraycopy(bytes, 0, result, startPos, bytes.length);
                startPos += bytes.length;
            }
        }
        return result;
    }

    /**
     * Join bytes in collection into one
     * @param list src bytes
     * @return byte[] new array
     */
    public static byte[] joinBytes(Collection<byte[]> list) {
        byte[][] bytes = list.toArray(new byte[list.size()][]);
        return joinBytes(bytes);
    }
}
