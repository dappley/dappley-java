package com.dappley.java.core.crypto;

import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

/**
 * This class is used to generate EC KeyPairs and read key bytes from a base64 format string.
 * <p>
 * The method <code>newKeyPair</code>will generate a new KeyPair by EC algorithm.
 * If you wanna to read private/public key from disk, use getPublicKey/getPrivateKey.
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
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
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

    /**
     * Convert KeyPair info to ECKeyPair format.
     * @param keyPair
     * @return ECKeyPair
     */
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
     * Returns relevant PublicKey with PrivateKey
     * @param privateKey
     * @return BigInteger PublicKey
     */
    public static BigInteger getPublicKeyFromPrivate(BigInteger privateKey) {
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        return keyPair.getPublicKey();
    }
}
