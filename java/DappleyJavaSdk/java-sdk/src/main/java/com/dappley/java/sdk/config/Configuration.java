package com.dappley.java.sdk.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SDK configuration values
 */
@Slf4j
public class Configuration {
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
     * @return Configuration obj
     */
    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration();
            configuration.init();
        }
        return configuration;
    }

    /**
     * Read node.properties once
     */
    private void init() {
        InputStream in = null;
        try {
            Properties prop = new Properties();
            in = Configuration.class.getResourceAsStream("/node.properties");
            prop.load(in);
            serverIp = prop.getProperty("serverIp", "");
            String port = prop.getProperty("serverPort", "");
            if (port != null && port.length() > 0) {
                serverPort = Integer.parseInt(port);
            } else {
                serverPort = 135;
            }
            log.info("init: serverIp:" + serverIp + ", serverPort:" + serverPort);
        } catch (FileNotFoundException e) {
            log.error("init: ", e);
        } catch (IOException e) {
            log.error("init: ", e);
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
