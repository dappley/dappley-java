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
     * @param transaction
     * @return int errorCode
     */
    public int sendTransaction(Transaction transaction) {
        TransactionProto.Transaction tx = transaction.toProto();
        int errorCode = protocalProvider.sendTransaction(tx);
        return errorCode;
    }
}
