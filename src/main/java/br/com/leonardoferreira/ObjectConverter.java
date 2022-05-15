package br.com.leonardoferreira;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class ObjectConverter {

    private static final TypeAdapters DEFAULT_TYPE_ADAPTERS = TypeAdapters.from(
            new TypeAdapter<>(String.class, String::valueOf),
            new TypeAdapter<>(Integer.class, it -> Integer.parseInt(String.valueOf(it))),
            new TypeAdapter<>(Long.class, it -> Long.parseLong(String.valueOf(it))),
            new TypeAdapter<>(Double.class, it -> Double.parseDouble(String.valueOf(it))),
            new TypeAdapter<>(Float.class, it -> Float.parseFloat(String.valueOf(it))),
            new TypeAdapter<>(BigDecimal.class, it -> new BigDecimal(String.valueOf(it)))
    );

    private ObjectConverter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static <T> T create(final Class<T> target) {
        final Map<Method, MethodHandler> handlers = Arrays.stream(target.getMethods())
                .map(method ->
                        Pair.of(
                                method,
                                method.isDefault() ? DefaultMethodHandler.from(method) : new ConverterMethodHandler(Converter.converterFor(method, DEFAULT_TYPE_ADAPTERS))
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
