package com.dappley.android.sdk.net;

import android.content.Context;

import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.protobuf.TransactionProto;

/**
 * A sender of transaction. Send transaction datas to a neighbor node.
 */
public class TransactionSender {
    private ProtocalProvider protocalProvider;

    private Context context;

    public TransactionSender(Context context) {
        this.context = context;
        // TODO get a provider
        this.protocalProvider = new RpcProvider();
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
