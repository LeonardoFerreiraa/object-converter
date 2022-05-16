package br.com.leonardoferreira.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.leonardoferreira.domain.Attribute;
import br.com.leonardoferreira.domain.ObjectConverterOptions;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public final class ReflectionUtils {

    private static final String GET_PREFIX = "get";

    private static final String SET_PREFIX = "set";

    private static final String TO_STRING_METHOD_NAME = "toString";

    private static final String EQUALS_METHOD_NAME = "equals";

    private static final String HASH_CODE_METHOD_NAME = "hashCode";

    public ReflectionUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Map<String, Attribute> findAllAttributes(final Class<?> clazz, final ObjectConverterOptions options) {
        return findAllFields(clazz)
                .stream()
                .map(field -> parseAttribute(clazz, field, options))
                .collect(Pair.toMap());
    }

    private static Pair<String, Attribute> parseAttribute(final Class<?> clazz, final Field field, final ObjectConverterOptions options) {
        final Method getter = Try.orNull(() -> clazz.getDeclaredMethod(retrieveGetNameFor(field)));
        final Method setter = Try.orNull(() -> clazz.getDeclaredMethod(retrieveSetNameFor(field), field.getType()));

        return Pair.of(field.getName(), Attribute.from(field, getter, setter, options));
    }

    private static List<Field> findAllFields(final Class<?> clazz) {
        final List<Field> fields = new ArrayList<>();

        if (clazz.getSuperclass() != null) {
            fields.addAll(findAllFields(clazz.getSuperclass()));
        }

        fields.addAll(Arrays.stream(clazz.getDeclaredFields())
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

    public static boolean isBasicType(final Class<?> clazz) {
        return clazz.getPackage() == null ||
                clazz.getPackage().getName().startsWith("java.lang") ||
                clazz.getPackage().getName().startsWith("java.math");
    }

}