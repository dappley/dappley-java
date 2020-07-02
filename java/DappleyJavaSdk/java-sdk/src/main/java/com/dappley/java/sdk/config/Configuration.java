package com.dappley.java.sdk.config;

import com.dappley.java.core.po.ServerNode;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * SDK configuration values
 */
@Slf4j
public class Configuration {
    private static Configuration configuration;
    /**
     * Server host info of remote RPC server
     */
    private ServerNode[] serverNodes;

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
        String nodeConfig = readNodeConfig();
        ServerNode[] serverNodes = new Gson().fromJson(nodeConfig, ServerNode[].class);
        this.serverNodes = serverNodes;
    }

    /**
     * Read node config file content
     * @return
     */
    private String readNodeConfig() {
        InputStream in = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            in = Configuration.class.getResourceAsStream("/server.conf");
            inputReader = new InputStreamReader(in);
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("readNodeConfig: ", e);
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public ServerNode[] getServerNodes() {
        return serverNodes;
    }
}
