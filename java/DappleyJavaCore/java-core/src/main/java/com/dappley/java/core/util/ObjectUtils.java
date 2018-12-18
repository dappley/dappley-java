package com.dappley.java.core.util;

import java.util.Collection;

/**
 * Utils of Object.
 */
public class ObjectUtils {
    /**
     * Returns if the string is empty
     * @param string
     * @return boolean true/false
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * Returns if the string is not empty
     * @param string
     * @return boolean true/false
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * Returns if the collection is empty
     * @param collection
     * @return boolean true/false
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Returns if the collection is not empty
     * @param collection
     * @return boolean true/false
     */
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }
}
