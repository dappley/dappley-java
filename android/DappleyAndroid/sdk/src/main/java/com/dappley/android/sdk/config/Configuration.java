package com.dappley.android.sdk.config;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SDK configuration values
 */
public class Configuration {
    private static final String TAG = "Configuration";
    private static Configuration configuration;
    /**
     * IP of remote RPC server
     */
    private String serverIp;
    /**
     * Port of RPC service
     */
    private int serverPort;

    /**
     * Returns the instance of Configuration class
     * @param context
     * @return Configuration obj
     */
    public static Configuration getInstance(Context context) {
        if (configuration == null) {
            configuration = new Configuration();
            configuration.init(context);
        }
        return configuration;
    }

    /**
     * Read node.properties once
     * @param context
     */
    private void init(Context context) {
        InputStream in = null;
        try {
            Properties prop = new Properties();
            in = context.getAssets().open("node.properties");
            prop.load(in);
            serverIp = prop.getProperty("serverIp", "");
            String port = prop.getProperty("serverPort", "");
            if (port != null && port.length() > 0) {
                serverPort = Integer.parseInt(port);
            } else {
                serverPort = 135;
            }
            Log.i(TAG, "init: serverIp:" + serverIp + ", serverPort:" + serverPort);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "init: ", e);
        } catch (IOException e) {
            Log.e(TAG, "init: ", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }
}
