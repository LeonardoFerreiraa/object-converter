package br.com.leonardoferreira;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

public class ObjectConverter {

    private ObjectConverter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static <T> T create(final Class<T> target) {
        final Map<Method, MethodHandler> handlers = Arrays.stream(target.getMethods())
                .map(method ->
                        Pair.of(
                                method,
                                method.isDefault() ? DefaultMethodHandler.from(method) : ConverterMethodHandler.from(method)
                        )
                )
                .collect(Pair.toMap());

        final Object instance = Proxy.newProxyInstance(
                ObjectConverter.class.getClassLoader(),
                new Class[]{target},
                new ObjectConverterInvocationHandler(target, handlers)
        );

        handlers.values()
                .stream()
                .filter(methodHandler -> methodHandler instanceof DefaultMethodHandler)
                .map(DefaultMethodHandler.class::cast)
                .forEach(handler -> handler.bindTo(instance));

        return target.cast(instance);
    }

}
