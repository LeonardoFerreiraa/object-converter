package br.com.leonardoferreira;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class ObjectConverter {

    private static final Map<Class<?>, Function<Object, Object>> DEFAULT_TYPE_ADAPTERS = Pair.mapOf(
            Pair.of(String.class, String::valueOf),
            Pair.of(Integer.class, it -> Integer.parseInt(String.valueOf(it))),
            Pair.of(Long.class, it -> Long.parseLong(String.valueOf(it))),
            Pair.of(Double.class, it -> Double.parseDouble(String.valueOf(it))),
            Pair.of(Float.class, it -> Float.parseFloat(String.valueOf(it))),
            Pair.of(BigDecimal.class, it -> new BigDecimal(String.valueOf(it)))
    );

    private ObjectConverter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static <T> T create(final Class<T> target) {
        final Map<Method, MethodHandler> handlers = Arrays.stream(target.getMethods())
                .map(method ->
                        Pair.of(
                                method,
                                method.isDefault() ? DefaultMethodHandler.from(method) : ConverterMethodHandler.from(method, DEFAULT_TYPE_ADAPTERS)
                        )
                )
                .collect(Pair.toMap());

        final Object instance = Proxy.newProxyInstance(
                ObjectConverter.class.getClassLoader(),
                new Class[]{target},
                new ObjectConverterInvocationHandler(target, handlers)
        );

        handlers.forEach((key, value) -> {
            if (value instanceof DefaultMethodHandler) {
                ((DefaultMethodHandler) value).bindTo(instance);
            }
        });

        return target.cast(instance);
    }

}
