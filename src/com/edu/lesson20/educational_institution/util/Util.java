package com.edu.lesson20.educational_institution.util;

public class Util {
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static <T> boolean isNullOrEmpty(java.util.Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
} 