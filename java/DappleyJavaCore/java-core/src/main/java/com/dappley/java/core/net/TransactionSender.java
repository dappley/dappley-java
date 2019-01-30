package com.dappley.java.core.net;

import com.dappley.java.core.po.Transaction;
import com.dappley.java.core.protobuf.TransactionProto;

/**
 * A sender of transaction. Send transaction datas to a neighbor node.
 */
public class TransactionSender {
    private ProtocalProvider protocalProvider;

    public TransactionSender(String serverIp, int serverPort) {
        this.protocalProvider = new RpcProtocalProvider();
        this.protocalProvider.init(serverIp, serverPort);
    }

    /**
     * Send transaction info
     * <p>Throws exception if transaction failed</p>
     * @param transaction
     */
    public void sendTransaction(Transaction transaction) {
        TransactionProto.Transaction tx = transaction.toProto();
        protocalProvider.sendTransaction(tx);
    }

    /**
     * Release rpc connections.
     */
    public void release() {
        if (protocalProvider != null) {
            protocalProvider.close();
        }
    }
}
