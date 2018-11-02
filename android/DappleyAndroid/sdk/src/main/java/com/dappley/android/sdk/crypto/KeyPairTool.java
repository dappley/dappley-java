package com.dappley.android.sdk.crypto;

import com.dappley.android.sdk.util.Base64;

import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.spongycastle.jce.ECNamedCurveTable;
import org.spongycastle.jce.ECPointUtil;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.jce.spec.ECPublicKeySpec;
import org.spongycastle.math.ec.ECPoint;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * This class is used to generate EC KeyPairs and read key bytes from a base64 format string.
 * <p>
 *     The method <code>newKeyPair</code>will generate a new KeyPair by EC algorithm.
 *     If you wanna to read private/public key from disk, use getPublicKey/getPrivateKey.
 * </p>
 */
public class KeyPairTool {
    /**
     * user ec algorithm
     */
    private static final String ALGORITHM = "ECDSA";
    /**
     * standard name secp256k1
     */
    private static final String STD_NAME = "secp256k1";

    static {
        // add BouncyCastleProvider(BC) provider into system security
//        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
//            Security.addProvider(new BouncyCastleProvider());
//        }
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    /**
     * Generate a new KeyPair object with EC algorithm.
     * <p>Usually, it'll get different results due to inner params changed. Remember to save the KeyPair as you need.</p>
     * @return KeyPair A new KeyPair generated with EC algorithm
     */
    public static KeyPair newKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec(STD_NAME);
            keyPairGenerator.initialize(ecGenParameterSpec);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ECKeyPair castToEcKeyPair(KeyPair keyPair) {
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();

        BigInteger privateKeyValue = privateKey.getD();

        // Ethereum does not use encoded public keys like bitcoin - see
        // https://en.bitcoin.it/wiki/Elliptic_Curve_Digital_Signature_Algorithm for details
        // Additionally, as the first bit is a constant prefix (0x04) we ignore this value
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
        BigInteger publicKeyValue =
                new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));

        return new ECKeyPair(privateKeyValue, publicKeyValue);
    }

    /**
     * Returns the PublicKey object from pubKey string in Base64 format.
     * @param pubKey String in Base64 format
     * @return PublicKey converted object
     * @throws NoSuchAlgorithmException If EC is not supported
     * @throws InvalidKeySpecException If key is not valid
     */
    public static PublicKey getPublicKey(String pubKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] pubKeyBytes = Base64.decode(pubKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        return publicKey;
    }

    /**
     * Returns the PrivateKey object from pubKey string in Base64 format.
     * @param privKey String in Base64 format
     * @return PrivateKey converted object
     * @throws NoSuchAlgorithmException If EC is not supported
     * @throws InvalidKeySpecException If key is not valid
     */
    public static PrivateKey getPrivateKey(String privKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privKeyBytes = Base64.decode(privKey);
        PKCS8EncodedKeySpec x509KeySpec = new PKCS8EncodedKeySpec(privKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(x509KeySpec);
        return privateKey;
    }

    public static BCECPublicKey fromPubInteger(BigInteger pubKeyInteger) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            ECParameterSpec ecParameters = ECNamedCurveTable.getParameterSpec(STD_NAME);
            ECNamedCurveSpec params = new ECNamedCurveSpec(STD_NAME, ecParameters.getCurve(), ecParameters.getG(), ecParameters.getN());
            byte[] pubKey = pubKeyInteger.toByteArray();
            BigInteger x = new BigInteger(1, Arrays.copyOfRange(pubKey, 1, 33));
            BigInteger y = new BigInteger(1, Arrays.copyOfRange(pubKey, 33, 65));
            java.security.spec.ECPoint point = ECPointUtil.decodePoint(params.getCurve(), pubKey);
            ECPoint ecPoint = ecParameters.getCurve().createPoint(x, y);
            ECPublicKeySpec keySpec = new ECPublicKeySpec(ecPoint, ecParameters);
            java.security.spec.ECPublicKeySpec publicKeySpec = new java.security.spec.ECPublicKeySpec(point, params);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
//            BCECPublicKey prk = (BCECPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
            BCECPublicKey prk = (BCECPublicKey) keyFactory.generatePublic(publicKeySpec);
            return prk;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BCECPrivateKey fromPrivInteger(BigInteger privKeyInteger) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            ECParameterSpec ecParameters = ECNamedCurveTable.getParameterSpec(STD_NAME);
            ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(privKeyInteger, ecParameters);
            BCECPrivateKey prk = (BCECPrivateKey) keyFactory.generatePublic(privateKeySpec);
            return prk;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigInteger getPublicKeyFromPrivate(BigInteger privateKey) {
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        return keyPair.getPublicKey();
    }
}
