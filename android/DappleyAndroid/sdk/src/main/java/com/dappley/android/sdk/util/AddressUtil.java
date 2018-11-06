package com.dappley.android.sdk.util;

import android.support.annotation.NonNull;

import com.dappley.android.sdk.crypto.KeyPairTool;

import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.KeyPair;
import java.util.Arrays;

import io.opencensus.internal.StringUtil;

/**
 * Wallet address util.
 */
public class AddressUtil {

    /**
     * Create a new wallet address
     * @return String wallet address
     */
    public static String createAddress() {
        KeyPair keyPair = KeyPairTool.newKeyPair();
        ECKeyPair ecKeyPair = KeyPairTool.castToEcKeyPair(keyPair);
        return AddressUtil.createAddress(ecKeyPair);
    }

    /**
     * Recovery a wallet address from KeyPair
     * @param keyPair Key group
     * @return String wallet address
     */
    public static String createAddress(ECKeyPair keyPair) {
        // generate publicKey hash
        byte[] pubKeyHash = HashUtil.getPubKeyHash(keyPair.getPublicKey());
        return createAddress(pubKeyHash);
    }

    /**
     * Recovery a wallet address from public key
     * @param publicKey
     * @return String wallet address
     */
    public static String createAddress(BigInteger publicKey) {
        // generate publicKey hash
        byte[] pubKeyHash = HashUtil.getPubKeyHash(publicKey);
        return createAddress(pubKeyHash);
    }

    /**
     * Recover a wallet address from public key hash
     * @param pubKeyHash public key hash
     * @return String wallet address
     */
    public static String createAddress(byte[] pubKeyHash) {
        // prefix 0x00
        byte[] version = new byte[]{0x00};
        // concat version and pubKeyHash to get a new byte array
        byte[] versionedPayload = ByteUtil.concat(version, pubKeyHash);
        // get the checksum of versionedPayload
        byte[] checksum = HashUtil.getAddressChecksum(versionedPayload, Constant.ADDRESS_CHECKSUM_LENGTH);
        // append the checksum to versionedPayload's tail
        byte[] fullPayload = ByteUtil.concat(versionedPayload, checksum);
        // use Base58 encode method to get the encodes byte array
        byte[] address = Base58.encodeBytes(fullPayload);
        // return encoded String as a wallet address
        return new String(address);
    }

    /**
     * Returns if the wallet address is legal
     * @param address wallet address
     * @return boolean true/false
     */
    public static boolean validateAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        byte[] addrBytes;
        try {
            addrBytes = Base58.decode(address);
        } catch (Exception e) {
            return false;
        }
        if (addrBytes == null || addrBytes.length < Constant.ADDRESS_CHECKSUM_LENGTH) {
            return false;
        }
        byte[] actualChecksum = ByteUtil.slice(addrBytes, addrBytes.length - Constant.ADDRESS_CHECKSUM_LENGTH
                , Constant.ADDRESS_CHECKSUM_LENGTH);
        byte[] version = new byte[]{addrBytes[0]};
        byte[] pubKeyHash = ByteUtil.slice(addrBytes, 1, addrBytes.length - Constant.ADDRESS_CHECKSUM_LENGTH - 1);
        // concat version and pubKeyHash to get a new byte array
        byte[] versionedPayload = ByteUtil.concat(version, pubKeyHash);
        // get the checksum of versionedPayload
        byte[] checksum = HashUtil.getAddressChecksum(versionedPayload, Constant.ADDRESS_CHECKSUM_LENGTH);
        return Arrays.equals(actualChecksum, checksum);
    }
}
