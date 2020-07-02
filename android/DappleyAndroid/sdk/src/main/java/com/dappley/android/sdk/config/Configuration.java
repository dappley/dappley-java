package com.dappley.android.sdk.config;

import android.content.Context;
import android.util.Log;

import com.dappley.java.core.po.ServerNode;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * SDK configuration values
 */
public class Configuration {
    private static final String TAG = "Configuration";
    private static Configuration configuration;
    /**
     * Server host info of remote RPC server
     */
    private ServerNode[] serverNodes;

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
        String nodeConfig = readNodeConfig(context);
        ServerNode[] serverNodes = new Gson().fromJson(nodeConfig, ServerNode[].class);
        this.serverNodes = serverNodes;
    }

    /**
     * Read node config file content
     * @param context
     * @return
     */
    private String readNodeConfig(Context context) {
        InputStream in = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            in = context.getAssets().open("server.conf");
            inputReader = new InputStreamReader(in);
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            Log.e(TAG, "readConfig: ", e);
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
