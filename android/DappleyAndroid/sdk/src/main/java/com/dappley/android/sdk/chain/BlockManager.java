package com.dappley.android.sdk.chain;

import com.dappley.android.sdk.crypto.ShaDigest;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.BlockHeader;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.po.TxInput;
import com.dappley.android.sdk.po.TxOutput;
import com.dappley.android.sdk.util.ByteUtil;
import com.dappley.android.sdk.util.Constant;
import com.dappley.android.sdk.util.HashUtil;

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
     * @return Block genesis block
     * @throws UnsupportedEncodingException
     */
    public static Block newGenesisBlock() throws UnsupportedEncodingException {

        TxInput txInput = new TxInput();
        txInput.setTxId(ByteUtil.EMPTY_BYTE);
        txInput.setVout(-1);
        txInput.setSignature(ByteUtil.EMPTY_BYTE);
        txInput.setPubKey(GENESIS_COIN_BASE_DATA.getBytes(Constant.CHARSET_UTF_8));


        TxOutput txOutput = new TxOutput();
        txOutput.setValue(new BigInteger(SUBSIDY).toByteArray());
        txOutput.setPubKeyHash(HashUtil.getPubKeyHash(GENESIS_ADDRESS));

        Transaction transaction = new Transaction();
        transaction.setId(ByteUtil.EMPTY_BYTE);
        transaction.addTxInput(txInput);
        transaction.addTxOutput(txOutput);
        transaction.setTip(0);
        // calculate ID of transaction
        transaction.createId();

        List<Transaction> transactions = new ArrayList<>(1);
        transactions.add(transaction);

        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setHash(new byte[32]);
        blockHeader.setPrevHash(ByteUtil.EMPTY_BYTE);
        blockHeader.setNonce(0);
        //July 23,2018 17:42 PST
        blockHeader.setTimestamp(1532392928);
        blockHeader.setHeight(0);
        blockHeader.setHash(calculateHash(blockHeader, transactions));
        Block block = new Block();
        block.setHeader(blockHeader);
        block.setTransactions(transactions);
        return block;
    }

    public static byte[] calculateHash(BlockHeader blockHeader, List<Transaction> transactions) {
        return calculateHashWithoutNonce(blockHeader, transactions);
    }

    public static byte[] calculateHashWithoutNonce(BlockHeader blockHeader, List<Transaction> transactions) {
        byte[] prevHash = blockHeader.getPrevHash();
        byte[] txHash = TransactionManager.hashTransactions(transactions);
        byte[] timeHash = ByteUtil.long2Bytes(blockHeader.getTimestamp());
        // concat prevHash/txHash/timeHash
        byte[] data = ByteUtil.concat(prevHash, txHash);
        data = ByteUtil.concat(data, timeHash);
        return ShaDigest.sha256(data);
    }

}
