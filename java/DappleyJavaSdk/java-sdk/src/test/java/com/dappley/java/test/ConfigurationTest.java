package com.dappley.java.test;

import com.dappley.java.sdk.config.Configuration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationTest {

    @Test
    public void testReadConfig() {
        String serverIp = Configuration.getInstance().getServerIp();
        assertEquals("config read failure.", serverIp, "192.168.1.61");
    }
}
