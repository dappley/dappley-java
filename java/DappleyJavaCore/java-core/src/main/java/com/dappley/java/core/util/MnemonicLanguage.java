package com.dappley.java.core.util;

/**
 * Mnemonic language type
 */
public enum MnemonicLanguage {
    /**
     * English
     */
    EN("en-mnemonic-word-list.txt"),
    /**
     * Chinese simplified
     */
    ZH_CN("zh-cn-mnemonic-word-list.txt"),
    /**
     * Chinese traditional
     */
    ZH_TW("zh-tw-mnemonic-word-list.txt");

    /**
     * Dict file name
     */
    private String fileName;

    MnemonicLanguage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
