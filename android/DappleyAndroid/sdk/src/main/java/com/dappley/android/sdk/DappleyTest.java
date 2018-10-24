package com.dappley.android.sdk;

import android.content.Context;

import com.dappley.android.sdk.chain.TransactionManager;
import com.dappley.android.sdk.config.Configuration;
import com.dappley.android.sdk.crypto.AesCipher;
import com.dappley.android.sdk.crypto.Bip39;
import com.dappley.android.sdk.crypto.EcCipher;
import com.dappley.android.sdk.crypto.KeyPairTool;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.net.ProtocalProvider;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.protobuf.RpcServiceGrpc;
import com.dappley.android.sdk.util.AddressUtil;
import com.dappley.android.sdk.util.Base64;
import com.google.protobuf.ByteString;
import com.tencent.mmkv.MMKV;

import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DappleyTest {
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = KeyPairTool.newKeyPair();

//        System.out.println(Base64.encode(keyPair.getPrivate().getEncoded()));
//        System.out.println(Base58.encode(keyPair.getPrivate().getEncoded()));
//        System.out.println(Sha3Digest.digest2String(keyPair.getPrivate().getEncoded()));
//        System.out.println(keyPair.getPrivate().getAlgorithm());
//        System.out.println(keyPair.getPrivate().getFormat());
//        System.out.println(keyPair.getPrivate().getEncoded());
//        System.out.println(keyPair.getPrivate().getEncoded().length);

//        System.out.println(keyPair.getPublic());
//        PublicKey publicKey = KeyPairTool.getPublicKey(Base64.encode(keyPair.getPublic().getEncoded()));
//        System.out.println(publicKey);
//        ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
//        System.out.println(ecPrivateKey.getS());
//        PrivateKey privateKey = KeyPairTool.getPrivateKey(Base64.encode(keyPair.getPrivate().getEncoded()));
//        System.out.println(Hex.toHexString(keyPair.getPrivate().getEncoded()));
//        System.out.println(keyPair.getPrivate().getEncoded().length);
//        System.out.println(Hex.toHexString(keyPair.getPublic().getEncoded()));
//        System.out.println(privateKey.getFormat());
//        BigInteger bigInteger = new BigInteger("bb23d2ff19f5b16955e8a24dca34dd520980fe3bddca2b3e1b56663f0ec1aa7e",16);
//        byte[] newPubBytes = ECKey.publicKeyFromPrivate(bigInteger, true);
//        PublicKey publicKey2 = KeyPairTool.getPublicKey(Base64.encode(newPubBytes));
//        System.out.println(publicKey2);
//        System.out.println(keyPair.getPublic());

//        BigInteger privateKey = new BigInteger("bb23d2ff19f5b16955e8a24dca34dd520980fe3bddca2b3e1b56663f0ec1aa7e", 16);
//        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        ECKeyPair ecKeyPair = KeyPairTool.castToEcKeyPair(keyPair);
        System.out.println(ecKeyPair.getPrivateKey().toString(16));
        String address = AddressUtil.createAddress(ecKeyPair);
        System.out.println(address);

//        testRpcConnect(null);
//        DappleyClient.createWalletAddress();

//        BigInteger privateKey = new BigInteger("bb23d2ff19f5b16955e8a24dca34dd520980fe3bddca2b3e1b56663f0ec1aa7e", 16);
//        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
//        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
//        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
//        String test = "this is original";
//        byte[] encoded = EcCipher.encrypt(test.getBytes("UTF-8"), publicKey);
//        System.out.println("test:" + test);
//        System.out.println("encoded:" + encoded);


//        ECParameterSpec ecParameters = ECNamedCurveTable.getParameterSpec("secp256k1");
//        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME);
//        byte[] pubKey = publicKey.toByteArray();
//        BigInteger x = new BigInteger(1, Arrays.copyOfRange(pubKey, 1, 33));
//        BigInteger y = new BigInteger(1, Arrays.copyOfRange(pubKey, 33, 65));
//
//        ECPoint ecPoint = ecParameters.getCurve().createPoint(x, y);
//        ECPublicKeySpec keySpec = new ECPublicKeySpec(ecPoint, ecParameters);
//        BCECPrivateKey prk = (BCECPrivateKey) keyFactory.generatePrivate(keySpec);

//        BlockProto.Block block = BlockManager.newGenesisBlock();
//        System.out.println(block.toString());

//        testRecovery();
//        ByteString bytes = ByteString.copyFromUtf8("hello");
//        System.out.println(bytes.toStringUtf8());


    }

    public static void testTransaction(Context context){
        DappleyClient.init(context, ProtocalProvider.ProviderType.RPC);

        new Thread(new Runnable() {
            @Override
            public void run() {
                BigInteger privateKey = new BigInteger("bb23d2ff19f5b16955e8a24dca34dd520980fe3bddca2b3e1b56663f0ec1aa7e", 16);
                ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
                String fromAddress = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
                String toAddress = "1FZqATrZWdXWi9tsGHZzHzgwJRnpwQoCGi";
                BigInteger amount = BigInteger.valueOf(2);
                Transaction transaction = TransactionManager.newTransaction(fromAddress, toAddress, amount, ecKeyPair);
                System.out.println("transaction string: " + transaction.toString());

//                Assert.assertEquals(transaction.toString(), "");
            }
        }).start();
    }

    public static void testAes(){
        String encoded = AesCipher.encryptToHex("I am the one.", "aesKey");
        System.out.println(encoded);
        String decoded = AesCipher.decryptFromHex(encoded, "aesKey");
        System.out.println(decoded);
        System.out.println(new String(AesCipher.decrypt(AesCipher.encrypt("I am the one.".getBytes(), "aesKey"),"aesKey")));
    }

    public static void testWord() {
        Bip39.generateMnemonic();
    }

    public static void testDB(Context context) {
        MMKV.initialize(context);
        BlockDb blockDB = new BlockDb(context);
        BlockProto.BlockHeader blockHeader = BlockProto.BlockHeader.newBuilder().setHash(ByteString.copyFromUtf8("testblock")).build();
        BlockProto.Block block = BlockProto.Block.newBuilder().setHeader(blockHeader).build();
//        boolean isSuccess = blockDB.saveBytes(block);
//        boolean isSuccess = blockDB.save(block);
        System.out.println(block);
//        System.out.println(isSuccess);

        // read
//        byte[] bytes = blockDB.getBytes("testblock");
//        BlockProto.Block block1 = blockDB.get("testblock");
//        System.out.println(new String(bytes));
//        System.out.println(block1);
    }

    public static void testRecovery() {
        BigInteger privateKey = new BigInteger("bb23d2ff19f5b16955e8a24dca34dd520980fe3bddca2b3e1b56663f0ec1aa7e", 16);
        BigInteger publicKey = new BigInteger("5767188359795479736405662394620174832271978020098784807457600728164221489565479030464547807529865979388276872414794355820379785119919302677391625913455408", 16);
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        System.out.println(ecKeyPair.getPublicKey());
        BCECPublicKey prk = KeyPairTool.fromPubInteger(publicKey);
        BCECPrivateKey prvk = KeyPairTool.fromPrivInteger(privateKey);
        byte[] publicKeyBytes = prk.getQ().getEncoded(false);
        BigInteger publicKeyValue =
                new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
        System.out.println(publicKeyValue);
        System.out.println(prvk);
    }

    public static String testEncrypt() {
        KeyPair keyPair = KeyPairTool.newKeyPair();
//        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
//        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        try {
//            System.out.println(new BigInteger(keyPair.getPrivate().getEncoded()));
            PublicKey publicKey = KeyPairTool.getPublicKey(Base64.encode(keyPair.getPublic().getEncoded()));
            PrivateKey privateKey = KeyPairTool.getPrivateKey(Base64.encode(keyPair.getPrivate().getEncoded()));
            String test = "this is data";
            byte[] encoded = new byte[0];
            encoded = EcCipher.encrypt(test.getBytes("UTF-8"), publicKey);
            String result = "original:" + test;
            byte[] decoded = EcCipher.decrypt(encoded, privateKey);
            result += "\ndecoded:" + new String(decoded, "UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void testRpcConnect(Context context) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(Configuration.getInstance(context).getServerIp(), Configuration.getInstance(context).getServerPort())
                .usePlaintext()
                .build();
        RpcServiceGrpc.RpcServiceBlockingStub stub = RpcServiceGrpc.newBlockingStub(channel);
        RpcProto.GetBalanceRequest request = RpcProto.GetBalanceRequest.newBuilder()
                .setAddress("1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG")
                .build();
        RpcProto.GetBalanceResponse response = stub.rpcGetBalance(request);
        String message = response.getMessage();
        System.out.println(message);
    }
}
