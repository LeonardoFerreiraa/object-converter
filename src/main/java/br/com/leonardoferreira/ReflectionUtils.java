package br.com.leonardoferreira;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class ReflectionUtils {

    private static final String GET_PREFIX = "get";

    private static final String SET_PREFIX = "set";

    private static final String TO_STRING_METHOD_NAME = "toString";

    private static final String EQUALS_METHOD_NAME = "equals";

    private static final String HASH_CODE_METHOD_NAME = "hashCode";

    public ReflectionUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Map<String, Accessors> findAllFieldsWithAccessors(final Class<?> clazz) {
        return findAllFields(clazz)
                .stream()
                .map(field -> parseAccessor(clazz, field))
                .collect(Pair.toMap());
    }

    private static Pair<String, Accessors> parseAccessor(final Class<?> clazz, final Field field) {
        final Method getter = Try.orNull(() -> clazz.getDeclaredMethod(retrieveGetNameFor(field)));
        final Method setter = Try.orNull(() -> clazz.getDeclaredMethod(retrieveSetNameFor(field), field.getType()));

        return Pair.of(field.getName(), new Accessors(field.getType(), getter, setter));
    }

    private static List<Field> findAllFields(final Class<?> clazz) {
        final List<Field> fields = new ArrayList<>();

        if (clazz.getSuperclass() != null) {
            fields.addAll(findAllFields(clazz.getSuperclass()));
        }

        fields.addAll(Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isFinal(field.getModifiers()))
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList()));

        return fields;
    }

    private static String retrieveGetNameFor(final Field field) {
        return GET_PREFIX + StringUtils.capitalize(field.getName());
    }

    private static String retrieveSetNameFor(final Field field) {
        return SET_PREFIX + StringUtils.capitalize(field.getName());
    }

    public static boolean isToStringMethod(final Method method) {
        return TO_STRING_METHOD_NAME.equals(method.getName()) &&
                method.getParameterCount() == 0;
    }

    public static boolean isEqualsMethod(final Method method) {
        return EQUALS_METHOD_NAME.equals(method.getName()) &&
                method.getParameterCount() == 1 &&
                method.getParameterTypes()[0] == Object.class;
    }

    public static boolean isHashCodeMethod(final Method method) {
        return HASH_CODE_METHOD_NAME.equals(method.getName()) &&
                method.getParameterCount() == 0 &&
                "int".equals(method.getReturnType().getName());
    }

}