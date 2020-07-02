package com.dappley.java.test;

import com.dappley.java.core.po.ServerNode;
import com.dappley.java.sdk.config.Configuration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationTest {

    @Test
    public void testReadConfig() {
        ServerNode[] serverNodes = Configuration.getInstance().getServerNodes();
        assertEquals("config read failure.", 2, serverNodes.length);
    }
}
