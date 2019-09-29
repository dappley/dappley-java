package com.dappley.java.core.net;

import com.dappley.java.core.po.Block;
import com.dappley.java.core.po.ContractQueryResult;
import com.dappley.java.core.po.Transaction;
import com.dappley.java.core.po.Utxo;

import java.math.BigInteger;
import java.util.List;

/**
 * Blockchain data provider interface.
 * @see RemoteDataProvider
 */
public interface DataProvider {

    /**
     * Release resources or connections.
     */
    void release();

    /**
     * Returns all unspent balance list.(utxo list)
     * @param address wallet address
     * @return List<Utxo>
     */
    List<Utxo> getUtxos(String address);

    /**
     * Returns Block info by the hash value
     * @param hash block's hash
     * @return Block data
     */
    Block getBlockByHash(String hash);

    /**
     * Returns several Blocks after start hash in number of count.
     * <p>If the chain has been forked, the start hashes will be used in order to find the forked position.</p>
     * <p><code>startHashs</code>: the first one is the chain' tail. It's grouped by reverse order.</p>
     * @param startHashs hash list.
     * @param count      the total back num
     * @return List<Block> block list
     */
    List<Block> getBlocks(List<String> startHashs, int count);

    /**
     * Returns the balance of current address.
     * @param address wallet address
     * @return BigInteger wallet balance
     */
    BigInteger getBalance(String address);

    /**
     * Returns the gas consumption of current contract.
     * @param transaction
     * @return long gas consumption
     */
    BigInteger estimateGas(Transaction transaction);

    /**
     * Returns current gas price
     * @return long gas price
     */
    BigInteger getGasPrice();

    /**
     * Returns data storage query result from blockchain
     * <p>If key is not null, it will return the value of key in storage if there exists.</p>
     * <p>If key is null and value is not null, it still return relevant key and value in storage if there exists.</p>
     * @param contractAddress contract address
     * @param key             storage key
     * @param value           storage value
     * @return ContractQueryResult
     */
    ContractQueryResult contractQuery(String contractAddress, String key, String value);
}
