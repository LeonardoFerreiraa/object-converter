package br.com.leonardoferreira.util;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class StringUtils {

    private StringUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static String capitalize(final String str) {
        if (str == null) {
            return null;
        }

        if (str.isEmpty()) {
            return str;
        }

        final char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);

        return new String(chars);
    }

}