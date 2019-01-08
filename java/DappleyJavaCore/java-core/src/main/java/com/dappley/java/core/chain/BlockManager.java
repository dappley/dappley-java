package com.dappley.java.core.chain;

import com.dappley.java.core.crypto.Sha3Digest;
import com.dappley.java.core.po.*;
import com.dappley.java.core.util.ByteUtil;
import com.dappley.java.core.util.Constant;
import com.dappley.java.core.util.HashUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils to handle Block datas.
 */
@Slf4j
public class BlockManager {
    private static final String GENESIS_COIN_BASE_DATA = "Hello world";
    private static final String SUBSIDY = "10";
    private static final String GENESIS_ADDRESS = "121yKAXeG4cw6uaGCBYjWk9yTWmMkhcoDD";

    /**
     * Generate genesis block
     * @return Block genesis block
     * @throws UnsupportedEncodingException
     */
    public static Block newGenesisBlock() {
        // set input
        TxInput txInput = new TxInput();
        txInput.setTxId(ByteUtil.EMPTY_BYTE);
        txInput.setVout(-1);
        txInput.setSignature(ByteUtil.EMPTY_BYTE);
        try {
            txInput.setPubKey(GENESIS_COIN_BASE_DATA.getBytes(Constant.CHARSET_UTF_8));
        } catch (UnsupportedEncodingException e) {
            log.error("newGenesisBlock: ", e);
        }

        // set output
        TxOutput txOutput = new TxOutput();
        txOutput.setValue(new BigInteger(SUBSIDY).toByteArray());
        txOutput.setPubKeyHash(HashUtil.getPublicKeyHash(GENESIS_ADDRESS));

        Transaction transaction = new Transaction();
        transaction.addTxInput(txInput);
        transaction.addTxOutput(txOutput);
        transaction.setTip(BigInteger.ZERO);
        // calculate ID of transaction
        transaction.createId();

        List<Transaction> transactions = new ArrayList<>(1);
        transactions.add(transaction);

        // initialize block header info
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setPrevHash(ByteUtil.EMPTY_BYTE);
        blockHeader.setNonce(0);
        //July 23,2018 17:42 PST
        blockHeader.setTimestamp(1532392928);
        blockHeader.setHeight(0);
        blockHeader.setHash(calculateHash(blockHeader, transactions));

        // create genesis block
        Block block = new Block();
        block.setHeader(blockHeader);
        block.setTransactions(transactions);
        return block;
    }

    /**
     * Returns block's hash value
     * @param blockHeader  header info
     * @param transactions transaction list
     * @return byte[] hash value
     */
    public static byte[] calculateHash(BlockHeader blockHeader, List<Transaction> transactions) {
        return calculateHashWithoutNonce(blockHeader, transactions);
    }

    /**
     * Returns block's hash value without nonce
     * @param blockHeader  header info
     * @param transactions transaction list
     * @return byte[] hash value
     */
    public static byte[] calculateHashWithoutNonce(BlockHeader blockHeader, List<Transaction> transactions) {
        byte[] prevHash = blockHeader.getPrevHash();
        byte[] txHash = TransactionManager.hashTransactions(transactions);
        byte[] timeHash = ByteUtil.long2Bytes(blockHeader.getTimestamp());
        // concat prevHash/txHash/timeHash
        byte[] data = ByteUtil.joinBytes(prevHash, txHash, timeHash);
        return Sha3Digest.sha3256(data);
    }

}
