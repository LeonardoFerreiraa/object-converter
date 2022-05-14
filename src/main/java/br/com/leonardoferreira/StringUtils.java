package br.com.leonardoferreira;

class StringUtils {

    public StringUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    static String capitalize(final String str) {
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