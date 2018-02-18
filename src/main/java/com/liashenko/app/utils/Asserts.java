package com.liashenko.app.utils;

public abstract class Asserts {

    public static boolean assertIsNull(Object obj) {
        return obj == null;
    }

    public static boolean assertLongIsNullOrZeroOrLessZero(Long obj) {
        return (obj == null) || (obj <= 0);
    }

    public static boolean assertIntIsNullOrZeroOrLessZero(Integer obj) {
        return (obj == null) || (obj <= 0);
    }

    public static boolean assertIntIsLessThanZero(Integer obj) {
        return (obj == null) || (obj < 0);
    }

    public static boolean assertStringIsEmpty(String str) {
        return str.isEmpty();
    }

    public static boolean assertStringIsNullOrEmpty(String str) {
        return (assertIsNull(str) || assertStringIsEmpty(str));
    }

    public static boolean assertStringSizeIsMore(String str, int size) {
        return assertIsNull(str) || str.length() > size;
    }

    public static boolean assertStringSizeIsLess(String str, int size) {
        return assertIsNull(str) || str.length() < size;
    }

    public static boolean assertArrayIsNullOrEmpty(char[] arr) {
        return assertIsNull(arr) || (arr.length == 0);
    }
}
