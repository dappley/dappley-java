package com.dappley.android.sdk.db;

import android.content.Context;

import com.dappley.android.sdk.protobuf.BlockProto;
import com.google.protobuf.ByteString;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * Database for Block class.
 * <p>Before read/write data operations, remember to call <code>open()</code> to build connection with database server.</p>
 * <p>After read/write, remember to call <code>close()</code> to break-down the connection.</p>
 * <p>In batch operations, <code>open()</code> and <code>close()</code> should be called just one time.</p>
 */
public class BlockDB {
    private static final String DB_NAME = "block";
    private Context context;
    private DB db;

    /**
     * Constructor
     * @param context
     */
    public BlockDB(Context context) {
        this.context = context;
    }

    /**
     * Open database connection
     */
    public void open() {
        try {
            db = DBFactory.open(context, DB_NAME);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection of database and release resources.
     */
    public void close() {
        if (db == null) {
            return;
        }
        try {
            if (db.isOpen()) {
                db.close();
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save object data to database.
     * @param block object
     * @return boolean true/false
     */
    public boolean save(BlockProto.Block block) {
        boolean success = false;
        String key = block.getHeader().getHash().toStringUtf8();
        try {
            db.put(key, block);
            success = true;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean saveBytes(BlockProto.Block block) {
        boolean success = false;
        String key = block.getHeader().getHash().toStringUtf8();
        try {
            db.put(key, block.getHeader().getHash().toByteArray());
            success = true;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return success;
    }

    public byte[] getBytes(String hashString) {
        try {
            byte[] bytes = db.getBytes(hashString);
            return bytes;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read object from database.
     * @param byteString A ByteString ID(Key)
     * @return BlockProto.Block If key does not exits, returns null.
     */
    public BlockProto.Block get(ByteString byteString) {
        String hashString = byteString.toStringUtf8();
        return get(hashString);
    }

    /**
     * Read object from database.
     * @param hashString A String ID(Key)
     * @return BlockProto.Block If key does not exits, returns null.
     */
    public BlockProto.Block get(String hashString) {
        try {
            BlockProto.Block block = db.getObject(hashString, BlockProto.Block.class);
            return block;
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
