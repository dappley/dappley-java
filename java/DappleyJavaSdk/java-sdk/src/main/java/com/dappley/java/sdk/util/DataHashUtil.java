package com.dappley.java.sdk.util;

import com.dappley.java.core.crypto.ShaDigest;
import com.dappley.java.core.util.HexUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Util of data hash
 * <p>Support with algorithm 'sha-256'.</p>
 */
public class DataHashUtil {
    public static final String HASH_METHOD_SHA = "sha-256";

    /**
     * Returns file signature
     * @param filePath full path of file
     * @return String upper hex hash value with hex format
     */
    public static String getFileSHA256(String filePath) {
        return getFileSHA256(new File(filePath));
    }

    /**
     * Returns file signature
     * @param file
     * @return String
     */
    public static String getFileSHA256(File file) {
        if (file == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        MessageDigest shaDigest = null;
        FileInputStream fis = null;
        try {
            shaDigest = MessageDigest.getInstance(HASH_METHOD_SHA);
            fis = new FileInputStream(file);
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                shaDigest.update(byteArray, 0, bytesCount);
            }
            byte[] bytes = shaDigest.digest();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Returns data signature
     * @param bytes data
     * @return String upper hex hash value with hex format
     */
    public static String getDataSHA256(byte[] bytes) {
        return HexUtil.toHex(ShaDigest.sha256(bytes)).toUpperCase();
    }

    /**
     * Returns data signature
     * @param string data
     * @return String upper hex hash value with hex format
     */
    public static String getDataSHA256(String string) {
        try {
            return getDataSHA256(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
