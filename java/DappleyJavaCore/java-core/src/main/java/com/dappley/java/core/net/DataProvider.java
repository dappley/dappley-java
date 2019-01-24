package com.dappley.java.core.net;

import com.dappley.java.core.po.Block;
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
}
