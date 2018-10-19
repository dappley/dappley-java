package com.dappley.android.sdk;


import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class CompressTest {

    @Test
    public void compressTest() {
        String string = "com.dappley.android.sdk.protobuf";
        byte[] bytes = string.getBytes();
        System.out.println(bytes.length);
        try {
            byte[] newBytes = compress(bytes);
            System.out.println(newBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 压缩
    public static byte[] compress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(data);
        gzip.close();
        return out.toByteArray();//out.toString("ISO-8859-1");
    }

    public static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        return compress(str.getBytes("utf-8"));
    }
}
