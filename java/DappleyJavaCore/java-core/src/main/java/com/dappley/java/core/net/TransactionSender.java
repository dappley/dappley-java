package com.dappley.java.core.net;

import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.core.po.Transaction;
import com.dappley.java.core.protobuf.TransactionProto;

/**
 * A sender of transaction. Send transaction datas to a neighbor node.
 */
public class TransactionSender {
    private ProtocalProvider protocalProvider;

    public TransactionSender(ProtocalProvider protocalProvider) {
        this.protocalProvider = protocalProvider;
    }

    /**
     * Send transaction info
     * <p>Throws exception if transaction failed</p>
     * @param transaction
     */
    public SendTxResult sendTransaction(Transaction transaction) {
        TransactionProto.Transaction tx = transaction.toProto();
        return protocalProvider.sendTransaction(tx);
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
