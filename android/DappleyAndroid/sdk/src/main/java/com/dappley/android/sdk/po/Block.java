package com.dappley.android.sdk.po;

import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Block object.
 * <p>Can be Transformed by BlockProto.Block.</p>
 * <p>Mainly used in java sdk.</p>
 */
@Data
public class Block {
    /**
     * Block info
     */
    private BlockHeader header;
    /**
     * Inner transaction list
     */
    private List<Transaction> transactions;
    /**
     * Previous Block hash value
     */
    private byte[] parentHash;

    public Block() {
    }

    /**
     * Constructor with BlockProto.Block
     * @param block BlockProto.Block
     */
    public Block(BlockProto.Block block) {
        this.parseProto(block);
    }

    /**
     * Parse BlockProto.Block to this object.
     * @param block protobuf obj
     */
    public void parseProto(BlockProto.Block block) {
        if (block.getHeader() != null) {
            this.setHeader(new BlockHeader(block.getHeader()));
        }

        List<TransactionProto.Transaction> transactionsList = block.getTransactionsList();
        if (transactionsList != null) {
            List<Transaction> transactions = new ArrayList<>(transactionsList.size());
            for (TransactionProto.Transaction ts : transactionsList) {
                transactions.add(new Transaction(ts));
            }
            this.setTransactions(transactions);
        }

        this.setParentHash(block.getParentHash() == null ? null : block.getParentHash().toByteArray())
        ;
    }

    /**
     * Convert to BlockProto.Block
     * @return BlockProto.Block
     */
    public BlockProto.Block toProto() {
        if (this.getHeader() == null) {
            return null;
        }
        BlockProto.Block.Builder builder = BlockProto.Block.newBuilder();
        builder.setHeader(this.getHeader().toProto());

        List<Transaction> transactions = this.getTransactions();
        if (transactions != null) {
            TransactionProto.Transaction transactionProto;
            for (Transaction transaction : transactions) {
                transactionProto = transaction.toProto();
                if (transactionProto == null) {
                    continue;
                }
                builder.addTransactions(transactionProto);
            }
        }
        if (this.getParentHash() != null) {
            builder.setParentHash(ByteString.copyFrom(this.getParentHash()));
        }
        return builder.build();
    }

    /**
     * Add a transaction info into block object.
     * @param transaction transaction info
     */
    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
    }
}
