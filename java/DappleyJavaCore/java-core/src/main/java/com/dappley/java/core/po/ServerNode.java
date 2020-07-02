package com.dappley.java.core.po;

import lombok.Data;

/**
 * Rpc network node address
 */
@Data
public class ServerNode {
    private String host;
    private int port;
}
