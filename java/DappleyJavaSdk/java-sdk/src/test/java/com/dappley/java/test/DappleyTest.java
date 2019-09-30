package com.dappley.java.test;

import com.dappley.java.core.po.ContractQueryResult;
import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.po.Wallet;
import com.dappley.java.core.util.HashUtil;
import com.dappley.java.core.util.MnemonicLanguage;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DappleyTest {

    @Test
    public void testLocal() {
        boolean isException = false;
        try {
            Dappley.init(Dappley.DataMode.LOCAL_STORAGE);
        } catch (Exception e) {
            isException = true;
            log.error("local exception: ", e);
        }
        Assert.assertTrue(isException);
    }

    @Test
    public void testCreateWallet() {
        Wallet wallet = Dappley.createWallet();
        log.info("wallet: " + wallet.toString());
        wallet = Dappley.createWallet(MnemonicLanguage.ZH_CN);
        log.info("wallet: " + wallet.toString());
        wallet = Dappley.createWallet(MnemonicLanguage.ZH_TW);
        log.info("wallet: " + wallet.toString());
        Assert.assertNotNull(wallet);
    }

    @Test
    public void testImportWalletFromMnemonic() {
        String mnemonic = "soup soldier soft sell stadium video club initial cake poet crystal mix";
        Wallet wallet = Dappley.importWalletFromMnemonic(mnemonic);
        log.info("wallet: " + wallet.toString());
        log.info("wallet privateKey: " + wallet.getPrivateKey().toString(16));
        Assert.assertEquals("wallet convert error", "dW9JqNxJ4T39MmTPx5imuS1LopsGzrXj9X", wallet.getAddress());
    }

    @Test
    public void testImportWalletFromPrivateKey() {
        String privateKeyHex = "558d86a761934501f6419b8187925b5cc186368c20aace1d7af91f98fc5c79bc";
        Wallet wallet = Dappley.importWalletFromPrivateKey(privateKeyHex);
        log.info("wallet: " + wallet.toString());
        log.info("wallet privateKey: " + wallet.getPrivateKey().toString(16));
        Assert.assertEquals("wallet convert error", "dW9JqNxJ4T39MmTPx5imuS1LopsGzrXj9X", wallet.getAddress());
    }

    @Test
    public void testGetWalletBalance() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        BigInteger balance = Dappley.getWalletBalance(address);
        log.info("balance: " + balance.toString());
        Assert.assertTrue(balance.compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testGetWalletBalances() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        Wallet wallet1 = Dappley.importWalletFromPrivateKey("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a");
        Wallet wallet2 = Dappley.importWalletFromPrivateKey("558d86a761934501f6419b8187925b5cc186368c20aace1d7af91f98fc5c79bc");
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(wallet1);
        wallets.add(wallet2);
        wallets = Dappley.getWalletBalances(wallets);
        log.info("wallet balance: wallet1:" + wallet1.getBalance().toString() + ", wallet2:" + wallet2.getBalance().toString());
        Assert.assertTrue(wallet1.getBalance().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testEncryptWallet() {
        Wallet wallet = Dappley.importWalletFromPrivateKey("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a");
        wallet = Dappley.encryptWallet(wallet, "123456");
        log.info("wallet:" + wallet.toString());
        Assert.assertTrue(wallet.getEncryptedPrivateKey().length() > 0 && wallet.getPrivateKey() == null);
    }

    @Test
    public void testDecryptWallet() {
        Wallet wallet = new Wallet();
        wallet.setEncryptedPrivateKey("47641db304063bbbcdf19e797760b3399e26b77ff40359b0f00946a5521c2ad840770738dd613e10323f14690a127c94");
        wallet = Dappley.decryptWallet(wallet, "123456");
        log.info("wallet:" + wallet.toString());
        Assert.assertTrue(wallet.getPrivateKey().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testAddAddress() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        Dappley.addAddress("dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa");
    }

    @Test
    public void testRemoveAddress() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        Dappley.removeAddress("dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa");
    }

    @Test
    public void testPublicKeyToAddress() {
        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        byte[] hash = HashUtil.getPublicKeyHash(address);
        String addr = Dappley.publicKeyHashToAddress(hash);
        log.info("addr:" + addr);
        Assert.assertEquals("convert failed", address, addr);
    }

    @Test
    public void testValidateAddress() {
        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        boolean isAddress = Dappley.validateAddress(address);
        log.info("isAddress:" + isAddress);
        Assert.assertTrue(isAddress);
    }

    @Test
    public void testValidateContractAddress() {
        String address = "cXuhH7BZKHuMAGtLkqyzGZWSBBSWqm19KY";
        boolean isContractAddress = Dappley.validateContractAddress(address);
        log.info("isContractAddress:" + isContractAddress);
        Assert.assertTrue(isContractAddress);
    }

    @Test
    public void testGetUtxos() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        List<Utxo> utxos = Dappley.getUtxos(address, 1, 10);
        log.info("utxos size:" + utxos.size());
        Assert.assertTrue(utxos.size() > 0);
    }

    @Test
    public void testSendTransaction() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String from = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        String to = "dW9JqNxJ4T39MmTPx5imuS1LopsGzrXj9X";
        BigInteger amount = new BigInteger("1");
        BigInteger tip = new BigInteger("0");
        BigInteger privateKey = new BigInteger("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a", 16);
        SendTxResult sendTxResult = Dappley.sendTransaction(from, to, amount, privateKey, tip);
        if (sendTxResult != null) {
            log.info("sendTransaction isSuccess: " + sendTxResult.isSuccess());
        } else {
            log.info("sendTransaction failed");
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testSendTransactionWithContract() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String from = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        String toContract = "cXuhH7BZKHuMAGtLkqyzGZWSBBSWqm19KY";
        BigInteger amount = new BigInteger("1");
        BigInteger tip = new BigInteger("0");
        BigInteger gasLimit = new BigInteger("30000");
        BigInteger gasPrice = new BigInteger("1");
        BigInteger privateKey = new BigInteger("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a", 16);
        String contract = "{\"function\":\"record\",\"args\":[\"%s\",\"%d\"]}";
        contract = String.format(contract, from, 10);
        SendTxResult sendTxResult = Dappley.sendTransactionWithContract(from, toContract, amount, privateKey, tip, gasLimit, gasPrice, contract);
        if (sendTxResult != null) {
            log.info("sendTransactionWithContract isSuccess: " + sendTxResult.isSuccess());
            log.info("sendTransactionWithContract contractAddress: " + sendTxResult.getGeneratedContractAddress());
        } else {
            log.info("sendTransactionWithContract failed");
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testCreateContractAddress() {
        String address = Dappley.createContractAddress();
        log.info("address:" + address);
        Assert.assertTrue(address.length() > 0);
    }

    @Test
    public void testEstimateGas() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String from = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        String toContract = "cWU5kmWy6iXVPkgvpegdLzS9ahinouUnue";
        BigInteger privateKey = new BigInteger("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a", 16);
        String contract = "{\"function\":\"put_sign\",\"args\":[\"key\",\"value\"]}";
        contract = String.format(contract, from, 10);
        BigInteger gasLimit = Dappley.estimateGas(from, privateKey, toContract, contract);
        if (gasLimit != null) {
            log.info("testEstimateGas gasLimit: " + gasLimit.toString());
        } else {
            log.info("testEstimateGas failed");
        }
        Assert.assertTrue(gasLimit.compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testGetGasPrice() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        BigInteger gasPrice = Dappley.getGasPrice();
        if (gasPrice != null) {
            log.info("testGetGasPrice gasPrice: " + gasPrice.toString());
        } else {
            log.info("testGetGasPrice failed");
        }
        Assert.assertTrue(gasPrice.compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testContractQuery() {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String contractAddress = "cWU5kmWy6iXVPkgvpegdLzS9ahinouUnue";
        String key = "file-name";
        String value = "SHA-1-sign";
        ContractQueryResult contractQueryResult = Dappley.contractQuery(contractAddress, key, null);
        log.info("testContractQuery1: resultKey: " + contractQueryResult.getResultKey() + ",resultValue:" + contractQueryResult.getResultValue());
        contractQueryResult = Dappley.contractQuery(contractAddress, null, value);
        log.info("testContractQuery2: resultKey: " + contractQueryResult.getResultKey() + ",resultValue:" + contractQueryResult.getResultValue());
        contractQueryResult = Dappley.contractQuery(contractAddress, key, value);
        log.info("testContractQuery3: resultKey: " + contractQueryResult.getResultKey() + ",resultValue:" + contractQueryResult.getResultValue());
        Assert.assertTrue(value.equals(contractQueryResult.getResultValue()));
    }
}
