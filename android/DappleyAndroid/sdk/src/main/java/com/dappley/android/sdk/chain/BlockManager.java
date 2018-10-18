package com.dappley.android.sdk.chain;

import com.dappley.android.sdk.crypto.ShaDigest;
import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.dappley.android.sdk.util.ByteUtil;
import com.dappley.android.sdk.util.HashUtil;
import com.google.protobuf.ByteString;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils to handle Block datas.
 */
public class BlockManager {
    private static final String GENESIS_COIN_BASE_DATA = "Hello world";
    private static final String SUBSIDY = "10";
    private static final String GENESIS_ADDRESS = "16PencPNnF8CiSx2EBGEd1axhf7vuHCouj";

    /**
     * Generate genesis block
     * @return BlockProto.Block genesis block
     * @throws UnsupportedEncodingException
     */
    public static BlockProto.Block newGenesisBlock() throws UnsupportedEncodingException {
        TransactionProto.TXInput txInput = TransactionProto.TXInput.newBuilder()
                .setTxid(ByteUtil.EMPTY_BYTE_STRING)
                .setVout(-1)
                .setSignature(ByteUtil.EMPTY_BYTE_STRING)
                .setPubKey(ByteString.copyFrom(GENESIS_COIN_BASE_DATA, "UTF-8"))
                .build();

        TransactionProto.TXOutput txOutput = TransactionProto.TXOutput.newBuilder()
                .setValue(ByteString.copyFrom(new BigInteger(SUBSIDY).toByteArray()))
                .setPubKeyHash(ByteString.copyFrom(HashUtil.getPubKeyHash(GENESIS_ADDRESS)))
                .build();

        TransactionProto.Transaction.Builder txBuilder = TransactionProto.Transaction.newBuilder()
                .setID(ByteUtil.EMPTY_BYTE_STRING)
                .addVin(txInput)
                .addVout(txOutput)
                .setTip(0);
        // calculate ID of transaction
        txBuilder.setID(TransactionManager.newId(txBuilder));

        TransactionProto.Transaction transaction = txBuilder.build();
        List<TransactionProto.Transaction> transactions = new ArrayList<>(1);
        transactions.add(transaction);

        BlockProto.BlockHeader.Builder blockHeaderBuilder = BlockProto.BlockHeader.newBuilder()
                .setHash(ByteString.copyFrom(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}))
                .setPrevhash(ByteUtil.EMPTY_BYTE_STRING)
                .setNonce(0)
                //July 23,2018 17:42 PST
                .setTimestamp(1532392928)
                .setHeight(0);
        blockHeaderBuilder.setHash(ByteString.copyFrom(calculateHash(blockHeaderBuilder, transactions)));

        // build block
        BlockProto.Block.Builder blockBuilder = BlockProto.Block.newBuilder()
                .setHeader(blockHeaderBuilder.build())
                .addTransactions(transaction);

        BlockProto.Block block = blockBuilder.build();
        return block;
    }

    public static byte[] calculateHash(BlockProto.BlockHeader.Builder blockHeaderBuilder, List<TransactionProto.Transaction> transactions) {
        return calculateHashWithoutNonce(blockHeaderBuilder, transactions);
    }

    public static byte[] calculateHashWithoutNonce(BlockProto.BlockHeader.Builder blockHeaderBuilder, List<TransactionProto.Transaction> transactions) {
        byte[] prevHash = blockHeaderBuilder.getPrevhash().toByteArray();
        byte[] txHash = TransactionManager.hashTransactions(transactions);
        // TODO confirm IntToHex method is right
        byte[] timeHash = ByteUtil.fromLong(blockHeaderBuilder.getTimestamp());
        // concat prevHash/txHash/timeHash
        byte[] data = ByteUtil.concat(prevHash, txHash);
        data = ByteUtil.concat(data, timeHash);
        return ShaDigest.sha256(data);
    }

}
