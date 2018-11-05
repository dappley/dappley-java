package com.dappley.android.util;

import android.os.Environment;

import com.dappley.android.sdk.po.Wallet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StorageUtil {

    private static final String PATH_APP = "com.dappley.android/";

    private static String PATH_ROOT = Environment.getExternalStorageDirectory().getPath() + "/" + PATH_APP;

    private static final String PATH_APP_WALLET = PATH_ROOT + "wallet/";

    private static String getWalletPath() {
        String path = PATH_APP_WALLET;
        File dir = new File(path);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        return path;
    }

    public static void saveWalletMap(Map<String, byte[]> walletMap) throws IOException {
        File file = new File(getWalletPath() + File.separator + "wallet");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.writeValue(file, walletMap);
    }

    public static Map<String, byte[]> getWalletMap() throws IOException {
        File file = new File(getWalletPath() + File.separator + "wallet");
        if (!file.exists()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        Map<String, byte[]> walletMap = objectMapper.readValue(file, Map.class);
        return walletMap;
    }

    public static void saveAddresses(List<String> addresses) throws IOException {
        File file = new File(getWalletPath() + File.separator + "address");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.writeValue(file, addresses);
    }

    public static List<String> getAddresses() throws IOException {
        File file = new File(getWalletPath() + File.separator + "address");
        if (!file.exists()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        List<String> addressList = objectMapper.readValue(file, List.class);
        return addressList;
    }

    public static void deleteAddress(String address) {
        try {
            List<String> addresses = getAddresses();
            if (addresses != null) {
                addresses.remove(address);
                saveAddresses(addresses);
            }
            Map<String, byte[]> walletMap = getWalletMap();
            if (walletMap != null) {
                walletMap.remove(walletMap);
                saveWalletMap(walletMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
