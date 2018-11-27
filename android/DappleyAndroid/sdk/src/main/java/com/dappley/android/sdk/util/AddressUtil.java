package com.dappley.android.sdk.util;

import com.dappley.android.sdk.crypto.KeyPairTool;

import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.KeyPair;
import java.util.Arrays;

/**
 * Wallet address util.
 */
public class AddressUtil {

    /**
     * Create a new wallet address
     * @return String wallet address
     */
    public static String createUserAddress() {
        KeyPair keyPair = KeyPairTool.newKeyPair();
        ECKeyPair ecKeyPair = KeyPairTool.castToEcKeyPair(keyPair);
        return AddressUtil.getUserAddress(ecKeyPair);
    }

    /**
     * Recovery a wallet address from KeyPair
     * @param keyPair Key group
     * @return String wallet address
     */
    public static String getUserAddress(ECKeyPair keyPair) {
        // generate publicKey hash
        byte[] pubKeyHash = HashUtil.getUserPubKeyHash(keyPair.getPublicKey());
        return getAddressFromPubKeyHash(pubKeyHash);
    }

    /**
     * Recovery a wallet address from public key
     * @param publicKey
     * @return String wallet address
     */
    public static String getUserAddress(BigInteger publicKey) {
        // generate publicKey hash
        byte[] pubKeyHash = HashUtil.getUserPubKeyHash(publicKey);
        return getAddressFromPubKeyHash(pubKeyHash);
    }

    /**
     * Create a new contract address
     * @return String contract address
     */
    public static String createContractAddress() {
        KeyPair keyPair = KeyPairTool.newKeyPair();
        ECKeyPair ecKeyPair = KeyPairTool.castToEcKeyPair(keyPair);
        return AddressUtil.getContractAddress(ecKeyPair);
    }

    /**
     * Recovery a contract address from KeyPair
     * @param keyPair Key group
     * @return String contract address
     */
    public static String getContractAddress(ECKeyPair keyPair) {
        // generate publicKey hash
        byte[] pubKeyHash = HashUtil.getContractPubKeyHash(keyPair.getPublicKey());
        return getAddressFromPubKeyHash(pubKeyHash);
    }

    /**
     * Recover a address from public key hash
     * @param pubKeyHash public key hash
     * @return String address
     */
    public static String getAddressFromPubKeyHash(byte[] pubKeyHash) {
        // get the checksum of pubKeyHash
        byte[] checksum = HashUtil.getPubKeyHashChecksum(pubKeyHash, Constant.ADDRESS_CHECKSUM_LENGTH);
        // append the checksum to pubKeyHash's tail
        byte[] fullPayload = ByteUtil.concat(pubKeyHash, checksum);
        // use Base58 encode method to get the encodes byte array
        byte[] address = Base58.encodeBytes(fullPayload);
        // return encoded String as a wallet address
        return new String(address);
    }

    /**
     * Returns if the address is legal
     * @param address
     * @return boolean true/false
     */
    public static boolean validateAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        byte[] fullPayload;
        try {
            fullPayload = Base58.decode(address);
        } catch (Exception e) {
            return false;
        }
        if (fullPayload == null || fullPayload.length < Constant.ADDRESS_CHECKSUM_LENGTH) {
            return false;
        }
        byte[] actualChecksum = ByteUtil.slice(fullPayload, fullPayload.length - Constant.ADDRESS_CHECKSUM_LENGTH
                , Constant.ADDRESS_CHECKSUM_LENGTH);
        // get pubKeyHash data
        byte[] pubKeyHash = ByteUtil.slice(fullPayload, 0, fullPayload.length - Constant.ADDRESS_CHECKSUM_LENGTH);
        // get the checksum of pubKeyHash
        byte[] checksum = HashUtil.getPubKeyHashChecksum(pubKeyHash, Constant.ADDRESS_CHECKSUM_LENGTH);
        return Arrays.equals(actualChecksum, checksum);
    }

    /**
     * Returns if the user address is legal
     * @param userAddress user address
     * @return boolean true/false
     */
    public static boolean validateUserAddress(String userAddress) {
        if (!validateAddress(userAddress)) {
            return false;
        }
        byte[] fullPayload = null;
        try {
            fullPayload = Base58.decode(userAddress);
        } catch (Exception e) {
        }
        byte[] prefix = ByteUtil.slice(fullPayload, 0, 1);
        return Arrays.equals(prefix, HashUtil.VERSION_USER);
    }

    /**
     * Returns if the contract address is legal
     * @param contractAddress contract address
     * @return boolean true/false
     */
    public static boolean validateContractAddress(String contractAddress) {
        if (!validateAddress(contractAddress)) {
            return false;
        }
        byte[] fullPayload = null;
        try {
            fullPayload = Base58.decode(contractAddress);
        } catch (Exception e) {
        }
        byte[] prefix = ByteUtil.slice(fullPayload, 0, 1);
        return Arrays.equals(prefix, HashUtil.VERSION_CONTRACT);
    }
}
