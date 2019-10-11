package com.dappley.java.test;

import com.dappley.java.sdk.util.DataHashUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;

@Slf4j
public class DataHashTest {
    @Test
    public void testGetFileSHA256() {
        String filePath = "D:\\readme.txt";
        String hash1 = DataHashUtil.getFileSHA256(filePath);
        log.debug("hash1: " + hash1);
        File file = new File(filePath);
        String hash2 = DataHashUtil.getFileSHA256(file);
        log.debug("hash2: " + hash2);
        Assert.assertEquals(hash1, hash2);
    }

    @Test
    public void testGetDataSHA256() throws UnsupportedEncodingException {
        String data = "abc";
        String hash1 = DataHashUtil.getDataSHA256(data);
        log.debug("hash1: " + hash1);
        byte[] bytes = data.getBytes("UTF-8");
        String hash2 = DataHashUtil.getDataSHA256(bytes);
        log.debug("hash2: " + hash2);
        Assert.assertEquals(hash1, hash2);
    }
}
