package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dappley.android.sdk.config.Configuration;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ConfigurationTest {

    @Test
    public void testReadConfig() {
        Context context = InstrumentationRegistry.getTargetContext();
        String serverIp = Configuration.getInstance(context).getServerIp();
        assertEquals("config read failure.", serverIp, "192.168.1.66");
    }
}
