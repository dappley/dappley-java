package com.dappley.android.util;

import android.content.Context;
import android.os.Environment;

import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Wallet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.common.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageUtil {

    private static final String FILE_WALLET = "wallet";

    public static void saveWallet(Context context, Wallet wallet) throws IOException {
        List<Wallet> wallets = getWallets(context);
        if (wallets == null) {
            wallets = new ArrayList<>();
        }
        for (Wallet w : wallets) {
            if (w == null) {
                continue;
            }
            if (w.getAddress() != null && w.getAddress().equals(wallet.getAddress())) {
                return;
            }
        }
        wallets.add(wallet);
        saveWallets(context, wallets);
    }

    public static void saveWallets(Context context, List<Wallet> wallets) throws IOException {
        File folder = context.getExternalFilesDir(null);
        File file = new File(folder, FILE_WALLET);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.writeValue(file, wallets);
    }

    public static List<Wallet> getWallets(Context context) throws IOException {
        File folder = context.getExternalFilesDir(null);
        File file = new File(folder, FILE_WALLET);
        if (!file.exists()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Wallet.class);
        List<Wallet> wallets = objectMapper.readValue(file, javaType);
        return wallets;
    }

    public static boolean checkPassword(Context context, String password) {
        if (password == null || password.length() == 0) {
            return false;
        }
        try {
            List<Wallet> wallets = getWallets(context);
            if (wallets == null || wallets.size() == 0) {
                return true;
            }
            Wallet wallet = wallets.get(0);
            if (wallet == null) {
                return true;
            }
            wallet = Dappley.decryptWallet(wallet, password);
            if (wallet.getPrivateKey() != null && wallet.getPrivateKey().compareTo(BigInteger.ZERO) > 0) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void deleteAddress(Context context, String address) {
        if (address == null) {
            return;
        }
        try {
            List<Wallet> wallets = getWallets(context);
            if (wallets == null) {
                wallets = new ArrayList<>();
            }
            Wallet wallet = null;
            for (int i = 0; i < wallets.size(); i++) {
                wallet = wallets.get(i);
                if (wallet == null) {
                    continue;
                }
                if (wallet.getAddress() != null && wallet.getAddress().equals(address)) {
                    break;
                }
            }
            if (wallet != null) {
                wallets.remove(wallet);
                saveWallets(context, wallets);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
